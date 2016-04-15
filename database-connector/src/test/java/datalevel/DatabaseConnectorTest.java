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

    }

    @org.junit.Test
    public void addCash() throws Exception {

    }

    @org.junit.Test
    public void getBalance() throws Exception {

    }

    @org.junit.Test
    public void changePin() throws Exception {

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

    }

    @org.junit.Test
    public void createAccount() throws Exception {

    }

    @org.junit.Test
    public void addCard() throws Exception {

    }

    @org.junit.Test
    public void getBlockedCards() throws Exception {

    }

    @org.junit.Test
    public void blockCard() throws Exception {

    }

    @org.junit.Test
    public void unblockCard() throws Exception {

    }

}