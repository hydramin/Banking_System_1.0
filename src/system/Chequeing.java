package system;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Chequeing extends Account implements Runnable {
	// The account gets created and it will be modified
	//private double overdraftLimit; // set a the overdraft limit
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
	private boolean takeDailyFee;
	private static HashMap<Integer, Chequeing> chequeingAccList= new HashMap<>();
	private static final SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss");	
	private String later;
	private int overdraftChargeTime;
	private boolean threadOnce;
	private ScheduledExecutorService pay;

	////////////////////////////// CONSTRUCTOR
	private Chequeing(int accountNumber) {
		super(accountNumber);
		takeDailyFee = false;
		chosenOverdraftOption = OVER_DRAFT_OPTION_1; // Default overdraft option is Option 1.
		threadOnce = true;		
		System.out.println("Chequeing acc created.");		
	}

    //////////////////////////  GETTERS  //////////////////////////

	public static HashMap<Integer, Chequeing> getChequeingAccList() {
		return chequeingAccList;
	}

    //////////////////////////  SETTERS  //////////////////////////

    //////////////////////////  OPERATIONS  //////////////////////////

	public double indebtednessCalc(){
		if(this.getBalance() < 0)
			return (-super.getBalance());
		return 0.0;
	}

	public static Chequeing addChequeing(int accountNumber){
		if (!chequeingAccList.containsKey(accountNumber))
			chequeingAccList.put(accountNumber, new Chequeing(accountNumber));
        return chequeingAccList.get(accountNumber);
	}
	
	public void setOverdraftOption(int option){
		if (option < OVER_DRAFT_OPTION_1 || option > OVER_DRAFT_OPTION_3)
			throw new IllegalArgumentException();
		this.chosenOverdraftOption = option;
        // if(option == 1)
        //    pay.;
		if(option == 2)
			overdraftChargeTime = 20000; //60 sec
		if(option == 3){
			overdraftChargeTime = 60000; // 60 sec			
			takeDailyFee = true;
			setChargingTime(); // charging time is set once when overdraft is created
			System.out.println("Option 3 triggered thread.>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> "+ threadOnce);
			if(threadOnce)
				deductionThread();
		}
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
				this.takeDailyFee = true;
				super.withdrawAmount(amount + DAILY_OVERDRAFT_FEE); // everytime overdraft is created fee is charged
				setChargingTime(); // charging time is set once when overdraft is created
				if (threadOnce)
					deductionThread();
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
	
	@Override
	public void run(){
        // try {
			endDayOptionTwoCharge();
        // } catch (NegativeBalanceException e1) {
        //		e1.printStackTrace();
        // }
	}
	
	private void deductionThread() {
		System.out.println("Deduction thread called.>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> "+ threadOnce);
		this.pay = Executors.newSingleThreadScheduledExecutor();
		this.pay.scheduleAtFixedRate(this, 0, 1, TimeUnit.SECONDS); // checks every 5 seconds
		this.threadOnce = false;
	}
	
	private void endDayOptionTwoCharge(){
		String now = time.format(System.currentTimeMillis());
		if (chosenOverdraftOption == OVER_DRAFT_OPTION_2) {
			if (super.getBalance() < 0 && now.equals(later)) {
				super.withdrawAmount(Chequeing.DAILY_OVERDRAFT_FEE);			
				this.takeDailyFee = true;
				setChargingTime();
			}
		} else if(chosenOverdraftOption == OVER_DRAFT_OPTION_3) {
			if (now.equals(later)) {
				super.withdrawAmount(Chequeing.MONTHLY_OVERDRAFT_FEE);
				System.out.println("Monthly fee deducted >>>>>>>>>>>>>> $"+Chequeing.MONTHLY_OVERDRAFT_FEE);
				takeDailyFee = true;
				setChargingTime();
			}
		}
	}
	
	private void setChargingTime(){
		if(takeDailyFee){			
			later = time.format(System.currentTimeMillis()+overdraftChargeTime); // the time to charge the account will be set at after 5 / 10 secs
			takeDailyFee = false; // make false after time is set to : after 5 mins
			System.out.println("NEW TIME >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> "+ later );
		}
	}

	@Override
	public String toString() {
		return super.toString() + "Option: " + this.chosenOverdraftOption +"\n"+ 
								  "overdraft Limit: "+ super.getLimit() +"\n" +
								  "withdraw Limit: "+ withdrawLimit+"\n";
	}
}
