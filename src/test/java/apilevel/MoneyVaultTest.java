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

import java.util.Map;

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
            Assert.assertEquals(hasMoney, true);
            hasMoney = mv.hasMoney(-1.0);
            Assert.assertEquals(hasMoney, true);
            mv.addCashToVault(123.0);
            Assert.assertEquals(mv.getCashValue(), 123.0, DELTA);
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
            mv.addCashToVault(20.0);
            mv.withdrawCash(10.0);
            Assert.assertEquals(mv.getCashValue(), 10.0, DELTA);
            mv.withdrawCash(10.0);
            Assert.assertEquals(mv.getCashValue(), 0.0, DELTA);
        } catch(RequestException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testChaningContruction() {
        try {
            MoneyVault mv = new MoneyVault();
            mv.addCashToVault(105.);
            Map<Integer, Integer> vault = mv.withdrawCash(0.);
            MoneyVault mv2 = new MoneyVault(vault);
            Assert.assertEquals(mv.withdrawCash(0.).keySet(), mv2
                    .withdrawCash(0.).keySet());
            Assert.assertEquals(mv.getCashValue(), mv2.getCashValue(), DELTA);
//            System.out.println(mv.getCashValue() +""+ mv2.getCashValue());
        } catch(RequestException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testMapWithdrawal() {
        try {
            MoneyVault mv = new MoneyVault();
            mv.addCashToVault(250.);
            MoneyVault mv2 = new MoneyVault();
            mv2.addCashToVault(105.);
            mv.withdrawCash(mv2.withdrawCash(0.));
            MoneyVault expected = new MoneyVault();
            expected.addCashToVault(250. - 105.);
            Assert.assertEquals(expected.getCashValue(), mv.getCashValue(), DELTA);
        } catch(RequestException e) {
            e.printStackTrace();
        }
    }
}
