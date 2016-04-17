package datalevel;

import static org.junit.Assert.*;

/**
 * Implements unit tests for {@code DatabaseConnector} class
 */
public class DatabaseConnectorTest {
    @org.junit.Test
    public void checkPin() throws Exception {

        DatabaseConnector connector = new DatabaseConnector();
        assertTrue(connector.checkPin("0000111122223333", "0000"));
        assertTrue(connector.checkPin("1111222233334444", "0000"));
        assertFalse(connector.checkPin("0000111122223333", "0001"));
        assertFalse(connector.checkPin("0111222233334444", "0000"));

        try {connector.checkPin("qwerty", "0000");}catch (RequestException r) {System.out.println(r.getErrorCode());}
        try {connector.checkPin("0000111122223333", "qwerty");}catch (RequestException r) {
            System.out.println(r.getErrorCode());
        }
    }

    @org.junit.Test
    public void receiveCash() throws Exception {
        DatabaseConnector connector = new DatabaseConnector();
        double currentBalance = connector.receiveCash("1111222233334444", "0000", 50);
        System.out.println(currentBalance);

        try{
            connector.receiveCash("1111222233334444", "0000", 500);
        }
        catch (RequestException e) {
            if(e.getErrorCode() == RequestErrorCode.INSUFFICIENT_FUNDS) {
                System.out.println("insufficient funds");
            }
        }
    }

    @org.junit.Test
    public void addCash() throws Exception {

        DatabaseConnector connector = new DatabaseConnector();
        connector.addCash("1111222233334444", "0000", 100);
    }

    @org.junit.Test
    public void getBalance() throws Exception {

        DatabaseConnector connector = new DatabaseConnector();
        System.out.println(connector.getBalance("0000111122223333", "0000"));
        try {connector.getBalance("0000111122223333", "0001");} catch (RequestException e) {System.out.println(e);}
        try {connector.getBalance("1000111122223333", "0000");} catch (RequestException e) {System.out.println(e);}
    }

    @org.junit.Test
    public void changePin() throws Exception {

        DatabaseConnector connector = new DatabaseConnector();
        System.out.println("Try change pin : " + connector.changePin("0000111122223333", "0000", "1111"));
    }

    @org.junit.Test
    public void testConnection() throws Exception {

        DatabaseConnector connector = new DatabaseConnector();
        int numberOfTests = 10;

        System.out.println("Test with valid server location: (Must returns "+
                                                              numberOfTests + " successfully responses)");
        for (int i = 0; i <numberOfTests ; i++) {

            System.out.print("Send request to " + connector.getDatabaseLocation());
            assertTrue(connector.testConnection());
            System.out.println(" - connection OK");
        }

        System.out.println("\nTest with wrong server location: (Must returns " +
                                                                numberOfTests + " unsuccessfully responses)");
        connector.setDatabaseLocation("http://google.com");

        for (int i = 0; i <numberOfTests ; i++) {

            System.out.print("Send request to " + connector.getDatabaseLocation());
            assertFalse(connector.testConnection());
            System.out.println(" - no connection");
        }

    }

    @org.junit.Test
    public void checkServiceKey() throws Exception {

        DatabaseConnector connector = new DatabaseConnector();
        System.out.println("Checking service key: " + connector.checkServiceKey("0000000000"));
        System.out.println("Checking service key: " + connector.checkServiceKey("1234567890"));
    }

    @org.junit.Test
    public void createAccount() throws Exception {

        DatabaseConnector connector = new DatabaseConnector();
        System.out.print("Try to create new account:");
        int accountID = connector.createAccount("1234567890", "Василь", "Петрович", "Коваленко", 40, "Київ");
        System.out.println(" successfully created with #" + accountID);
    }

    @org.junit.Test
    public void addCard() throws Exception {

        DatabaseConnector connector = new DatabaseConnector();
        System.out.println("Adding card : " + connector.addCard("1234567890", 1) + " successfully added");

    }

    @org.junit.Test
    public void getBlockedCards() throws Exception {

    }

    @org.junit.Test
    public void blockCard() throws Exception {

        DatabaseConnector connector = new DatabaseConnector();
        System.out.println("Try block card: " + connector.blockCard("1111222233334444"));

    }

    @org.junit.Test
    public void unblockCard() throws Exception {

        DatabaseConnector connector = new DatabaseConnector();
        System.out.println("Try unblock card: " + connector.unblockCard("1234567890","1111222233334444"));

    }

}