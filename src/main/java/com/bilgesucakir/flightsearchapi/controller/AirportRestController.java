package com.bilgesucakir.flightsearchapi.controller;

import com.bilgesucakir.flightsearchapi.dto.AirportDTO;
import com.bilgesucakir.flightsearchapi.entity.Airport;
import com.bilgesucakir.flightsearchapi.exception.AirportNotFoundException;
import com.bilgesucakir.flightsearchapi.exception.InvalidCityNameException;
import com.bilgesucakir.flightsearchapi.service.AirportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/airports")
public class AirportRestController {

    private AirportService airportService;

    @Autowired
    public AirportRestController(AirportService airportService) {
        this.airportService = airportService;
    }

    @GetMapping
    public ResponseEntity<List<AirportDTO>> getAllAirports() {

        List<Airport> airports = airportService.findAll();

        List<AirportDTO> airportDTOs = airports.stream()
                .map(airportService::convertAirportToAirportDTO)
                .collect(Collectors.toList());

        return new ResponseEntity<>(airportDTOs, HttpStatus.OK);
    }

    @GetMapping("/{airportId}")
    public ResponseEntity<AirportDTO> getAirport(@PathVariable int airportId){

        Airport airport = airportService.findById(airportId);

        if(airport == null){
            throw new AirportNotFoundException("Couldn't find airport with id: " + airportId);
        }

        AirportDTO airportDTO = airportService.convertAirportToAirportDTO(airport);

        return new ResponseEntity<>(airportDTO, HttpStatus.OK);
    }

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

    @DeleteMapping("/{airportId}")
    public ResponseEntity<String> deleteAirport(@PathVariable int airportId){

        if(!airportService.airportExists(airportId)){
            throw new AirportNotFoundException("No airport exists with id: " + airportId);
        }

        airportService.deleteById(airportId);

        return new ResponseEntity<>("Deleted airport id: " + airportId,HttpStatus.OK);
    }
}
