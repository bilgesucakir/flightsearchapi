package com.bilgesucakir.flightsearchapi.controller;

import com.bilgesucakir.flightsearchapi.dto.AirportDTO;
import com.bilgesucakir.flightsearchapi.entity.Airport;
import com.bilgesucakir.flightsearchapi.exception.AirportNotFoundException;
import com.bilgesucakir.flightsearchapi.exception.InvalidCityNameException;
import com.bilgesucakir.flightsearchapi.exception.handling.CustomErrorResponse;
import com.bilgesucakir.flightsearchapi.service.AirportService;
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
 * REST Controller for Airport entity. JPA repository methods are reached by AirportService.
 * Only users with role = ROLE_ADMIN have access to its endpoints
 */
@RestController
@RequestMapping("/api/airports")
@SecurityRequirement(name="bearerAuth")
public class AirportRestController {

    private AirportService airportService;

    @Autowired
    public AirportRestController(AirportService airportService) {
        this.airportService = airportService;
    }

    @Operation(summary = "GET request for airports", description = "Returns all airports in database<br>Only accessible to admins",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200"),
                    @ApiResponse(description = "Unauthorized<br>-Token invalid (wrong or expired token)",
                            responseCode = "401", content = {}),
                    @ApiResponse(description = "Internal Server Error",
                            responseCode = "500", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = CustomErrorResponse.class)))})
    })
    @GetMapping
    public ResponseEntity<List<AirportDTO>> getAllAirports() {

        List<Airport> airports = airportService.findAll();

        List<AirportDTO> airportDTOs = airports.stream()
                .map(airportService::convertAirportToAirportDTO)
                .collect(Collectors.toList());

        return new ResponseEntity<>(airportDTOs, HttpStatus.OK);
    }

    @Operation(summary = "GET request for airports", description = "Returns an airport with given id<br>Only accessible to admins",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200"),
                    @ApiResponse(description = "Unauthorized<br>-Token invalid (wrong or expired token)",
                            responseCode = "401", content = {}),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500",
                            content = {@Content(array = @ArraySchema(schema = @Schema(implementation = CustomErrorResponse.class)))}),
                    @ApiResponse(
                            description = "Not Found" + "<br>-No airport exists with given id", responseCode = "404",
                            content = {@Content(array = @ArraySchema(schema = @Schema(implementation = CustomErrorResponse.class)))})
    })
    @GetMapping("/{airportId}")
    public ResponseEntity<AirportDTO> getAirport(@PathVariable int airportId){

        Airport airport = airportService.findById(airportId);

        if(airport == null){
            throw new AirportNotFoundException("Couldn't find airport with id: " + airportId);
        }

        AirportDTO airportDTO = airportService.convertAirportToAirportDTO(airport);

        return new ResponseEntity<>(airportDTO, HttpStatus.OK);
    }

    @Operation(summary = "POST request for airports", description = "Creates an airport with given fields<br>Only accessible to admins",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200"),
                    @ApiResponse(description = "Unauthorized<br>-Token invalid (wrong or expired token)",
                            responseCode = "401", content = {}),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500",
                            content = {@Content(array = @ArraySchema(schema = @Schema(implementation = CustomErrorResponse.class)))}),
                    @ApiResponse(description = "Bad Request" + "<br>-Given city name is not valid", responseCode = "500",
                            content = {@Content(array = @ArraySchema(schema = @Schema(implementation = CustomErrorResponse.class)))}),
                    @ApiResponse(description = "Not Found" + "<br>-No airport exists with given id", responseCode = "404",
                            content = {@Content(array = @ArraySchema(schema = @Schema(implementation = CustomErrorResponse.class)))})
    })
    @PostMapping
    public ResponseEntity<AirportDTO> createAirport(@RequestBody AirportDTO airportDTO) {

        airportDTO.setId(0);

        if(!airportService.isCityValid(airportDTO.getCity())){
            throw new InvalidCityNameException("Cannot add airport. City name given is not valid.");
        }

        Airport airport = airportService.convertAirportDTOToAirport(airportDTO);

        Airport createdAirport = airportService.save(airport);

        AirportDTO airportDTOFromDB = airportService.convertAirportToAirportDTO(createdAirport);

        return new ResponseEntity<>(airportDTOFromDB, HttpStatus.CREATED);
    }

    @Operation(summary = "PUT request for airports", description = "Updates an airport with given id<br>Only accessible to admins",
            responses = {
                    @ApiResponse(description = "Success",responseCode = "200"),
                    @ApiResponse(description = "Unauthorized<br>-Token invalid (wrong or expired token)",
                            responseCode = "401", content = {}),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500",
                            content = {@Content(array = @ArraySchema(schema = @Schema(implementation = CustomErrorResponse.class)))}),
                    @ApiResponse(description = "Bad Request" + "<br>-Given city name is not valid", responseCode = "500",
                            content = {@Content(array = @ArraySchema(schema = @Schema(implementation = CustomErrorResponse.class)))}),
                    @ApiResponse(description = "Not Found" + "<br>-No airport exists with given id", responseCode = "404",
                            content = {@Content(array = @ArraySchema(schema = @Schema(implementation = CustomErrorResponse.class)))})
    })
    @PutMapping("/{airportId}")
    public ResponseEntity<AirportDTO> updateAirport(@PathVariable int airportId, @RequestBody AirportDTO airportDTO) {

        if(!airportService.airportExists(airportId)){
            throw new AirportNotFoundException("No airport exists with id: " + airportId);
        }

        if(!airportService.isCityValid(airportDTO.getCity())){
            throw new InvalidCityNameException("Cannot update airport. City name given is not valid.");
        }

        airportDTO.setId(airportId);

        Airport airport = airportService.convertAirportDTOToAirport(airportDTO);

        Airport updatedAirport = airportService.save(airport);

        AirportDTO airportDTOFromDB = airportService.convertAirportToAirportDTO(updatedAirport);

        return new ResponseEntity<>(airportDTOFromDB, HttpStatus.OK);
    }

    @Operation(summary = "DELETE request for airports", description = "Deletes an airport with given id<br>Only accessible to admins",
            responses = {@ApiResponse(description = "Success", responseCode = "200"),
                    @ApiResponse(description = "Unauthorized<br>-Token invalid (wrong or expired token)",
                            responseCode = "401", content = {}),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500",
                            content = {@Content(array = @ArraySchema(schema = @Schema(implementation = CustomErrorResponse.class)))}),
                    @ApiResponse(description = "Not Found" + "<br>-No airport exists with given id", responseCode = "404",
                            content = {@Content(array = @ArraySchema(schema = @Schema(implementation = CustomErrorResponse.class)))})
    })
    @DeleteMapping("/{airportId}")
    public ResponseEntity<String> deleteAirport(@PathVariable int airportId){

        if(!airportService.airportExists(airportId)){
            throw new AirportNotFoundException("No airport exists with id: " + airportId);
        }

        airportService.deleteById(airportId);

        return new ResponseEntity<>("Deleted airport id: " + airportId,HttpStatus.OK);
    }
}
