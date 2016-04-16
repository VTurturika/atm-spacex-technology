package apilevel;

 /*
 * BankAccount   4/15/16, 10:37
 *
 * By Kyrylo Havrylenko
 *
 */

import java.util.ArrayList;
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

    /**
     * @param accountId
     * @param p
     */
    public BankAccount(Integer accountId, Person p) {
        this.accountId = accountId;
        this.person = p;
        creditCards = new ArrayList<>();
    }

    public Integer getAccountId() {
        return accountId;
    }

    public Person getPerson() {
        return person;
    }

    public CreditCard getCard(String cardId) {
        for(CreditCard c : creditCards) {
            if(c.getCardId().equals(cardId)) return c;
        }
        return null;
    }

    public List<CreditCard> getCreditCards() {
        return creditCards;
    }

    public void addCard(CreditCard card) {
        creditCards.add(card);
    }

    public Boolean hasCard(CreditCard card) {
        return creditCards.contains(card);
    }
}
