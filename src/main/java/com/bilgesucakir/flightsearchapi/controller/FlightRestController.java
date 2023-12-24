package com.bilgesucakir.flightsearchapi.controller;

import com.bilgesucakir.flightsearchapi.dto.FlightRequestDTO;
import com.bilgesucakir.flightsearchapi.dto.FlightResponseDTO;
import com.bilgesucakir.flightsearchapi.entity.Flight;
import com.bilgesucakir.flightsearchapi.exception.*;
import com.bilgesucakir.flightsearchapi.exception.handling.CustomErrorResponse;
import com.bilgesucakir.flightsearchapi.service.FlightService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.stream.Collectors;

/**
 * REST Controller for Flight entity. JPA repository methods are reached by FlightService.
 * Only users with role = ROLE_ADMIN have access to its endpoints
 */
@RestController
@RequestMapping("/api/flights")
@SecurityRequirement(name="bearerAuth")
public class FlightRestController {

    private FlightService flightService;

    @Autowired
    public FlightRestController(FlightService flightService) {
        this.flightService = flightService;
    }

    @Operation(summary = "GET request for flights", description = "Returns all flights from database<br>Only accessible to admins",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200"),
                    @ApiResponse(description = "Unauthorized<br>-Token invalid (wrong or expired token)",
                            responseCode = "401", content = {}),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500",
                            content = {@Content(array = @ArraySchema(schema = @Schema(implementation = CustomErrorResponse.class)))})
    })
    @GetMapping
    public ResponseEntity<List<FlightResponseDTO>> getAllFlights() {

        List<Flight> flights = flightService.findAll();

        List<FlightResponseDTO> flightResponseDTOs = flights.stream()
                .map(flightService::convertflightToFlightResponseDTO)
                .collect(Collectors.toList());

        return new ResponseEntity<>(flightResponseDTOs, HttpStatus.OK);
    }


    @Operation(summary = "GET request for flights (with id)", description = "Returns a flight with given id<br>Only accessible to admins",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200"),
                    @ApiResponse(description = "Unauthorized<br>-Token invalid (wrong or expired token)",
                            responseCode = "401", content = {}),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500",
                            content = {@Content(array = @ArraySchema(schema = @Schema(implementation = CustomErrorResponse.class)))}),
                    @ApiResponse(description = "Not Found" + "<br>No flight exists with given id", responseCode = "404",
                            content = {@Content(array = @ArraySchema(schema = @Schema(implementation = CustomErrorResponse.class)))})
    })
    @GetMapping("/{flightId}")
    public ResponseEntity<FlightResponseDTO> getFlight(@PathVariable int flightId){

        Flight flight = flightService.findById(flightId);

        if(flight == null){
            throw new FlightNotFoundException("Couldn't find flight with id: " + flightId);
        }

        FlightResponseDTO flightResponseDTO = flightService.convertflightToFlightResponseDTO(flight);

        return new ResponseEntity<>(flightResponseDTO, HttpStatus.OK);
    }

    @Operation(summary = "POST request for flights", description = "Creates a flight with given fields<br>Only accessible to admins",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200"),
                    @ApiResponse(description = "Unauthorized<br>-Token invalid (wrong or expired token)",
                            responseCode = "401", content = {}),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500",
                            content = {@Content(array = @ArraySchema(schema = @Schema(implementation = CustomErrorResponse.class)))}),
                    @ApiResponse(description = "Bad Request" + "<br>-Given price invalid" + "<br>-Departure and airport ids cannot be the same", responseCode = "400",
                            content = {@Content(array = @ArraySchema(schema = @Schema(implementation = CustomErrorResponse.class)))}),
                    @ApiResponse(description = "Not Found" + "<br>-No airport exists with given departure aiport id" + "<br>-No airport exists with given arrival airport id", responseCode = "404",
                            content = {@Content(array = @ArraySchema(schema = @Schema(implementation = CustomErrorResponse.class)))})
    })
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

    @Operation(summary = "PUT request for flights", description = "Updates a flight with given id<br>Only accessible to admins",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200"),
                    @ApiResponse(description = "Unauthorized<br>-Token invalid (wrong or expired token)", responseCode = "401", content = {}),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500",
                            content = {@Content(array = @ArraySchema(schema = @Schema(implementation = CustomErrorResponse.class)))}),
                    @ApiResponse(description = "Bad Request" + "<br>-Given price invalid" + "<br>-Departure and airport ids cannot be the same", responseCode = "400",
                            content = {@Content(array = @ArraySchema(schema = @Schema(implementation = CustomErrorResponse.class)))}),
                    @ApiResponse(
                            description = "Not Found" + "<br>-No flight exists with given id" + "<br>-No airport exists with given departure aiport id" + "<br>-No airport exists with given arrival airport id", responseCode = "404",
                            content = {@Content(array = @ArraySchema(schema = @Schema(implementation = CustomErrorResponse.class)))})
    })
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

    @Operation(summary = "DELETE request for flights", description = "Deletes a flight with given id<br>Only accessible to admins",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200"),
                    @ApiResponse(description = "Unauthorized<br>-Token invalid (wrong or expired token)",
                            responseCode = "401", content = {}),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500",
                            content = {@Content(array = @ArraySchema(schema = @Schema(implementation = CustomErrorResponse.class)))}),
                    @ApiResponse(description = "Not Found" + "<br>-No flight exists with given id", responseCode = "404",
                            content = {@Content(array = @ArraySchema(schema = @Schema(implementation = CustomErrorResponse.class)))})
    })
    @DeleteMapping("/{flightId}")
    public ResponseEntity<String> deleteFlight(@PathVariable int flightId){

        if(!flightService.flightExists(flightId)){
            throw new FlightNotFoundException("No flight exists with id: " + flightId);
        }

        flightService.deleteById(flightId);

        return new ResponseEntity<>("Deleted flight id: " + flightId, HttpStatus.OK);
    }
}
