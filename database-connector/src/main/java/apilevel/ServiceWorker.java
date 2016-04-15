package apilevel;

 /*
 * ServiceWorker   4/15/16, 10:36
 *
 * By Kyrylo Havrylenko
 *
 */

/**
 * API for ServiceWorkers in ATM
 *
 * @author Kyrylo Havrylenko
 * @see
 */
public class ServiceWorker {
    String serviceKey;

    public BankAccount createNewAccount(Person p) {
        // TODO: NULL
        return null;
    }

    public void addNewCreditCard(BankAccount b) {
        //TODO: NULL
    }

    public void unlockCard(CreditCard c) {
        c.unlockCard();
    }

    public void addCash(Double cash) {
        //TODO: fill atm with money
    }

    public String getServiceKey() {
        return serviceKey;
    }

    public void setServiceKey(String serviceKey) {
        this.serviceKey = serviceKey;
    }
}
