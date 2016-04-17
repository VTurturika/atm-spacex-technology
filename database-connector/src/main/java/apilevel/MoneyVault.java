package apilevel;

 /*
 * MoneyVault   4/17/16, 18:19
 *
 * By Kyrylo Havrylenko
 *
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * API for controller to vault money
 *
 * @author Kyrylo Havrylenko
 * @see
 */
public class MoneyVault {
    Map<Integer, Integer> vault;
    int[] notes = { 1, 2, 5, 10, 20, 50, 100, 200, 500 };

    public MoneyVault() {
        vault = new HashMap<>();
        for(int i = 0; i < notes.length; i++) {
            vault.put(notes[i], 0);
        }
    }

    public Map<Integer, Integer> getCash(Double cash) {
        while(cash != 0) {
            for(int i = notes.length; i > 0; i++) {
                if(cash % notes[i] == 0) {
                    cash -= notes[i];
                    vault.put(notes[i], vault.get(i) - 1);
                }
            }
        }
        return vault;


    }

    public Map<Integer, Integer> addCash(Double cash) {
        while(cash != 0) {
            for(int i = notes.length; i > 0; i++) {
                if(cash % notes[i] == 0) {
                    cash -= notes[i];
                    vault.put(notes[i], vault.get(i) + 1);
                }
            }
        }
        return vault;
    }
}
