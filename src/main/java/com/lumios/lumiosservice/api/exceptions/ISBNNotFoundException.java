package com.lumios.lumiosservice.api.exceptions;

public class ISBNNotFoundException extends RuntimeException {
    public ISBNNotFoundException() {
        super("No book found for ISBN");
    }
}
