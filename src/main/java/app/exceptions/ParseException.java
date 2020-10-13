package app.exceptions;

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
