package apilevel;

 /*
 * AtmClientSingletonTest   4/15/16, 16:51
 *
 * By Kyrylo Havrylenko
 *
 */

import datalevel.DatabaseConnector;
import datalevel.RequestException;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests class {@code AtmClientSingleton}
 *
 * @author Kyrylo Havrylenko
 * @see AtmClientSingleton
 */
public class AtmClientSingletonTest {
    private static final double DELTA = 1e-15;

    @Test
    public void showBalance() {

        try {
            AtmClientSingleton atmClientSingleton = AtmClientSingleton.getInstance();
            atmClientSingleton.setCurrentCard(new CreditCard("0000111122223333"));
            atmClientSingleton.setConnector(new DatabaseConnector());

            double actualBalance = atmClientSingleton.showBalance();
            double balance = new DatabaseConnector().getBalance("0000111122223333", "0000");

            Assert.assertEquals(balance, actualBalance, DELTA);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void withdrawCash() {
        try {
            AtmClientSingleton atmClientSingleton = AtmClientSingleton.getInstance();
            atmClientSingleton.setCurrentCard(new CreditCard("0000111122223333"));
            atmClientSingleton.setConnector(new DatabaseConnector());

            double balance = atmClientSingleton.showBalance();
            atmClientSingleton.withdrawCash(10.0);
            if(balance != 0) {
                Assert.assertEquals(balance, balance - 10.0, DELTA);
            } else {
                Assert.assertEquals(balance, 0, DELTA);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void addCash() {
        try {
            AtmClientSingleton atmClientSingleton = AtmClientSingleton.getInstance();
            atmClientSingleton.setCurrentCard(new CreditCard("0000111122223333"));
            atmClientSingleton.setConnector(new DatabaseConnector());

            double balance = atmClientSingleton.showBalance();
            atmClientSingleton.addCash(10.0);
            Assert.assertEquals(balance + 10.0, balance, DELTA);

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void fakeController() {
        // how this could be not taxi?
        AtmClientSingleton atmClientSingleton = AtmClientSingleton.getInstance();
        atmClientSingleton.setConnector(new DatabaseConnector());
        atmClientSingleton.setCurrentCard(new CreditCard());

        try {
            atmClientSingleton.addCash(10.0);
            Assert.assertEquals(atmClientSingleton.showBalance(), 10.0, DELTA);

            atmClientSingleton.withdrawCash(10.0);
            Assert.assertEquals(atmClientSingleton.showBalance(), 0.0, DELTA);

            atmClientSingleton.changePin("0001");
            Assert.assertEquals(atmClientSingleton.getCurrentCard().getPinCode(), "0001");

            atmClientSingleton.changePin("0000");
            Assert.assertEquals(atmClientSingleton.getCurrentCard().getPinCode(), "0000");


        } catch(RequestException e) {
            e.printStackTrace();
        }

    }
}
