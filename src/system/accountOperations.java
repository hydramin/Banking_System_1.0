package system;

public interface accountOperations {
	public int withdrawAmount(); // all 
	public int depositAmount(); // all
	public void suspendAccount(); // all
	public void reactivateAccount(); // all
	public double getBalance();	 // all
	public double transferAmount(double amount, Account account); // all
	double transferAmount();
	
}
