package system;


public interface accountOperations
{
	public void withdrawAmount(double amount) /*throws  NegativeBalanceException*/; // all
	public void depositAmount(double amount); // all
	public void suspendAccount(); // all
	public void reactivateAccount(); // all
	public double getBalance();	 // all	
	public void setLimit(int limit); // credit and checking
	public void transferAmount(double amount, Account accountFrom, Account accountTo); // all
	
}
