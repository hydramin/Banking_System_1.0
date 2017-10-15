package system;

import java.util.HashMap;
import java.util.Random;

public class Customer {

	private final int securityNumber;
	private double totalIndebtedness;
	private Chequeing chequeing;
	private Credit credit;
	private Loan loan;
	private int loanAccNum;
	private static HashMap<Integer, Customer> customerList = new HashMap<>();

    ////////////////////////////// CONSTRUCTOR

	private Customer(int securityNumber) {
		this.securityNumber = securityNumber;
		this.chequeing = null;
		this.credit = null;
		this.loan = null;
		this.totalIndebtedness = 0;
		loanAccNum = 0;
		System.out.println("Customer Created!");
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
     * @Description: This method returns the chequeing account of a customer.
     *
     * @return  account of type Chequeing.
     */
	public Chequeing getChequeing() {
		return chequeing;
	}

    /**
     * @Description: This method returns the credit account of a customer.
     *
     * @return  account of type Credit.
     */
	public Credit getCredit() {
		return credit;
	}

    /**
     * @Description: This method returns the Demand loan account of a customer.
     *
     * @return  account of type Loan.
     */
	public Loan getLoan() {
		return loan;
	}

    /**
     * @Description: This method returns the total indebtedness of the customer,
     *              which is a sum of indebtedness across all accounts.
     *
     * @return  double value containing total indebtedness.
     */
    public double getTotalIndebtedness() {
        return totalIndebtedness;
    }

    //////////////////////////  OPERATIONS  //////////////////////////

    /**
     * @Description: This method is the only way to create a new customer. A new security number is passed
     *              as a parameter and if the customer is not in the system, it will be added as a new one.
     *              The new customer is then passed in to the list of customers map(customerList).
     *
     * @param securityNumber: unique identifier for a single customer
     *
     * @return a new customer if the security number is not already registered in the system
     */
	public static Customer addCustomer(int securityNumber) {
		if (!customerList.containsKey(securityNumber))
			customerList.put(securityNumber, new Customer(securityNumber));
		return customerList.get(securityNumber);
	}

    /**
     * @Description: This method sets the chequeing account of the customer.
     *
     * @param account new account of type Chequeing.
     */
	public void addAccount(Chequeing account){
		this.chequeing = account;
		this.chequeing.setSIN(this.securityNumber);
	}

    /**
     * @Description: This method sets the credit account of the customer.
     *
     * @param account new account of type Credit.
     */
	public void addAccount(Credit account){
		this.credit = account;
	}

    /**
     * @Description: This method sets the Demand loan account of the customer.
     *
     * @param account new account of type Loan.
     */
	public void addAccount(Loan account){
		this.loan = account;
	}

    /**
     * @Description: This method is a means of creating a Demand loan account.
     *              It creates an account of type Loan and add it to the loan account
     *              list with a unique random account number. The total indebtedness
     *              of the account is then transferred over to the Demand loan account.
     *
     */
	private void loanAccCreator() {

		if (this.loan == null) {
			int num = randomAccountNumGenerator();
			this.loan = Loan.addAccount(num);
		}

		this.loan.depositAmount(totalIndebtedness);
		this.totalIndebtedness = 0;
	}

    /**
     * @Description: This method returns a three digit random number between 100 to 999.
     *              This number is used as the account number to create a unique Demand loan account.
     *
     * @return  random number of type int.
     */
	private int randomAccountNumGenerator(){
		Random random = new Random();
		return random.nextInt(999) + 100;
	}

    /**
     * @Description: This method terminates a specified account.
     *              Before termination, this method ensures to take all the
     *              indebtedness of a specified account and add to total indebtedness.
     *
     * @param account is an int representing the type of account to be terminated.
     */
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

    /**
     * @Description: Return the string instance of this Customer
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