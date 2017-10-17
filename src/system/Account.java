package system;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 
 * @author: Amin Adam
 * @author: Sorab O
 * @invariant: 100 >= accountNumber >= 999
 * @invariant: 100000 >=SIN >= 999999
 * @invariant: limit > 0
 * @invariant: balance is a floating point number  		
 *
 */
public abstract class Account implements accountOperations, Runnable {
	 double balance; // the current amount in the account
     int accountNumber; // identification for an account
     int SIN;
     boolean isAccountActive; // account active = true, account suspended = false
     int limit;
     boolean transferStatus;
	 static final String WITHDRAW = "Withdrawal";
	 static final String TRANSFER = "Transfer";
	 static boolean isTransfer; // becomes true when the transfer function is used, it prevents duplicate withdrawal records
	 static final String DEPOSITE = "Deposite";
	 static final String SUSPEND = "Suspend";
	 static final double NO_TRANSACTION = 0.0;
	 static final String END_MONTH = "End of Month report";
	 static final String END_DAY = "End of day report";
	 static String comment;
     ScheduledExecutorService pay;		// the scheduled executor runs the thread every second to allow the accounts to monitor time

    ////////////////////////////// CONSTRUCTOR
     /**
      * @Description Super constructor for chequeing credit and loan account types
      *
 	  * @Precondition: Account number or int type that is between 100 and 999
      *
      * <dt><b>Postcondition:</b><dd> Creates an account instance.
      */
     Account(int accountNumber){
		this.accountNumber = accountNumber;
		this.transferStatus = true;
		this.isAccountActive = true;
		Account.comment = "-";
		isTransfer = false;
		timeThread();
	}

    /////\/\////////////////////////////////////////////  GETTERS  ////////////////////////////////////////////////////
    ////\/\/\///////////////////////////////////////////  GETTERS  ////////////////////////////////////////////////////
    ///\/==\/\//////////////////////////////////////////  GETTERS  ////////////////////////////////////////////////////
    //\/////\/\/////////////////////////////////////////  GETTERS  ////////////////////////////////////////////////////
    
    public static double getNoTransaction() {
		return NO_TRANSACTION;
	}
    
	/**
     * @Description This method returns the total balance of the account.
     *
	 * @return : returns a double representing the value of the current balance.
	 */
	@Override
	public double getBalance(){
		return this.balance;
	}

    /**
     * @Description This method returns the limit of the account.
     *
     * @return : returns an int representing the limit of the account.
     */
	public int getLimit(){
		return this.limit;
	}

    /**
     * @Description This method returns a transfer status of the account.
     *
     * @return : returns a boolean representing the transfer status of the account.
     */
	public boolean getTransferStatus(){
		return this.transferStatus;
	}

    /**
     * @Description This method returns the number (ID) of the account.
     *
     * @return : returns an int representing the account number.
     */
	public int getAccountNumber(){
		return accountNumber;
	}
	
	/**
     * @Description An executor service that runns the thread of an account
     *
     * @return : returns the thread of the current account
     */
	public ScheduledExecutorService getPay() {
		return pay;
	}

    ////////////////////////////////////////////////////  SETTERS  ////////////////////////////////////////////////////
    ////////////////////////////////////////////////////  SETTERS  ////////////////////////////////////////////////////
    ////////////////////////////////////////////////////  SETTERS  ////////////////////////////////////////////////////
    ////////////////////////////////////////////////////  SETTERS  ////////////////////////////////////////////////////

	
	/**
     * @Description This method sets the comments for the AccountActivity class before it logs an activity
     *
     *  @Precondition: argument must be any string
     *
     * <dt><b>Postcondition:</b><dd> Sets Account.comment to the parameter's value
     */
	public static void setComment(String comment) {
		Account.comment = comment;
	}
	
	/**
     * @Description This method sets the Account.isTransfer field to the value of the parameter
     *
	 * @Precondition: parameter must be boolean type
     *
     * <dt><b>Postcondition:</b><dd> It sets Account.isTransfer to the parameter's value
     * 
     * <dt><b>@param:</b><dd> It sets Account.isTransfer to the parameter's value
     */
	public static void setTransfer(boolean isTransfer) {
		Account.isTransfer = isTransfer;
	}
    /**
     * @Description This method sets the transfer status of the account.
     *
	 * @Precondition: The argument transfer status must be either true or false.
     *
     * <dt><b>Postcondition:</b><dd> The transfer status of the account of the customer will be set.
     */
	public void setTransferStatus(boolean transferStatus) {
		this.transferStatus = transferStatus;
	}

    /**
     * @Description This method sets the limit of the account.
     *
     * @Precondition: The argument limit must be an integer value.
     * <dt><b>Postcondition:</b><dd> The limit of the account of the customer will be set.
     */
	public void setLimit(int limit) {
		this.limit = limit;
        this.record("-", NO_TRANSACTION, comment);
        Account.setComment("-");
	}

	public void setSIN(int SIN) {
		this.SIN = SIN;
	}

    ////////////////////////////////////////////////////  OPERATIONS  ////////////////////////////////////////////////////
    ////////////////////////////////////////////////////  OPERATIONS  ////////////////////////////////////////////////////
    ////////////////////////////////////////////////////  OPERATIONS  ////////////////////////////////////////////////////
    ////////////////////////////////////////////////////  OPERATIONS  ////////////////////////////////////////////////////

	/**
	 * @Description Takes in a certain amount and depending on the overdraft option reduces the balance.
     *
	 * @param amount value of type double to be withdrawn.
     *
     * @Precondition: The argument amount be a non-negative real number of type double.
     * <dt><b>Postcondition:</b><dd> The specified amount will be withdrawn if account is active.
	 */
	@Override
	public void withdrawAmount(double amount) {
		if (isAccountActive) {			
			this.balance -= amount;
			if(!isTransfer)
				this.record(WITHDRAW, amount, comment);
			Account.setComment("-");
			Account.setTransfer(false);
		} else{			
			this.record("-", NO_TRANSACTION, "Account is Suspended.");
			Account.setTransfer(false);
		}
	}

	/**
	 * @Description Takes in a certain amount and increases the balance by the amount.
     *
	 * @param amount value of type double to be deposited.
     *
     * @Precondition: The argument amount must be a real number of type float.
     * <dt><b>Postcondition:</b><dd> The specified amount will be deposited if the account is active.
	 */
	@Override
	public void depositAmount(double amount) {
		if (isAccountActive) {
			this.balance += amount;
			if(!isTransfer)
				this.record(DEPOSITE, amount, comment);
			Account.setComment("-");
			Account.setTransfer(false);
		} else
			this.record("-", NO_TRANSACTION, "Account is Suspended.");
	}

	/**
	 * @Description Changes the users' permission so that the user can only see the balance
     *              and account activity; all other activities (withdrawing, depositing) are prohibited.
     *
     * @Precondition:
     * @Precondition: The account will be suspended.
	 */
	@Override
	public void suspendAccount() {
		isAccountActive = false;
		this.record("-", NO_TRANSACTION, "Account Suspension Active.");
	}

    /**
     * @Description Changes the users' permissions so that the user can perform all transactions.
     *
     * @Precondition:
     * @Precondition:  The account will be reactivated.
     */
	@Override
	public void reactivateAccount() {
		isAccountActive = true;
		this.record("-", NO_TRANSACTION, "Account Reactivated");
	}

    /**
     * @Description Transfers a specified amount from specified account to another account.
     *
     * @param amount double representing specified amount.
     * @param accountFrom specified account to transfer from, of type Account.
     * @param accountTo  account to transfer to, of type Account.
     *
     * @Precondition: The arguments must be a real number of type double, an account of type
     *                   Account and not null and another Account also not null.
     * <dt><b>Postcondition:</b><dd> The specified amount will be transferred from the specified account to the other
     *                   if the transfer status is true.
     */
	@Override
	public void transferAmount(double amount, Account accountFrom, Account accountTo) {
		Account.setTransfer(true);
		accountFrom.withdrawAmount(amount);		
		if(accountFrom.getTransferStatus()){
			accountTo.depositAmount(amount);
            accountFrom.record(TRANSFER, amount, "Transfered to account "+accountTo.getAccountNumber());
            accountTo.record(TRANSFER, amount, "Transfer recieved from account number "+accountFrom.getAccountNumber());
        } else
            this.record(TRANSFER, NO_TRANSACTION, "Transfer Unsuccessful.");
        System.out.println("Transfer Complete! "+amount+"$ was transfered from "+accountFrom.getAccountNumber()+" to "+accountTo.getAccountNumber());
        System.out.println("Current balance From: "+accountFrom.getBalance()+" To: "+accountTo.getBalance());
	}

    /**
     * @Description The method is to make a record of each transaction of the user.
     *                          Any method that is invoked in this class will record information about
     *                          through the below method. Transaction type, transaction amount, date
     *                          and resulting balance are key records.
     *
     * @param tranType This is a String value representing the type of transaction. eg; deposit, withdrawal, etc.
     * @param tranAmount This is a value of type double representing the transaction amount for deposits,
     *               withdrawals, transfers, and limit.
     * @param comment This is a value of type string representing the balance after the transaction has occurred.
     *
     * @Precondition: tranType must be a string value and not empty. amount and balance must be real numbers.
     * @Postcondition: a record of type AccountActivity is created with all the above information.
     */

	public void record(String tranType, double tranAmount, String comment){
	    AccountActivity accountActivity = new AccountActivity(this.SIN, this.accountNumber, this.balance);

	    accountActivity.setComment(comment);
	    accountActivity.setTransactionType(tranType);
        accountActivity.setTransactionAmount(tranAmount);
        accountActivity.addToList();
    }
	
	//><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><> THREAD FUNCTIONS
	//><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><> THREAD FUNCTIONS
	//><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><> THREAD FUNCTIONS
	//><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><> THREAD FUNCTIONS	

	@Override
	public void run() {
		log20Seconds(); // for daily deductions and logging
		log60Seconds(); // for monthly deductions and logging
	}

	/**
	 * @Description: starts the thread for this class instance
	 * @Precondition: no thread has been started before in this class
	 * @Postcondition: a new thread will be started
	 */
	 void timeThread() {
		System.out.println("Class thread called.>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> " + this.getAccountNumber());
		this.pay = Executors.newSingleThreadScheduledExecutor();
		this.pay.scheduleAtFixedRate(this, 0, 1, TimeUnit.SECONDS); // checks
																	// every 5
																	// seconds
	}

	 abstract void log20Seconds();

	 abstract void log60Seconds();

	 /**
	  * 
	  * @return the current value of the seconds from the epoch
	  */
	static  long time() {
		long timeMillis = System.currentTimeMillis();
		long timeSeconds = TimeUnit.MILLISECONDS.toSeconds(timeMillis);
		return timeSeconds;
	}

		
	//><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><> END END END END
	//><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><> END END END END
	//><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><> END END END END
	//><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><> END END END END

    /**
     *
     * @return the account number and the balance of the account
     */
	@Override
	public String toString() {
		return "Acc #: "+accountNumber +"\n" +
			   "Bal: "+balance +"\n";			   
	}
	
}
