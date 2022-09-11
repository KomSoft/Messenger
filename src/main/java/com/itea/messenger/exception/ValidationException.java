package com.itea.messenger.exception;

import java.util.function.Supplier;

public class ValidationException extends Exception implements Supplier<String> {
    private String message;

    public ValidationException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String get() {
        return message;
    }
}
