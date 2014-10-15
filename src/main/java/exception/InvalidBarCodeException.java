package exception;

/**
 * @author Alexey
 */
public class InvalidBarCodeException extends Exception {
    public InvalidBarCodeException() {
    }

    public InvalidBarCodeException(String message) {
        super(message);
    }

    public InvalidBarCodeException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidBarCodeException(Throwable cause) {
        super(cause);
    }
}
