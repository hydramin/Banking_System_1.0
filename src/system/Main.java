package system;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;




public class Main {
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
    public static void main(String[] args) {
        
    	
    	/*Calendar date = Calendar.getInstance();
//    	SimpleDateFormat dFormat = new SimpleDateFormat("HH");
//    	String hour = dFormat.format(date);
    	System.out.println(date.getTime());
    	
    	Date date2 = new Date();
    	SimpleDateFormat dFormat2 = new SimpleDateFormat("MM");
    	String hour2 = dFormat2.format(date2);
    	System.out.println(hour2);
    	
    	System.out.println(date.getTime());
    	System.out.println(System.currentTimeMillis()/ 86400000);
    	
    	Customer customer = Customer.addCustomer(123321);    	
    	Chequeing chequeing = Chequeing.addChequeing(111);    	
    	chequeing.setOverdraftOption(2);
    	chequeing.setOverdraftLimit(100);
    	chequeing.depositAmount(200);
    		System.out.println(chequeing.toString());
    		
		customer.addAccount(chequeing);
			System.out.println(customer.toString());
		
		customer.getChequeing().withdrawAmount(100);
		customer.getChequeing().withdrawAmount(110);
			System.out.println(customer);*/
			
		// Make an interactive app from terminal.
			boolean exit = false;
			String instruction = "";
			int numbers =0;
			Scanner command = new Scanner(System.in);
			
			
			while(!exit){
				System.out.println("What is your command?");
				instruction = command.nextLine();
				
				switch (instruction) {
				case "exit":
					exit = true;
					System.out.println("Exiting system.");
				case "commands":
					System.out.printf("add customer \nadd account\n");
					break;
					
				case "add customer":
					System.out.println("Customer's SIN number.");
					numbers = command.nextInt();
					Customer.addCustomer(numbers);
					break;
				case "create account":
					System.out.println("What kind of account? \n"
							+ "Enter : 1 for Chequing \n"
							+ "Enter : 2 for Credit \n"
							+ "Enter : 3 for Load \n");
					
					Account account = Main.addThisAcc(command.nextInt());
					System.out.println("Add this account to customer, customer SIN?");
					Customer cus = Customer.getCustomerList().get(command.nextInt());
					System.out.println("print cus "+ cus);
					cus.addAccount(account);
					break;
				case "customer list":
					System.out.println("Here is the list of customers.");
					for (Integer i : Customer.getCustomerList().keySet()) {
						System.out.println(Customer.getCustomerList().get(i) + "\n");
					}
					Customer.getCustomerList();
					break;
					
				case "add account to":
					System.out.printf("What's customers SIN?");
					Customer.getCustomerList().get(command.nextInt());
					break;
				default:
					break;
				}
//				add customer
//				add account
				
				
			}
//			modify account
//			modify customer
    		

    }
}
