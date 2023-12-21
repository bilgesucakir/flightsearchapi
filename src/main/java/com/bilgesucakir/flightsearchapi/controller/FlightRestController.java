package com.bilgesucakir.flightsearchapi.controller;

import com.bilgesucakir.flightsearchapi.dto.AirportDTO;
import com.bilgesucakir.flightsearchapi.dto.FlightRequestDTO;
import com.bilgesucakir.flightsearchapi.dto.FlightResponseDTO;
import com.bilgesucakir.flightsearchapi.entity.Airport;
import com.bilgesucakir.flightsearchapi.entity.Flight;
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
            throw new RuntimeException("Couldn't find flight with id: " + flightId);
        }

        FlightResponseDTO flightResponseDTO = flightService.convertflightToFlightResponseDTO(flight);

        return new ResponseEntity<>(flightResponseDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<FlightResponseDTO> createFlight(@RequestBody FlightRequestDTO flightRequestDTO) {

        if(flightRequestDTO.getId() != null) {
            throw new RuntimeException("Providing id during add flight now allowed.");
        }

        flightRequestDTO.setId(0);
        if(flightRequestDTO.getDepartureAirportId() != null && !flightService.givenAirportExists(flightRequestDTO.getDepartureAirportId())){
            throw new RuntimeException("Departure airport with given id: " + flightRequestDTO.getDepartureAirportId() + " does not exist.");
        }

        if(flightRequestDTO.getArrivalAirportId() != null && !flightService.givenAirportExists(flightRequestDTO.getArrivalAirportId())){
            throw new RuntimeException("Arrival airport with given id: " + flightRequestDTO.getArrivalAirportId() + " does not exist.");
        }

        Flight flight = flightService.convertFlightRequestDTOToFlight(flightRequestDTO);

        Flight createdFlight = flightService.save(flight);

        FlightResponseDTO flightResponseDTO = flightService.convertflightToFlightResponseDTO(createdFlight);

        return new ResponseEntity<>(flightResponseDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{flightId}")
    public ResponseEntity<FlightResponseDTO> updateFlight(@PathVariable int flightId, @RequestBody FlightRequestDTO flightRequestDTO) {

        if(!flightService.flightExists(flightId)){
            throw new RuntimeException("No flight exists with id: " + flightId);
        }

        if(flightRequestDTO.getDepartureAirportId() != null && !flightService.givenAirportExists(flightRequestDTO.getDepartureAirportId())){
            throw new RuntimeException("Departure airport with given id: " + flightRequestDTO.getDepartureAirportId() + " does not exist.");
        }

        if(flightRequestDTO.getArrivalAirportId() != null && !flightService.givenAirportExists(flightRequestDTO.getArrivalAirportId())){
            throw new RuntimeException("Arrival airport with given id: " + flightRequestDTO.getArrivalAirportId() + " does not exist.");
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
            throw new RuntimeException("No flight exists with id: " + flightId);
        }

        flightService.deleteById(flightId);

        return new ResponseEntity<>("Deleted flight id: " + flightId, HttpStatus.OK);
    }
}
