package system;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class Loan extends Account {

	private static HashMap<Integer, Loan> accountList = new HashMap<>();

	private Loan(int accountNumber) {
		super(accountNumber);
//		super.record("-", super.getNoTransaction(), "On Demand Loan account created.");
	}

	/**
	 * @Description: This method is a means of creating a Demand loan account.
	 *               It creates the account if one with specified number does'nt
	 *               already exist.
	 * @param accountNumber
	 *            unique number of type int to represent loan account.
	 * @return account of type Loan.
	 */
	public static Loan createAccount(int accountNumber) {
		if (!accountList.containsKey(accountNumber)) {
			accountList.put(accountNumber, new Loan(accountNumber));
			return accountList.get(accountNumber);
		}
		return accountList.get(accountNumber);
	}
	
	@Override
	public void depositAmount(double amount) {
		super.setComment(String.format("$%.2f deposited in On Demand Loan Acc: %d", amount, super.getAccountNumber()));
		super.depositAmount(amount);
	}
	
	
	//><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><> THREAD FUNCTIONS
	//><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><> THREAD FUNCTIONS
	//><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><> THREAD FUNCTIONS

	
	@Override
	protected void log20Seconds(){ // logs day end balance and deducts any overdraft fees		
		long timeMillis = System.currentTimeMillis();
		long timeSeconds = TimeUnit.MILLISECONDS.toSeconds(timeMillis);
		if(timeSeconds % 20 == 0){
			super.record("-", super.getNoTransaction(), END_DAY);
		}
	}
	

	@Override
	protected void log60Seconds() { // logs and deducts monthly fee and interest
		long timeMillis = System.currentTimeMillis();
		long timeSeconds = TimeUnit.MILLISECONDS.toSeconds(timeMillis);
		if (timeSeconds % 60 == 0){
			super.record("-", super.getNoTransaction(), END_MONTH);
		}
	}
	
	//><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><> END END END END
	//><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><> END END END END
	//><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><> END END END END
	

}
