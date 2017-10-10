package system;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Chequeing extends Account implements Runnable {
	
	// The account gets created and it will be modified

//	private double overdraftLimit; // set a the overdraft limit
	private int chosenOverdraftOption;
	private double withdrawLimit;
	public static final int OVER_DRAFT_OPTION_1 = 1; // No Overdraft Protection: with this option, if a withdrawal from the
					       							//	checking account would cause the balance to be less than 0, then the withdrawal will
					       							//	be declined, and a Non-Sufficient Funds (NSF) penalty will be charged.
	public static final int OVER_DRAFT_OPTION_2 = 2; // Pay Per Use Overdraft Protection.
	public static final int OVER_DRAFT_OPTION_3 = 3; // Monthly Fixed Fee Overdraft Protection.
	private static final double NONSUFFICIENT_FUNDS_FEE = 25; // 25 dollars fee
	private static final double DAILY_OVERDRAFT_FEE = 5; // 25 dollars fee
	private static boolean takeDailyFee;
	private static HashMap<Integer, Chequeing> chequeingAccList= new HashMap<>();

	
	////////////////////////////////////////////////////////////////////////////////////////// constructor
	private Chequeing(int accountNumber) {
		super(accountNumber);
		takeDailyFee = false;
//		overdraftLimit = 0;
		chosenOverdraftOption = OVER_DRAFT_OPTION_1; // Default overdraft option is Option 1.
		System.out.println("Chequeing acc created.");
		System.out.println(this);
	}
	////////////////////////////////////////////////////////////////////////////////////////// Operations
	public static Chequeing addChequeing(int accountNumber) // only one account number can be assigned to one customer
	{
//		Chequeing c = chequeingAccList.get(accountNumber);
//		if (c == null)
//		{
//			Chequeing.chequeingAccList.put(accountNumber, new Chequeing(accountNumber));
//		} else {
//			throw new IllegalArgumentException("\n this chequing account already taken");
//		}
//		return c;
		
		if (!chequeingAccList.containsKey(accountNumber))
		{
			chequeingAccList.put(accountNumber, new Chequeing(accountNumber));
		}
		return chequeingAccList.get(accountNumber);
	}
	
	public void setOverdraftOption(int option) // chequeing
	{	
		if (option < OVER_DRAFT_OPTION_1 || option > OVER_DRAFT_OPTION_3) {
			throw new IllegalArgumentException();
		} 
		this.chosenOverdraftOption = option;
	}
	
	@Override
	public void setLimit(int limit) {
//		this.overdraftLimit = overdraftLimit;
		super.setLimit(limit);
		this.withdrawLimit = - super.getLimit();
//		super.setLimit(limit);
	}

	
	@Override
	public void withdrawAmount(double amount) {
		// TODO Auto-generated method stub
//		System.out.println("withdraw called here");
		
			switch (chosenOverdraftOption) {
			case OVER_DRAFT_OPTION_1:
				// Withdrawal declined, NSF penalty charged to the account increasing the indebtedness
				// indebtedness only increases as a result of NSF fee, not as a result of withdrawal
				super.withdrawAmount(NONSUFFICIENT_FUNDS_FEE);
				break;
			case OVER_DRAFT_OPTION_2:
				//pay per use, $5.00 for each day (based on end-of-day balances), End of day = 5pm
				//i have to know when the day ends.
				double tempBalance = super.getBalance() - amount;
//				System.out.println("Temp bal: " + withdrawLimit);
				if(tempBalance < withdrawLimit){
					// can't withdraw, charge NSF
					super.withdrawAmount(NONSUFFICIENT_FUNDS_FEE);
					break;
				} else if(tempBalance >= withdrawLimit && tempBalance <0){
					// trigger the day end charge thread, 
					// thread triggerd once per day
					takeDailyFee = true;
					super.withdrawAmount(amount + DAILY_OVERDRAFT_FEE); // everytime overdraft is created fee is charged
					
					ScheduledExecutorService pay = Executors.newSingleThreadScheduledExecutor();
					pay.scheduleAtFixedRate(this, 0, 5, TimeUnit.SECONDS);
					break;
				}
				super.withdrawAmount(amount);			
				
				break;
			case OVER_DRAFT_OPTION_3:

				break;

			default:
				break;
			}
		
	}
	
	@Override
	public void run() {
		System.out.println("I am scheduled runner");
		endDayOptionTwoCharge();
		if (takeDailyFee == false)
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
	}
	
	private void endDayOptionTwoCharge(){ //////////////////////////////I couldn't do a balance and time listener simultaneously using Runnable
		// if balance is -ve and time is 5pm (17:00)
		SimpleDateFormat hour = new SimpleDateFormat("HH:mm");
		String endOfDayHour = hour.format(System.currentTimeMillis());
		System.out.println("\n"+"    balance: "+ super.getBalance() + "\n"+"    hour: "+ endOfDayHour);

		if (super.getBalance() < 0 && endOfDayHour.equals("23:29") && takeDailyFee) {
			System.out.println("\n"+"if works");
			super.withdrawAmount(DAILY_OVERDRAFT_FEE);
			takeDailyFee = false;
		}
		
		System.out.println(this.getBalance());
	}
	
	
	@Override
	public String toString() {
		return super.toString() + "Option: " + this.chosenOverdraftOption +"\n"+ 
									"overdraft Limit: "+ super.getLimit() +"\n" +
									"withdraw Limit: "+ withdrawLimit+"\n";
	}

}
