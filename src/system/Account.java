package system;

public class Account implements accountOperations {
	private double balance; // the current amount in the account
    private int accountNumber; // identification for an account
    private  double withdrawAmount;
    private double depositAmount;
    
    //////////////////////////////////////////// Constructor
    public Account() {
		this.balance = 0;
		this.accountNumber = 0;
		this.withdrawAmount = 0;
		this.depositAmount = 0;		
	}
    
	//////////////////////////////////////////////
	// Getters
	@Override
	public void withdrawAmount(double amount) {
		this.balance -= amount;
		
		if (this.balance < 0); // trigger the overdraft or NFS fee blah blah ...
	}
	@Override
	public void depositAmount(double amount) {
		this.balance += amount;
	}
	@Override
	public double getBalance() {
		// TODO Auto-generated method stub
		return 0;
	}
	//////////////////////////////////////////////
	//Operations
	@Override
	public void suspendAccount() {  // user can only see the balance and account activity, 
									// all other activities (withdrawing, depositing) are prohibited
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
		return "Acc #: "+accountNumber + " Bal: "+balance;
	}

	

}
