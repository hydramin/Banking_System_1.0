package system;

public interface accountOperations {
	public int withdrawAmount();
	public int depositAmount();
//	public void createAccount(); A method for the Customer Class
//	public void cancleAccount(); A method for the Customer Class
	public void suspendAccount();
	public void reactivateAccount();
	public double getBalance();	
//	public void terminateAccount(); for Customer
	public void setOverdraftOption();
	public void setLimit();
	public double transferAmount();
	
}
