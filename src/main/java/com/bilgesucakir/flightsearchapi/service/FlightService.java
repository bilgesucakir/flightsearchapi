package com.bilgesucakir.flightsearchapi.service;

import com.bilgesucakir.flightsearchapi.dto.FlightRequestDTO;
import com.bilgesucakir.flightsearchapi.dto.FlightResponseDTO;
import com.bilgesucakir.flightsearchapi.entity.Flight;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;

public interface FlightService {

    List<Flight> findAll();

    Flight findById(int id);

    Flight save(Flight flight);

    void deleteById(int id);

    boolean givenAirportExists(int id);

    boolean flightExists(int id);


    Flight convertFlightRequestDTOToFlight(FlightRequestDTO flightRequestDTO);

    FlightResponseDTO convertflightToFlightResponseDTO(Flight flight);

    List<FlightResponseDTO> findFlightsWithFilters(String departureCity, String arrivalCity, LocalDate departureDate);


    //flight seach methods will be added

}
