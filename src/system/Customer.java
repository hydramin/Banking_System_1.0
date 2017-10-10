package system;


import java.util.HashMap;

public class Customer /*implements customerOperations*/ // 
/**
 * Note: addAccount will be single method, stores as account
 * 		 getChequeing and the likes, cast the Account to the appropriate account before returning
 */

{

	private final int securityNumber; // social security number of the customer, serves as the name of the customer
	private double totalIndebtedness; // the total amount overdrawn at any point in time in all accounts
	private Chequeing chequeing;
	private Credit credit;
	private Loan loan;
//	private Account chequeing;
//	private Account credit;
//	private Account loan;
	private static HashMap<Integer, Customer> customerList= new HashMap<>();
	
	

    /**
     * @Description: The constructor is only called by the addCustomer(..) method and it
     *              instantiates a new customer
	 *
     * @param securityNumber: unique identifier for a single customer
     */
	private Customer(int securityNumber) 
	{
		this.securityNumber = securityNumber;
		this.chequeing = null;
		this.credit = null;
		this.loan = null;
		this.totalIndebtedness = 0;
		System.out.println("Customer Created!");
	}


	//////////////////////////////////////////////////////////////////////////////////////////////
    // Getters

    public static HashMap<Integer, Customer> getCustomerList()
	{
        return customerList;
    }
    
	public Chequeing getChequeing() {
		return (Chequeing) chequeing;
	}
	
	public Credit getCredit() {
		return (Credit) credit;
	}
	
	public Loan getLoan() {
		return (Loan) loan;
	}

    //////////////////////////////////////////////////////////////////////////////////////////////

    /**
	 *
     * @Description: This method is the only way to create a new customer. A new security number is passed
     *              as a parameter and if the customer is not in the system, it will be added as a new one.
     *              The new customer is then passed in to the list of customers map(customerList).
	 *
	 *
     * @param securityNumber: unique identifier for a single customer
	 * @pre
	 * @post
     * @return a new customer if the security number is not already registered in the system
	 *
     */

	public static Customer addCustomer(int securityNumber)
	{
		/**
		 * Why don't we try it like this?
		 * Ans, Amin: Good Idea
		 *
		 */
		
		if (!customerList.containsKey(securityNumber))
		{
			customerList.put(securityNumber, new Customer(securityNumber));
		}
		return customerList.get(securityNumber);
	}
	
	public void addAccount(Chequeing account){
		this.chequeing = account;
	}
	
	public void addAccount(Credit account){
		this.credit = account;
	}
	
	public void addAccount(Loan account){
		this.loan = account;
	}

	/*public void addAccount(Account account) {
		System.out.println("is null?" + account);
		if (!account.equals(null)) {
			
			if (account instanceof Chequeing && this.chequeing == null) // Can't use .equals for comparing a class to null
				this.chequeing = account;
			if (account instanceof Credit && this.chequeing == null)
				this.credit = account;
			if (account instanceof Loan && this.chequeing == null)
				this.loan = account;
		} else {
			throw new IllegalArgumentException();
		}
		System.out.println("Account added \n" + this);
	}*/
	
	
	

	//////////////////////////////////////////////////////////////

    /**
	 *
     * @Description: Return the string instance of this Customer
	 *
     * @return the account types, null or not
	 *
     */

	
	@Override
	public String toString() {
//        return String.format ("%d %s, %s, %s \n",securityNumber, chequeing, credit, loan);		// we should try using String format
			return (securityNumber +" "+ chequeing +" " + credit + " " + loan);
	}


	
}


