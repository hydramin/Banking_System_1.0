package system;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.io.FileWriter;

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
     * <dt><b>Description:</b><dd> This method is a getter that returns the account log.
     *                           Which contains records of type AccountActivity.
     *
     * @return an ArrayList of AccountActivities.
     */
    public static ArrayList<AccountActivity> getAccountLog() {
		return accountLog;
	}

    /**
     * <dt><b>Description:</b><dd> This method returns the comment of this record.
     *
     * @return A String value representing the the
     */
    public String getComment() {
		return comment;
	}



    ////////////////////////////////////////////////////  SETTERS  ////////////////////////////////////////////////////

    /**
     *
     * @param transactionType
     */
    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    /**
     *
     * @param transactionAmount
     */
    public void setTransactionAmount(double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    /**
     *
     * @param comment
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     *
     */
    public void addToList(){
        accountLog.add(this);
    }

    /**
     *
     */
    public static void sortAccountLog() {
        AccountActivity temp;
        for (int i = 1; i < accountLog.size(); i++) {
            for (int j = i; j > 0; j--) {
                if (accountLog.get(j).SIN < accountLog.get(j-1).SIN) {
                    temp = accountLog.get(j);
                    accountLog.set(j, accountLog.get(j-1));
                    accountLog.set(j-1, temp);
                }
                else if (accountLog.get(j).SIN == accountLog.get(j-1).SIN) {
                    if (accountLog.get(j).transactionDate.getTime() < accountLog.get(j-1).transactionDate.getTime()) {
                        temp = accountLog.get(j);
                        accountLog.set(j, accountLog.get(j-1));
                        accountLog.set(j-1, temp);
                    }
                }
            }
        }
    }

    /**
     *
     * @throws FileNotFoundException
     */
    public static void processAccountLogEndOfDay() throws IOException {
        sortAccountLog();
        try {
            saveAccountLog();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @throws FileNotFoundException
     */
    public static void processAccountEndOfMonth() throws IOException {
        sortAccountLog();
        try {
            saveAccountLog();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @throws FileNotFoundException
     */
    public static void saveAccountLog() throws IOException {
//        try {
//            FileOutputStream fileOut = new FileOutputStream("./accountLog.ser");
//            ObjectOutputStream out = new ObjectOutputStream(fileOut);
//            out.writeObject(accountLog);
//            out.close();
//            fileOut.close();
//            System.out.println("Serialized data is stored in ./accountLog.ser");
//        }
//        catch (IOException i){
//            i.printStackTrace();
//        }

        FileWriter writer = new FileWriter("output.txt");
        for(AccountActivity a : accountLog) {
            writer.write(a.toString());
        }
        writer.close();
    }

    /**
     *
     */
    public static void retrieveAccountLog() {
        try {
            FileInputStream fileIn = new FileInputStream("./accountLog.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            accountLog = (ArrayList<AccountActivity>) in.readObject();
            in.close();
            fileIn.close();
        }catch(IOException i) {
            i.printStackTrace();
        }catch(ClassNotFoundException c) {
            System.out.println("account logs not found");
            c.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return String.format("\nSIN: %d\nAccount#: %d\nDate: %s\nType: %s\nTrans. Amt: $%.2f\nBalance: $%.2f\nComment: %s\n",
                this.SIN, this.accountNumber, this.transactionDate, this.transactionType, this.transactionAmount,this.balance, this.comment);
    }
}
