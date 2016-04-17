package apilevel;

 /*
 * AtmClient   4/15/16, 10:32
 *
 * By Kyrylo Havrylenko
 *
 */

import datalevel.DatabaseConnector;
import datalevel.RequestException;


/**
 * API for usual customers of ATM
 *
 * @author Kyrylo Havrylenko
 * @see
 */
public class AtmClient {
    CreditCard currentCard;
    DatabaseConnector connector;

    private static final AtmClient instance = new AtmClient();
    private AtmClient() { }

    /**
     * Singleton
     * @return {@code AtmClient} instance
     */
    public  static AtmClient getInstance() {
        return instance;
    }

    /**
     * Getter
     * @return current {@code CreditCard} in instance
     */
    public CreditCard getCurrentCard() {
        return currentCard;
    }

    /**
     * Setter
     * @param currentCard new current {@code CreditCard} for instance to work with
     */
    public void setCurrentCard(CreditCard currentCard) {
        this.currentCard = currentCard;
    }

    /**
     * Synchronizes balance of the {@code currentCard} with server
     *
     * @return balance of {@code currentCard}
     */
    Double showBalance() {
        try {
            currentCard.setBalance(connector.getBalance(currentCard.getCardId(), currentCard.getPinCode()));
        } catch(RequestException e) {
            e.printStackTrace();
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
     */
    Double withdrawCash(Double money) {
        try {
            currentCard.setBalance(connector.receiveCash(currentCard.getCardId(), currentCard.getPinCode(), money));
        } catch(RequestException e) {
            e.printStackTrace();
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
     */
    Double addCash(Double money) {
        try {
            currentCard.setBalance(connector.addCash(currentCard.getCardId(), currentCard.getPinCode(), money));
        } catch(RequestException e) {
            e.printStackTrace();
        }
        return currentCard.getBalance();
    }

    /**
     * Getter
     * @return {@code DatabaseConnector} instance
     */
    DatabaseConnector getConnector() {
        return connector;
    }

    /**
     * Setter
     * @param connector {@code DatabaseConnector} instance
     */
    public void setConnector(DatabaseConnector connector) {
        if(connector.testConnection()) this.connector = connector;
    }

    public void blockCard(CreditCard c) {
        try {
            if(connector.blockCard(c.getCardId())) c.lockCard();
        } catch(RequestException e) {
            e.printStackTrace();
        }
    }
}
