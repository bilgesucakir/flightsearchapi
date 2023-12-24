package com.bilgesucakir.flightsearchapi.service;

import com.bilgesucakir.flightsearchapi.dto.FlightRequestDTO;
import com.bilgesucakir.flightsearchapi.dto.FlightResponseDTO;
import com.bilgesucakir.flightsearchapi.entity.Flight;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Flight service
 */
public interface FlightService {

    List<Flight> findAll();

    Flight findById(int id);

    Flight save(Flight flight);

    void deleteById(int id);

    boolean givenAirportExists(int id);

    boolean flightExists(int id);

    Flight convertFlightRequestDTOToFlight(FlightRequestDTO flightRequestDTO);

    FlightResponseDTO convertflightToFlightResponseDTO(Flight flight);

    boolean isPriceValid(BigDecimal price);

    boolean isDateRangeValid(LocalDateTime departure, LocalDateTime arrival);

}
