package system;

import java.util.HashMap;

import org.omg.DynamicAny.DynValueBoxOperations;

public class Credit extends Account
{
	private static final int CLEPenalty = 25;
	private static HashMap<Integer, Credit> creditAccList = new HashMap<>();
	private boolean CLEpenaltyStatus;

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
	public void withdrawAmount(double amount)/* throws NegativeBalanceException*/ { 
//		super.withdrawAmount(amount);
		double tempBalance = super.getBalance() - amount;
		super.setTransferStatus(true);
		if (tempBalance < 0) {
			super.setTransferStatus(false);
			if (this.CLEpenaltyStatus) {
				chargeCLEPenalty();
				declineWithdrawal();
			} else
				declineWithdrawal();
		} else{
			super.withdrawAmount(amount);			
		}
	}


	private void chargeCLEPenalty()/* throws NegativeBalanceException*/ {
		super.withdrawAmount(Credit.CLEPenalty);
	}

	private void declineWithdrawal() /*throws NegativeBalanceException*/ {
//		throw new NegativeBalanceException("Balance is negative");
		System.out.println("Withdrawal Declined!");
		super.setTransferStatus(false);
	}
	
	@Override
	public void setLimit(int limit) { // the CLE penalty can be determined as soon as the Credit Limit is set
		super.setLimit(limit);
		super.depositAmount(limit);
		CLEpenaltyStatus = (limit > 1000) ? true : false;
	}
	
	@Override
		public void depositAmount(double amount) { // Can not deposite into a credit account, transfer function is better
			super.depositAmount(amount);
		}


	@Override
	public String toString() {
		String CLEstatusDisplay = (CLEpenaltyStatus)?"High Credit Limit & CLE penalty": "Low Credit Limit & No CLE penalty";
		return super.toString() + "CLE Penalty: "+CLEstatusDisplay +"\n";
	}
}
