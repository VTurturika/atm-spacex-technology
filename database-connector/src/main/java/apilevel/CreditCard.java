package apilevel;

 /*
 * CreditCard   4/15/16, 10:34
 *
 * By Kyrylo Havrylenko
 *
 */

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

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public Double getBalance() {
        return balance;
    }

    private void setBalance(Double balance) {
        this.balance = balance;
    }

    public void addMoney(Double money) {
        setBalance(getBalance() + money);
    }

    public void withdrawMoney(Double money) {
        setBalance(getBalance() - money);
    }

    public Boolean isLocked() {
        return isLocked;
    }

    private void setLocked(Boolean locked) {
        isLocked = locked;
    }

    public void lockCard() {
        setLocked(true);
    }

    public void unlockCard() {
        setLocked(false);
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    // TODO: Constructor and DatabaseConnectr actions!!!
}
