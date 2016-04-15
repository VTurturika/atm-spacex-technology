package apilevel;

 /*
 * CreditCard   4/15/16, 10:34
 *
 * By Kyrylo Havrylenko
 *
 */

import datalevel.DatabaseConnector;
import datalevel.RequestException;

/**
 * Storage and API for customers Credit Cards in ATM
 *
 * @author Kyrylo Havrylenko
 * @see
 */
public class CreditCard {
    String cardId;
    Double balance;
    Boolean isLocked;
    String pinCode;
    DatabaseConnector connector = new DatabaseConnector();

    /**
     * Creates empty {@code CreditCard}
     */
    public CreditCard() {
        this("0000000000000000", 0.00, false, "0000");
    }

    /**
     * Creates new {@code CreditCard} with specified {@code cardId}
     * @param cardId id (number) of card to create
     */
    public CreditCard(String cardId) {
        this(cardId, 0.00, false, "0000");
    }

    /**
     * Creates new {@code CreditCard}
     * @param cardId id (number) of card to create
     * @param balance balance of money on card
     * @param isLocked true if card should be locked
     * @param pinCode pincode of card
     */
    public CreditCard(String cardId, Double balance, Boolean isLocked, String pinCode) {
        this.cardId = cardId;
        this.balance = balance;
        this.isLocked = isLocked;
        this.pinCode = pinCode;
    }

    /**
     * Getter
     * @return {@code cardId}
     */
    public String getCardId() {
        return cardId;
    }

    /**
     * Setter (use only as builder)
     * @param cardId
     */
    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    /**
     * Returns LOCAL balance of the card
     * @return {@code balance}
     * @see AtmClient
     */
    Double getBalance() {
        return balance;
    }

    /**
     * Sets LOCAL balance of the card
     * @param balance
     */
    void setBalance(Double balance) {
        this.balance = balance;
    }

    /**
     * Getter on the state of {@code locked}
     * @return true if locked
     */
    public Boolean isLocked() {
        return isLocked;
    }

    /**
     * Sets {@code locked} status of the card on server and here
     * @param sw {@code SerivceWorker} instance
     * @param locked what {@code locked} status card should have
     * @see ServiceWorker
     */
    private void setLocked(ServiceWorker sw, Boolean locked) {
        try {
            if(locked) connector.blockCard(sw.getServiceKey(), cardId);
            else connector.unblockCard(sw.getServiceKey(), cardId);
        } catch(RequestException e) {
            e.printStackTrace();
        }
        isLocked = locked;
    }

//    TODO: NEEDS SEPARATE METHOD ON LOWER LEVEL
//    public void lockCard() {
//        setLocked(true);
//    }

    /**
     * Unlocks the card
     * @param sw {@code SerivceWorker} instance
     * @see ServiceWorker
     */
    public void unlockCard(ServiceWorker sw) {
        setLocked(sw, false);
    }

    /**
     * Getter
     * @return String with pincode of the card
     */
    public String getPinCode() {
        return pinCode;
    }

    /**
     * Setter of the pincode here and on server
     * @param pinCode String with 4 chars
     */
    public void setPinCode(String pinCode) {
        try {
            connector.changePin(cardId, this.pinCode, pinCode);
        } catch(RequestException e) {
            e.printStackTrace();
        }
        this.pinCode = pinCode;
    }
}
