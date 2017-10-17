package system;


import java.io.IOException;
import java.util.Scanner;

public final class Main {
	
	private static boolean back = false; // is used to go back from one page to another based on its value
	private static Scanner choice = new Scanner(System.in); // is used to enter discrete integer choices specified by the user 
	private static String boundary = "\n<><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><>\n"  // is boundary used to separate one page from the other
									+ "<><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><>\n"; 
	private static String boundary2 = "\n<><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><>\n"; 
	
	

	//////////////////////////////////////////////////////////////////////////////////////////////////// Choices and Validators
	//////////////////////////////////////////////////////////////////////////////////////////////////// Choices and Validators
	//////////////////////////////////////////////////////////////////////////////////////////////////// Choices and Validators
	//////////////////////////////////////////////////////////////////////////////////////////////////// Choices and Validators
	/**
	 * @Description: it presents the options for the first UI page and ensures only 1 to 4 is selected
	 * @return: returns an int value of 1 to 4
	 */
	private static int firstPageChoice(){
		System.out.println(boundary);
		System.out.println("Home Screen");
		System.out.println("What would you like to do? Enter a number option.");
		System.out.println("1. Work with existing customers.");
		System.out.println("2. Add a new customer.");
		System.out.println("3. Current Activity log of all accounts");
		System.out.println("4. Exit.");
		
		return boundaryValidator(1, 4);
	}
	
	/**
	 * @Description: Shows the SIN number of available customers
	 * @return: returns the chosen int only from 1 to 3
	 */
	private static int existingCustomerChoice() {
		System.out.println(boundary);
		System.out.println("Here is a list of Customers' SIN numbers");
		
		int j = 0;
		for (Integer i : Customer.getCustomerList().keySet()) {
			System.out.println(++j + ": "+i);
		}
		System.out.println(boundary);
		
		System.out.println("What would you like to do?");		
		System.out.println("1. Enter Customer Choice Page.");
		System.out.println("2. Back.");
		System.out.println("3. Exit.");
		return boundaryValidator(1, 3);
	}
	
/**
 * @Description: Gives options for what to do to a customer account
 * @return: returns option ints from 0 to 6
 */
	private static int workOnCusChoice() {
		System.out.println(boundary);
		System.out.println("What would you like to do?");
		System.out.println("0. Customer status");
		System.out.println("1. Add/Open a Chequeing Account.");
		System.out.println("2. Add/Open a Credit Account.");
		System.out.println("3. Transfer money.");
		System.out.println("4. Terminate an account.");
		System.out.println("5. Back.");
		System.out.println("6. Exit");
		return boundaryValidator(0, 6);
	}
	
	/**
	 * @Description: Displays the chosen credit accound and gives options on what to do to it
	 * @param credit
	 * @return returns the chosen int from 1 to 4
	 * @Precondition: credit must not be null 
	 */
	private static int modifyCreditChoice(Credit credit) {
		
		System.out.println(boundary);
		System.out.println("Modify this credit account. Details:");
		System.out.println(credit.toString());
		System.out.println(boundary);
		
		System.out.println("What would you like to modify?");			
		System.out.println("1. Change credit limit.");
		System.out.println("2. Withdraw money.");
		System.out.println("3. Back");
		System.out.println("4. Exit");
		
		return boundaryValidator(1, 4);
	}
	
	/**
	 * @Description: Displays the chosen chequeing account and gives option on how to affect it
	 * @param chequeing
	 * @return: returns the chosen option only from 1 to 6
	 */
	private static int chequeChoice(Chequeing chequeing) {
		System.out.println("Modify this chequing account. Details:");
		System.out.println(boundary2);
		System.out.println(chequeing.toString());
		System.out.println(boundary2);
		System.out.println("What would you like to modify?");
		System.out.println("1. Change overdraft option.");
		System.out.println("2. Change overdraft limit.");
		System.out.println("3. Deposit money.");
		System.out.println("4. Withdraw money.");
		System.out.println("5. Back");
		System.out.println("6. Exit");
		
		return boundaryValidator(1, 6);
	}
	
	/**
	 * @Description: Gives option for changing overdraft option
	 * @param chequeing
	 * @Precondition: chequeing must not be null
	 * @Postcondition: assigns the chosen option to the chosen chequing
	 */
	private static void chqOverdraftOptChoice(Chequeing chequeing) {
		System.out.println("Choose from the overdraft options: ");
		System.out.println("1.  No Overdraft Protection: with this option, if a withdrawal from the \n"
						+ "  checking account would cause the balance to be less than 0, then the withdrawal will \n"
						+ "  be declined, and a Non-Sufficient Funds (NSF) penalty will be charged.\n");
		System.out.println("2.  Pay Per Use Overdraft Protection. 5$ for creating overdraft or increasing overdraft balance.\n");
		System.out.println("3.  Monthly Fixed Fee Overdraft Protection. 4$ a month fixed\n");				
		int choose = boundaryValidator(1, 3);		
		chequeing.setOverdraftOption(choose); // choose is only between 1,3 ensured		
	}
	
	/**
	 * @Description: ensures the sin number passed by user is between 100000 and 999999
	 * @return sin nubmber between 100000 and 999999
	 */
	private static int sinValidator(){
		int sinNumber = Main.choice.nextInt();
		while(sinNumber <100000 && sinNumber > 999999){
			System.out.println("SIN number has to be 6 digits. Can not start with a 0.");
			sinNumber = Main.choice.nextInt();
		}
		return sinNumber;
	}
	
	/**
	 * @Description: allows the user to choose an int between from and to variables
	 * @param from
	 * @param to
	 * @return returns an int in between from and to
	 */
	private static int boundaryValidator(int from, int to) { // used for the choices
		int x = Main.choice.nextInt();
		while(x <from && x > to){
			System.out.println("Choose from the given choices.");
			x = Main.choice.nextInt();
		}
		return x;
	}
	
	/**
	 * @Description: Ensures the account number is a 3 digit number between 100 and 999
	 * @return: returns an the user entered int between 100 and 999
	 */
	private static int accNumValidator(){ // must be 3 digit number
		int accNum = Main.choice.nextInt();
		while(accNum <100 && accNum > 999){
			System.out.println("Account number has to be 3 digits. Can not start with a 0.");
			accNum = Main.choice.nextInt();
		}
		return accNum;
	}
	
	/**
	 * @Description: It creates a chequeing account and passes the customer's sin into it and modifies the account and gives option to add or cancle it
	 * @param customer
	 * @Precondition: customer must not be null
	 * @Postconditon: customer will have a chequeing account it it had none
	 */
	private static void chequeingCreator(Customer customer) { // customer is ensured not null
		System.out.println("Enter account number for the chequing account.");
		Chequeing chequeing = Chequeing.createAccount(accNumValidator());
		
		while(chequeing == null){
			System.out.println("Chequing account not created. Account number taken. Enter a different 3 digit number.");
			chequeing = Chequeing.createAccount(accNumValidator());				
		}		
		chequeing.setSIN(customer.getSIN());
		Main.modifyChequeing(chequeing); // chequeing is never null
		Main.addOrCancle(customer, chequeing); // both not null		
	}

	/**
	 * @Description: creates a credit account and assigns it into the chosen customer object, allows modification and choice to add or cancel
	 * @param customer
	 * @Precondition: customer must not be null
	 * @Postconditon: customer will have a credit account it it had none
	 */
	private static void creditCreator(Customer customer){
		System.out.println("Enter account number for the credit account.");
		Credit credit = Credit.createAccount(accNumValidator());

		while (credit == null) {
			System.out.println("Chequing account not created. Enter a different account number for chequing account.");
			credit = Credit.createAccount(accNumValidator());							
		}
		credit.setSIN(customer.getSIN());	
		Main.modifyCredit(credit);
		Main.addOrCancle(customer, credit);
	}
	
	/**
	 * @Description: Gives option to transfer some amount of money from chequing to credit or back 
	 * @param customer
	 * @Precondition: customer must have both accounts
	 * @Postcondition: requests transfer from one to the other account
	 */
	private static void transferMoney(Customer customer) {
		System.out.println(boundary2);
		System.out.println("1. Transfer money from Chequeing to Credit.");
		System.out.println("2. Transfer money from Credit to Chequeing.");
		Chequeing chequeing = customer.getChequeing();
		Credit credit = customer.getCredit();
		int value = 0;
		if (chequeing != null && credit != null) { // both must not be null
			 value = boundaryValidator(1, 2);
		}		
		
		System.out.println("How much do you want to transfer?");
		double transferAmt = moneyValidator(); // must be a positive number
		
		switch (value) {
		case 1:
			chequeing.transferAmount(transferAmt, chequeing, credit); // all 3 params ensured not invalid by transfer choice
			break;
		case 2:
			credit.transferAmount(transferAmt, credit, chequeing); // all 3 params ensured not invalid by transfer choice
			break;

		default:
			System.out.println("Can not Transfer! Your Credit or Chequing account doesnot exist!");						
			break;
		}
	}
	
	/**
	 * @Description: Ensures that the money amount entered is always positive
	 * @return a positive double amount
	 */
	private static double moneyValidator() { // returns always a + value
		int money = Main.choice.nextInt();
		while(money < 0){
			System.out.println("Please enter a positive money amount.");
			money = Main.choice.nextInt();
		}
		return money;
	}
	
	/**
	 * 
	 * @Description: Ensures the credit limit entered by user is between from and to
	 * @param from
	 * @param to
	 * @return returns a valid limit between from and to
	 */
	private static int creditLimitValidator(int from,int to){
		
		System.out.println(String.format("Enter a new value for the Credit limit between $%d and $%d.", from, to));
		int limit = Main.choice.nextInt();
		while (limit < from && limit > to) {
			System.out.println(String.format("Please enter value between between $%d and $%d.", from, to));
			limit = Main.choice.nextInt();
		}
		return limit;
	}
		
	/**
	 * @Description: Gives the option to terminate an account
	 * @param customer
	 * @Precondition: customer must have the account to be terminated
	 * @Postcondition: a request for termination will be sent
	 */
	private static void terminator(Customer customer) {
		System.out.println("Choose an account to terminate.");
		System.out.println("1. Chequeing account");
		System.out.println("2. Credit account");
		int value = boundaryValidator(1, 2);
		
		if(customer.getChequeing() != null && value == 1){
			customer.terminateAccount(value); // 1 cheq, 2 terminates credit
		} else if(customer.getCredit() != null && value == 2){
			customer.terminateAccount(value);
		}else{
			System.out.println("Account does not exist. No account terminated.");
		}	
	}
		/**
		 * @Description: Allows the system to exit and record the logs to file
		 */
	private static void exitSystem(){
		try {
			AccountActivity.saveAccountLog();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.exit(0);
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////// Pages or UI for software.
	//////////////////////////////////////////////////////////////////////////////////////////////////// Pages or UI for software.
	//////////////////////////////////////////////////////////////////////////////////////////////////// Pages or UI for software.
	//////////////////////////////////////////////////////////////////////////////////////////////////// Pages or UI for software.

	/**
	 * @Description: this is the first page of the UI interface and it processes the choices made by the user
	 */
	private static void firstPage() {
		AccountActivity.retrieveAccountLog();
		while (true) {			

			switch (Main.firstPageChoice()) {
			case 1:
				Main.back = false;
				Main.existingCustomers(); // takes no parameters and hence no contract to fulfill. 
				break;
			case 2:
				System.out.println("Please enter SIN number for the new customer.");
				int sinNumber = sinValidator();
				boolean keyExists = Customer.getCustomerList().containsKey(sinNumber);
				while (keyExists) {
					System.out.println("Customer already present, add a new customer SIN number.");
					keyExists = Customer.getCustomerList().containsKey(sinNumber = sinValidator());
				}
				Customer customer = Customer.addCustomer(sinNumber);
				System.out.println("Modify this customer.");
				Main.workOnCustomer(customer);
				break;
			case 3:
				System.out.println(boundary + "\nUpto Date Account log\n");				
				for (AccountActivity a : AccountActivity.getAccountLog()) {
					System.out.println(a);
				}
				break;
			case 4:
				System.out.println("Exiting System!");				
				exitSystem();
				break;				

			default:
				System.out.println("Invalid input, please choose from the options.");
				break;
			}
		}
	}
	
	/**
	 * @Description: the page where existing customers will be listed
	 */
	private static void existingCustomers(){
		
		while (!Main.back) {
			System.out.println(boundary);
			switch (existingCustomerChoice()) { // choice is correct.
			case 1:
				System.out.println("Please enter the SIN number of the customer.");
				int sinNum = sinValidator();
				Customer customer = Customer.getCustomerList().get(sinNum);				
				while(customer == null){
					System.out.println("Customer doesn't exist. Enter the correct SIN number?");
					customer = Customer.getCustomerList().get(sinNum = accNumValidator());		// a Non - null customer is retrieved	
				}		
				Main.back = false;
				Main.workOnCustomer(customer); // customer is never null
				break;
			case 2:
				Main.back = true;
				break;
			case 3:
				exitSystem();
				break;

			default:
				System.out.println("Choose from the given options.");
				break;
			}
		}		
		Main.back = false;
	}
	
	/**
	 * @Description: A page where a chosen customer's status and actions with in the customer are presented
	 * @param customer
	 */
	private static void workOnCustomer(Customer customer) { // customer will never be null, ensured by callers above.
		
		while (!back) {				
				System.out.println(boundary);
				switch (workOnCusChoice()) {
				case 0:
					System.out.println(boundary2+"\nCustomer's Status.");
					System.out.println(customer + "\n"+boundary2);
					break;
				case 1:
					if(customer.getChequeing() == null){						
						chequeingCreator(customer);
						Main.back = false;			
						break;
					}
					System.out.println(">>>>>Customer already has a Chequeing Account. Opening Account: ....");
					Main.modifyChequeing(customer.getChequeing());
					break;
				case 2:
					if (customer.getCredit() == null) {
						creditCreator(customer);
						Main.back = false;			
						break;
					}
					System.out.println(">>>>>Customer already has Credit Account. Opening ....");
					Main.modifyCredit(customer.getCredit());
					Main.back = false;
					break;
				
				case 3: // Transfer money.
					transferMoney(customer);
					Main.back = false;
					break;
				case 4: // Terminate this account					
					terminator(customer);									
					Main.back = false;
					break;
				case 5:
					System.out.println("Going back to previous page.");
					Main.back = true;
					break;
				case 6:
					System.out.println("Exiting System!");
					exitSystem();
					break;

				default:
					System.out.println("Choose from the options given.");
					break;
				}

		}
		Main.back = false;	
	}
	
	/**
	 * @Description: A choice page for adding or canceling a created chequeing account before it is added
	 * @param customer
	 * @param chequeing
	 */
	private static void addOrCancle(Customer customer, Chequeing chequeing) {
		
		System.out.println("Would you like to add this account?");
		System.out.println("1. Yes, Add account. \n2. Cancle account.");
		boolean action = false;
		while (!action) {
			switch (Main.choice.nextInt()) {
			case 1:
				customer.addAccount(chequeing);
//				chequeing.setSIN(customer.getSIN());
				action = true;
				break;
			case 2:
				chequeing.cancleAccount();
//				Chequeing.getAccountList().remove(chequeing.getAccountNumber());
				chequeing = null;
				action = true;
				break;
			default:
				System.out.println("Please Choose from the options.");
				break;
			}
		}
	}
	
	/**
	 * @Description: A page where a credit account is added or cancled into the customer's accounts
	 * @param customer
	 * @param credit
	 */
	private static void addOrCancle(Customer customer, Credit credit) {
		
		System.out.println("Would you like to add this account?");
		System.out.println("1. Yes, Add account. \n2. Cancle account.");
		boolean action = false;
		while (!action) {
			switch (Main.choice.nextInt()) {
			case 1:
				customer.addAccount(credit);
//				credit.setSIN(customer.getSIN());
				action = true;
				break;
			case 2:
				credit.cancleAccount();
//				Chequeing.getAccountList().remove(credit.getAccountNumber());
				credit = null;
				action = true;
				break;
			default:
				System.out.println("Please Choose from the options.");
				break;
			}
		}
	}

	/**
	 * A page where options chosen to modify the credit account are processed
	 * @param credit
	 */
	private static void modifyCredit(Credit credit) {
		while (!back) {
			
			
			switch (modifyCreditChoice( credit)) {
			case 1:			
							
				credit.setLimit(creditLimitValidator(100,50000));
				break;
			case 2:				
				System.out.println("Enter amount to withdraw.");
				credit.withdrawAmount(moneyValidator());
				break;
			case 3:
				System.out.println("Going back to previous page.");
				Main.back = true;
				break;
			case 4:
				System.out.println("Exiting System!");
				exitSystem();
				break;

			default:
				System.out.println("Enter from the options given.");
				break;
			}
		}
		Main.back = false;
	}
	/**
	 * @Description: A page where chequeing accounts are modified
	 * @param chequeing
	 */
	private static void modifyChequeing(Chequeing chequeing) {
		while(!back){
			
			
			switch (chequeChoice(chequeing)) {
			
			case 1: // change overdraft
				chqOverdraftOptChoice(chequeing);
				break;
			case 2:
				System.out.println("Enter whole number from 100 to 5000");
				chequeing.setLimit(creditLimitValidator(100,5000));
				break;
			case 3:
				System.out.println("Enter amount to deposit.");
				chequeing.depositAmount(moneyValidator());
				break;
			case 4:
				System.out.println("Enter amount to withdraw.");
					chequeing.withdrawAmount(moneyValidator());
				break;
			case 5:
				System.out.println("Going Back.");
				Main.back = true;
				break;
			case 6:
				System.out.println("Exiting system.");
				exitSystem();
				break;

			default:
				System.out.println("Choose from the options given.");
				break;
			}
		}		
		Main.back = false;
	}
	////////////////////////////////////////////////////////////////////////////

	

    public static void main(String[] args) throws IOException {

        Customer afia = Customer.addCustomer(333333);

        Chequeing chequeing3 = Chequeing.createAccount(113);
        chequeing3.setSIN(afia.getSIN());
        chequeing3.depositAmount(200000);

        Credit credit3 = Credit.createAccount(223);
        credit3.setSIN(afia.getSIN());
        credit3.setLimit(15000);
        afia.addAccount(chequeing3);
        afia.addAccount(credit3);

    	Customer amin = Customer.addCustomer(111111);

        Chequeing chequeing1 = Chequeing.createAccount(111);
        chequeing1.setSIN(amin.getSIN());
        chequeing1.depositAmount(10000);
        chequeing1.setOverdraftOption(2);
        chequeing1.setLimit(900);

        Credit credit1 = Credit.createAccount(227);
        credit1.setSIN(amin.getSIN());
        credit1.setLimit(1000);
        amin.addAccount(chequeing1);
        amin.addAccount(credit1);
    		
    	Customer sorab = Customer.addCustomer(222222);

        Chequeing chequeing2 = Chequeing.createAccount(112);
        chequeing2.setSIN(sorab.getSIN());
        chequeing2.depositAmount(1000);
        chequeing2.setOverdraftOption(2);

        Credit credit2 = Credit.createAccount(222);
        credit2.setSIN(sorab.getSIN());
        credit2.setLimit(1500);
        sorab.addAccount(chequeing2);
        sorab.addAccount(credit2);


		
			System.out.println("");
			
			System.out.println(AccountActivity.getAccountLog());
			AccountActivity.sortAccountLog();
			System.out.println("================================================================");
			System.out.println(AccountActivity.getAccountLog());
			AccountActivity.saveAccountLog();
			AccountActivity.retrieveAccountLog();
			System.out.println("================================================================");
			System.out.println(AccountActivity.getAccountLog());
			for (AccountActivity a : AccountActivity.getAccountLog()) {
				System.out.println(a.getComment());
			}

			
    	firstPage();
    }
}
