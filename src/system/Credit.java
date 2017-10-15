package system;

import java.util.HashMap;


public class Credit extends Account
{
	private static final int CLEPenalty = 25;
	private static HashMap<Integer, Credit> accountList = new HashMap<>();
	private boolean CLEpenaltyStatus;

	/////////////////////////////////////////////////////////////////////////////// Constructor
	private Credit(int accountNumber) {
		super(accountNumber);	
		CLEpenaltyStatus = false;
		System.out.println("Credit Account Created!");
	}
	///////////////////////////////////////////////////////////////////////////// Getters
	public static HashMap<Integer, Credit> getAccountList() {
		return accountList;
	}
	
	///////////////////////////////////////////////////////////////////////////// Other Operations
	public static Credit addAccount(int accountNumber){// only one account number can be assigned to one customer
		if (!accountList.containsKey(accountNumber)) {
			accountList.put(accountNumber, new Credit(accountNumber));
			return accountList.get(accountNumber);
		}
		return null;
	}
	
	public void cancleAccount(){
		Credit.getAccountList().remove(super.getAccountNumber());		
	}
	
	public double indebtednessCalc(){		
			return (super.getLimit() - super.getBalance()); 
	}
	
	

	@Override
	public void withdrawAmount(double amount)/* throws NegativeBalanceException*/ { 
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
