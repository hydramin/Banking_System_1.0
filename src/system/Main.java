package system;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        // some test cases

        /*Customer cus1 = Customer.addCustomer(123);
        Account acc1 = Chequeing.addChequeing(666);
        	Chequeing.setOverdraftOption(acc1, 1);
        	acc1.depositAmount(999.89);
//        Account acc1_ = Chequeing.addChequeing(666);
        Account acc2 = new Credit();
        Account acc3 = new Loan();

        cus1.addAccount(acc1);
        cus1.addAccount(acc2);
        cus1.addAccount(acc3);

        boolean t1 = acc1 instanceof Account;
        boolean t2 = acc2 instanceof Credit;
        boolean t3 = acc3 instanceof Loan;
        boolean t4 = acc1 instanceof Chequeing;

        System.out.println(t1);
        System.out.println(t2);
        System.out.println(t3);
        System.out.println(t4);
        for (Integer c : Customer.getCustomerList().keySet()) {
			System.out.println(Customer.getCustomerList().get(c).toString());
		}
        System.out.println(Customer.getCustomerList().size());*/
    	
    	Date date = new Date();
    	SimpleDateFormat dFormat = new SimpleDateFormat("HH");
    	String hour = dFormat.format(date);
    	System.out.println(hour);
    	
    	
    	System.out.println(date.getTime());
    	System.out.println(System.currentTimeMillis()/ 86400000);

    }
}
