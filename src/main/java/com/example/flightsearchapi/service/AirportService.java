package com.example.flightsearchapi.service;

import com.example.flightsearchapi.entity.Airport;
import com.example.flightsearchapi.entity.Flight;

import java.util.Date;
import java.util.List;

public interface AirportService {

    List<Airport> findAll();

    Airport findById(int id);

    Airport save(Airport airport);

    void deleteById(int id);

    List<Airport> findByDepartureFlight(Flight flight);

    List<Airport> findByArrivalFlight(Flight flight);

    //List<Airport> findByFilter();



}
