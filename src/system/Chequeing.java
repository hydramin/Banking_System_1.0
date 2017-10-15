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
	private static final double DAILY_OVERDRAFT_FEE = 5; // 5 dollars fee
	private static final double MONTHLY_OVERDRAFT_FEE = 4; // 5 dollars fee	
	private static HashMap<Integer, Chequeing> accountList= new HashMap<>();
//	private static final SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss");
	private ScheduledExecutorService pay;

	
	////////////////////////////////////////////////////////////////////////////////////////// constructor
	private Chequeing(int accountNumber) {
		super(accountNumber);
		chosenOverdraftOption = OVER_DRAFT_OPTION_1; // Default overdraft option is Option 1.		
		System.out.println("Chequeing acc created.");	
		timeThread();
	}
	////////////////////////////////////////////////////////////////////////////////////////// GETTERS
	public static HashMap<Integer, Chequeing> getAccountList() {
		return accountList;
	}
	
	public ScheduledExecutorService getPay() {
		return pay;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////// Operations
	public double indebtednessCalc(){
		if(this.getBalance() < 0){
			return (-super.getBalance());
		}
		return 0.0;
	}
	
	public void cancleAccount(){
		Chequeing.getAccountList().remove(super.getAccountNumber());		
	}
	
		
	public static Chequeing addAccount(int accountNumber){ // only one account number can be assigned to one customer	
		if (!accountList.containsKey(accountNumber)) {
			accountList.put(accountNumber, new Chequeing(accountNumber));
			return accountList.get(accountNumber);
		}
		return null;
	}
	
	public void setOverdraftOption(int option){ // chequeing
		
		if (option < OVER_DRAFT_OPTION_1 || option > OVER_DRAFT_OPTION_3) {
			throw new IllegalArgumentException();
		} 
		this.chosenOverdraftOption = option;		
	}
	
	@Override
	public void setLimit(int limit) {
		super.setLimit(limit);
		this.withdrawLimit = - super.getLimit();
	}
	
	
	@Override
	public void withdrawAmount(double amount)/* throws NegativeBalanceException*/ {
		super.setTransferStatus(true);
		double tempBalance = super.getBalance() - amount;
		
		switch (chosenOverdraftOption) {
		case OVER_DRAFT_OPTION_1:
			if (tempBalance < withdrawLimit) {
				super.withdrawAmount(NONSUFFICIENT_FUNDS_FEE);
				super.setTransferStatus(false);
				break;
			}		
			
			super.withdrawAmount(amount);
			break;
		case OVER_DRAFT_OPTION_2:

			if (tempBalance < withdrawLimit) {
				super.withdrawAmount(NONSUFFICIENT_FUNDS_FEE);
				super.setTransferStatus(false);
				break;
			} else if(tempBalance >= withdrawLimit && tempBalance <0){				
				super.withdrawAmount(amount + DAILY_OVERDRAFT_FEE); // everytime overdraft is created fee is charged
				break;
			}
			super.withdrawAmount(amount);		
				
				break;
		case OVER_DRAFT_OPTION_3:
			if (tempBalance < withdrawLimit) {
				super.withdrawAmount(NONSUFFICIENT_FUNDS_FEE);
				super.setTransferStatus(false);
				break;
			}
				
			super.withdrawAmount(amount); // everytime overdraft is created fee is charged
			break;
					
			default:
				break;
			}		
	}
	
	//><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><> THREAD FUNCTIONS
	//><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><> THREAD FUNCTIONS
	//><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><> THREAD FUNCTIONS

	@Override
	public void run() {
		log20Seconds(); // for daily deductions and logging
		log60Seconds(); // for monthly deductions and logging
	}
	
	private void timeThread() {
		System.out.println("Class thread called.>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> " + super.getAccountNumber());
		this.pay = Executors.newSingleThreadScheduledExecutor();
		this.pay.scheduleAtFixedRate(this, 0, 1, TimeUnit.SECONDS); // checks every 5 seconds		
	}
	
	private void log20Seconds(){ // logs day end balance and deducts any overdraft fees		
		long timeMillis = System.currentTimeMillis();
		long timeSeconds = TimeUnit.MILLISECONDS.toSeconds(timeMillis);
		if(timeSeconds % 20 == 0){
//			System.out.println("Daily dedution happened for "+ super.getAccountNumber());
			deductDailyOverdraft();
		}
	}
	
	private void deductDailyOverdraft() {
		if (chosenOverdraftOption == OVER_DRAFT_OPTION_2){
			if(super.getBalance() < 0 && super.getBalance() > this.withdrawLimit){
				super.withdrawAmount(Chequeing.DAILY_OVERDRAFT_FEE);
				System.out.println("Daily fee deducted >>>>>>>>>>>>>> $"+Chequeing.DAILY_OVERDRAFT_FEE);
			}
		}
	}
	
	private void deductMonthlyOverdraft() {
		if (chosenOverdraftOption == OVER_DRAFT_OPTION_3) {
			super.withdrawAmount(Chequeing.MONTHLY_OVERDRAFT_FEE);
			System.out.println("Monthly fee deducted >>>>>>>>>>>>>> $"+Chequeing.MONTHLY_OVERDRAFT_FEE);
		}
	}
	
	private void log60Seconds() { // logs and deducts monthly fee and interest

		long timeMillis = System.currentTimeMillis();
		long timeSeconds = TimeUnit.MILLISECONDS.toSeconds(timeMillis);
		// System.out.println(timeSeconds % 20);
		if (timeSeconds % 60 == 0){
//			System.out.println("Monthly dedution happened for " + super.getAccountNumber());
			deductMonthlyOverdraft();
		}
	}
	
	//><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><> END END END END
	//><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><> END END END END
	//><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><> END END END END
	
	
	
	@Override
	public String toString() {
		return super.toString() + "Option: " + this.chosenOverdraftOption +"\n"+ 
								  "overdraft Limit: "+ super.getLimit() +"\n" +
								  "withdraw Limit: "+ withdrawLimit+"\n";
	}

	
}
