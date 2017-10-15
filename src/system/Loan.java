package system;

import java.util.HashMap;

public class Loan  extends Account {

	private static HashMap<Integer, Loan> loadAccList = new HashMap<>();

	private Loan(int accountNumber) {
		super(accountNumber);		
	}

    /**
     * @Description: This method is a means of creating a Demand loan account.
     *              It creates the account if one with specified number does'nt already exist.
     * @param accountNumber unique number of type int to represent loan account.
     * @return account of type Loan.
     */
	public static Loan addLoan(int accountNumber) {
		if (!loadAccList.containsKey(accountNumber))
			loadAccList.put(accountNumber, new Loan(accountNumber));
		return loadAccList.get(accountNumber);
	}
}
