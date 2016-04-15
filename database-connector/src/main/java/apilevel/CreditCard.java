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

    public CreditCard() {
        this("0000000000000000", 0.00, false, "0000");
    }

    public CreditCard(String cardId, Double balance) {
        this(cardId, balance, false, "0000");
    }

    public CreditCard(String cardId, Double balance, Boolean isLocked, String pinCode) {
        this.cardId = cardId;
        this.balance = balance;
        this.isLocked = isLocked;
        this.pinCode = pinCode;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public Double getBalance() {
        return balance;
    }

    void setBalance(Double balance) {
        this.balance = balance;
    }

    public Boolean isLocked() {
        return isLocked;
    }

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

    public void unlockCard(ServiceWorker sw) {
        setLocked(sw, false);
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        try {
            connector.changePin(cardId, this.pinCode, pinCode);
        } catch(RequestException e) {
            e.printStackTrace();
        }
        this.pinCode = pinCode;
    }
}
