package apilevel;

 /*
 * MoneyVaultTest   4/19/16, 18:25
 *
 * By Kyrylo Havrylenko
 *
 */

import datalevel.RequestException;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests {@code MoneyVault}
 *
 * @author Kyrylo Havrylenko
 * @see MoneyVault
 */
public class MoneyVaultTest {
    private static final double DELTA = 1e-15;

    @Test
    public void testVault() {
        try {
            MoneyVault mv = new MoneyVault();
            boolean hasMoney = mv.hasMoney(10.0);
            Assert.assertEquals(hasMoney, false);
            hasMoney = mv.hasMoney(0.0);
            Assert.assertEquals(hasMoney, false);
            hasMoney = mv.hasMoney(-1.0);
            Assert.assertEquals(hasMoney, true);
            mv.addCashToVault(100.0);
            Assert.assertEquals(mv.getCashValue(), 100.0, DELTA);
            mv.withdrawCash(50.0);
            Assert.assertEquals(mv.getCashValue(), 50.0, DELTA);
        } catch(RequestException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAddingMoney() {
        MoneyVault mv = new MoneyVault();
        mv.addCashToVault(10.0);
        Assert.assertEquals(mv.getCashValue(), 10.0, DELTA);
    }

    @Test
    public void testWithdrawingMoney() {
        try {
            MoneyVault mv = new MoneyVault();
            mv.addCashToVault(10.0);
            mv.withdrawCash(10.0);
            Assert.assertEquals(mv.getCashValue(), 0.0, DELTA);
        } catch(RequestException e) {
            e.printStackTrace();
        }
    }
}
