package com.talxan.securitylearn.exceptions;

public class SelfFollowException extends RuntimeException  {
    public SelfFollowException() {
        super();
    }

    public SelfFollowException(String message) {
        super(message);
    }
}
