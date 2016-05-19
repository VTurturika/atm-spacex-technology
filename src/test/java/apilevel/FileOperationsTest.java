package apilevel;

import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;

import static org.junit.Assert.*;

/**
 * Created by Turturika on 19.05.2016.
 */
public class FileOperationsTest {

    //use only in development
    private String pathToFiles = System.getProperty("user.dir") + File.separator +
                                 "target" + File.separator + "binary-files" + File.separator;

    @Test
    public void createFileForCreditCard() throws Exception {

        FileOperations.createFileForCreditCard(new CreditCard("0000111122223333"), null);
    }

    @Test
    public void readCreditCardFromFile() throws Exception {
        FileOperations.readCreditCardFromFile(new File(pathToFiles + "0000111122223333.sxcard"));
    }

    @Test
    public void readServiceKeyFromFile() throws Exception {
        FileOperations.readServiceKeyFromFile(new File(pathToFiles + "1234567890.swkey"));
    }

    @Test
    public void serviceKeyCreator() throws Exception {
        FileOperations.serviceKeyCreator("1234567890");
    }

}