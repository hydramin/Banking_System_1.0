package system;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;

public class AccountActivity{
    private int SIN;    // the social security number of the customer
    private String transactionType; // either transfer, withdraw or deposit
    private double balance; // the balance of the current account after transaction
    private double transactionAmount; // if transaction approved , how much was withdrawn, transferred or deposited
    private Date transactionDate;	// the date of the transaction; for testing purposes date is in seconds or minutes
    private int accountNumber;		// the account number the transaction occurred
    private String comment;
   // private static LogMap<Integer, AccountActivity> accountLog = new LogMap<Integer, AccountActivity>();	// HashMap to hold Sin and AccountActivity key
    private static ArrayList<AccountActivity> accountLog = new ArrayList<>();
    
   
    public AccountActivity(int SIN, int accountNumber, double balance){
        this.SIN = SIN;
        this.balance = balance;
        this.accountNumber = accountNumber;
        this.transactionDate = new Date();
        this.comment="";        
    }
    
    ////////////////////////////////////////////////////  GETTERS  ////////////////////////////////////////////////////

    /**
    public static LogMap<Integer, AccountActivity> getAccountLog() {
		return accountLog;
	}*/
    
    public String getComment() {
		return comment;
	}

    ////////////////////////////////////////////////////  SETTERS  ////////////////////////////////////////////////////

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public void setTransactionAmount(double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void addToList(){
        //accountLog.put(SIN, this);
        accountLog.add(this);
    }

    
    @Override
    public String toString() {    	
    	return String.format("\nSIN: %d\nAccount#: %d\nDate: %s\nType: %s\nTrans. Amt: $%.2f\nBalance: $%.2f\nComment: %s\n", 
    						this.SIN, this.accountNumber, this.transactionDate, this.transactionType, this.transactionAmount,this.balance, this.comment);
    }
    


    public static void sortAccountLog() {
        AccountActivity temp;
        for (int i = 0; i < accountLog.size(); i++) {
            for (int j = i; j > 0; j--) {
                if (accountLog.get(j).SIN < accountLog.get(j-1).SIN) {
                    temp = accountLog.get(j-1);
                    accountLog.set(j, accountLog.get(j-1));
                    accountLog.set(j-1, temp);
                }
                else if (accountLog.get(j).SIN == accountLog.get(j-1).SIN) {
                    if (accountLog.get(j).transactionDate.getTime() > accountLog.get(j-1).transactionDate.getTime()) {
                        temp = accountLog.get(j-1);
                        accountLog.set(j, accountLog.get(j-1));
                        accountLog.set(j-1, temp);
                    }
                }
            }
        }
    }

    public static void processAccountLogEndOfDay() {

    }

    public static void processAccountEndOfMonth() {

    }

    public static void saveAccountLog() {

    }

    public static void retrieveAccountLog() {

    }
}
