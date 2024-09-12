package org.version1.exception;

public class ThirdPartyException extends RuntimeException {

    public ThirdPartyException(String message) {
        super(message);
    }

    public ThirdPartyException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
