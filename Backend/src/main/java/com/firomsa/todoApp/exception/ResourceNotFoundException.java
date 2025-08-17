package com.firomsa.todoApp.exception;

public class ResourceNotFoundException extends RuntimeException {
    private static final String message = "The requested resource could not be found";

    public ResourceNotFoundException() {
        super(message);
    }
}
