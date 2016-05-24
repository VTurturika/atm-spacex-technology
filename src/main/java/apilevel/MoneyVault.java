package apilevel;

 /*
 * MoneyVault   4/17/16, 18:19
 *
 * By Kyrylo Havrylenko
 *
 */

import datalevel.RequestErrorCode;
import datalevel.RequestException;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * API for controller to vault money
 *
 * @author Kyrylo Havrylenko
 * @see
 */
public class MoneyVault {
    Logger logger = Logger.getLogger(MoneyVault.class.getName());
    Map<Integer, Integer> vault;
    int[] notes = {1, 2, 5, 10, 20, 50, 100, 200, 500};

    /**
     * Constructor
     */
    public MoneyVault() {
        vault = new HashMap<>();
        for(int i = 0; i < notes.length; i++) {
            vault.put(notes[i], 0);
        }
        logger.log(Level.FINE, "Hashmap vault: " + vault.toString());
    }

    /**
     * Constructor via Map
     * @param cash Map with keySet from {@code MoneyVault}
     */
    public MoneyVault(Map<Integer, Integer> cash) {
        this();
        if(vault.keySet().equals(cash.keySet())) {
            vault = cash;
        }
    }

    public Map<Integer, Integer> getVault() {
        return vault;
    }

    /**
     * Withdraws money from Vault
     *
     * @param cash amount of money you want to withdraw
     *
     * @return {@code MoneyVault} after withdraw
     * @throws RequestException
     */
    public Map<Integer, Integer> withdrawCash(Double cash) throws RequestException {
        if(!hasMoney(cash)) throw new RequestException(RequestErrorCode.NOT_ENOUGH_MONEY_IN_VAULT);
        int howMuchMoney = 0;
        for(int i = 0; i < notes.length; i++) {
            if(notes[i] <= cash) {
                howMuchMoney += notes[i] * vault.get(notes[i]);
            }
        }
        if(howMuchMoney < cash) throw new RequestException(RequestErrorCode.NOT_ENOUGH_MONEY_IN_VAULT);
        while(cash != 0) {
            for(int i = notes.length - 1; i > 0; i--) {
                if(cash % notes[i] == 0) {
                    cash -= notes[i];
                    vault.put(notes[i], vault.get(notes[i]) - 1);
                    if(cash == 0) return vault;
                }
            }
        }
        return vault;
    }

    /**
     * Withdraws money from Vault
     *
     * @param cash Map (amount) of money you want to withdraw
     *
     * @return {@code MoneyVault} after withdraw
     * @throws RequestException
     */
    public Map<Integer, Integer> withdrawCash(Map<Integer, Integer> cash) throws RequestException {

        if(cash.keySet().equals(vault.keySet())) {
            if(!hasMoney(new MoneyVault(cash).getCashValue())) throw new RequestException(RequestErrorCode.NOT_ENOUGH_MONEY_IN_VAULT);
            for(Integer key : cash.keySet()) {
                vault.put(key, vault.get(key) - cash.get(key));
            }
        }
        return vault;
    }

    /**
     * Checks if vault has that amount of money
     *
     * @param cash number of money that you want to withdraw in future
     *
     * @return {@code true} if vault has that money
     */
    public boolean hasMoney(Double cash) {
        if(getCashValue() >= cash) {
            return true;
        }
        return false;
    }

    /**
     * Transforms {@code HashMap} of notes into one {@code Double} value
     *
     * @return {@code Double} money
     */
    public Double getCashValue() {
        Double result = 0.0;
        for(Map.Entry<Integer, Integer> entry : vault.entrySet()) {
            result += entry.getKey() * entry.getValue();
        }
        return result;
    }

    /**
     * Adds money to {@code MoneyVault} instance
     *
     * @param cash money you wan't to put into vault
     *
     * @return {@code MoneyVault} instance after adding money
     */
    public Map<Integer, Integer> addCashToVault(Double cash) {
        while(cash != 0) {
            for(int i = notes.length - 1; i >= 0; i--) {
                if(cash % notes[i] == 0) {
                    cash -= notes[i];
                    vault.put(notes[i], vault.get(notes[i]) + 1);
                    if(cash == 0) return vault;
                }
            }
        }
        return vault;
    }

    /**
     * Adds money to {@code MoneyVault} instance
     *
     * @param cash Map (money) you wan't to put into vault
     * @return {@code MoneyVault} instance after adding money
     */
    public Map<Integer, Integer> addCashToVault(Map<Integer, Integer> cash) {
        MoneyVault mv = new MoneyVault(cash);
        addCashToVault(mv.getCashValue());
        return vault;
    }
}
