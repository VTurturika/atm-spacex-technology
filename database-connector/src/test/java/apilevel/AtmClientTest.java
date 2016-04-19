package apilevel;

 /*
 * AtmClientTest   4/15/16, 16:51
 *
 * By Kyrylo Havrylenko
 *
 */

import datalevel.DatabaseConnector;
import datalevel.RequestException;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests class {@code AtmClient}
 *
 * @author Kyrylo Havrylenko
 * @see AtmClient
 */
public class AtmClientTest {
    private static final double DELTA = 1e-15;

    @Test
    public void showBalance() {

        try {
            AtmClient atmClient = AtmClient.getInstance();
            atmClient.setCurrentCard(new CreditCard("0000111122223333"));
            atmClient.setConnector(new DatabaseConnector());

            double actualBalance = atmClient.showBalance();
            double balance = new DatabaseConnector().getBalance("0000111122223333", "0000");

            Assert.assertEquals(balance, actualBalance, DELTA);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void withdrawCash() {
        try {
            AtmClient atmClient = AtmClient.getInstance();
            atmClient.setCurrentCard(new CreditCard("0000111122223333"));
            atmClient.setConnector(new DatabaseConnector());

            double balance = atmClient.showBalance();
            atmClient.withdrawCash(10.0);
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
            AtmClient atmClient = AtmClient.getInstance();
            atmClient.setCurrentCard(new CreditCard("0000111122223333"));
            atmClient.setConnector(new DatabaseConnector());

            double balance = atmClient.showBalance();
            atmClient.addCash(10.0);
            Assert.assertEquals(balance + 10.0, balance, DELTA);

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void fakeController() {
        // how this could be not taxi?
        AtmClient atmClient = AtmClient.getInstance();
        atmClient.setConnector(new DatabaseConnector());
        atmClient.setCurrentCard(new CreditCard());

        try {
            atmClient.addCash(10.0);
            Assert.assertEquals(atmClient.showBalance(), 10.0, DELTA);

            atmClient.withdrawCash(10.0);
            Assert.assertEquals(atmClient.showBalance(), 0.0, DELTA);

            atmClient.changePin("0001");
            Assert.assertEquals(atmClient.getCurrentCard().getPinCode(), "0001");

            atmClient.changePin("0000");
            Assert.assertEquals(atmClient.getCurrentCard().getPinCode(), "0000");


        } catch(RequestException e) {
            e.printStackTrace();
        }

    }
}