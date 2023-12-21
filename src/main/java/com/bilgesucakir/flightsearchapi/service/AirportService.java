package com.bilgesucakir.flightsearchapi.service;

import com.bilgesucakir.flightsearchapi.dto.AirportDTO;
import com.bilgesucakir.flightsearchapi.entity.Airport;
import com.bilgesucakir.flightsearchapi.entity.Flight;

import java.util.List;

public interface AirportService {

    List<Airport> findAll();

    Airport findById(int id);

    Airport save(Airport airport);

    void deleteById(int id);

    boolean airportExists(int id);

    AirportDTO convertAirportToAirportDTO(Airport airport);

    Airport convertAirportDTOToAirport(AirportDTO airportDTO);

    boolean isCityValid(String city);

}
