package com.bilgesucakir.flightsearchapi.repository;

import com.bilgesucakir.flightsearchapi.entity.Airport;
import com.bilgesucakir.flightsearchapi.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

public interface FlightRepository extends JpaRepository<Flight, Integer> {

    List<Flight> findByDepartureAirportInAndArrivalAirportInAndDepartureDateTimeBetween(List<Airport> departureAirports, List<Airport> arrivalAirports, LocalDateTime departureDateTimeBegin, LocalDateTime departureDatetimeEnd);
}