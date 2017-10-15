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
     *
     * @return
     */
	public static HashMap<Integer, Credit> getCreditAccList() {
		return creditAccList;
	}

    //////////////////////////  SETTERS  //////////////////////////

    /**
     *
     * @param limit
     */
    @Override
    public void setLimit(int limit) {
        // the CLE penalty can be determined as soon as the Credit Limit is set
        super.setLimit(limit);
        super.depositAmount(limit);
        CLEpenaltyStatus = (limit > 1000) ? true : false;
    }

    //////////////////////////  OPERATIONS  //////////////////////////

    /**
     *
     * @param accountNumber
     * @return
     */
    public static Credit addCredit(int accountNumber){
        if (!creditAccList.containsKey(accountNumber))
            creditAccList.put(accountNumber, new Credit(accountNumber));
        return creditAccList.get(accountNumber);
    }

    /**
     *
     * @return
     */
	public double indebtednessCalc(){		
			return (super.getLimit() - super.getBalance()); 
	}

    /**
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
     *
     */
	private void chargeCLEPenalty(){
		super.withdrawAmount(Credit.CLEPenalty);
	}

    /**
     *
     */
	private void declineWithdrawal(){
		System.out.println("Withdrawal Declined!");
		super.setTransferStatus(false);
	}

    /**
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
