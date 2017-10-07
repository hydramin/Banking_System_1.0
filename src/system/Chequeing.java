package system;

public class Chequeing extends Account {
	
	// The account gets created and it will be modified

	private int overdraftLimit; // set a the overdraft limit
	private byte chosenOverdraftOption;
	public final byte OVER_DRAFT_OPTION_1 = 1; // No Overdraft Protection: with this option, if a withdrawal from the
											//	checking account would cause the balance to be less than 0, then the withdrawal will
											//	be declined, and a Non-Sufficient Funds (NSF) penalty will be charged.
	public final byte OVER_DRAFT_OPTION_2 = 2; // Pay Per Use Overdraft Protection.
	public final byte OVER_DRAFT_OPTION_3 = 3; // Monthly Fixed Fee Overdraft Protection.
	
	public void setOverdraftOption(byte option) // chequeing
	{
		if (option < OVER_DRAFT_OPTION_1 || option > OVER_DRAFT_OPTION_3) {
			throw new IllegalArgumentException();
		} 
		chosenOverdraftOption = option;
	}
}
