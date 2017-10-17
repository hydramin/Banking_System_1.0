package system;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author: Amin Adam
 * @author: Sohrab Oryakhel
 * @invariant: 100000 >=SIN >= 999999
 * @invariant: transactionAmount >= 0.0;
 * @invariant: 100 >= accountNumber >= 999
 *
 */
public class AccountActivity implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int SIN;    // the social security number of the customer
    private String transactionType; // either transfer, withdraw or deposit
    private double balance; // the balance of the current account after transaction
    private double transactionAmount; // if transaction approved , how much was withdrawn, transferred or deposited
    private Date transactionDate;	// the date of the transaction; for testing purposes date is in seconds or minutes
    private int accountNumber;		// the account number the transaction occurred
    private String comment;
    private static ArrayList<AccountActivity> accountLog = new ArrayList<>();
    
   
    public AccountActivity(int SIN, int accountNumber, double balance){
        this.SIN = SIN;
        this.balance = balance;
        this.accountNumber = accountNumber;
        this.transactionDate = new Date();
        this.comment="";        
    }
    
    ////////////////////////////////////////////////////  GETTERS  ////////////////////////////////////////////////////
    ////////////////////////////////////////////////////  GETTERS  ////////////////////////////////////////////////////
    ////////////////////////////////////////////////////  GETTERS  ////////////////////////////////////////////////////
    ////////////////////////////////////////////////////  GETTERS  ////////////////////////////////////////////////////


    /**
     * @Description This method is a getter that returns the account log.
     *                           Which contains records of type AccountActivity.
     *
     * @return an ArrayList of AccountActivities.
     */
    public static ArrayList<AccountActivity> getAccountLog() {
		return accountLog;
	}

    /**
     * @Description This method returns the comment of this record.
     *
     * @return A String value representing the the
     */
    public String getComment() {
		return comment;
	}



    ////////////////////////////////////////////////////  SETTERS  ////////////////////////////////////////////////////
    ////////////////////////////////////////////////////  SETTERS  ////////////////////////////////////////////////////
    ////////////////////////////////////////////////////  SETTERS  ////////////////////////////////////////////////////
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
     * @Description This method sorts the account logs by SIN then by time.
     *                          This is a simple insertion sort algorithm which contains elements
     *                          of type AccountActivity which are records. Each record has a SIN
     *                          which is used in comparison and if the SINs are the same then date
     *                          is used to sort.
     *
     * <dt><b>Loop Invariant:</b><dd> At each step, accountLog<1..j-1> contains the first j-1 elements in SORTED order.
     *
     * @Precondition: The accountLog must not be empty.
     * @Postcondition: The accountLog will be sorted
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
     * @Description This method sorts the accountLog and outputs it to a file at the end of day.
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
     * @Description This method sorts the accountLog and outputs it to a file at the end of month.
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
     *@Description This method creates a text file and writes to it the records in account log.
     *@Preconditon: accountLog arraylist must not be null
     *@Postcondition: it saves the object to a file for later use
     * @throws FileNotFoundException
     */
    public static void saveAccountLog() throws IOException {

    	FileOutputStream writer = new FileOutputStream("output.ser");
    	ObjectOutputStream stream = new ObjectOutputStream(writer);
    	stream.writeObject(accountLog);
    	stream.close();
    }

    /**
     * @Description: This method retrieves the saved account activity log from file
     * @Precondition: The saved file must be available
     * @Postcondition: The log file is restored and appended into the accountLog function
     */
	@SuppressWarnings("unchecked")
	public static void retrieveAccountLog() {
		try {
			FileInputStream input = new FileInputStream("output.ser");
			ObjectInputStream stream = new ObjectInputStream(input);
			ArrayList<AccountActivity> temp;
			temp = (ArrayList<AccountActivity>) stream.readObject();
			accountLog.addAll(temp);
			stream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

    @Override
    public String toString() {
        return String.format("\nSIN: %d\nAccount#: %d\nDate: %s\nType: %s\nTrans. Amt: $%.2f\nBalance: $%.2f\nComment: %s\n",
                this.SIN, this.accountNumber, this.transactionDate, this.transactionType, this.transactionAmount,this.balance, this.comment);
    }
}
