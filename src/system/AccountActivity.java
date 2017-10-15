package system;

import java.util.Date;
import java.util.HashMap;

public class AccountActivity {

    public String transactionType;
    public double balance;
    public double transactionAmount;
    public Date transactionDate;
    public int SIN;
    public int accountNumber;
    public static HashMap<Integer, AccountActivity> accountLog = new HashMap<>();

    public AccountActivity(int SIN, int accountNumber){
        this.SIN = SIN;
        this.accountNumber = accountNumber;
        this.transactionDate = new Date();
    }


    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public void setTransactionAmount(double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void addToList(){
        accountLog.put(SIN, this);
    }

    public static void sortAccountLog() {

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
