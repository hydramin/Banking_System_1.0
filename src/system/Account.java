package system;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public abstract class Account implements accountOperations, Runnable {
	private double balance; // the current amount in the account
    private int accountNumber; // identification for an account
    private int SIN;
    private boolean isAccountActive = true; // account active = true, account suspended = false
	// private double indebtedness; // the amount overdrawn for the particular account
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
     * @Description: This method returns the total balance of the account.
     *
	 * @return : returns a double representing the value of the current balance.
	 */
	@Override
	public double getBalance(){
		return this.balance;
	}

    /**
     * @Description: This method returns the limit of the account.
     *
     * @return : returns an int representing the limit of the account.
     */
	public int getLimit(){
		return this.limit;
	}

    /**
     * @Description: This method returns a transfer status of the account.
     *
     * @return : returns a boolean representing the transfer status of the account.
     */
	public boolean getTransferStatus(){
		return this.transferStatus;
	}

    /**
     * @Description: This method returns the number (ID) of the account.
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
     * @Description: This method sets the transfer status of the account.
     *
     */
	public void setTransferStatus(boolean transferStatus) {
		this.transferStatus = transferStatus;
	}

    /**
     * @Description: This method sets the limit of the account.
     *
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
	 * @Description: Takes in a certain amount and depending on the overdraft option reduces the balance.
     *
	 * @param amount value of type double to be withdrawn.
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
	 * @Description: Takes in a certain amount and increases the balance by the amount.
     *
	 * @param amount value of type double to be deposited.
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
	 * @Description: Changes the users' permission so that the user can only see the balance
     *              and account activity; all other activities (withdrawing, depositing) are prohibited.
     *
	 */
	@Override
	public void suspendAccount() {
		isAccountActive = false;
		this.record("-", NO_TRANSACTION, "Account Suspension Active.");
	}

    /**
     * @Description: Changes the users' permissions so that the user can perform all transactions.
     *
     */
	@Override
	public void reactivateAccount() {
		isAccountActive = true;
		this.record("-", NO_TRANSACTION, "Account Reactivated");
	}

    /**
     * @Description: Transfers a specified amount from specified account to another account.
     *
     * @param amount double representing specified amount.
     * @param accountFrom specified account to transfer from, of type Account.
     * @param accountTo  account to transfer to, of type Account.
     */
	@Override
	public void transferAmount(double amount, Account accountFrom, Account accountTo) {
//		System.out.println("Previous balance From: "+accountFrom.getBalance()+" To: "+accountTo.getBalance());
		Account.setTransfer(true);
		accountFrom.withdrawAmount(amount);		
		if(accountFrom.getTransferStatus()){
			accountTo.depositAmount(amount);
			accountFrom.record(TRANSFER, amount, "Transfered to account "+accountTo.getAccountNumber());
			accountTo.record(TRANSFER, amount, "Transfer recieved from account number "+accountFrom.getAccountNumber());			
		}else			
			this.record(TRANSFER, NO_TRANSACTION, "Transfer Unsuccessful.");
	}

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
