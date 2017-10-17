package system;

import java.util.HashMap;
import java.util.Random;

/**
 * @author: Amin Adam
 * @author: Sohrab Oryakhel
 * 
 */
public class Customer {
	private final int securityNumber;
	private double totalIndebtedness;
	private Chequeing chequeing;
	private Credit credit;
	private Loan loan;
	private static HashMap<Integer, Customer> customerList = new HashMap<>();

    ////////////////////////////// CONSTRUCTOR
/**
 * @Description:  Constructor for customer class. It creates a customer instance with null Bank accounts
 * @param securityNumber
 * @Precondition: security number (SIN) must be integer between 100000 and 999999
 * @Postcondition: instantiates a new unique Custumer object 
 */
	 Customer(int securityNumber) {
		this.securityNumber = securityNumber;
		this.chequeing = null;
		this.credit = null;
		this.loan = null;
		this.totalIndebtedness = 0;
//		System.out.println("Customer Created!");
	}

    //////////////////////////  GETTERS  //////////////////////////

    /**
     * @Description: This method returns the HasMap containing the customers.
     * 
     * @return Object of type HasMap mapping securityNumber with Customer.
     */
    public static HashMap<Integer, Customer> getCustomerList() {
        return customerList;
    }

    /**
     * @Description
     *     This method returns the chequeing account of a customer.
     *
     * @return  account of type Chequeing.
     */
	public Chequeing getChequeing() {
		return chequeing;
	}

    /**
     * @Description
     *     This method returns the credit account of a customer.
     *
     * @return  account of type Credit.
     */
	public Credit getCredit() {
		return credit;
	}

    /**
     * @Description
     *     This method returns the Demand loan account of a customer.
     *
     * @return  account of type Loan.
     */
	public Loan getLoan() {
		return loan;
	}

    /**
     * @Description
     *     This method returns the total indebtedness of the customer,
     *     which is a sum of indebtedness across all accounts.
     *
     * @return  double value containing total indebtedness.
     */
    public double getTotalIndebtedness() {
        return totalIndebtedness;
    }
    
    public int getSIN() {
		return securityNumber;
	}

    //////////////////////////  OPERATIONS  //////////////////////////

    /**
     * @Description This method is the only way to create a new customer. A new security number is passed
     *              as a parameter and if the customer is not in the system, it will be added as a new one.
     *              The new customer is then passed in to the list of customers map(customerList).
     *
     * @param securityNumber: unique identifier for a single customer
	 *
	 * @Precondition: securityNumber bust be a positive integer argument.
     *
     * @Postcondition: A customer will be returned; newly created or pre-existing from the list.
     *
     * @return A new customer if the security number is not already registered in the system
     */
	public static Customer addCustomer(int securityNumber) {
		if (!customerList.containsKey(securityNumber))
			customerList.put(securityNumber, new Customer(securityNumber));
		return customerList.get(securityNumber);
	}

    /**
     * @Description This method sets the chequeing account of the customer.
     *
     * @param account new account of type Chequeing.
     *
     * @Precondition: The parameter account must be or type Chequeing and must not be null.
     *
     * <dt><b>Postcondition</b><dd> The account will be set as a chequeing account of the customer and passes the Customer's SIN to it.
     */
	public void addAccount(Chequeing account){
		this.chequeing = account;
		this.chequeing.setSIN(this.securityNumber);
	}

    /**
     * @Description This method sets the credit account of the customer.
     *
     * @param account new account of type Credit.
     *
     * @Precondition: The parameter account must be of type Credit and must not be null.
     *
     * @Precondition: The account will be set as the credit account of the customer and passes the Customer's SIN to it..
     *
     */
	public void addAccount(Credit account){
		this.credit = account;
		this.credit.setSIN(this.securityNumber);
	}

    /**
     * @Description This method sets the Demand loan account of the customer.
     *
     * @param account new account of type Loan.
     *
     * @Precondition: The parameter account must be of type Loan and must not be null.
     *
     * @Precondition: The account will be set as the Demand loan account of the customer  and passes the Customer's SIN to it..
     */
	public void addAccount(Loan account){
		this.loan = account;
		this.loan.setSIN(this.securityNumber);
	}

    /**
     * @Description This method is a means of creating a Demand loan account.
     *              It creates an account of type Loan and add it to the loan account
     *              list with a unique random account number. The total indebtedness
     *              of the account is then transferred over to the Demand loan account.
     * @Precondition: The customer's indebtedness must be positive.
     *
     * @Precondition: It creates a Loan account or deposits new indebtedness into  the loan account upon termination of an account with debt          
     *
     */
	private void loanAccCreator() {
		if (this.loan == null)
			this.loan = Loan.addAccount(randomAccountNumGenerator());
        this.loan.setSIN(this.getSIN());
        this.loan.depositAmount(totalIndebtedness);
        this.totalIndebtedness = 0;
	}

    /**
     * @Description This method returns a three digit random number between 100 to 999.
     *              This number is used as the account number to create a unique Demand loan account.
     *
     * @return  random number of type int.
     */
	private int randomAccountNumGenerator(){
		return new Random().nextInt(999) + 100;
	}

    /**
     * @Description This method terminates a specified account.
     *              Before termination, this method ensures to take all the
     *              indebtedness of a specified account and add to total indebtedness.
     *
     * @param account is an int representing the type of account to be terminated.
     *
     *  @Precondition: The argument account must be a positive integer value.
     *
     *  @Postcondition: The specified account will be terminated.
     */
	public void terminateAccount(int account){
		switch (account) {
		case 1:
			
			this.chequeing.getPay().shutdown(); //	Shuts down ScheduleExecutionService
			this.totalIndebtedness = this.getChequeing().indebtednessCalc(); // indebtedness from terminating chequeing account
			Chequeing.getAccountList().remove(this.getChequeing().getAccountNumber());	// create loan account and transfer the indebtedness.
			this.chequeing = null;
			System.out.println("Chequeing account Deleted");
			
			break;
		case 2:
			
			this.totalIndebtedness = this.getCredit().indebtednessCalc(); // indebtedness from terminating credit account
				this.credit.getPay().shutdown();
			Credit.getAccountList().remove(this.getCredit().getAccountNumber());
			this.credit = null;				
			System.out.println("Credit account Deleted");
			
			break;
	
		default:
			System.out.println("No account terminated! Try again");
			break;
		}		
		System.gc();
		loanAccCreator();
	}

    /**
     * @Description Return the string representation of the customer object
     *
     * @return the account types, null or not
     */
	@Override
	public String toString() {
        return String.format ("SIN number:>>>>> %d \n\n"
        		+ "Chequeing Account:>>>>> \n%s\n"
        		+ "Credit Account:>>>>> \n%s\n"
        		+ "Loan Account:>>>>>\n%s",securityNumber, this.chequeing, this.credit, this.loan);
	}
	
}