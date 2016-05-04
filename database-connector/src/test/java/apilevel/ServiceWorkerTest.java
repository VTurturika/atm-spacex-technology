package apilevel;

 /*
 * ServiceWorkerTest   4/15/16, 16:52
 *
 * By Kyrylo Havrylenko
 *
 */

import datalevel.RequestException;
import org.junit.Test;

/**
 * Tests class {@code ServiceWorker}
 *
 * @author Kyrylo Havrylenko
 * @see ServiceWorker
 */
public class ServiceWorkerTest {

    @Test
    public void testServiceWorker() {
        ServiceWorker sw = new ServiceWorker("ServiceKey");

        try {
            AtmClientSingleton atmClientSingleton = AtmClientSingleton.getInstance();
            Person p = new Person();
            BankAccount b = sw.createNewAccount(p, atmClientSingleton.getConnector());
            sw.addNewCreditCard(b, atmClientSingleton.getConnector());
            atmClientSingleton.blockCard(b.getCreditCards().get(0));
            sw.unlockCard(b.getCreditCards().get(0), atmClientSingleton.getConnector());
            sw.addCash(100.0);
        } catch(RequestException e) {
            e.printStackTrace();
        }

    }
}
