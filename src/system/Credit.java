package system;

import java.util.HashMap;

public class Credit extends Account {
	private static final int CLEPenalty = 25;
	private static HashMap<Integer, Credit> accountList = new HashMap<>();
	private boolean CLEpenaltyStatus;

	////////////////////////////// CONSTRUCTOR

	private Credit(int accountNumber) {
		super(accountNumber);
		System.out.println("Credit Account Created!");
	}

	//////////////////////////////////////////  GETTERS  //////////////////////////////////////////
	/**
	 * <dt><b>Description:</b><dd> This method returns the HasMap containing the credit accounts.
	 *
	 * @return Object of type HasMap mapping account numbers with credit accounts.
	 */
	static HashMap<Integer, Credit> getAccountList() {
			return accountList;
		}


    //////////////////////////////////////////  SETTERS  //////////////////////////////////////////

	/**
	 * <dt><b>Description:</b><dd> This method sets the limit of the account. This limit is the spending limit.
	 *              The CLE penalty status can be determined as soon as the credit limit is set.
	 *
	 * @param limit value of type int representing the spending limit.
     *
     * <dt><b>Precondition:</b><dd> The argument limit must be an integer value.
     * <dt><b>Postcondition:</b><dd> The spending limit of the credit account of the customer will be set to limit.
	 */
	@Override
	public void setLimit(int limit) {
		super.setLimit(limit);
		super.depositAmount(limit);
		CLEpenaltyStatus = (limit > 1000);
	}

	//////////////////////////////////////////  OPERATIONS  //////////////////////////////////////////

	/**
	 * <dt><b>Description:</b><dd> This method is the only way to create a new credit account. A new account number is passed
	 *              as a parameter and if the account is not in the system, it will be added as a new one.
	 *              The new account is then passed in to the list of accounts map(accountList).
	 *
	 * @param accountNumber value of type int representing the account number.
     *
     * <dt><b>Precondition:</b><dd> The argument accountNumber must an integer value.
     * <dt><b>Postcondition:</b><dd> A credit account will be returned; newly created or pre-existing from the list.
     *
	 * @return account of type credit.
	 */
	static Credit addAccount(int accountNumber){
			if (!accountList.containsKey(accountNumber))
				accountList.put(accountNumber, new Credit(accountNumber));
        return accountList.get(accountNumber);
	}

    /**
     *
     */
	public void cancleAccount(){
		Credit.getAccountList().remove(super.getAccountNumber());		
	}

    /**
     * <dt><b>Description:</b><dd> This method calculates the indebtedness of the account.
     *
     * <dt><b>Precondition:</b><dd>
     * <dt><b>Postcondition:</b><dd> If the balance is negative, then that balance is subtracted
     *                          from the credit limit and returned as debt.
     *
     * @return double representing indebtedness.
     */

	public double indebtednessCalc(){		
			return (super.getLimit() - super.getBalance()); 
	}

    /**
     * <dt><b>Description:</b><dd> This method is a means of withdrawing a specified amount from the account.
     *              It checks if the withdraw would result in a negative balance in which case
     *              depending on the penalty status, a penalty would be charged.
     *
     * @param amount value of type double to be withdrawn.
     *
     * <dt><b>Precondition:</b><dd> The argument amount must be a real number of type double.
     * <dt><b>Postcondition:</b><dd> The amount will be withdrawn depending on whether or not
     *               the resulting balance goes negative.
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
     * <dt><b>Description:</b><dd> This method charges the CLE penalty from the account.
     *
     * <dt><b>Precondition:</b><dd>
     * <dt><b>Postcondition:</b><dd>
     */
	private void chargeCLEPenalty(){
		super.withdrawAmount(Credit.CLEPenalty);
	}

    /**
     * <dt><b>Description:</b><dd> This method is a means of declining withdrawal. It sets the transfer status to false.
     *
     * <dt><b>Precondition:</b><dd>
     * <dt><b>Postcondition:</b><dd>
     */
	private void declineWithdrawal(){
		System.out.println("Withdrawal Declined!");
		super.setTransferStatus(false);
	}

    /**
     * <dt><b>Description:</b><dd> This method is a means of depositing a specified amount.
     *
     * <dt><b>Precondition:</b><dd>
     * <dt><b>Postcondition:</b><dd>
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
