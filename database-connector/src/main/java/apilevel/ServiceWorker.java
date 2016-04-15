package apilevel;

 /*
 * ServiceWorker   4/15/16, 10:36
 *
 * By Kyrylo Havrylenko
 *
 */

import datalevel.DatabaseConnector;

/**
 * API for ServiceWorkers in ATM
 *
 * @author Kyrylo Havrylenko
 * @see
 */
public class ServiceWorker {
    String serviceKey;
    static DatabaseConnector connector = new DatabaseConnector();

    public BankAccount createNewAccount(Person p) {

        return null;
    }

    public void addNewCreditCard(BankAccount b) {
        //TODO: NULL
    }

    public void addCreditCard(BankAccount b, CreditCard c) {
        //TODO: NULL
    }

    public void unlockCard(CreditCard c) {
        c.unlockCard(this);
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
