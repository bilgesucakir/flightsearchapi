package com.bilgesucakir.flightsearchapi.controller;

import com.bilgesucakir.flightsearchapi.dto.FlightResponseDTO;
import com.bilgesucakir.flightsearchapi.dto.OneWayFlightResponseDTO;
import com.bilgesucakir.flightsearchapi.dto.RoundTripFlightResponseDTO;
import com.bilgesucakir.flightsearchapi.entity.Flight;
import com.bilgesucakir.flightsearchapi.exception.AirportNotFoundException;
import com.bilgesucakir.flightsearchapi.exception.InvalidCityNameException;
import com.bilgesucakir.flightsearchapi.exception.InvalidDateRangeException;
import com.bilgesucakir.flightsearchapi.exception.InvalidFlightDestinationException;
import com.bilgesucakir.flightsearchapi.service.FlightSearchService;
import com.bilgesucakir.flightsearchapi.service.FlightService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/search/flights")
public class FlightSearchRestController {

    private FlightSearchService flightSearchService;
    private FlightService flightService;

    @Autowired
    public FlightSearchRestController(FlightSearchService flightSearchService, FlightService flightService){
        this.flightSearchService = flightSearchService;
        this.flightService = flightService;
    }

    @GetMapping
    public ResponseEntity<?> getFlightsWithFilters(
            @RequestParam(name = "departureCity") String departureCity,
            @RequestParam(name = "arrivalCity") String arrivalCity,
            @RequestParam(name = "departureDate") LocalDate departureDate,
            @RequestParam(name = "returnDate", required = false) LocalDate returnDate
    ){

        if(!flightSearchService.isCityValid(departureCity)){
            throw new AirportNotFoundException("Cannot get flights. No airport exists with name: " + departureCity);
        }

        if(!flightSearchService.isCityValid(arrivalCity)){
            throw new AirportNotFoundException("Cannot get flights. No airport exists with name: " + arrivalCity);
        }

        if(departureCity.equals(arrivalCity)){
            throw new InvalidFlightDestinationException("Cannot get flights. Departure and arrival cities cannot be the same.");
        }

        List<Flight> departureFlights = flightSearchService.findFlightsWithFilters(departureCity, arrivalCity, departureDate);

        List<FlightResponseDTO> departureFlightResponseDTOs = departureFlights.stream()
                .map(flightService::convertflightToFlightResponseDTO)
                .collect(Collectors.toList());


        if(returnDate != null){

            if(!flightSearchService.isDateRangeValid(departureDate, returnDate)){
                throw new InvalidDateRangeException("Cannot get flights. Departure date cannot be be bigger than return date.");
            }

            //for return flights, departure and arrival city information will be swapped between each other
            List<Flight> returnFlights = flightSearchService.findFlightsWithFilters(arrivalCity, departureCity, returnDate);

            List<FlightResponseDTO> returnFlightResponseDTOs = returnFlights.stream()
                    .map(flightService::convertflightToFlightResponseDTO)
                    .collect(Collectors.toList());

            RoundTripFlightResponseDTO roundTripFlightResponseDTO = new RoundTripFlightResponseDTO();

            roundTripFlightResponseDTO.setDepartureFlights(departureFlightResponseDTOs);
            roundTripFlightResponseDTO.setReturnFlights(returnFlightResponseDTOs);

            return new ResponseEntity<>(roundTripFlightResponseDTO, HttpStatus.OK);
        }
        else{

            OneWayFlightResponseDTO oneWayFlightResponseDTO = new OneWayFlightResponseDTO();

            oneWayFlightResponseDTO.setOneWayFlights(departureFlightResponseDTOs);

            return new ResponseEntity<>(oneWayFlightResponseDTO, HttpStatus.OK);
        }
    }
}
