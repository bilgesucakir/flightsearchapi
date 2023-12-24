package com.bilgesucakir.flightsearchapi.exception;

/**
 * Custom exception
 */
public class InvalidPriceException extends RuntimeException {

    public InvalidPriceException(String message) {
        super(message);
    }
}
