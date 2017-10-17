package system;

import static org.junit.Assert.*;
import org.junit.Test;

public class ChequeingTest {

    private double delta = 1e-9;

    @Test
    public void setLimitTest(){
        Chequeing chequeing = Chequeing.createAccount(123);
        chequeing.setLimit(1000);

        double expected = -1000.0;
        double actual = chequeing.withdrawLimit;

        assertEquals(expected, actual, delta);
    }

    @Test
    public void withdrawAmountTest1(){
        Chequeing chequeing = Chequeing.createAccount(123);
        chequeing.setLimit(1000);
        chequeing.withdrawAmount(2000);

        double expected = -25.0;
        double actual = chequeing.getBalance();

        assertEquals(expected, actual, delta);
    }

    @Test
    public void withdrawAmountTest2(){
        Chequeing chequeing = Chequeing.createAccount(123);
        chequeing.setLimit(1000);
        chequeing.withdrawAmount(500);

        double expected = -500.0;
        double actual = chequeing.getBalance();

        assertEquals(expected, actual, delta);
    }

    @Test
    public void withdrawAmountTest3(){
        Chequeing chequeing = Chequeing.createAccount(123);
        chequeing.setLimit(1000);
        chequeing.depositAmount(1000.0);
        chequeing.withdrawAmount(500.0);

        double expected = 500.0;
        double actual = chequeing.getBalance();

        assertEquals(expected, actual, delta);
    }

    @Test
    public void deductDailyOverdraftTest(){
        Chequeing chequeing = Chequeing.createAccount(123);
        chequeing.setLimit(1000);
        chequeing.depositAmount(1000.0);
        chequeing.withdrawAmount(1500.0);
        chequeing.deductDailyOverdraft();
        double actual = chequeing.getBalance();
        System.out.println(chequeing.withdrawLimit);


        double expected = -505.0;
        System.out.println(chequeing.getBalance());

        assertEquals(expected, actual);
    }

}