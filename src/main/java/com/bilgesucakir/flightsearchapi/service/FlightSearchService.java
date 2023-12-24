package com.bilgesucakir.flightsearchapi.service;

import com.bilgesucakir.flightsearchapi.entity.Flight;

import java.time.LocalDate;
import java.util.List;

/**
 * Flight service
 */
public interface FlightSearchService {

    List<Flight> findFlightsWithFilters(String departureCity, String arrivalCity, LocalDate departureDate);

    boolean isDateRangeValid(LocalDate departureDate, LocalDate returnDate);

    boolean isCityValid(String city);
}
