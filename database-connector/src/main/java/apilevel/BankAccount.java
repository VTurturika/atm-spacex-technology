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

    public BankAccount(ServiceWorker sw, Person person) {
        BankAccount b = sw.createNewAccount(person);
        this.accountId = b.getAccountId();
        this.creditCards = b.getCreditCards();
        this.person = person;
    }

    public BankAccount(Integer accountId, Person p) {
        this.accountId = accountId;
        this.person = p;
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

    public void addCard(ServiceWorker sw, CreditCard card) {
        sw.addCreditCard(this, card);
        creditCards.add(card);
    }

    public Boolean hasCard(CreditCard card) {
        return creditCards.contains(card);
    }
}
