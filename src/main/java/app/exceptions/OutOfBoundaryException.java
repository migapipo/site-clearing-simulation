package app.exceptions;

/**
 * This exception will be thrown if there is an attempt to navigate beyond the boundaries of the site
 */
public class OutOfBoundaryException extends RuntimeException{

    private String message;

    public OutOfBoundaryException(String message) {
        super();
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
