package apilevel;

 /*
 * AtmClientSingleton   4/15/16, 10:32
 *
 * By Kyrylo Havrylenko
 *
 */

import datalevel.DatabaseConnector;
import datalevel.RequestErrorCode;
import datalevel.RequestException;

import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


/**
 * API for usual customers of ATM
 *
 * @author Kyrylo Havrylenko
 * @see
 */
public class AtmClientSingleton {
    private static final AtmClientSingleton instance = new AtmClientSingleton();
    private ServiceWorker serviceWorker = null;
    CreditCard currentCard;
    DatabaseConnector connector;
    MoneyVault vault = new MoneyVault();

    private AtmClientSingleton() {
    }

    /**
     * Singleton
     *
     * @return {@code AtmClientSingleton} instance
     */
    public static AtmClientSingleton getInstance() {
        return instance;
    }

    public MoneyVault getVault() {
        return vault;
    }

    public void setVault(MoneyVault vault) {
        this.vault = vault;
    }

    /**
     * Getter
     *
     * @return current {@code CreditCard} in instance
     */
    public CreditCard getCurrentCard() {
        return currentCard;
    }

    /**
     * Setter
     *
     * @param currentCard new current {@code CreditCard} for instance to work with
     */
    public void setCurrentCard(CreditCard currentCard) {
        this.currentCard = currentCard;
    }

    /**
     * Sets currentCard from specified binary file
     *
     * @param card binary file
     */
    public void setCurrentCardFromFile(File card) throws RequestException {

        try {
            this.currentCard = FileOperations.readCreditCardFromFile(card);
        }
        catch (RequestException e) {
            this.currentCard = null;
            throw e;
        }
    }

    public ServiceWorker getServiceWorker() {
        return serviceWorker;
    }

    public void setServiceWorker(ServiceWorker serviceWorker) {
        this.serviceWorker = serviceWorker;
    }

    /**
     * Synchronizes balance of the {@code currentCard} with server
     *
     * @return balance of {@code currentCard}
     * @throws RequestException
     */
    public Double showBalance() throws RequestException {
        try {
            currentCard.setBalance(connector.getBalance(currentCard.getCardId(), currentCard.getPinCode()));
            currentCard.setTryCounter(0);
        } catch(RequestException e) {
            catchRequestExceptionWhenPinUsed(e);
        }
        return currentCard.getBalance();
    }

    /**
     * Withdraws {@code Double} amount to {@code CreditCard} balance
     *
     * @param money
     *
     * @return {@code CreditCard.getBalance()}
     * @see CreditCard
     * @throws RequestException
     */
    public Double withdrawCash(Double money) throws RequestException {
        try {
            if(currentCard.isLocked) throw new RequestException(RequestErrorCode.CARD_BLOCKED);
            if(vault.hasMoney(money)) {
                currentCard.setBalance(connector.receiveCash(currentCard.getCardId(), currentCard.getPinCode(), money));
                currentCard.setTryCounter(0);
                vault.withdrawCash(money);
            } else {
                throw new RequestException(RequestErrorCode.NOT_ENOUGH_MONEY_IN_VAULT);
            }
        } catch(RequestException e) {
            catchRequestExceptionWhenPinUsed(e);
        }
        return currentCard.getBalance();
    }

    /**
     * Adds {@code Double} amount to {@code CreditCard} balance
     *
     * @param money
     *
     * @return {@code CreditCard.getBalance()}
     * @see CreditCard
     * @throws RequestException
     */
    public Double addCash(Double money) throws RequestException {
        try {
            currentCard.setBalance(connector.addCash(currentCard.getCardId(), currentCard.getPinCode(), money));
            currentCard.setTryCounter(0);
        } catch(RequestException e) {
            catchRequestExceptionWhenPinUsed(e);
        }
        vault.addCashToVault(money);
        return currentCard.getBalance();
    }

    /**
     * Getter
     *
     * @return {@code DatabaseConnector} instance
     */
    public DatabaseConnector getConnector() {
        return connector;
    }

    /**
     * Setter
     *
     * @param connector {@code DatabaseConnector} instance
     */
    public void setConnector(DatabaseConnector connector) throws RequestException {
        if(connector.testConnection()) {
            this.connector = connector;
        }
        else {
            throw new RequestException(RequestErrorCode.CONNECTION_ERROR);
        }
    }

    /**
     * Blocks card
     * @param c {@code CreditCard} you wan't to block
     * @throws RequestException
     */
    public void blockCard(CreditCard c) throws RequestException {
        try {
            if(connector.blockCard(c.getCardId())) c.lockCard();
        } catch(RequestException e) {
            throw e;
        }
    }

    public List<String> getListOfBlockedCards() throws RequestException {
        String[] blockedCards = connector.getBlockedCards(serviceWorker.getServiceKey());
        if(blockedCards != null) {
            return Arrays.asList(blockedCards);
        } else return new LinkedList<>();
    }

    /**
     * Changes pin for {@code currentCard}
     * @param newPin desired pincode ("XXXX")
     * @throws RequestException
     */
    public void changePin(String newPin) throws RequestException {
        try {
            if(connector.changePin(currentCard.getCardId(), currentCard.getPinCode(), newPin)) currentCard.setPinCode
                    (newPin);
        } catch(RequestException e) {
            throw e;
        }
    }

    private boolean checkIfWrongPin(String message) {
        return message.equals(RequestErrorCode.WRONG_PIN.toString());
    }

    private void catchRequestExceptionWhenPinUsed(RequestException e) throws RequestException {

        if(checkIfWrongPin(e.getMessage())) {
            if(currentCard.setTryCounter(currentCard.getTryCounter() + 1)) {
                blockCard(currentCard);
                throw new RequestException(RequestErrorCode.CARD_BLOCKED);
            }
            throw new RequestException(RequestErrorCode.WRONG_PIN);
        }
        throw e;
    }
}
