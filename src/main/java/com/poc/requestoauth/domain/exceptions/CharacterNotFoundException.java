package com.poc.requestoauth.domain.exceptions;

public class CharacterNotFoundException extends RuntimeException {
    public CharacterNotFoundException(String message) {
        super(message);
    }
}
