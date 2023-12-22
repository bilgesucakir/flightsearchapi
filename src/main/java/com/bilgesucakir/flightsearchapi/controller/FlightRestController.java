package com.bilgesucakir.flightsearchapi.controller;

import com.bilgesucakir.flightsearchapi.dto.AirportDTO;
import com.bilgesucakir.flightsearchapi.dto.FlightRequestDTO;
import com.bilgesucakir.flightsearchapi.dto.FlightResponseDTO;
import com.bilgesucakir.flightsearchapi.entity.Airport;
import com.bilgesucakir.flightsearchapi.entity.Flight;
import com.bilgesucakir.flightsearchapi.exception.*;
import com.bilgesucakir.flightsearchapi.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/flights")
public class FlightRestController {

    private FlightService flightService;

    @Autowired
    public FlightRestController(FlightService flightService) {
        this.flightService = flightService;
    }

    @GetMapping
    public ResponseEntity<List<FlightResponseDTO>> getAllFlights() {

        List<Flight> flights = flightService.findAll();

        List<FlightResponseDTO> flightResponseDTOs = flights.stream()
                .map(flightService::convertflightToFlightResponseDTO)
                .collect(Collectors.toList());

        return new ResponseEntity<>(flightResponseDTOs, HttpStatus.OK);
    }

    @GetMapping("/{flightId}")
    public ResponseEntity<FlightResponseDTO> getFlight(@PathVariable int flightId){

        Flight flight = flightService.findById(flightId);

        if(flight == null){
            throw new FlightNotFoundException("Couldn't find flight with id: " + flightId);
        }

        FlightResponseDTO flightResponseDTO = flightService.convertflightToFlightResponseDTO(flight);

        return new ResponseEntity<>(flightResponseDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<FlightResponseDTO> createFlight(@RequestBody FlightRequestDTO flightRequestDTO) {

        flightRequestDTO.setId(0);

        if(flightRequestDTO.getDepartureAirportId() != null && !flightService.givenAirportExists(flightRequestDTO.getDepartureAirportId())){
            throw new AirportNotFoundException("Departure airport with given id: " + flightRequestDTO.getDepartureAirportId() + " does not exist.");
        }

        if(flightRequestDTO.getArrivalAirportId() != null && !flightService.givenAirportExists(flightRequestDTO.getArrivalAirportId())){
            throw new AirportNotFoundException("Arrival airport with given id: " + flightRequestDTO.getArrivalAirportId() + " does not exist.");
        }

        if(!flightService.isPriceValid(flightRequestDTO.getPrice())){
            throw new InvalidPriceException("Cannot add flight. Price given is not valid.");
        }

        if(!flightService.isDateRangeValid(flightRequestDTO.getDepartureDateTime(), flightRequestDTO.getArrivalDateTime())){
            throw new InvalidDateRangeException("Cannot add flight. Departure date time cannot be bigger than or equal to arrival date time.");
        }

        if(flightRequestDTO.getArrivalAirportId().equals(flightRequestDTO.getDepartureAirportId())){
            throw new InvalidFlightDestinationException("Cannot add flight. Departure and arrival airports cannot be the same.");
        }

        Flight flight = flightService.convertFlightRequestDTOToFlight(flightRequestDTO);

        Flight createdFlight = flightService.save(flight);

        FlightResponseDTO flightResponseDTO = flightService.convertflightToFlightResponseDTO(createdFlight);

        return new ResponseEntity<>(flightResponseDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{flightId}")
    public ResponseEntity<FlightResponseDTO> updateFlight(@PathVariable int flightId, @RequestBody FlightRequestDTO flightRequestDTO) {

        if(!flightService.flightExists(flightId)){
            throw new FlightNotFoundException("No flight exists with id: " + flightId);
        }

        if(flightRequestDTO.getDepartureAirportId() != null && !flightService.givenAirportExists(flightRequestDTO.getDepartureAirportId())){
            throw new AirportNotFoundException("Departure airport with given id: " + flightRequestDTO.getDepartureAirportId() + " does not exist.");
        }

        if(flightRequestDTO.getArrivalAirportId() != null && !flightService.givenAirportExists(flightRequestDTO.getArrivalAirportId())){
            throw new AirportNotFoundException("Arrival airport with given id: " + flightRequestDTO.getArrivalAirportId() + " does not exist.");
        }

        if(!flightService.isPriceValid(flightRequestDTO.getPrice())){
            throw new InvalidPriceException("Cannot update flight. Price given is not valid.");
        }

        if(!flightService.isDateRangeValid(flightRequestDTO.getDepartureDateTime(), flightRequestDTO.getArrivalDateTime())){
            throw new InvalidDateRangeException("Cannot update flight. Departure date time cannot be bigger than or equal to arrival date time.");
        }

        if(flightRequestDTO.getArrivalAirportId().equals(flightRequestDTO.getDepartureAirportId())){
            throw new InvalidFlightDestinationException("Cannot update flight. Departure and arrival airports cannot be the same.");
        }

        flightRequestDTO.setId(flightId);

        Flight flight = flightService.convertFlightRequestDTOToFlight(flightRequestDTO);

        Flight updatedFlight = flightService.save(flight);

        FlightResponseDTO flightResponseDTO = flightService.convertflightToFlightResponseDTO(updatedFlight);

        return new ResponseEntity<>(flightResponseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{flightId}")
    public ResponseEntity<String> deleteFlight(@PathVariable int flightId){

        if(!flightService.flightExists(flightId)){
            throw new FlightNotFoundException("No flight exists with id: " + flightId);
        }

        flightService.deleteById(flightId);

        return new ResponseEntity<>("Deleted flight id: " + flightId, HttpStatus.OK);
    }
}
