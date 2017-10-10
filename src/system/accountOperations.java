package system;

import java.net.PortUnreachableException;

public interface accountOperations {
	public void withdrawAmount(double amount); // all 
	public void depositAmount(double amount); // all
	public void suspendAccount(); // all
	public void reactivateAccount(); // all
	public double getBalance();	 // all
	public double getIndebtedness(); // all
	public int setLimit(int limit); // credit and checking
	public double transferAmount(double amount, Account account); // all
	
}
