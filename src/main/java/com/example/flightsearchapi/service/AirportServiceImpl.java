package com.example.flightsearchapi.service;

import com.example.flightsearchapi.entity.Airport;
import com.example.flightsearchapi.entity.Flight;
import com.example.flightsearchapi.repository.AirportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AirportServiceImpl implements AirportService{
    private AirportRepository airportRepository;

    @Autowired
    public AirportServiceImpl(AirportRepository airportRepository) {
        this.airportRepository = airportRepository;
    }

    @Override
    public List<Airport> findAll() {
        return airportRepository.findAll();
    }

    @Override
    public Airport findById(int id) {
        Optional<Airport> result = airportRepository.findById(id);

        Airport airport = null;

        if(result.isPresent()){
            airport = result.get();
        }

        return airport;
    }

    @Override
    public Airport save(Airport airport) {
        return airportRepository.save(airport);
    }

    @Override
    public void deleteById(int id) {
        airportRepository.deleteById(id);
    }


    @Override
    public List<Airport> findByDepartureFlight(Flight flight) {
        return airportRepository.findByDepartingFlightsContaining(flight);
    }

    @Override
    public List<Airport> findByArrivalFlight(Flight flight) {
        return airportRepository.findByArrivingFlightsContaining(flight);
    }




    //other imp emthods will be added
}

