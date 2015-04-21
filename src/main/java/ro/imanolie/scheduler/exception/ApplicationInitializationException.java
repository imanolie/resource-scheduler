package ro.imanolie.scheduler.exception;

/**
 * @author imanolie on 4/21/2015.
 */
public class ApplicationInitializationException extends Exception {
    public ApplicationInitializationException(String message) {
        super(message);
    }

    public ApplicationInitializationException(Throwable cause) {
        super(cause);
    }

    public ApplicationInitializationException(String message, Throwable cause) {
        super(message, cause);
    }
}
