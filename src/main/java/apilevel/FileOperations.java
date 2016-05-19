package apilevel;

import datalevel.RequestErrorCode;
import datalevel.RequestException;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.charset.Charset;
import java.util.Base64;

/**
 * Implements creation and writing files of {@code CreditCard}
 */
public class FileOperations {

    private static final String creditCardPhrase = "spacex-technology";
    private static final String serviceWorkerPhrase = "service-worker";

    /**
     * Creates binary file of specified {@code CreditCard}
     *
     * @param c specified {@code CreditCard}
     * @param dest specified {@code File} file destination
     * @return true if file created successfully, else returns false
     */
    public static boolean createFileForCreditCard(CreditCard c, File dest) {

        try{

            if(dest == null) {
                dest = new File(System.getProperty("user.dir") + File.separator + "target" + File.separator +
                                                   "binary-files" + File.separator + c.getCardId() + ".sxcard");

                if (!dest.createNewFile()) return false;
            }

            FileOutputStream fos = new FileOutputStream(dest);
            fos.write(Base64.getEncoder().encode( (creditCardPhrase + ":" + c.getCardId()).getBytes() ));
            return true;
        }
        catch(Exception e) {
            return false;
        }
    }

    /**
     * Returns instance of {@code CreditCard} from specified {@code File}
     *
     * @param creditCardFile binary file of {@code CreditCard}
     * @return instance of {@code CreditCard} from specified {@code File}
     * @throws RequestException if specified file not exists or file is invalid
     */
    public static CreditCard readCreditCardFromFile(File creditCardFile) throws RequestException {

        try {

            byte data[] = new byte[(int)creditCardFile.length()];
            FileInputStream fis = new FileInputStream(creditCardFile);

            if( fis.read(data) > 0) {

                String rawData = new String(Base64.getDecoder().decode(data));

                if(rawData.contains(creditCardPhrase + ":")) {
                    String cardId = rawData.substring(rawData.indexOf(":")+1);
                    return new CreditCard(cardId);
                }
            }
        }
        catch (Exception e) {
            throw new RequestException(RequestErrorCode.WRONG_CARD_ID);
        }

        throw new RequestException(RequestErrorCode.WRONG_CARD_ID);
    }

    /**
     * Reads service key from binary file
     *
     * @param serviceKeyFile specified file
     * @return service key from binary file
     * @throws RequestException if specified file not exists or file is invalid
     */
    public static String readServiceKeyFromFile(File serviceKeyFile) throws RequestException {
        try {

            byte data[] = new byte[(int)serviceKeyFile.length()];
            FileInputStream fis = new FileInputStream(serviceKeyFile);

            if( fis.read(data) > 0) {

                String rawData = new String(Base64.getDecoder().decode(data));

                if(rawData.contains(serviceWorkerPhrase + ":")) {
                    return rawData.substring(rawData.indexOf(":")+1);
                }
            }
        }
        catch (Exception e) {
            throw new RequestException(RequestErrorCode.WRONG_SERVICE_KEY);
        }
        throw new RequestException(RequestErrorCode.WRONG_SERVICE_KEY);
    }

    /**
     * Service method for creating binary files with service keys
     * Use only in simulation of atm system. Don't use in code
     *
     * @param serviceKey specified service key
     */
    public static void serviceKeyCreator(String serviceKey) {

        try{

            File file =new File(System.getProperty("user.dir") + File.separator + "target" + File.separator +
                                                 "binary-files" + File.separator + serviceKey + ".swkey");
            if( file.createNewFile() ) {

                FileOutputStream fos = new FileOutputStream(file);
                fos.write(Base64.getEncoder().encode( (serviceWorkerPhrase + ":" + serviceKey).getBytes() ));
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}
