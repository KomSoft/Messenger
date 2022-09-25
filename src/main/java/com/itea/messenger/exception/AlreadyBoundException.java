package com.itea.messenger.exception;

import java.util.function.Supplier;

public class AlreadyBoundException extends Exception implements Supplier<String> {
    private String message;

    public AlreadyBoundException(String message) {
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
