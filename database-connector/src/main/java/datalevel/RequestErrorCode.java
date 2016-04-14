package datalevel;

/**
 * Represents types of request errors
 */
public enum RequestErrorCode {

    WRONG_CARD_ID, WRONG_PIN, INSUFFICIENT_FUNDS, WRONG_CASHSIZE, WRONG_NEW_PIN, WRONG_SERVICE_KEY,
    WRONG_ACCOUNT, TO_LONG_FIRST_NAME, TO_LONG_MIDDLE_NAME, TO_LONG_LAST_NAME, SOMETHING_WRONG;

    @Override
    public String toString() {

        switch (this.ordinal()) {
            case 0:
                return "WRONG_CARD_ID";
            case 1:
                return "WRONG_PIN";
            case 2:
                return "INSUFFICIENT_FUNDS";
            case 3:
                return "WRONG_CASHSIZE";
            case 4:
                return "WRONG_NEW_PIN";
            case 5:
                return "WRONG_SERVICE_KEY";
            case 6:
                return "WRONG_ACCOUNT";
            case 7:
                return "TO_LONG_FIRST_NAME";
            case 8:
                return "TO_LONG_MIDDLE_NAME";
            case 9:
                return "TO_LONG_LAST_NAME";
            default:
                return "SOMETHING_WRONG";
        }
    }
}
