package system;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;




public class Main {
	
	static boolean back = false;
	static Scanner choice = new Scanner(System.in);
	static Random num = new Random();
	static int y = num.nextInt(100) + 100;
	private static Account addThisAcc(int x){
		switch (x) {
		case 1: // chequing			
			Chequeing chequeing = Chequeing.addChequeing(y);
			System.out.println("Cheq created :"+ chequeing);
			return chequeing;
			
		case 2: // credit
			Credit credit = Credit.addCredit(y);
			System.out.println("Cheq created :"+ credit);
			return credit;
			
		case 3: // loan

			break;

		default:
			break;
		}
		System.out.println("null returned");
		return null;
	}
	////////////////////////////////////////////////////////////////////////////
	private static void existingCustomers(){
		
		while (!Main.back) {
			System.out.println("Here is a list of customer SIN (Names)");
			System.out.println("//////////////////////////////////");
			int j = 0;
			for (Integer i : Customer.getCustomerList().keySet()) {
				System.out.println(++j + ": "+i);
			}
			System.out.println("//////////////////////////////////");
			
			System.out.println("What would you like to do?");		
			System.out.println("1. Choose a customer to work with. Enter SIN number.");
			System.out.println("2. back to previous.");
			System.out.println("3. Exit.");
			
			switch (Main.choice.nextInt()) {
			case 1:
				Customer customer = Customer.getCustomerList().get(Main.choice.nextInt());
				while(customer == null){
					System.out.println("Customer doesn't exist. Enter the correct SIN number?");
					customer = Customer.getCustomerList().get(Main.choice.nextInt());				
				}		
				back = false;
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
				System.out.println("1. Add a Chequeing Account.");
				System.out.println("2. Add a Credit Account.");
				System.out.println("3. Add a Loan Account.");

				switch (choice.nextInt()) {
				case 1:
					System.out.println("Enter account number for chequing account.");
					Chequeing chequeing = Chequeing.addChequeing(choice.nextInt());
					
					while(chequeing == null){
						System.out.println("Chequing account not created. Enter a different account number for chequing account.");
						chequeing = Chequeing.addChequeing(choice.nextInt());				
					}		
					back = false;
					Main.modifyChequeing(chequeing);
					break;
				case 2:

					break;
				case 3:

					break;

				default:
					System.out.println("Choose from the options given.");
					break;
				}

			} else {
				System.out.println("Customer doesn't exist. Did you enter the correct SIN number?");
			}
		}
			
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
			
			switch (choice.nextInt()) {
			
			case 1:
				System.out.println("Choose from the overdraft options: ");
				System.out.println("1. No Overdraft Protection: with this option, if a withdrawal from the \n"
								+ "checking account would cause the balance to be less than 0, then the withdrawal will \n"
								+ "be declined, and a Non-Sufficient Funds (NSF) penalty will be charged.\n");
				System.out.println("2. Pay Per Use Overdraft Protection. 5$ for creating overdraft or increasing overdraft balance.\n");
				System.out.println("Monthly Fixed Fee Overdraft Protection. 4$ a month fixed\n");				
				
				chequeing.setOverdraftOption(choice.nextInt());
				break;
			case 2:
				System.out.println("Enter whole number from 100 to 5000");
				chequeing.setOverdraftLimit(choice.nextInt());
				break;

			default:
				System.out.println("Choose from the options given.");
				break;
			}
		}
		
	}
	////////////////////////////////////////////////////////////////////////////
	
    public static void main(String[] args) {
    	
    	boolean exit = false;
    	Scanner choice = new Scanner(System.in);
    	
    	while (!exit) {
			System.out.println("Home Screen");
			System.out.println("What would you like to do? Enter a number option.");
			System.out.println("1. Work with existing customers.");
			System.out.println("2. Add a new customer.");
			System.out.println("3. Exit.");
			
			switch (choice.nextInt()) {
			case 1:
				Main.existingCustomers();
				break;
			case 2:
				System.out.println("Please enter SIN number for customer.");
				int sinNumber = Main.choice.nextInt();
				boolean keyExists = Customer.getCustomerList().containsKey(sinNumber);
				while (keyExists) {
					System.out.println("Customer already present, add a new customer.");
					keyExists = Customer.getCustomerList().containsKey(sinNumber = Main.choice.nextInt());
				}
				Customer customer = Customer.addCustomer(sinNumber);
				System.out.println("Modify this customer.");
				Main.workOnCustomer(customer);
				break;
			case 3:
				System.out.println("Exiting System!");
				System.exit(0);				
				break;

			default:
				System.out.println("Invalid input, please choose from the options.");
				break;
			}
		}
        
    	
    		

    }
}
