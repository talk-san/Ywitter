package com.talxan.ywitter.exceptions;

public class EmailAlreadyTakenException extends RuntimeException {

    public EmailAlreadyTakenException() {
        super("Email is already taken");
    }

    public EmailAlreadyTakenException(String email) {
        super("User with email " + email + " is already taken!");
    }
}
