package com.bilgesucakir.flightsearchapi.exception;

public class AirportNotFoundException extends RuntimeException {

    public AirportNotFoundException(String message) {
        super(message);
    }
}
