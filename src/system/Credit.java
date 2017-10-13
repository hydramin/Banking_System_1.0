package system;

import java.util.HashMap;

import org.omg.DynamicAny.DynValueBoxOperations;

public class Credit extends Account
{
//	private double balance; // balance is already in Account, you can add/subtract from it by withdraw and deposit methods. super.deposit/ super.withdraw
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

	/*public boolean applyCreditLimitCLE(double withdrawalAmt) { // changed from highCreditLimitCLE to applyCreditLimitCLE so it makes more sense
		return ((super.getLimit() > 1000) && ((super.getBalance() - withdrawalAmt) + super.getLimit()) < 0); // No need to add the limit, Eg: balance = 1000, Limit = 1000 (nothing is withdrawn)
																	  //withdraw = 1010$ ; (balance - withdraw) = -10 < 0 (no need to add the super.getLimit())
	}*/
		

//	public boolean lowCreditLimitCLE(double withdrawalAmt) { //////////////////////////////////////////// this method is duplicate, the above sufficies
//		return ((super.getLimit() <= 1000) && ((super.getBalance() - withdrawalAmt)/* + super.getLimit()*/) < 0);
//	}

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
//		System.out.println("Credit Limit Changed");
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
