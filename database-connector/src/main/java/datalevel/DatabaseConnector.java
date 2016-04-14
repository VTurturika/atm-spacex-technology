package datalevel;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Implements connection with Bank.
 * Sends specified requests to server and returns data to model
 */
public class DatabaseConnector {

    String databaseLocation = "https://spacex-technology.herokuapp.com/";
                               //"http://localhost";

    /**
     * Checks PIN code of credit card
     *
     * @param cardID number of credit card
     * @param pin PIN code, that will be checked
     * @return {@code true} if specified PIN code is correct, else returns {@code false}
     * @throws RequestException if received incorrect parameters
     */
    public boolean checkPin(String cardID, String pin) throws RequestException {

        try {
            HttpResponse<JsonNode> response = Unirest.post(databaseLocation + "/customer/check-pin")
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json")
                    .queryString("cardID",cardID)
                    .queryString("pinCode", pin)
                    .asJson();

            switch ((String)response.getBody().getObject().get("Result")) {
                case "OK":
                    return true;
                case "WRONG_CARD_ID":
                    throw new RequestException(RequestErrorCode.WRONG_CARD_ID);
                case "WRONG_PIN":
                    throw new RequestException(RequestErrorCode.WRONG_PIN);
                default:
                    throw new RequestException(RequestErrorCode.SOMETHING_WRONG);
            }

        }catch (UnirestException e) {
            throw new RequestException(RequestErrorCode.SOMETHING_WRONG);
        }
    }

    /**
     * Checks PIN code and receives specified sum from credit card
     *
     * @param cardID number of credit card
     * @param pin PIN code of credit card
     * @param cashSize sum that will be received
     * @return balance of credit card after transaction
     * @throws RequestException if received incorrect parameters or not enough money
     */
    public double receiveCash(String cardID, String pin, double cashSize) throws RequestException {
        return 0;
    }

    /**
     * Checks PIN code and adds specified sum to credit card
     *
     * @param cardID number of credit card
     * @param pin PIN code of credit card
     * @param cashSize sum that will be added
     * @return balance of credit card after transaction
     * @throws RequestException if received incorrect parameters
     */
    public double addCash(String cardID, String pin, double cashSize) throws RequestException {
        return 0;
    }

    /**
     * Checks PIN code and returns balance of credit card
     *
     * @param cardID number of credit card
     * @param pin PIN code of credit card
     * @return balance of credit card
     * @throws RequestException if received incorrect parameters
     */
    public double getBalance(String cardID, String pin) throws RequestException {
        return 0;
    }

    /**
     * Checks PIN code and changes its to new one
     *
     * @param cardID number of credit card
     * @param oldPin PIN code of credit card
     * @param newPin new PIN code of credit card
     * @throws RequestException if received incorrect parameters
     */
    public void changePin(String cardID, String oldPin, String newPin) throws RequestException {

    }

    /**
     * Tests connection with database server
     *
     * @return {@code true} if connection is success, else returns {@code false}
     */
    public boolean testConnection() {

        try {
            HttpResponse<String> response = Unirest.post(databaseLocation + "/service/test-connection").asString();
            return response.getBody().equals("Ready");

        } catch (UnirestException e) {
            return false;
        }

    }

    /**
     * Checks security service key of ATM
     *
     * @param serviceKey service key of ATM
     * @return {@code true} if service key is correct, else returns {@code false}
     */
    public boolean checkServiceKey(String serviceKey) {
        return false;
    }

    /**
     * Checks security service key and creates new bank account
     *
     * @param serviceKey service key of ATM
     * @param customerFirstName fist name of customer
     * @param customerMiddleName middle name of customer
     * @param customerLastName last name of customer
     * @param customerAge age of customer
     * @param customerAddress address of customer
     * @return bank account ID
     * @throws RequestException if received incorrect parameters
     */
    public int createAccount(String serviceKey, String customerFirstName,
                             String customerMiddleName, String customerLastName,
                             int customerAge, String customerAddress) throws RequestException {
        return 0;
    }

    /**
     * Checks security service key and adds new credit card to specified bank account
     *
     * @param serviceKey service key of ATM
     * @param accountID bank account ID
     * @return number of new credit card
     * @throws RequestException if received incorrect parameters
     */
    public String addCard(String serviceKey, int accountID) throws RequestException {
        return "";
    }

    /**
     * Checks security service key and returns list of blocked credit cards
     *
     * @param serviceKey service key of ATM
     * @return array of numbers of blocked credit cards
     * @throws RequestException if received incorrect serviceKey
     */
    public String[] getBlockedCards(String serviceKey) throws RequestException {
        return null;
    }

    /**
     * Checks security service key and blocks specified credit card
     *
     * @param serviceKey service key of ATM
     * @param cardID number of credit card
     * @throws RequestException if received incorrect parameters
     */
    public void blockCard(String serviceKey, String cardID) throws RequestException {

    }

    /**
     * Checks security service key and unblocks specified credit card
     *
     * @param serviceKey service key of ATM
     * @param cardID number of credit card
     * @throws RequestException if received incorrect parameters
     */
    public void unblockCard(String serviceKey, String cardID) throws RequestException {

    }

    /**
     * Returns bank server URL
     *
     * @return  bank server URL
     */
    public String getDatabaseLocation() {
        return databaseLocation;
    }

    /**
     * Sets bank server URL
     *
     * @param databaseLocation URL of server
     */
    public void setDatabaseLocation(String databaseLocation) {
        this.databaseLocation = databaseLocation;
    }
}
