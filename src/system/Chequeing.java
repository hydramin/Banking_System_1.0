package system;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Chequeing extends Account implements Runnable {
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
	private static HashMap<Integer, Chequeing> accountList= new HashMap<>();
	private ScheduledExecutorService pay;

    ////////////////////////////// CONSTRUCTOR

	private Chequeing(int accountNumber) {
		super(accountNumber);
		chosenOverdraftOption = OVER_DRAFT_OPTION_1;
		System.out.println("Chequeing acc created.");	
		timeThread();
	}

    //////////////////////////////////////////  GETTERS  //////////////////////////////////////////
	/**
	* <dt><b>Description:</b><dd> This method returns the HasMap containing the chequeing accounts.
	*
	* @return Object of type HasMap mapping account numbers with chequeing accounts.
	*/
	public static HashMap<Integer, Chequeing> getAccountList() {
		return accountList;
	}

    /**
     * <dt><b>Description:</b><dd>
     *
     * @return
     */
	public ScheduledExecutorService getPay() {
		return pay;
	}

    //////////////////////////////////////////  SETTERS  //////////////////////////////////////////

    /**
     * <dt><b>Description:</b><dd> This method sets the limit of the account.
     *
     * @param limit value of type int representing the spending limit.
     *
     * <dt><b>Precondition:</b><dd> The argument limit must be an integer value.
     * <dt><b>Postcondition:</b><dd> The limit of the account of the customer will be set.
     */
    @Override
    public void setLimit(int limit) {
        super.setLimit(limit);
        this.withdrawLimit = - super.getLimit();
    }

    //////////////////////////////////////////  OPERATIONS  //////////////////////////////////////////

    /**
     * <dt><b>Description:</b><dd> This method calculates the indebtedness of the account.
     *
     * <dt><b>Precondition:</b><dd>
     * <dt><b>Postcondition:</b><dd> If the balance is negative, that negative balance is returned as debt.
     *                          Other wise the debt returned is zero.
     *
     * @return double representing indebtedness.
     */
	public double indebtednessCalc(){
		if(this.getBalance() < 0)
			return (-super.getBalance());
		return 0.0;
	}

    /**
     *
     */
	public void cancleAccount(){
		Chequeing.getAccountList().remove(super.getAccountNumber());		
	}
	
    /**
     * <dt><b>Description:</b><dd> This method is the only way to create a new chequeing account. A new account number is passed
     *              as a parameter and if the account is not in the system, it will be added as a new one.
     *              The new account is then passed in to the list of accounts map(accountList).
     *
     * @param accountNumber value of type int representing the account number.
     *
     * <dt><b>Precondition:</b><dd> The argument accountNumber must an integer value.
     * <dt><b>Postcondition:</b><dd> A chequeing account will be returned; newly created or pre-existing from the list.
     *
     * @return account of type Chequeing.
     */
	public static Chequeing addAccount(int accountNumber){
		if (!accountList.containsKey(accountNumber))
			accountList.put(accountNumber, new Chequeing(accountNumber));
        return accountList.get(accountNumber);
	}
	
	/**
     * <dt><b>Description:</b><dd> This method is a means of setting the over draft option of the account.
     *
     * @param option value of type int representing the over draft option.
     *
     * <dt><b>Precondition:</b><dd> The argument option must be an integer value.
     * <dt><b>Postcondition:</b><dd> If the option is an integer between and including 1 and 3, the overdraft
     *               option will be set accordingly.
     */
	public void setOverdraftOption(int option){
		if (option < OVER_DRAFT_OPTION_1 || option > OVER_DRAFT_OPTION_3)
			throw new IllegalArgumentException();
		this.chosenOverdraftOption = option;		
	}
	
    /**
     * <dt><b>Description:</b><dd> This method will withdraw a specified amount and depending
     *              on whether or not the balance goes negative, fees will be
     *              charged based on overdraft options. If overdraft option 1 is selected, then
     *              a negative balance resulting withdrawal would be declined and NSF penalty charged.
     *              If overdraft 2 is selected,
     *
     * @param amount value of type double to be withdrawn.
     *
     * <dt><b>Description:</b><dd> The argument amount must be a real number of type double.
     * <dt><b>Description:</b><dd> The amount will be withdrawn depending on whether or not
     *               the resulting balance goes negative.
     */
	@Override
	public void withdrawAmount(double amount) {
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
			} else if (tempBalance >= withdrawLimit && tempBalance < 0) {				
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


