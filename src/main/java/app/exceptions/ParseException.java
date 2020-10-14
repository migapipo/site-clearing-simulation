package app.exceptions;

/**
 * This exception will be thrown if the input file/commands cannot be parsed successfully.
 */

public class ParseException extends RuntimeException {

    private String message;
    public ParseException(String message) {
        super();
       this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
