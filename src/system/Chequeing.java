package system;

import java.util.HashMap;
import java.util.Set;

import javax.xml.transform.Templates;

public class Chequeing extends Account {
	
	// The account gets created and it will be modified

	private int overdraftLimit; // set a the overdraft limit
	private int chosenOverdraftOption;
	private static final int OVER_DRAFT_OPTION_1 = 1; // No Overdraft Protection: with this option, if a withdrawal from the
					       							//	checking account would cause the balance to be less than 0, then the withdrawal will
					       							//	be declined, and a Non-Sufficient Funds (NSF) penalty will be charged.
	private static final int OVER_DRAFT_OPTION_2 = 2; // Pay Per Use Overdraft Protection.
	private static final int OVER_DRAFT_OPTION_3 = 3; // Monthly Fixed Fee Overdraft Protection.
	private static final double NONSUFFICIENT_FUNDS_FEE = 25; // 25 dollars fee
	private static HashMap<Integer, Chequeing> chequeingAccList= new HashMap<>();

	
	////////////////////////////////////////////////////////////////////////////////////////// constructor
	private Chequeing(int accountNumber) {
		super();
		overdraftLimit = 0;
		chosenOverdraftOption = OVER_DRAFT_OPTION_1; // Default overdraft option is Option 1.
	}
	//////////////////////////////////////////////////////////////////////////////////////////
	public static Chequeing addChequeing(int accountNumber) // only one account number can be assigned to one customer
	{
		int key = accountNumber;
		Chequeing c = chequeingAccList.get(key);
		if (c == null)
		{
			c = new Chequeing(accountNumber);
			Chequeing.chequeingAccList.put(key, c);
		} else {
			throw new IllegalArgumentException("\n this chequing account already taken");
		}
		return c;
	}
	
	public static void setOverdraftOption(Account account, int option) // chequeing
	{
		Chequeing chequeing = (Chequeing) account;
		if (option < OVER_DRAFT_OPTION_1 || option > OVER_DRAFT_OPTION_3) {
			throw new IllegalArgumentException();
		} 
		chequeing.chosenOverdraftOption = option;
	}
	
	public void setOverdraftLimit(int overdraftLimit) {
		this.overdraftLimit = overdraftLimit;
	}
	
	@Override
	public void withdrawAmount(double amount) {
		// TODO Auto-generated method stub
		
		if (super.getBalance() < 0) {
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
				double withdrawLimit = - overdraftLimit;
				if(tempBalance < withdrawLimit){
					// can't withdraw, charge NSF
					super.withdrawAmount(NONSUFFICIENT_FUNDS_FEE);
					break;
				}
				super.withdrawAmount(amount);
				// trigger the day end charge thread
				
				break;
			case OVER_DRAFT_OPTION_3:

				break;

			default:
				break;
			}
		}
	}
	
	@Override
	public String toString() {
		return super.toString() + " Option: " + this.chosenOverdraftOption + "\n";
	}

}
