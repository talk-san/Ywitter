package com.talxan.ywitter.exceptions;

public class PostNotFoundException extends RuntimeException {

    public PostNotFoundException() {
        super("This post is no longer available");
    }

    public PostNotFoundException(String message) {
        super(message);
    }

    public PostNotFoundException(Integer id) {
        super("Post with id " + id + " is not found!");
    }
}

