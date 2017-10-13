package system;

public class Account implements accountOperations
{
	private double balance; // the current amount in the account
    private int accountNumber; // identification for an account
    private boolean isAccountActive = true; // account active = true, account suspended = false
    private double indebtedness; // the amount overdrawn for the particular account
    private int limit;
    private boolean transferStatus;

    
    //////////////////////////////////////////// Constructor
    protected Account(int accountNumber)
	{
		this.balance = 0;
		this.accountNumber = accountNumber;		
		this.indebtedness = 0;
		this.transferStatus = true;
	}
    
	//////////////////////////////////////////////// Getters
	/**
	 * @return : returns the value of the current balance.
	 */
	@Override
	public double getBalance()
	{
		return this.balance;
	}
	
	@Override
	public double getIndebtedness()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	public int getLimit()
	{
		return this.limit;
	}
	
	public boolean getTransferStatus(){
		return this.transferStatus;
	}
	
	public int getAccountNumber() {
		return accountNumber;
	}
	//////////////////////////////////////////////// Setters
	public void setTransferStatus(boolean transferStatus) {
		this.transferStatus = transferStatus;
	}
	
	public void setLimit(int limit)
	{
		this.limit = limit;
	}
	//////////////////////////////////////////////Operations
	/**
	 * @Description : takes in a certain amount and depending on the overdraft option reduces the balance
	 * @param : amount to be withdrawn
	 */
	@Override
	public void withdrawAmount(double amount) /*throws NegativeBalanceException*/
	{
		if (isAccountActive) {			
			this.balance -= amount;
			System.out.println("withdrawn $" + amount + " from : " + this.accountNumber+ " new balance = " + this.balance);
		} else {
			System.out.println("Account is suspended --");
		}
	}

//	public void withdrawAmount(double amount) throws NegativeBalanceException, AccountSuspendedException
//	{
//        /**
//         * Make transaction only if account is still active.
//         * Is the purpose of NegativeBalanceException just for us to know what happened?
//         * cant we just record the negative balance and that be it ?
//         */
//	    if (isAccountActive) {
//            this.balance -= amount;
//            if (this.balance < 0)
//                throw new NegativeBalanceException("Your Balance is now " + this.balance);
//        }
//	    else throw new AccountSuspendedException("This account has been suspended and withdrawal cannot be made");
//	}
	
	/**
	 * @Description : takes in a certain amount and increases the balance by the amount
	 * @param : amount to be deposited
	 * 
	 */
	@Override
	public void depositAmount(double amount)
	{
		if(isAccountActive){
			this.balance += amount;
		}else{
			System.out.println("Account is suspended --");
		}
	}
	/**
	 * @Description : user can only see the balance and account activity; all other activities (withdrawing, depositing) are prohibited
	 */
	@Override
	public void suspendAccount()

	{
		isAccountActive = false;		
	}

	@Override
	public void reactivateAccount()
	{ 
		isAccountActive = true;		
	}
	
	
	@Override
	public void transferAmount(double amount, Account accountFrom, Account accountTo)
	{  // transfers $xXx amount to another account
		System.out.println("Previous balance From: "+accountFrom.getBalance()+" To: "+accountTo.getBalance());
		accountFrom.withdrawAmount(amount);
		if(accountFrom.getTransferStatus()){
			accountTo.depositAmount(amount);
			System.out.println("Transfer Complete! "+amount+"$ was transfered from "+accountFrom.getAccountNumber()+" to "+accountTo.getAccountNumber());
			System.out.println("Current balance From: "+accountFrom.getBalance()+" To: "+accountTo.getBalance());
		}
		System.out.println("Transfer Unsuccessful.");
	}
	

	/////////////////////////////////////////////
	@Override
	public String toString()
	{
		return "Acc #: "+accountNumber +"\n" +
			   "Bal: "+balance +"\n" +
			   "Indebt: " + indebtedness +"\n";
	}

}
