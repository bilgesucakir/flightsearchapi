package com.bilgesucakir.flightsearchapi.service;

import com.bilgesucakir.flightsearchapi.dto.FlightResponseDTO;
import com.bilgesucakir.flightsearchapi.entity.Airport;
import com.bilgesucakir.flightsearchapi.entity.Flight;
import com.bilgesucakir.flightsearchapi.repository.AirportRepository;
import com.bilgesucakir.flightsearchapi.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FlightSearchServiceImpl implements FlightSearchService{


    private FlightRepository flightRepository;
    private AirportRepository airportRepository;

    @Autowired
    public FlightSearchServiceImpl(FlightRepository flightRepository, AirportRepository airportRepository){
        this.flightRepository = flightRepository;
        this.airportRepository = airportRepository;
    }


    @Override
    public List<Flight> findFlightsWithFilters(String departureCity, String arrivalCity, LocalDate departureDate) {

        OffsetDateTime departureDateTimeBegin = departureDate.atStartOfDay().atOffset(ZoneOffset.UTC);
        OffsetDateTime departureDateTimeEnd = departureDate.atTime(23, 59, 59, 999999999).atOffset(ZoneOffset.UTC);

        List<Airport> departureAirports = airportRepository.findByCity(departureCity);
        List<Airport> arrivalAirports = airportRepository.findByCity(arrivalCity);

        List<Flight> filteredFlights = flightRepository.findByDepartureAirportInAndArrivalAirportInAndDepartureDateTimeBetween(departureAirports, arrivalAirports, departureDateTimeBegin, departureDateTimeEnd);

        return filteredFlights;
    }

    @Override
    public boolean isDateRangeValid(LocalDate departureDate, LocalDate returnDate) {
        return !departureDate.isAfter(returnDate);
    }

    @Override
    public boolean isCityValid(String city) {
        return !airportRepository.findByCity(city).isEmpty();
    }


}
