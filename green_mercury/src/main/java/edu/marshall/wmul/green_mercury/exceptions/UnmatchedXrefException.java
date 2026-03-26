package edu.marshall.wmul.green_mercury.exceptions;

public class UnmatchedXrefException extends RuntimeException{

    public UnmatchedXrefException(String errorMessage) {
        super(errorMessage);
    }

    public UnmatchedXrefException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
    
}
