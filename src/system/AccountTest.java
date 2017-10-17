package system;

import static org.junit.Assert.*;
import org.junit.Test;

public class AccountTest {

    @Test
    public void withdrawAmountTest1(){
        Account chequeing = Chequeing.createAccount(123);
        chequeing.depositAmount(1000);
        chequeing.withdrawAmount(500);

        double expected = 500;
        double actual = chequeing.getBalance();
        double delta = 1e-9;

        assertEquals(expected, actual, delta);
    }

    @Test
    public void depositTest() {
        Account chequeing = Chequeing.createAccount(123);
        Account credit = Credit.createAccount(123);

        chequeing.depositAmount(1000);

        double expected = 1000;
        double actual = chequeing.getBalance();
        double delta = 1e-9;

        assertEquals(expected, actual, delta);
    }

    @Test
    public void transferTest(){
        Chequeing chequeing = Chequeing.createAccount(123);
        Credit credit = Credit.createAccount(123);

        chequeing.depositAmount(1000.0);
        chequeing.transferAmount(1000.0, chequeing, credit);

        boolean expected = true;
        boolean actual = (chequeing.getBalance() == 0.0 && credit.getBalance() == 1000.0);

        assertEquals(expected, actual);
    }
}