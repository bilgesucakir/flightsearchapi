package com.bilgesucakir.flightsearchapi.exception;

/**
 * Custom exception
 */
public class InvalidFlightDestinationException extends RuntimeException {

    public InvalidFlightDestinationException(String message) {
        super(message);
    }
}

