package system;

import java.util.HashMap;

public class Chequeing extends Account {
	
	// The account gets created and it will be modified

	private int overdraftLimit; // set a the overdraft limit
	private int chosenOverdraftOption;
	public static final int OVER_DRAFT_OPTION_1 = 1; // No Overdraft Protection: with this option, if a withdrawal from the
											//	checking account would cause the balance to be less than 0, then the withdrawal will
											//	be declined, and a Non-Sufficient Funds (NSF) penalty will be charged.
	public static final int OVER_DRAFT_OPTION_2 = 2; // Pay Per Use Overdraft Protection.
	public static final int OVER_DRAFT_OPTION_3 = 3; // Monthly Fixed Fee Overdraft Protection.
	private static HashMap<Integer, Chequeing> chequeingAccList= new HashMap<>();

	
	////////////////////////////////////////////////////////////////////////////////////////// constructor
	private Chequeing(int accountNumber) {
		super();
		overdraftLimit = 0;
		chosenOverdraftOption = OVER_DRAFT_OPTION_1;
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
	
	@Override
	public String toString() {
		return super.toString() + " Option: " + this.chosenOverdraftOption + "\n";
	}

}
