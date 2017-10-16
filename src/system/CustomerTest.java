package system;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.testng.AssertJUnit.assertEquals;

class CustomerTest {

    @Test
    void addCustomer() {

    }

    @Test
    void terminateAccountTest() {
        Customer customer = Customer.addCustomer(123);

        Chequeing chequeing = Chequeing.createAccount(123);
        Credit credit = Credit.createAccount(123);

        chequeing.getAccountList().isEmpty();

        customer.terminateAccount(1);
        customer.terminateAccount(2);
        customer.terminateAccount(3);

        Chequeing expected = null;

        assertEquals(expected, customer.getChequeing());
        assertEquals(expected, customer.getCredit());
        assertEquals(expected, customer.getLoan());

    }

}