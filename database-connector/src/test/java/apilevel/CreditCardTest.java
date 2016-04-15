package apilevel;

 /*
 * CreditCardTest   4/15/16, 16:51
 *
 * By Kyrylo Havrylenko
 *
 */

import datalevel.DatabaseConnector;
import datalevel.RequestException;
import org.junit.Assert;
import org.junit.Test;

import java.util.logging.Logger;

/**
 * Tests class {@code CreditCard}
 *
 * @author Kyrylo Havrylenko
 * @see CreditCard
 */
public class CreditCardTest {
    public static final Logger log = Logger.getLogger(CreditCardTest.class.getName());

    @Test
    public void changePin() {
        try {
            CreditCard c = new CreditCard("0000111122223333");
            DatabaseConnector d = new DatabaseConnector();
            c.setPinCode("1111");
            Assert.assertEquals(c.getPinCode(), "1111");
            new DatabaseConnector().changePin("0000111122223333", "1111", "0000");
            Assert.assertEquals(c.getPinCode(), "0000");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
