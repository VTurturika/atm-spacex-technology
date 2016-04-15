package datalevel;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Implements connection with Bank.
 * Sends specified requests to server and returns data to model
 */
public class DatabaseConnector {

    private String databaseLocation = "https://spacex-technology.herokuapp.com/";
    private static Pattern validPin = Pattern.compile("^\\d{4}$");
    private static Pattern validCardId = Pattern.compile("^\\d{16}$");

    /**
     * Checks PIN code of credit card
     *
     * @param cardID number of credit card
     * @param pin PIN code, that will be checked
     * @return {@code true} if specified PIN code is correct, else returns {@code false}
     * @throws RequestException if received incorrect parameters
     */
    public boolean checkPin(String cardID, String pin) throws RequestException {

        if(!isValidCardId(cardID)) throw new RequestException(RequestErrorCode.WRONG_CARD_ID);
        if(!isValidPin(pin)) throw new RequestException(RequestErrorCode.WRONG_PIN);

        try {
            HttpResponse<String> response = Unirest.post(databaseLocation + "/customer/check-pin")
                    .queryString("cardID",cardID)
                    .queryString("pinCode", pin)
                    .asString();

            switch (response.getBody()) {
                case "OK":
                    return true;
                case "LOGIN_ERROR":
                    return false;
                default:
                    throw new RequestException(RequestErrorCode.FATAL_ERROR);
            }

        }catch (UnirestException e) {
            throw new RequestException(RequestErrorCode.CONNECTION_ERROR);
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

        if(!isValidCardId(cardID)) throw new RequestException(RequestErrorCode.WRONG_CARD_ID);
        if(!isValidPin(pin)) throw new RequestException(RequestErrorCode.WRONG_PIN);

        try {

            HttpResponse<JsonNode> response = Unirest.post(databaseLocation + "/customer/add-cash")
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json")
                    .queryString("cardID", cardID)
                    .queryString("pinCode", pin)
                    .queryString("cashSize", cashSize)
                    .asJson();

            return parseBalanceResponse(response.getBody().getObject());

        }catch (UnirestException e) {
            throw new RequestException(RequestErrorCode.CONNECTION_ERROR);
        }
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

        if(!isValidCardId(cardID)) throw new RequestException(RequestErrorCode.WRONG_CARD_ID);
        if(!isValidPin(pin)) throw new RequestException(RequestErrorCode.WRONG_PIN);

        try {

            HttpResponse<JsonNode> response = Unirest.post(databaseLocation + "/customer/get-balance")
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json")
                    .queryString("cardID",cardID)
                    .queryString("pinCode", pin)
                    .asJson();

            return parseBalanceResponse(response.getBody().getObject());

        }catch (UnirestException e) {
            throw new RequestException(RequestErrorCode.CONNECTION_ERROR);
        }
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
     * Blocks specified credit card
     *
     * @param cardID number of credit card
     * @return {@code true} if card successfully blocked, else returns {@code false}
     * @throws RequestException if received incorrect parameters
     */
    public boolean blockCard(String cardID) throws RequestException {
        return false;
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

    /**
     * Verifies PIN code
     *
     * @param pin PIN code for verifying
     * @return {@code true} if specified PIN is correct, else returns {@code false}
     */
    private boolean isValidPin(String pin) {
        return validPin.matcher(pin).matches();
    }

    /**
     * Verifies number of credit card
     *
     * @param cardId number for verifying
     * @return {@code true} if specified number of card is correct, else returns {@code false}
     */
    private boolean isValidCardId(String cardId) {
        return  validCardId.matcher(cardId).matches();
    }

    /**
     * Converts string of monetary type to double type
     *
     * @param money string of monetary type
     * @return converted double value
     */
    private double convertMoneyToDouble(String money) {
        return Double.parseDouble(money.substring(1).replaceAll(",", ""));
    }

    /**
     * Receives response from server as JSON object and chooses correct action for received balance
     *
     * @param response contain information about received balance
     * @return balance of card as double
     * @throws RequestException if received unsuccessfull response from server
     */
    private double parseBalanceResponse(JSONObject response) throws RequestException {

        switch ((String) response.get("Result")) {
            case "OK":
                String balanceString = (String) response.get("Balance");
                return convertMoneyToDouble(balanceString);
            case "LOGIN_ERROR":
                throw new RequestException(RequestErrorCode.LOGIN_ERROR);
            default:
                throw new RequestException(RequestErrorCode.FATAL_ERROR);
        }
    }
}