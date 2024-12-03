package com.lumios.lumiosservice.api.exceptions;

public class NoSuchUserExistsException extends RuntimeException {

    public NoSuchUserExistsException(String message) {
        super(message);
    }
}
