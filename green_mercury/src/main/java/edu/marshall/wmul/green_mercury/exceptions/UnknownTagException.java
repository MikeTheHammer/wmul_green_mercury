package edu.marshall.wmul.green_mercury.exceptions;

public class UnknownTagException extends RuntimeException {
 
    public UnknownTagException(String errorMessage) {
        super(errorMessage);
    }

    public UnknownTagException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
