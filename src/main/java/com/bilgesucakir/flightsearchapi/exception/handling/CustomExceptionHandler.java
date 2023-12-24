package com.bilgesucakir.flightsearchapi.exception.handling;

import com.bilgesucakir.flightsearchapi.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Custom exception handler to return necessary http responses to the users.
 * Custom exceptions are handled according to their required http responses to be returned.
 * A response entity is returned for each exception type with CustomErrorResponse object.
 * Any other exception that is caught as Exception type are treated as internal server error and
 * that exception's message is provided to the user.
 * */

@ControllerAdvice
public class CustomExceptionHandler {


    @ExceptionHandler
    public ResponseEntity<CustomErrorResponse> handleException(AirportNotFoundException exc) {
        CustomErrorResponse error = new CustomErrorResponse(HttpStatus.NOT_FOUND.value(), exc.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<CustomErrorResponse> handleException(FlightNotFoundException exc) {
        CustomErrorResponse error = new CustomErrorResponse(HttpStatus.NOT_FOUND.value(), exc.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<CustomErrorResponse> handleException(InvalidCityNameException exc) {
        CustomErrorResponse error = new CustomErrorResponse(HttpStatus.BAD_REQUEST.value(), exc.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<CustomErrorResponse> handleException(InvalidDateRangeException exc) {
        CustomErrorResponse error = new CustomErrorResponse(HttpStatus.BAD_REQUEST.value(), exc.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<CustomErrorResponse> handleException(InvalidPriceException exc) {
        CustomErrorResponse error = new CustomErrorResponse(HttpStatus.BAD_REQUEST.value(), exc.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<CustomErrorResponse> handleException(InvalidFlightDestinationException exc) {
        CustomErrorResponse error = new CustomErrorResponse(HttpStatus.BAD_REQUEST.value(), exc.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<CustomErrorResponse> handleException(UsernameInvalidException exc) {
        CustomErrorResponse error = new CustomErrorResponse(HttpStatus.BAD_REQUEST.value(), exc.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler //for all exception types
    public ResponseEntity<CustomErrorResponse> handleException(Exception exc) {

        CustomErrorResponse error = new CustomErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), exc.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
