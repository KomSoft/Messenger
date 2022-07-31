package com.itea.messenger.exception;

public class ValidationException extends Exception {
    private String message;

    public ValidationException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return message;
    }
}
