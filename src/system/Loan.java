package system;

import java.util.HashMap;

public class Loan  extends Account {

	private static HashMap<Integer, Loan> accountList = new HashMap<>();
	
	private Loan(int accountNumber) {
		super(accountNumber);		
	}

	public static Loan addAccount(int accountNumber){
		if (!accountList.containsKey(accountNumber)) {
			accountList.put(accountNumber, new Loan(accountNumber));
			return accountList.get(accountNumber);
		}
		return accountList.get(accountNumber);
	}
	
}
