package system;

import static org.junit.Assert.*;
import org.junit.Test;

public class AccountTest {

    @Test
    public void depositTest() {
        Chequeing chequeing = Chequeing.createAccount(123);
        Credit credit = Credit.createAccount(123);

        chequeing.depositAmount((double)1000);
        double expected = 1000;
        double actual = chequeing.getBalance();

        assertEquals(expected, actual);
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