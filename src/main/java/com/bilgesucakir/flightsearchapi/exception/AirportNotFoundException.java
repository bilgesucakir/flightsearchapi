package com.bilgesucakir.flightsearchapi.exception;

/**
 * Custom exception
 */
public class AirportNotFoundException extends RuntimeException {

    public AirportNotFoundException(String message) {
        super(message);
    }
}
