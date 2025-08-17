package com.firomsa.todoApp.exception;

public class UserNameAlreadyExistsException extends RuntimeException {
    private static final String message = "UserName already exists";

    public UserNameAlreadyExistsException() {
        super(message);
    }
}
