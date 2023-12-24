package com.bilgesucakir.flightsearchapi.exception;

/**
 * Custom exception
 */
public class InvalidCityNameException extends RuntimeException {

    public InvalidCityNameException(String message) {
        super(message);
    }
}
