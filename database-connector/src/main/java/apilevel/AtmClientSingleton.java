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


/**
 * API for usual customers of ATM
 *
 * @author Kyrylo Havrylenko
 * @see
 */
public class AtmClientSingleton {
    private static final AtmClientSingleton instance = new AtmClientSingleton();
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

    void setVault(MoneyVault vault) {
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
    public void setConnector(DatabaseConnector connector) {
        if(connector.testConnection()) this.connector = connector;
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
