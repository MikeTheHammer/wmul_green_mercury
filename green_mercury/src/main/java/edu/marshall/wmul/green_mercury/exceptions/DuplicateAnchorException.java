package edu.marshall.wmul.green_mercury.exceptions;

public class DuplicateAnchorException extends RuntimeException {

    public DuplicateAnchorException(String errorMessage) {
        super(errorMessage);
    }

    public DuplicateAnchorException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
    
}
