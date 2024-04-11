package com.talxan.ywitter.exceptions;

public class TokenInvalidException extends RuntimeException {
    public TokenInvalidException() {
        super();
    }

    public TokenInvalidException(String message) {
        super(message);
    }

    public TokenInvalidException(Throwable cause) {
        super(cause);
    }
}
