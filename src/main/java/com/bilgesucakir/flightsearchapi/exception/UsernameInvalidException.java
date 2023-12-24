package com.bilgesucakir.flightsearchapi.exception;

/**
 * Custom exception
 */
public class UsernameInvalidException extends  RuntimeException{

    public UsernameInvalidException(String message) {
        super(message);
    }
}
