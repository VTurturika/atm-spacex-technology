package apilevel;

 /*
 * BankAccount   4/15/16, 10:37
 *
 * By Kyrylo Havrylenko
 *
 */

import java.util.List;

/**
 * Storage for data about customers bank account
 *
 * @author Kyrylo Havrylenko
 * @see
 */
public class BankAccount {
    Integer accountId;
    List<CreditCard> creditCards;
    Person person;

    public Person getPerson() {
        return person;
    }

    public CreditCard getCard(String cardId) {
        for(CreditCard c : creditCards) {
            if(c.getCardId().equals(cardId)) return c;
        }
        return new CreditCard();
    }

    public void addCard(CreditCard card) {
        creditCards.add(card);
    }

    public void removeCard(CreditCard card) {
        creditCards.remove(card);
    }

    public Boolean hasCard(CreditCard card) {
        return creditCards.contains(card);
    }
}
