package apilevel;

 /*
 * ServiceWorker   4/15/16, 10:36
 *
 * By Kyrylo Havrylenko
 *
 */

import datalevel.DatabaseConnector;
import datalevel.RequestException;

import java.util.Arrays;
import java.util.List;

/**
 * API for ServiceWorkers in ATM
 *
 * @author Kyrylo Havrylenko
 * @see
 */
public class ServiceWorker {
    String serviceKey;
    DatabaseConnector connector = new DatabaseConnector();

    /**
     * @param serviceKey
     * @param connector
     */
    public ServiceWorker(String serviceKey, DatabaseConnector connector) {
        this.serviceKey = serviceKey;
        this.connector = connector;
    }

    /**
     * Creates new {@code BankAccount} on server and here
     *
     * @param p data about customer
     *
     * @return BankAccount of {@code p}
     * @see BankAccount
     */
    public BankAccount createNewAccount(Person p) throws RequestException {
        try {
            return new BankAccount(connector.createAccount(serviceKey, p.getFirstName(), p.getMiddleName(), p.getLastName
                    (), p.getAge(), p.getAdress()), p);
        } catch(RequestException e) {
            throw e;
        }
    }

    /**
     * Adds new (empty) {@code CreditCard} to specified {@code BankAccount}
     *
     * @param b {@code BankAccount} where to put new {@code CreditCard}
     *
     * @see BankAccount
     * @see CreditCard
     */
    public void addNewCreditCard(BankAccount b) throws RequestException {
        try {
            b.addCard(new CreditCard(connector.addCard(this.serviceKey, b.getAccountId())));

        } catch (RequestException e) {
            throw e;
        }

    }

//    /**
//     * Adds specified {@code CreditCard} to specified {@code BankAccount}
//     *
//     * @param b {@code BankAccount} where to put new {@code CreditCard}
//     * @param c {@code CreditCard} to add
//     *
//     * @see BankAccount
//     * @see CreditCard
//     */
//    public void addCreditCard(BankAccount b, CreditCard c) {
//        b.addCard(c);
//    }

    /**
     * Unlocks {@code CreditCard} on server and here
     *
     * @param c potentially locked {@code CreditCard}
     */
    public void unlockCard(CreditCard c) throws RequestException {

        try {
            List<String> blockedCards = Arrays.asList(connector.getBlockedCards(serviceKey));
            if(blockedCards.contains(c.cardId)) {
                c.unlockCard();
                connector.unblockCard(this.serviceKey, c.getCardId());
            }
        } catch(RequestException e) {
            throw e;
        }
    }

    /**
     * Fills MoneyVault with cash
     *
     * @param cash
     */
    public void addCash(Double cash) {
        AtmClient.getInstance().getVault().addCashToVault(cash);
    }

    /**
     * Getter
     *
     * @return {@code serviceKey}
     */
    public String getServiceKey() {
        return serviceKey;
    }

    /**
     * Sets {@code serviceKey} to specified value if it's {@code serviceKey} of existing {@code ServiceWorker}
     *
     * @param serviceKey
     * @throws RequestException
     */
    public void setServiceKey(String serviceKey) throws RequestException {
        try {
            if(connector.checkServiceKey(serviceKey)) this.serviceKey = serviceKey;
        } catch(RequestException e) {
            throw e;
        }
    }

    public void createFileForCreditCard(CreditCard c) {
        // TODO: CREATE FILE
    }
}
