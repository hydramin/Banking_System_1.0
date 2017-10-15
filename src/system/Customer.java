package system;

import java.util.HashMap;
import java.util.Random;

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
	private int loanAccNum;
	private static HashMap<Integer, Customer> customerList= new HashMap<>();
	
	

    /**
     * @Description: The constructor is only called by the addCustomer(..) method and it
     *               instantiates a new customer
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
		loanAccNum = 0;
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

    /////////////////////////////////////////////////////////////////////////////////// Operations

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
	
	public double getTotalIndebtedness() {
		return totalIndebtedness;
	}
	
	private void loanAccCreator(){
		if(this.loan == null){
			int num = randomAccountNumGenerator();
			this.loan = Loan.addAccount(num);			
		}		
		this.loan.depositAmount(totalIndebtedness);
		this.totalIndebtedness = 0;
	}
	
	private int randomAccountNumGenerator(){
		// generates random 3 digit account number for the loan account
		Random random = new Random();
		int number = random.nextInt(999) + 100;
		return number;
	}
	
	public void terminateAccount(int account){
		
		switch (account) {
		case 1:			
				
			if(this.chequeing != null){
				this.chequeing.getPay().shutdown();
				this.totalIndebtedness = this.getChequeing().indebtednessCalc(); // indebtedness from terminating chequeing account
			// create loan account and transter the indebtedness as account balance. 
				
				Chequeing.getAccountList().remove(this.getChequeing().getAccountNumber());
				this.chequeing = null;
				System.out.println("Chequeing account Deleted");
			}
			break;
		case 2:		
				
			if(this.credit != null){
				this.totalIndebtedness = this.getCredit().indebtednessCalc(); // indebtedness from terminating credit account
//				this.credit.getPay().shutdown();
				Credit.getAccountList().remove(this.getCredit().getAccountNumber());
				this.credit = null;				
				System.out.println("Credit account Deleted");
			}
			break;
		/*case 3:
			Credit.getCreditAccList().remove(this.getLoan().getAccountNumber());		
			break;*/
		default:
			System.out.println("No account terminated! Try again");
			break;
		}		
		System.gc();
		
		loanAccCreator();
	}
	//////////////////////////////////////////////////////////////

    /**
	 *
     * @Description: Return the string instance of this Customer
     * @return the account types, null or not
	 *
     */

	
	@Override
	public String toString() {
        return String.format ("SIN number:>>>>> %d \n\n"
        		+ "Chequeing Account:>>>>> \n%s\n"
        		+ "Credit Account:>>>>> \n%s\n"
        		+ "Loan Account:>>>>>\n%s",securityNumber, this.chequeing, this.credit, this.loan);		// we should try using String format
	}	
}


