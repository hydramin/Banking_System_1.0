package system;

import java.util.TreeMap;

public class Customer {

	private int securityNumber;
	private Chequeing cheque;
	private Credit credit;
	private Loan loan;
	private static TreeMap<Integer, Customer> customerList= new TreeMap<Integer, Customer>(); 
	
	private Customer(int securityNumber) 
	{
		this.securityNumber = securityNumber;
		this.cheque = null;
		this.credit = null;
		this.loan = null;
	}
	
	//////////////////////////////////////////////////////////////
	// Getters
	public static TreeMap<Integer, Customer> getCustomerList() {
		return customerList;
	}
	//////////////////////////////////////////////////////////////
		
	public static Customer addCustomer(int securityNumber)
	{
		int key = securityNumber;
		Customer c = Customer.customerList.get(key);
		if (c == null)
		{
			c = new Customer(securityNumber);
			Customer.customerList.put(key, c);
		}
		return c;
	}
	
	public void addAccount(Account account)
	{
		if (account instanceof Chequeing)
			;
		if (account instanceof Credit);
		if (account instanceof Loan);
	}
	//////////////////////////////////////////////////////////////

}
