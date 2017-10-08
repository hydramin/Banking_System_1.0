package system;

public interface accountOperations {
	public void withdrawAmount(double amount); // all 
	public void depositAmount(double amount); // all
	public void suspendAccount(); // all
	public void reactivateAccount(); // all
	public double getBalance();	 // all
	public double transferAmount(double amount, Account account); // all
	
}
