package system;

//import java.text.SimpleDateFormat;
//import java.util.Calendar;
//import java.util.Date;
import java.util.Scanner;

public final class Main {
	
	static boolean back = false;
	static Scanner choice = new Scanner(System.in);
	
	
	//////////////////////////////////////////////////////////////////////////// methods
	private static void firstPage() {
		
		while (true) {
			System.out.println("Home Screen");
			System.out.println("What would you like to do? Enter a number option.");
			System.out.println("1. Work with existing customers.");
			System.out.println("2. Add a new customer.");
			System.out.println("3. Exit.");

			switch (choice.nextInt()) {
			case 1:
				Main.back = false;
				Main.existingCustomers();
				break;
			case 2:
				System.out.println("Please enter SIN number for customer.");
				int sinNumber = Main.choice.nextInt();
				boolean keyExists = Customer.getCustomerList().containsKey(sinNumber);
				while (keyExists) {
					System.out.println("Customer already present, add a new customer SIN number.");
					keyExists = Customer.getCustomerList().containsKey(sinNumber = Main.choice.nextInt());
				}
				Customer customer = Customer.addCustomer(sinNumber);
				System.out.println("Modify this customer.");
				Main.workOnCustomer(customer);
				break;
			case 3:
				System.out.println("Exiting System!");				
				System.exit(0);
//				System.out.println(Customer.getCustomerList());
				break;

			default:
				System.out.println("Invalid input, please choose from the options.");
				break;
			}
		}
	}
	
	private static void existingCustomers(){
		
		while (!Main.back) {			
			System.out.println("//////////////////////////////////");
			System.out.println("Here is a list of customer SIN numbers");
			int j = 0;
			for (Integer i : Customer.getCustomerList().keySet()) {
				System.out.println(++j + ": "+i);
			}
			System.out.println("//////////////////////////////////");
			
			System.out.println("What would you like to do?");		
			System.out.println("1. Choose a customer to work with. Enter SIN number.");
			System.out.println("2. Back.");
			System.out.println("3. Exit.");
			
			switch (Main.choice.nextInt()) {
			case 1:
				System.out.println("Please enter the SIN number of the customer.");
				Customer customer = Customer.getCustomerList().get(Main.choice.nextInt());				
				while(customer == null){
					System.out.println("Customer doesn't exist. Enter the correct SIN number?");
					customer = Customer.getCustomerList().get(Main.choice.nextInt());				
				}		
				Main.back = false;
				Main.workOnCustomer(customer);
				break;
			case 2:
				Main.back = true;
				break;
			case 3:
				System.exit(0);
				break;

			default:
				System.out.println("Choose from the given options.");
				break;
			}
		}		
		Main.back = false;
	}
	private static void workOnCustomer(Customer customer) {
		
		while (!back) {
			if (customer != null) {
				
				System.out.println("What would you like to do?");
				System.out.println("0. Customer status");
				System.out.println("1. Add/Open a Chequeing Account.");
				System.out.println("2. Add/Open a Credit Account.");
				System.out.println("3. Transfer money.");
				System.out.println("4. Terminate an account.");
				System.out.println("5. Back.");
				System.out.println("6. Exit");

				switch (choice.nextInt()) {
				case 0:
					System.out.println("//////////////// Customer status of accounts. //////////////////");
					System.out.println(customer + "\n");
					System.out.println("//////////////////////////////////");
					break;
				case 1:
					if(customer.getChequeing() == null){
						System.out.println("Enter account number for chequing account.");
						Chequeing chequeing = Chequeing.addChequeing(choice.nextInt());
						
						while(chequeing == null){
							System.out.println("Chequing account not created. Enter a different account number for chequing account.");
							chequeing = Chequeing.addChequeing(choice.nextInt());				
						}		
						customer.addAccount(chequeing);
						Main.back = false;
						Main.modifyChequeing(chequeing);
						break;
					}
					System.out.println(">>>>>Customer already has a Chequeing Account. Opening ....");
					Main.modifyChequeing(customer.getChequeing());
					break;
				case 2:
					if (customer.getCredit() == null) {
						System.out.println("Enter account number for the credit account.");
						Credit credit = Credit.addCredit(choice.nextInt());

						while (credit == null) {
							System.out.println("Chequing account not created. Enter a different account number for chequing account.");
							credit = Credit.addCredit(choice.nextInt());							
						}
						customer.addAccount(credit);
						Main.back = false;
						Main.modifyCredit(credit);
					}
					System.out.println(">>>>>Customer already has Credit Account. Opening ....");
					Main.modifyCredit(customer.getCredit());
					break;
				
				case 3:
					System.out.println("How much do you want to transfer?");
					double transferAmt = Main.choice.nextDouble();
						System.out.println("1. Transfer money from Chequeing to Credit.");
						System.out.println("2. Transfer money from Credit to Chequeing.");
						Chequeing chequeing = customer.getChequeing();
						Credit credit = customer.getCredit();
					switch (Main.choice.nextInt()) {
					case 1:						
						chequeing.transferAmount(transferAmt, chequeing, credit);
						break;
					case 2:
						credit.transferAmount(transferAmt, credit, chequeing);
						break;

					default:
						System.out.println("Choose from the options.");
						break;
					}
					
					break;
				case 4:
					System.out.println("Choose an account to terminate.");
					System.out.println("1. Chequeing account");
					System.out.println("2. Credit account");
					int value = Main.choice.nextInt();
					if (value == 1 || value == 2){
						customer.terminateAccount(value);
					}
					else {
						System.out.println("No account terminated, Please choose 1 or 2.");
					}
					break;
				case 5:
					System.out.println("Going back to previous page.");
					Main.back = true;
					break;
				case 6:
					System.out.println("Exiting System!");
					System.exit(0);
					break;

				default:
					System.out.println("Choose from the options given.");
					break;
				}

			} else {
				System.out.println("Customer doesn't exist. Did you enter the correct SIN number?");
			}
		}
		Main.back = false;	
	}

	private static void modifyCredit(Credit credit) {
		while (!back) {
			System.out.println("Modify this credit account. Details:");
			System.out.println("//////////////////////////////////");
			System.out.println(credit.toString());
			System.out.println("//////////////////////////////////");
			
			System.out.println("What would you like to modify?");			
			System.out.println("1. Change credit limit.");
			System.out.println("2. Back");
			System.out.println("3. Exit");
			
			switch (Main.choice.nextInt()) {
			case 1:
				System.out.println("Enter a new value for the Credit limit.");
				int creditLimit = Main.choice.nextInt();
				if(creditLimit < 100){
					System.out.println("Credit Limit not changed. Enter a value greater or equals to 100.");
					break;
				}
				credit.setLimit(creditLimit);
				break;
			case 2:
				System.out.println("Going back to previous page.");
				Main.back = true;
				break;
			case 3:
				System.out.println("Exiting System!");
				System.exit(0);
				break;

			default:
				System.out.println("Enter from the options given.");
				break;
			}
		}
		Main.back = true;
	}
	private static void modifyChequeing(Chequeing chequeing) {
		while(!back){
			System.out.println("Modify this chequing account. Details:");
			System.out.println("//////////////////////////////////");
			System.out.println(chequeing.toString());
			System.out.println("//////////////////////////////////");
			System.out.println("What would you like to modify?");
			System.out.println("1. Change overdraft option.");
			System.out.println("2. Change overdraft limit.");
			System.out.println("3. Deposit money.");
			System.out.println("4. Withdraw money.");
			System.out.println("5. Back");
			System.out.println("6. Exit");
			
			switch (choice.nextInt()) {
			
			case 1:
				System.out.println("Choose from the overdraft options: ");
				System.out.println("1.  No Overdraft Protection: with this option, if a withdrawal from the \n"
								+ "  checking account would cause the balance to be less than 0, then the withdrawal will \n"
								+ "  be declined, and a Non-Sufficient Funds (NSF) penalty will be charged.\n");
				System.out.println("2.  Pay Per Use Overdraft Protection. 5$ for creating overdraft or increasing overdraft balance.\n");
				System.out.println("3.  Monthly Fixed Fee Overdraft Protection. 4$ a month fixed\n");				
				int choose = Main.choice.nextInt();
				if(choose >=1 && choose <=3){
					chequeing.setOverdraftOption(choose);
				} else{
					System.out.println("No option chosen for overdraft limit.");					
				}
				break;
			case 2:
				System.out.println("Enter whole number from 100 to 5000");
				chequeing.setLimit(choice.nextInt());
				break;
			case 3:
				System.out.println("Enter amount to deposit.");
				chequeing.depositAmount(choice.nextInt());
				break;
			case 4:
				System.out.println("Enter amount to withdraw.");
//				try {
					chequeing.withdrawAmount(choice.nextInt());
//				}  (NegatcatchiveBalanceException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
				break;
			case 5:
				System.out.println("Going Back.");
				Main.back = true;
				break;
			case 6:
				System.out.println("Exiting system.");
				System.exit(0);
				break;

			default:
				System.out.println("Choose from the options given.");
				break;
			}
		}		
		Main.back = false;
	}
	////////////////////////////////////////////////////////////////////////////
	
    public static void main(String[] args) {
    	Customer amin = Customer.addCustomer(123321);
    		Chequeing chequeing1 = Chequeing.addChequeing(111);
	    		chequeing1.depositAmount(10000);
	    		chequeing1.setOverdraftOption(2);
	    		chequeing1.setLimit(900);
	    		chequeing1.withdrawAmount(10500);	    		
	    		
	    	Credit credit3 = Credit.addCredit(227);
	    		credit3.setLimit(1000);
	    		credit3.withdrawAmount(500);
    		amin.addAccount(chequeing1);
    		amin.addAccount(credit3);
    		amin.terminateAccount(1);
    		amin.terminateAccount(2);
    		
    		System.out.println("total debt: "+amin.getTotalIndebtedness());
    		System.out.println(amin.getLoan());
    		
//    	Customer sorab = Customer.addCustomer(321123);
//    		Chequeing chequeing2 = Chequeing.addChequeing(112);
//    			chequeing2.depositAmount(1000);
//    			chequeing2.setOverdraftOption(2);
//    		Credit credit1 = Credit.addCredit(222);
//    			credit1.setLimit(1500);
//			sorab.addAccount(chequeing2);
//			sorab.addAccount(credit1);
//    			
//    	Customer afia = Customer.addCustomer(890098);
//    		Chequeing chequeing3 = Chequeing.addChequeing(113);
//    			chequeing3.depositAmount(200000);
//    		Credit credit2 = Credit.addCredit(223);
//				credit2.setLimit(15000);
//			afia.addAccount(chequeing3);
//			afia.addAccount(credit2);
		
			System.out.println("");
//    	firstPage();    	
    }
}
