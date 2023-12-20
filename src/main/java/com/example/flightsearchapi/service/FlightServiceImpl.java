package com.example.flightsearchapi.service;

import com.example.flightsearchapi.entity.Airport;
import com.example.flightsearchapi.entity.Flight;
import com.example.flightsearchapi.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FlightServiceImpl implements FlightService{
    private FlightRepository flightRepository;

    @Autowired
    public FlightServiceImpl(FlightRepository flightRepository){
        this.flightRepository = flightRepository;
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
    public List<Flight> findByDepartureAirport(Airport airport) {
        return flightRepository.findByDepartureAirport(airport);
    }


    @Override
    public List<Flight> findByArrivalAirport(Airport airport) {
        return flightRepository.findByArrivalAirport(airport);
    }







    //other imp emthods will be added
}
