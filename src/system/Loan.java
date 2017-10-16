package system;

import java.util.HashMap;

public class Loan extends Account {

	private static HashMap<Integer, Loan> accountList = new HashMap<>();

	private Loan(int accountNumber) {
		super(accountNumber);
	}

	/**
	 * @Description: This method is a means of creating a Demand loan account.
	 *               It creates the account if one with specified number does'nt
	 *               already exist.
	 * @param accountNumber
	 *            unique number of type int to represent loan account.
	 *
	 * <dt><b>Precondition:</b><dd> The argument accountNumber must an integer value.
     * <dt><b>Postcondition:</b><dd> A Demand loan account will be returned; newly created or pre-existing from the list.
     *
	 * @return account of type Loan.
	 */
	public static Loan addAccount(int accountNumber) {
		if (!accountList.containsKey(accountNumber))
			accountList.put(accountNumber, new Loan(accountNumber));
		return accountList.get(accountNumber);
	}
}
