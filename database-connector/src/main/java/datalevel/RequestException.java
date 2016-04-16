package datalevel;

/**
 * Implements exception for {@code DatabaseConnector} requests
 */
public class RequestException extends Exception {

    private RequestErrorCode errorCode;

    public RequestException(RequestErrorCode errorCode) {
        super();
        this.errorCode = errorCode;
    }

    public RequestErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(RequestErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public String getMessage() {
        return  "Catch: " + this.errorCode;
    }

    @Override
    public String toString() {
        return getMessage();
    }
}
