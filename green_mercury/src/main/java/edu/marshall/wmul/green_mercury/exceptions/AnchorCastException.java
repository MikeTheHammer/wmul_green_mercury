package edu.marshall.wmul.green_mercury.exceptions;

public class AnchorCastException extends RuntimeException {
    
    public AnchorCastException(String errorMessage) {
        super(errorMessage);
    }

    public AnchorCastException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }

}
