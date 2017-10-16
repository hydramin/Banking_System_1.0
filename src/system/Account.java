package system;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public abstract class Account implements accountOperations, Runnable {
	private double balance; // the current amount in the account
    private int accountNumber; // identification for an account
    private int SIN;
    private boolean isAccountActive = true; // account active = true, account suspended = false
    private int limit;
    private boolean transferStatus;
	private static final String WITHDRAW = "Withdrawal";
	private static final String TRANSFER = "Transfer";
	private static boolean isTransfer;
	private static final String DEPOSITE = "Deposite";
	private static final String SUSPEND = "Suspend";
	private static final double NO_TRANSACTION = 0.0;
	protected static final String END_MONTH = "End of Month report";
	protected static final String END_DAY = "End of day report";
	private static String comment;
    private ScheduledExecutorService pay;

    ////////////////////////////// CONSTRUCTOR

    protected Account(int accountNumber){
		this.accountNumber = accountNumber;
		this.transferStatus = true;
		Account.comment = "-";
		isTransfer = false;
//		timeThread();
	}

    ////////////////////////////////////////////////////  GETTERS  ////////////////////////////////////////////////////
    ////////////////////////////////////////////////////  GETTERS  ////////////////////////////////////////////////////
    ////////////////////////////////////////////////////  GETTERS  ////////////////////////////////////////////////////
    ////////////////////////////////////////////////////  GETTERS  ////////////////////////////////////////////////////
    
    public static double getNoTransaction() {
		return NO_TRANSACTION;
	}
    
	/**
     * <dt><b>Description:</b><dd> This method returns the total balance of the account.
     *
	 * @return : returns a double representing the value of the current balance.
	 */
	@Override
	public double getBalance(){
		return this.balance;
	}

    /**
     * <dt><b>Description:</b><dd> This method returns the limit of the account.
     *
     * @return : returns an int representing the limit of the account.
     */
	public int getLimit(){
		return this.limit;
	}

    /**
     * <dt><b>Description:</b><dd> This method returns a transfer status of the account.
     *
     * @return : returns a boolean representing the transfer status of the account.
     */
	public boolean getTransferStatus(){
		return this.transferStatus;
	}

    /**
     * <dt><b>Description:</b><dd> This method returns the number (ID) of the account.
     *
     * @return : returns an int representing the account number.
     */
	public int getAccountNumber(){
		return accountNumber;
	}

    ////////////////////////////////////////////////////  SETTERS  ////////////////////////////////////////////////////
    ////////////////////////////////////////////////////  SETTERS  ////////////////////////////////////////////////////
    ////////////////////////////////////////////////////  SETTERS  ////////////////////////////////////////////////////
    ////////////////////////////////////////////////////  SETTERS  ////////////////////////////////////////////////////

	public static void setComment(String comment) {
		Account.comment = comment;
	}
	
	public static void setTransfer(boolean isTransfer) {
		Account.isTransfer = isTransfer;
	}
    /**
     * <dt><b>Description:</b><dd> This method sets the transfer status of the account.
     *
	 * <dt><b>Precondition:</b><dd> The argument transfer status must be either true or false.
     *
     * <dt><b>Postcondition:</b><dd> The transfer status of the account of the customer will be set.
     */
	public void setTransferStatus(boolean transferStatus) {
		this.transferStatus = transferStatus;
	}

    /**
     * <dt><b>Description:</b><dd> This method sets the limit of the account.
     *
     * <dt><b>Precondition:</b><dd> The argument limit must be an integer value.
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
	 * <dt><b>Description:</b><dd> Takes in a certain amount and depending on the overdraft option reduces the balance.
     *
	 * @param amount value of type double to be withdrawn.
     *
     * <dt><b>Precondition:</b><dd> The argument amount be a real number of type double.
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
	 * <dt><b>Description:</b><dd> Takes in a certain amount and increases the balance by the amount.
     *
	 * @param amount value of type double to be deposited.
     *
     * <dt><b>Precondition:</b><dd> The argument amount must be a real number of type float.
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
	 * <dt><b>Description:</b><dd> Changes the users' permission so that the user can only see the balance
     *              and account activity; all other activities (withdrawing, depositing) are prohibited.
     *
     * <dt><b>Precondition:</b><dd>
     * <dt><b>Precondition</b><dd> The account will be suspended.
	 */
	@Override
	public void suspendAccount() {
		isAccountActive = false;
		this.record("-", NO_TRANSACTION, "Account Suspension Active.");
	}

    /**
     * <dt><b>Description:</b><dd> Changes the users' permissions so that the user can perform all transactions.
     *
     * <dt><b>Precondition</b><dd>
     * <dt><b>Precondition</b><dd>  The account will be reactivated.
     */
	@Override
	public void reactivateAccount() {
		isAccountActive = true;
		this.record("-", NO_TRANSACTION, "Account Reactivated");
	}

    /**
     * <dt><b>Description:</b><dd> Transfers a specified amount from specified account to another account.
     *
     * @param amount double representing specified amount.
     * @param accountFrom specified account to transfer from, of type Account.
     * @param accountTo  account to transfer to, of type Account.
     *
     * <dt><b>Precondition:</b><dd> The arguments must be a real number of type double, an account of type
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
     * <dt><b>Description:</b><dd> The method is to make a record of each transaction of the user.
     *                          Any method that is invoked in this class will record information about
     *                          through the below method. Transaction type, transaction amount, date
     *                          and resulting balance are key records.
     *
     * @param tranType This is a String value representing the type of transaction. eg; deposit, withdrawal, etc.
     * @param tranAmount This is a value of type double representing the transaction amount for deposits,
     *               withdrawals, transfers, and limit.
     * @param comment This is a value of type string representing the balance after the transaction has occurred.
     *
     * <dt><b>Precondition</b><dd> tranType must be a string value and not empty. amount and balance must be real numbers.
     * <dt><b>Precondition</b><dd> a record of type AccountActivity is created with all the above information.
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

	private void timeThread() {
		System.out.println("Class thread called.>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> " + this.getAccountNumber());
		this.pay = Executors.newSingleThreadScheduledExecutor();
		this.pay.scheduleAtFixedRate(this, 0, 1, TimeUnit.SECONDS); // checks
																	// every 5
																	// seconds
	}

	protected abstract void log20Seconds();

	protected abstract void log60Seconds();

	static protected long time() {
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
     * @return
     */
	@Override
	public String toString() {
		return "Acc #: "+accountNumber +"\n" +
			   "Bal: "+balance +"\n";			   
	}
	
}
