package com.example.flightsearchapi.repository;

import com.example.flightsearchapi.entity.Airport;
import com.example.flightsearchapi.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FlightRepository extends JpaRepository<Flight, Integer> {
    List<Flight> findByArrivalAirport(Airport airport);
    List<Flight> findByDepartureAirport(Airport airport);
}
