package system;

import java.util.HashMap;

public class Credit extends Account
{
	private double balance;
	private int CLEPenalty;
	private static HashMap<Integer, Credit> creditAccList = new HashMap<>();

	protected Credit(int accountNumber)
	{
		super(accountNumber);
		// TODO Auto-generated constructor stub
	}
	
	public static Credit addCredit(int accountNumber) // only one account number can be assigned to one customer
	{
		if (!creditAccList.containsKey(accountNumber))
			creditAccList.put(accountNumber, new Credit(accountNumber));
		return creditAccList.get(accountNumber);
	}

    @Override
    public void withdrawAmount(double amount) throws NegativeBalanceException
    {
        if (highCreditLimitCLE(amount))
        {
            chargeCLEPenalty();
            declineWithdrawal();
        }
        else if (lowCreditLimitCLE(amount))
            declineWithdrawal();
    }

    public boolean highCreditLimitCLE(double withdrawalAmt)
	{
		return ((super.getLimit() > 1000) && ((balance - withdrawalAmt) + super.getLimit()) < 0);
	}

	public boolean lowCreditLimitCLE(double withdrawalAmt)
	{
		return ((super.getLimit() <= 1000) && ((balance - withdrawalAmt) + super.getLimit()) < 0);
	}

    private void chargeCLEPenalty()
    {
        balance -= CLEPenalty;
    }

	public void declineWithdrawal() throws NegativeBalanceException
	{
		throw new NegativeBalanceException("Balance is negative");
	}

    @Override
    public String toString() {
        return super.toString();
    }
}
