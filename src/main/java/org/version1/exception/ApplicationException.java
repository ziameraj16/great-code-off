package org.version1.exception;

public class ApplicationException extends RuntimeException {

    public ApplicationException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
