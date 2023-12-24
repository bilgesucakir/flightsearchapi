package com.bilgesucakir.flightsearchapi.repository;

import com.bilgesucakir.flightsearchapi.entity.Airport;
import com.bilgesucakir.flightsearchapi.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Airport repository extending JPA repository for db access and db operations
 */
public interface AirportRepository extends JpaRepository<Airport, Integer> {
    List<Airport> findByCity(String city);
}
