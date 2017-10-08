package system;


import java.util.HashMap;

public class Customer {

	private int securityNumber;
	private Account cheque;
	private Account credit;
	private Account loan;
	private static HashMap<Integer, Customer> customerList= new HashMap<>();

    /**
     * @Description: The constructor is only called by the addCustomer(..) method and it
     *              instantiates a new customer
     * @param securityNumber: unique identifier for a single customer
     */
	private Customer(int securityNumber) 
	{
		this.securityNumber = securityNumber;
		this.cheque = null;
		this.credit = null;
		this.loan = null;
	}


	//////////////////////////////////////////////////////////////////////////////////////////////
    // Getters

    public static HashMap<Integer, Customer> getCustomerList() {
        return customerList;
    }

    //////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * @Description: This method is the only way to create a new customer. A new security number is passed
     *              as a parameter and if the customer is not in the system, it will be added as a new one.
     *              The new customer is then passed in to the list of customers map(customerList).
     * @param securityNumber: unique identifier for a single customer
     * @return a new customer if the security number is not already registered in the system
     */

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

    /**
     * @Description: The 3 types of accounts are instantiated by means of polymorphism and passed
     *              in as arguments, if the account is null it will be assigned to the parameter.
     * @param account: it is either a Chequeing, Credit or Loan type
     * @throws throws IllegalArgument Exception if parameter is null
     */
	public void addAccount(Account account)
	{
	    if(account != null)
	    {
            if (account instanceof Chequeing && this.cheque == null)
                this.cheque = account;
            if (account instanceof Credit && this.credit == null)
                this.credit = account;
            if (account instanceof Loan && this.loan == null)
                this.loan = account;
        }else {
	        throw new IllegalArgumentException();
        }
	}

	//////////////////////////////////////////////////////////////

    /**
     *
     * @return the account types, null or not
     */
    @Override
    public String toString() {
        return (cheque +" " + credit + " " + loan);
    }


}
