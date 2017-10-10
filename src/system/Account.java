package system;

public class Account implements accountOperations {
	private double balance; // the current amount in the account
    private int accountNumber; // identification for an account
    private boolean isAccountActive = true; // account active = true, account suspended = false
    private double indebtedness; // the amount overdrawn for the particular account
    
    //////////////////////////////////////////// Constructor
    protected Account(int accountNumber) {
		this.balance = 0;
		this.accountNumber = accountNumber;		
		this.indebtedness = 0;
	}
    
	//////////////////////////////////////////////// Getters
	/**
	 * @return : returns the value of the current balance.
	 */
	@Override
	public double getBalance() {
		return this.balance;
	}
	
	@Override
	public double getIndebtedness() {
		// TODO Auto-generated method stub
		return 0;
	}
	////////////////////////////////////////////////Operations
	/**
	 * @Description : takes in a certain amount and depending on the overdraft option reduces the balance
	 * @param : amount to be withdrawn
	 */
	@Override
	public void withdrawAmount(double amount) {
		this.balance -= amount;
	}
	
	/**
	 * @Description : takes in a certain amount and increases the balance by the amount
	 * @param : amount to be deposited
	 * 
	 */
	@Override
	public void depositAmount(double amount) {
		this.balance += amount;
	}
	/**
	 * @Description : user can only see the balance and account activity; all other activities (withdrawing, depositing) are prohibited
	 */
	@Override
	public void suspendAccount() {  // isAccountActive = false
		// TODO Auto-generated method stub
		
	}
	@Override
	public void reactivateAccount() { // all functionality is restored to the account
		// TODO Auto-generated method stub
		
	}	


	@Override
	public double transferAmount(double amount, Account account) {  // transfers $xXx amount to another account
		// TODO Auto-generated method stub
		return 0;
	}
	/////////////////////////////////////////////
	@Override
	public String toString() {
		return "Acc #: "+accountNumber +"\n" +
				"Bal: "+balance +"\n" +
				"Indebt: " + indebtedness +"\n";
	}

	

}
