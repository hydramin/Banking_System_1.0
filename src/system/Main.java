package system;

public class Main {
    public static void main(String[] args) {
        // some test cases

        Customer cus1 = Customer.addCustomer(123321);
        Account acc1 = new Chequeing();
        Account acc2 = new Credit();
        Account acc3 = new Loan();

        cus1.addAccount(acc1);
        cus1.addAccount(acc2);
        cus1.addAccount(acc3);

        boolean t1 = acc1 instanceof Account;
        boolean t2 = acc2 instanceof Credit;
        boolean t3 = acc3 instanceof Loan;

        System.out.println(t1);
        System.out.println(t2);
        System.out.println(t3);
        System.out.println(cus1.toString());
        System.out.println(Customer.getCustomerList().size());

    }
}
