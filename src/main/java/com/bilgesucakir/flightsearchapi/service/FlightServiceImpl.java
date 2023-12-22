package com.bilgesucakir.flightsearchapi.service;

import com.bilgesucakir.flightsearchapi.dto.FlightRequestDTO;
import com.bilgesucakir.flightsearchapi.dto.FlightResponseDTO;
import com.bilgesucakir.flightsearchapi.entity.Flight;
import com.bilgesucakir.flightsearchapi.repository.AirportRepository;
import com.bilgesucakir.flightsearchapi.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
public class FlightServiceImpl implements FlightService{
    private FlightRepository flightRepository;
    private AirportRepository airportRepository;

    @Autowired
    public FlightServiceImpl(FlightRepository flightRepository, AirportRepository airportRepository){
        this.flightRepository = flightRepository;
        this.airportRepository = airportRepository;
    }


    @Override
    public List<Flight> findAll() {
        return flightRepository.findAll();
    }

    @Override
    public Flight findById(int id) {
        Optional<Flight> result = flightRepository.findById(id);

        Flight flight = null;

        if(result.isPresent()){
            flight = result.get();
        }

        return flight;
    }

    @Override
    public Flight save(Flight flight) {
        return flightRepository.save(flight);
    }

    @Override
    public void deleteById(int id) {
        flightRepository.deleteById(id);
    }

    @Override
    public boolean givenAirportExists(int id) {
        return airportRepository.existsById(id);
    }

    @Override
    public boolean flightExists(int id) {
        return flightRepository.existsById(id);
    }

    @Override
    public Flight convertFlightRequestDTOToFlight(FlightRequestDTO flightRequestDTO) {
        Flight flight = new Flight();

        if( flightRequestDTO.getId() != null && flightRequestDTO.getId() != 0 ){
            flight = findById(flightRequestDTO.getId());
        }

        if(flightRequestDTO.getArrivalDateTime() != null) {
            flight.setArrivalDateTime(flightRequestDTO.getArrivalDateTime());
        }

        if(flightRequestDTO.getDepartureDateTime() != null) {
            flight.setDepartureDateTime(flightRequestDTO.getDepartureDateTime());
        }

        if(flightRequestDTO.getPrice() != null){
            flight.setPrice(flightRequestDTO.getPrice());
        }

        if(flightRequestDTO.getDepartureAirportId() != null){
            flight.setDepartureAirport(airportRepository.getById(flightRequestDTO.getDepartureAirportId()));
        }

        if(flightRequestDTO.getArrivalAirportId() != null){
            flight.setArrivalAirport(airportRepository.getById(flightRequestDTO.getArrivalAirportId()));
        }

        return flight;
    }

    @Override
    public FlightResponseDTO convertflightToFlightResponseDTO(Flight flight) {
        FlightResponseDTO flightResponseDTO = new FlightResponseDTO();

        flightResponseDTO.setId(flight.getId());

        if(flight.getArrivalAirport() != null){
            flightResponseDTO.setArrivalAirport(flight.getArrivalAirport().getCity());
        }

        if(flight.getDepartureAirport() != null){
            flightResponseDTO.setDepartureAirport(flight.getDepartureAirport().getCity());
        }

        if(flight.getArrivalDateTime() != null) {
            flightResponseDTO.setArrivalDateTime(flight.getArrivalDateTime());
        }

        if(flight.getDepartureDateTime() != null) {
            flightResponseDTO.setDepartureDateTime(flight.getDepartureDateTime());
        }

        if(flight.getPrice() != null){
            flightResponseDTO.setPrice(flight.getPrice());
        }

        return flightResponseDTO;
    }

    @Override
    public boolean isPriceValid(BigDecimal price) {
        return price.doubleValue() >= 0;
    }

    @Override
    public boolean isDateRangeValid(LocalDateTime departure, LocalDateTime arrival) {
        return departure.isBefore(arrival);
    }

    //other imp emthods will be added
}
