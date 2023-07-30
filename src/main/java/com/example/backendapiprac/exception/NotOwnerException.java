package com.example.backendapiprac.exception;

public class NotOwnerException extends RuntimeException {
    public NotOwnerException() {
        super();
    }

    public NotOwnerException(String message) {
        super(message);
    }

    public NotOwnerException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotOwnerException(Throwable cause) {
        super(cause);
    }

    public NotOwnerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
