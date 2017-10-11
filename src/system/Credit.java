package system;

import java.util.HashMap;

public class Credit extends Account
{
	private static final int CLEPenalty = 25;
	private static HashMap<Integer, Credit> creditAccList = new HashMap<>();
	private static boolean CLEpenaltyStatus;

	/////////////////////////////////////////////////////////////////////////////// Constructor
	private Credit(int accountNumber) {
		super(accountNumber);	
		CLEpenaltyStatus = false;
		System.out.println("Credit Account Created!");
	}
	
	///////////////////////////////////////////////////////////////////////////// Other Operations
	public static Credit addCredit(int accountNumber){// only one account number can be assigned to one customer
		if (!creditAccList.containsKey(accountNumber)) {
			creditAccList.put(accountNumber, new Credit(accountNumber));
			return creditAccList.get(accountNumber);
		}
		return null;
	}

	@Override
	public void withdrawAmount(double amount) throws NegativeBalanceException { 
		if (applyCreditLimitCLE(amount)) {
			chargeCLEPenalty();
			declineWithdrawal();
		} else
			declineWithdrawal();
	}

	private boolean applyCreditLimitCLE(double withdrawalAmt) {
		return (CLEpenaltyStatus && ((super.getBalance() - withdrawalAmt)) < 0);

	}

	private void chargeCLEPenalty() throws NegativeBalanceException {
		super.withdrawAmount(Credit.CLEPenalty);
	}

	private void declineWithdrawal() throws NegativeBalanceException {
		throw new NegativeBalanceException("Balance is negative");
	}
	
	@Override
	public void setLimit(int limit) { // the CLE penalty can be determined as soon as the Credit Limit is set
		super.setLimit(limit);
		super.depositAmount(limit);
		CLEpenaltyStatus = (limit > 1000) ? true : false;
	}
	
	@Override
	public void depositAmount(double amount) { // Can not deposite into a credit account, transfer function is better
		throw new UnsupportedOperationException();
	}

	@Override
	public String toString() {
		String CLEstatusDisplay = (CLEpenaltyStatus)?"High Credit Limit & CLE penalty": "Low Credit Limit & No CLE penalty";
		return super.toString() + "CLE Penalty: "+CLEstatusDisplay +"\n";
	}
}
