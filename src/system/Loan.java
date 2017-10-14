package system;

import java.util.HashMap;

public class Loan  extends Account {

	private static HashMap<Integer, Loan> loadAccList = new HashMap<>();
	
	private Loan(int accountNumber) {
		super(accountNumber);		
	}

	public static Loan addLoan(int accountNumber){
		if (!loadAccList.containsKey(accountNumber)) {
			loadAccList.put(accountNumber, new Loan(accountNumber));
			return loadAccList.get(accountNumber);
		}
		return loadAccList.get(accountNumber);
	}
	
}
