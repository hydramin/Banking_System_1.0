package system;

import java.util.HashMap;

public class Credit extends Account {
	private static final int CLEPenalty = 25;
	private static HashMap<Integer, Credit> creditAccList = new HashMap<>();
	private boolean CLEpenaltyStatus;

	////////////////////////////// CONSTRUCTOR

	private Credit(int accountNumber) {
		super(accountNumber);
		System.out.println("Credit Account Created!");
	}

    //////////////////////////  GETTERS  //////////////////////////

    /**
     * @Description: This method returns the HasMap containing the credit accounts.
     *
     * @return Object of type HasMap mapping account numbers with credit accounts.
     */
	public static HashMap<Integer, Credit> getCreditAccList() {
		return creditAccList;
	}

    //////////////////////////  SETTERS  //////////////////////////

    /**
     * @Description: This method sets the limit of the account. This limit is the spending limit.
     *              The CLE penalty can be determined as soon as the credit limit is set.
     *
     * @param limit value of type int representing the spending limit.
     */
    @Override
    public void setLimit(int limit) {
        super.setLimit(limit);
        super.depositAmount(limit);
        CLEpenaltyStatus = (limit > 1000) ? true : false;
    }

    //////////////////////////  OPERATIONS  //////////////////////////

    /**
     * @Description: This method is the only way to create a new credit account. A new account number is passed
     *              as a parameter and if the account is not in the system, it will be added as a new one.
     *              The new account is then passed in to the list of accounts map(accountList).
     *
     * @param accountNumber value of type int representing the account number.
     *
     * @return account of type credit.
     */
    public static Credit addCredit(int accountNumber){
        if (!creditAccList.containsKey(accountNumber))
            creditAccList.put(accountNumber, new Credit(accountNumber));
        return creditAccList.get(accountNumber);
    }

    /**
     * @Description: This method calculates the indebtedness of the account.
     *
     * @return double representing indebtedness.
     */
	public double indebtednessCalc(){		
			return (super.getLimit() - super.getBalance()); 
	}

    /**
     * @Description: This method is a means of withdrawing a specified amount from the account.
     *              It checks if the withdraw would result in a negative balance in which case
     *              depending on the penalty status, a penalty would be charged.
     *
     * @param amount value of type double to be withdrawn.
     */
	@Override
	public void withdrawAmount(double amount){
		super.setTransferStatus(true);
		if ((super.getBalance() - amount) < 0) {
			super.setTransferStatus(false);
			if (this.CLEpenaltyStatus) {
				chargeCLEPenalty();
				declineWithdrawal();
			} else
				declineWithdrawal();
		} else
			super.withdrawAmount(amount);
	}

    /**
     * @Description: This method charges the CLE penalty from the account.
     *
     */
	private void chargeCLEPenalty(){
		super.withdrawAmount(Credit.CLEPenalty);
	}

    /**
     * @Description: This method is a means of declining withdrawal. It sets the transfer status to false.
     *
     */
	private void declineWithdrawal(){
		System.out.println("Withdrawal Declined!");
		super.setTransferStatus(false);
	}

    /**
     * @Description: This method is a means of depositing a specified amount.
     *
     * @param amount value of type double to be deposited.
     */
	@Override
    public void depositAmount(double amount){
        super.depositAmount(amount);
    }

    /**
     *
     * @return
     */
	@Override
	public String toString(){
		String CLEstatusDisplay = (CLEpenaltyStatus) ? "High Credit Limit & CLE penalty" : "Low Credit Limit & No CLE penalty";
		return super.toString() + "CLE Penalty: "+CLEstatusDisplay +"\n";
	}
}
