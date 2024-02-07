package com.talxan.ywitter.exceptions;

public class PostNotFoundException extends RuntimeException {
    public PostNotFoundException() {
        super("Post not found");
    }
    public PostNotFoundException(String message) {
        super(message);
    }

    public PostNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

