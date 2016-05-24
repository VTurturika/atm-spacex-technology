package apilevel;

 /*
 * ServiceWorker   4/15/16, 10:36
 *
 * By Kyrylo Havrylenko
 *
 */

import datalevel.DatabaseConnector;
import datalevel.RequestErrorCode;
import datalevel.RequestException;

import java.io.File;
import java.io.FileOutputStream;
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

    /**
     * @param serviceKey
     */
    public ServiceWorker(String serviceKey) {
        this.serviceKey = serviceKey;
    }

    /**
     * Creates new {@code BankAccount} on server and here
     *
     * @param p         data about customer
     * @param connector {@code DatabaseConnector}
     *
     * @return BankAccount of {@code p}
     * @throws RequestException
     * @see BankAccount
     * @see DatabaseConnector
     */
    public BankAccount createNewAccount(Person p, DatabaseConnector connector) throws RequestException {
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
     * @param b         {@code BankAccount} where to put new {@code CreditCard}
     * @param connector {@code DatabaseConnector}
     *
     * @throws RequestException
     * @see DatabaseConnector
     * @see BankAccount
     * @see CreditCard
     */
    public CreditCard addNewCreditCard(BankAccount b, DatabaseConnector connector) throws RequestException {
        try {
            CreditCard creditCard = new CreditCard(connector.addCard(this.serviceKey, b.getAccountId()));
            b.addCard(creditCard);
            return creditCard;
        } catch(RequestException e) {
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
     * @param c         potentially locked {@code CreditCard}
     * @param connector {@code DatabaseConnector}
     *
     * @throws RequestException
     * @see DatabaseConnector
     */
    public void unlockCard(CreditCard c, DatabaseConnector connector) throws RequestException {

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
        AtmClientSingleton.getInstance().getVault().addCashToVault(cash);
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
     * @param connector
     *
     * @throws RequestException
     */
    public void setServiceKey(String serviceKey, DatabaseConnector connector) throws RequestException {
        try {
            if(connector.checkServiceKey(serviceKey)) this.serviceKey = serviceKey;
        } catch(RequestException e) {
            throw e;
        }
    }

    public void createFileForCreditCard(CreditCard c, File dest) throws RequestException {

        try {
           if( FileOperations.createFileForCreditCard(c,dest) ) return;
           else throw new RequestException(RequestErrorCode.WRONG_CARD_ID);
        }
        catch (Exception e) {
            throw new RequestException(RequestErrorCode.FATAL_ERROR);
        }
    }
}
