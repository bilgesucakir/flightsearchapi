package com.bilgesucakir.flightsearchapi.controller;

import com.bilgesucakir.flightsearchapi.dto.FlightResponseDTO;
import com.bilgesucakir.flightsearchapi.dto.OneWayFlightResponseDTO;
import com.bilgesucakir.flightsearchapi.dto.RoundTripFlightResponseDTO;
import com.bilgesucakir.flightsearchapi.entity.Flight;
import com.bilgesucakir.flightsearchapi.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/search/flights")
public class FlightSearchRestController {

    private FlightService flightService;

    @Autowired
    public FlightSearchRestController(FlightService flightService){
        this.flightService = flightService;
    }

    @GetMapping
    public ResponseEntity<?> getFlightsWithFilters(
            @RequestParam(name = "departureCity") String departureCity,
            @RequestParam(name = "arrivalCity") String arrivalCity,
            @RequestParam(name = "departureDate") LocalDate departureDate,
            @RequestParam(name = "returnDate", required = false) LocalDate returnDate
    ){

        List<FlightResponseDTO> departureFlights = flightService.findFlightsWithFilters(departureCity, arrivalCity, departureDate);

        if(returnDate != null){

            //for return flights, departure and arrival city information will be swapped between each other
            List<FlightResponseDTO> returnFlights = flightService.findFlightsWithFilters(arrivalCity, departureCity, returnDate);

            RoundTripFlightResponseDTO roundTripFlightResponseDTO = new RoundTripFlightResponseDTO();

            roundTripFlightResponseDTO.setDepartureFlights(departureFlights);
            roundTripFlightResponseDTO.setReturnFlights(returnFlights);

            return new ResponseEntity<>(roundTripFlightResponseDTO, HttpStatus.OK);
        }
        else{

            OneWayFlightResponseDTO oneWayFlightResponseDTO = new OneWayFlightResponseDTO();

            oneWayFlightResponseDTO.setOneWayFlights(departureFlights);

            return new ResponseEntity<>(oneWayFlightResponseDTO, HttpStatus.OK);
        }
    }
}
