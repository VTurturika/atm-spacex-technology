package apilevel;

 /*
 * AtmClient   4/15/16, 10:32
 *
 * By Kyrylo Havrylenko
 *
 */

import datalevel.DatabaseConnector;

/**
 * API for usual customers of ATM
 *
 * @author Kyrylo Havrylenko
 * @see
 */
public class AtmClient {
    CreditCard currentCard;
    DatabaseConnector connector;

    Double showBalance() {
        // TODO: DatabaseConnector actions!!!
        return currentCard.getBalance();
    }

    Double withdrawCash(Double money) {
        // TODO: DatabaseConnector actions!!!
        currentCard.withdrawMoney(money);
        return currentCard.getBalance();
    }

    Double addCash(Double money) {
        // TODO: DatabaseConnector actions!!!
        currentCard.addMoney(money);
        return currentCard.getBalance();
    }

    DatabaseConnector getConnector() {
        return connector;
    }

    public void setConnector(DatabaseConnector connector) {
        this.connector = connector;
    }
}
