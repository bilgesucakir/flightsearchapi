package com.bilgesucakir.flightsearchapi.controller;

import com.bilgesucakir.flightsearchapi.dto.FlightResponseDTO;
import com.bilgesucakir.flightsearchapi.dto.FlightSearchResponseDTO;
import com.bilgesucakir.flightsearchapi.entity.Flight;
import com.bilgesucakir.flightsearchapi.exception.AirportNotFoundException;
import com.bilgesucakir.flightsearchapi.exception.InvalidDateRangeException;
import com.bilgesucakir.flightsearchapi.exception.InvalidFlightDestinationException;
import com.bilgesucakir.flightsearchapi.exception.handling.CustomErrorResponse;
import com.bilgesucakir.flightsearchapi.service.FlightSearchService;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * REST Controller for Flight search with filter parameters. Necessary methods are accessed by FlightService and FlightSearchService.
 * Accessible by users having role = ROLE_USER
 */
@RestController
@RequestMapping("/api/search/flights")
@SecurityRequirement(name="bearerAuth")
public class FlightSearchRestController {

    private FlightSearchService flightSearchService;
    private FlightService flightService;

    @Autowired
    public FlightSearchRestController(FlightSearchService flightSearchService, FlightService flightService){
        this.flightSearchService = flightSearchService;
        this.flightService = flightService;
    }

    @Operation(summary = "GET request with flight search having filters",
            description = "If 3 parameters are given, one way flight search is done, if all parameters are given, two way flights search is done." +
                    "<br>For one way flights search result, FlightSearchResponseDTO's returnFlights parameter is given as null for the distinction between two search types." +
                    "<br>For two way flights search result, both parameters are not null but if no suitable flight found, they can be retuned as empty lists." +
                    "<br>Accessible to users",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200"
                    ),
                    @ApiResponse(description = "Unauthorized<br>-Token invalid (wrong or expired token)", responseCode = "401", content = {}
                    ),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500",
                            content = {@Content(array = @ArraySchema(schema = @Schema(implementation = CustomErrorResponse.class)))}
                    ),
                    @ApiResponse(description = "Not Found<br>-No departure airport exists with given city name<br>-No arrival airport exists with given city name", responseCode = "404",
                            content = {@Content(array = @ArraySchema(schema = @Schema(implementation = CustomErrorResponse.class)))}
                    ),
                    @ApiResponse(description = "Bad Request<br>-Departure date cannot be be bigger than return date<br>-Departure and arrival cities cannot be the same", responseCode = "400",
                            content = {@Content(array = @ArraySchema(schema = @Schema(implementation = CustomErrorResponse.class)))})
    })
    @GetMapping
    public ResponseEntity<FlightSearchResponseDTO> getFlightsWithFilters(
            @RequestParam(name = "departureCity") String departureCity,
            @RequestParam(name = "arrivalCity") String arrivalCity,
            @RequestParam(name = "departureDate") LocalDate departureDate,
            @RequestParam(name = "returnDate", required = false) LocalDate returnDate
    ){

        if(!flightSearchService.isCityValid(departureCity)){
            throw new AirportNotFoundException("Cannot get flights. No airport city exists with name: " + departureCity);
        }

        if(!flightSearchService.isCityValid(arrivalCity)){
            throw new AirportNotFoundException("Cannot get flights. No airport city exists with name: " + arrivalCity);
        }

        if(departureCity.equals(arrivalCity)){
            throw new InvalidFlightDestinationException("Cannot get flights. Departure and arrival cities cannot be the same.");
        }

        List<Flight> departureFlights = flightSearchService.findFlightsWithFilters(departureCity, arrivalCity, departureDate);

        List<FlightResponseDTO> departureFlightResponseDTOs = departureFlights.stream()
                .map(flightService::convertflightToFlightResponseDTO)
                .collect(Collectors.toList());

        FlightSearchResponseDTO flightSearchResponseDTO = new FlightSearchResponseDTO();

        flightSearchResponseDTO.setDepartureFlights(departureFlightResponseDTOs);
        flightSearchResponseDTO.setReturnFlights(null);

        if(returnDate != null){

            if(!flightSearchService.isDateRangeValid(departureDate, returnDate)){
                throw new InvalidDateRangeException("Cannot get flights. Departure date cannot be bigger than return date.");
            }

            //for return flights, departure and arrival city information will be swapped between each other
            List<Flight> returnFlights = flightSearchService.findFlightsWithFilters(arrivalCity, departureCity, returnDate);

            List<FlightResponseDTO> returnFlightResponseDTOs = returnFlights.stream()
                    .map(flightService::convertflightToFlightResponseDTO)
                    .collect(Collectors.toList());

            flightSearchResponseDTO.setReturnFlights(returnFlightResponseDTOs);
        }

        return new ResponseEntity<>(flightSearchResponseDTO, HttpStatus.OK);
    }
}
