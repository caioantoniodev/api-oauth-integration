package com.poc.requestoauth.domain.exceptions;

public class OauthException extends RuntimeException {
    public OauthException(String message) {
        super(message);
    }
}
