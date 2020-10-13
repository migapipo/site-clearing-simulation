package app.exceptions;

/**
 * This exception will be thrown when the program is trying to clear a protected tree
 */
public class CutTreeException extends RuntimeException{

    private String message;

    public CutTreeException(String message) {
        super();
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
