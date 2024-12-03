package com.lumios.lumiosservice.api.exceptions;

public class NoSuchBookExistsException extends RuntimeException {

    public NoSuchBookExistsException(String message) {
        super(message);
    }
}
