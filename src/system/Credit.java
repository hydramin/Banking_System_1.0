package system;

import java.util.HashMap;

public class Credit extends Account
{
	private double balance;
	private int creditLimit;
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

	public double withdraw(double withdrawalAmt) throws NegativeBalanceException
	{
		if (highCreditLimitCLE(withdrawalAmt))
		{
			chargeCLEPenalty();
			declineWithdrawal();
		}
		else if (lowCreditLimitCLE(withdrawalAmt))
			declineWithdrawal();
		return balance -= withdrawalAmt;
	}

	public boolean highCreditLimitCLE(double withdrawalAmt)
	{
		return ((creditLimit > 1000) && ((balance - withdrawalAmt) + creditLimit) < 0);
	}

	public boolean lowCreditLimitCLE(double withdrawalAmt)
	{
		return ((creditLimit <= 1000) && ((balance - withdrawalAmt) + creditLimit) < 0);
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
