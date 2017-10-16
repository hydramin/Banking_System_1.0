package system;

public class Account implements accountOperations {
	private double balance; // the current amount in the account
    private int accountNumber; // identification for an account
    private int SIN;
    private boolean isAccountActive = true; // account active = true, account suspended = false
    private int limit;
    private boolean transferStatus;

    ////////////////////////////// CONSTRUCTOR

    protected Account(int accountNumber){
		this.accountNumber = accountNumber;
		this.transferStatus = true;
	}

	//////////////////////////  GETTERS  //////////////////////////

	/**
     * <dt><b>Description:</b><dd> This method returns the total balance of the account.
     *
	 * @return : returns a double representing the value of the current balance.
	 */
	@Override
	public double getBalance(){
        record("Balance", 0.0, this.balance);
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

    //////////////////////////  SETTERS  //////////////////////////

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
		record("Limit", limit, this.balance);
	}

	public void setSIN(int SIN) {
	    this.SIN = SIN;
    }

    //////////////////////////  OPERATIONS  //////////////////////////

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
			record("Withdrawal", amount,  this.balance);
			System.out.println("withdrawn $" + amount + " from : " + this.accountNumber+ " new balance = " + this.balance);
		} else
			System.out.println("Account is suspended --");
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
            record("Withdrawal", amount,  this.balance);
		} else
			System.out.println("Account is suspended --");
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
		record("Suspend", 0.0, this.balance);
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
        record("Reactivate", 0.0, this.balance);
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
		System.out.println("Previous balance From: "+accountFrom.getBalance()+" To: "+accountTo.getBalance());
		accountFrom.withdrawAmount(amount);
		if(accountFrom.getTransferStatus()){
			accountTo.depositAmount(amount);
            record("Transfer", 0.0, this.balance);
			System.out.println("Transfer Complete! "+amount+"$ was transfered from "+accountFrom.getAccountNumber()+" to "+accountTo.getAccountNumber());
			System.out.println("Current balance From: "+accountFrom.getBalance()+" To: "+accountTo.getBalance());
		}else
			System.out.println("Transfer Unsuccessful.");
	}

    /**
     * <dt><b>Description:</b><dd> The method is to make a record of each transaction of the user.
     *                          Any method that is invoked in this class will record information about
     *                          through the below method. Transaction type, transaction amount, date
     *                          and resulting balance are key records.
     *
     * @param tranType This is a String value representing the type of transaction. eg; deposit, withdrawal, etc.
     * @param amount This is a value of type double representing the transaction amount for deposits,
     *               withdrawals, transfers, and limit.
     * @param balance This is a value of type double representing the balance after the transaction has occurred.
     *
     * <dt><b>Precondition</b><dd> tranType must be a string value and not empty. amount and balance must be real numbers.
     * <dt><b>Precondition</b><dd> a record of type AccountActivity is created with all the above information.
     */
	public void record(String tranType, double amount, double balance){
	    AccountActivity accountActivity = new AccountActivity(this.SIN, this.accountNumber);

	    accountActivity.setTransactionType(tranType);
        accountActivity.setTransactionAmount(amount);
        accountActivity.setBalance(balance);
        accountActivity.addToList();
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
