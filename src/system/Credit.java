package system;

import java.util.HashMap;

public class Credit extends Account  {

	private double withdrawLimit;	
	private static HashMap<Integer, Credit> creditAccList= new HashMap<>();


	protected Credit(int accountNumber) {
		super(accountNumber);
		// TODO Auto-generated constructor stub
	}
	
	public static Credit addCredit(int accountNumber) // only one account number can be assigned to one customer
	{
		
		if (!creditAccList.containsKey(accountNumber))
		{
			creditAccList.put(accountNumber, new Credit(accountNumber));
		}
		return creditAccList.get(accountNumber);
	}
	
}
