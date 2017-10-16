package system;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class Credit extends Account {
	private static final int CLEPenalty = 25;
	private static HashMap<Integer, Credit> accountList = new HashMap<>();
	private boolean CLEpenaltyStatus;

	////////////////////////////// CONSTRUCTOR

	private Credit(int accountNumber) {
		super(accountNumber);
//		super.record("-", 0.0, "Chequeing account created");
	}

	///////////////////////////////////////////////////////////////////////////// Getters
/**
 * @Description: This method returns the HasMap containing the credit accounts.
 *
 * @return Object of type HasMap mapping account numbers with credit accounts.
 */	
public static HashMap<Integer, Credit> getAccountList() {
		return accountList;
	}
	
	///////////////////////////////////////////////////////////////////////////// Other Operations
/**
 * @Description: This method is the only way to create a new credit account. A new account number is passed
 *              as a parameter and if the account is not in the system, it will be added as a new one.
 *              The new account is then passed in to the list of accounts map(accountList).
 *
 * @param accountNumber value of type int representing the account number.
 *
 * @return account of type credit.
 */	
	public static Credit createAccount(int accountNumber){
			if (!accountList.containsKey(accountNumber)) {
				accountList.put(accountNumber, new Credit(accountNumber));
				return accountList.get(accountNumber);
			}
			return null;
	}
		
	public void cancleAccount(){
		Credit.getAccountList().remove(super.getAccountNumber());
		super.record("-", 0.0, String.format("Credit account: %d cancled.", super.getAccountNumber()));
	}
	


    ////////////////////////////////////////////////////  GETTERS  ////////////////////////////////////////////////////
    ////////////////////////////////////////////////////  GETTERS  ////////////////////////////////////////////////////
    ////////////////////////////////////////////////////  GETTERS  ////////////////////////////////////////////////////
    ////////////////////////////////////////////////////  GETTERS  ////////////////////////////////////////////////////

  

    ////////////////////////////////////////////////////  SETTERS  ////////////////////////////////////////////////////
    ////////////////////////////////////////////////////  SETTERS  ////////////////////////////////////////////////////
    ////////////////////////////////////////////////////  SETTERS  ////////////////////////////////////////////////////
    ////////////////////////////////////////////////////  SETTERS  ////////////////////////////////////////////////////

    /**
     * @Description: This method sets the limit of the account. This limit is the spending limit.
     *              The CLE penalty can be determined as soon as the credit limit is set.
     *
     * @param limit value of type int representing the spending limit.
     */
    @Override
    public void setLimit(int limit) {
    	Account.setComment(String.format("Credit limit set to $%d", limit));
        super.setLimit(limit);
        Account.setTransfer(true);
        super.depositAmount(limit);
        CLEpenaltyStatus = (limit > 1000) ? true : false;
    }

    ////////////////////////////////////////////////////  OPERATIONS  ////////////////////////////////////////////////////
    ////////////////////////////////////////////////////  OPERATIONS  ////////////////////////////////////////////////////
    ////////////////////////////////////////////////////  OPERATIONS  ////////////////////////////////////////////////////
    ////////////////////////////////////////////////////  OPERATIONS  ////////////////////////////////////////////////////

 

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
		super.setComment(String.format("$%.2f withdrawn from Acc: %d", amount,super.getAccountNumber()));
		super.setTransferStatus(true);
		if ((super.getBalance() - amount) < 0) {
			super.setTransferStatus(false);
			if (this.CLEpenaltyStatus) {
				chargeCLEPenalty();
				declineWithdrawal();
			} else{
				declineWithdrawal();
			}
		} else{
			super.withdrawAmount(amount);
		}
	}

    /**
     * @Description: This method charges the CLE penalty from the account.
     *
     */
	private void chargeCLEPenalty(){
		Account.setComment(String.format("Credit account charged fee. Transaction passed credit limit $%d.", Credit.CLEPenalty));
		super.withdrawAmount(Credit.CLEPenalty);
	}

    /**
     * @Description: This method is a means of declining withdrawal. It sets the transfer status to false.
     *
     */
	private void declineWithdrawal(){
		super.record("Withdrawal", super.getNoTransaction(), "Withdrawal Declined!");
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
	//><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><> THREAD FUNCTIONS
	//><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><> THREAD FUNCTIONS
	//><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><> THREAD FUNCTIONS

		
		@Override
		protected void log20Seconds(){ // logs day end balance and deducts any overdraft fees	
			if(super.time() % 20 == 0){
				super.record("-", super.getNoTransaction(), END_DAY);
			}
		}		
	
		
	
		@Override
		protected void log60Seconds() { // logs and deducts monthly fee and interest

			if (super.time() % 60 == 0){
				super.record("-", super.getNoTransaction(), END_MONTH);
			}
		}
		
	//><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><> END END END END
	//><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><> END END END END
	//><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><> END END END END
	@Override
	public String toString(){
		String CLEstatusDisplay = (CLEpenaltyStatus) ? "High Credit Limit & CLE penalty" : "Low Credit Limit & No CLE penalty";
		return super.toString() + "CLE Penalty: "+CLEstatusDisplay +"\n";
	}


}
