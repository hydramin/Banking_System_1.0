package system;

public class Account implements accountOperations{
	private double balance;
	private double withdrawAmount;
	private double dipositAmount;
	//////////////////////////////////////////////
	// Getters
	@Override
	public int withdrawAmount() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public int depositAmount() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public double getBalance() {
		// TODO Auto-generated method stub
		return 0;
	}
	//////////////////////////////////////////////
	//Operations
	@Override
	public void suspendAccount() {  // user can only see the balance and account actitity, 
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
		return "dee";
	}
}
