package system;

public class Account implements accountOperations {
	private double balance; // the current amount in the account
    private int accountNumber; // identification for an account
    private boolean isAccountActive = true; // account active = true, account suspended = false
	// private double indebtedness; // the amount overdrawn for the particular account
    private int limit;
    private boolean transferStatus;

    ////////////////////////////// CONSTRUCTOR

    protected Account(int accountNumber){
		this.accountNumber = accountNumber;
		this.transferStatus = true;
	}

	//////////////////////////  GETTERS  //////////////////////////

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

    //////////////////////////  SETTERS  //////////////////////////

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
	}

    //////////////////////////  OPERATIONS  //////////////////////////

	/**
	 * @Description: Takes in a certain amount and depending on the overdraft option reduces the balance.
     *
	 * @param amount value of type double to be withdrawn.
	 */
	@Override
	public void withdrawAmount(double amount) {
		if (isAccountActive) {			
			this.balance -= amount;
			System.out.println("withdrawn $" + amount + " from : " + this.accountNumber+ " new balance = " + this.balance);
		} else
			System.out.println("Account is suspended --");
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
		} else
			System.out.println("Account is suspended --");
	}

	/**
	 * @Description: Changes the users' permission so that the user can only see the balance
     *              and account activity; all other activities (withdrawing, depositing) are prohibited.
     *
	 */
	@Override
	public void suspendAccount() {
		isAccountActive = false;
	}

    /**
     * @Description: Changes the users' permissions so that the user can perform all transactions.
     *
     */
	@Override
	public void reactivateAccount() {
		isAccountActive = true;
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
		System.out.println("Previous balance From: "+accountFrom.getBalance()+" To: "+accountTo.getBalance());
		accountFrom.withdrawAmount(amount);
		if(accountFrom.getTransferStatus()){
			accountTo.depositAmount(amount);
			System.out.println("Transfer Complete! "+amount+"$ was transfered from "+accountFrom.getAccountNumber()+" to "+accountTo.getAccountNumber());
			System.out.println("Current balance From: "+accountFrom.getBalance()+" To: "+accountTo.getBalance());
		}else
			System.out.println("Transfer Unsuccessful.");
	}

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
