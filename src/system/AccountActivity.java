package system;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;


public class AccountActivity{
	
	

    private int SIN;					// the social security number of the customer
    private String transactionType; // either transfer, withdraw or deposit
    private double balance;			// the balance of the current account after transaction
    private double transactionAmount; // if transaction approved , how much was withdrawn, transfered or deposited
    private Date transactionDate;	// the date of the transaction; for testing purposes date is in seconds or minutes
    private int accountNumber;		// the account number the transaction occured
    private String comment;
//    private static HashMap<Integer, AccountActivity> accountLog = new HashMap<>();	// HashMap to hold Sin and AccountActivity key
    private static LogMap<Integer, AccountActivity> accountLog = new LogMap<Integer, AccountActivity>();	// HashMap to hold Sin and AccountActivity key
    
   
    public AccountActivity(int SIN, int accountNumber, double balance){
        this.SIN = SIN;
        this.balance = balance;
        this.accountNumber = accountNumber;
        this.transactionDate = new Date();
        this.comment="";        
    }
    
    
    /////\/\////////////////////////////////////////////  GETTERS  //////////////////////////////////////////////////// 
    ////\/\/\///////////////////////////////////////////  GETTERS  ////////////////////////////////////////////////////
    ///\/==\/\//////////////////////////////////////////  GETTERS  ////////////////////////////////////////////////////
    //\/////\/\/////////////////////////////////////////  GETTERS  ////////////////////////////////////////////////////
    
    public static LogMap<Integer, AccountActivity> getAccountLog() {
		return accountLog;
	}
    
    public String getComment() {
		return comment;
	}
    ////////////////////////////////////////////////////  SETTERS  ////////////////////////////////////////////////////
    ////////////////////////////////////////////////////  SETTERS  ////////////////////////////////////////////////////
    ////////////////////////////////////////////////////  SETTERS  ////////////////////////////////////////////////////
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
        accountLog.put(SIN, this);
    }
    
    @Override
    public String toString() {    	
    	return String.format("\nSIN: %d\nAccount#: %d\nDate: %s\nType: %s\nTrans. Amt: $%.2f\nBalance: $%.2f\nComment: %s\n", 
    						this.SIN, this.accountNumber, this.transactionDate, this.transactionType, this.transactionAmount,this.balance, this.comment);
    }
    
}
