package com.example.flightsearchapi.service;

import com.example.flightsearchapi.entity.Airport;
import com.example.flightsearchapi.entity.Flight;

import java.util.List;

public interface FlightService {

    List<Flight> findAll();

    Flight findById(int id);

    Flight save(Flight flight);

    void deleteById(int id);

    List<Flight> findByDepartureAirport(Airport airport);

    List<Flight> findByArrivalAirport(Airport airport);

    //List<Airport> findByFilter();

    //flight seach methods will be added

}
