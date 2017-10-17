package system;

import static org.junit.Assert.*;
import org.junit.Test;

public class CreditTest {

    @Test
    public void withdrawAmountTest1(){
        Credit credit = Credit.createAccount(123);
        credit.setLimit(1000);
        credit.withdrawAmount(1000);

        System.out.println(credit.getBalance());

        double expected = 0.0;
        double actual = credit.getBalance();
        double delta = 1e-9;

        assertEquals(expected, actual, delta);
    }

    @Test
    public void withdrawAmountTest2(){
        Credit credit = Credit.createAccount(123);
        credit.setLimit(1000);
        credit.withdrawAmount(1001);

        System.out.println(credit.getBalance());

        double expected = 1000.0;
        double actual = credit.getBalance();
        double delta = 1e-9;

        assertEquals(expected, actual, delta);
    }

    @Test
    public void withdrawAmountTest3(){
        Credit credit = Credit.createAccount(123);
        credit.setLimit(2000);
        credit.withdrawAmount(2001);

        System.out.println(credit.getBalance());

        double expected = 1971.0;
        double actual = credit.getBalance();
        double delta = 1e-9;

        assertEquals(expected, actual, delta);
    }

    @Test
    public void setLimitTest1(){
        Credit credit = Credit.createAccount(123);
        credit.setLimit(2000);

        double expected = 2000.0;
        double actual = credit.getBalance();
        double delta = 1e-9;

        assertEquals(expected, actual, delta);
    }

    @Test
    public void setLimitTest2(){
        Credit credit = Credit.createAccount(123);
        credit.setLimit(2000);

        boolean expected = true;
        boolean actual = credit.CLEpenaltyStatus;

        assertEquals(expected, actual);
    }

    @Test
    public void indebtednessCalcTest(){
        Credit credit = Credit.createAccount(123);
        credit.setLimit(2000);
        credit.withdrawAmount(2001.0);

        double expected = 29.0;
        double actual = credit.indebtednessCalc();
        double delta = 1e-9;

        assertEquals(expected, actual, delta);
    }

}