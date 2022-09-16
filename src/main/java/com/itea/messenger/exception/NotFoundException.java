package com.itea.messenger.exception;

import java.util.function.Supplier;

public class NotFoundException extends Exception implements Supplier<String> {
    private String message;

    public NotFoundException(String message) {
        this.message = message;
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
