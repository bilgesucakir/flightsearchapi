package com.bilgesucakir.flightsearchapi.service;

import com.bilgesucakir.flightsearchapi.dto.AirportDTO;
import com.bilgesucakir.flightsearchapi.entity.Airport;
import com.bilgesucakir.flightsearchapi.entity.Flight;
import com.bilgesucakir.flightsearchapi.repository.AirportRepository;
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
    public boolean airportExists(int id) {
        return airportRepository.existsById(id);
    }

    @Override
    public AirportDTO convertAirportToAirportDTO(Airport airport) {
        AirportDTO airportDTO = new AirportDTO();

        if(airport.getCity() != null){
            airportDTO.setCity(airport.getCity());
        }

        airportDTO.setId(airport.getId());

        return airportDTO;
    }

    @Override
    public Airport convertAirportDTOToAirport(AirportDTO airportDTO) {

        Airport airport = new Airport();

        if(airportDTO.getId() != null && airportDTO.getId() != 0){
            airport = findById(airportDTO.getId());
        }

        if(airportDTO.getCity() != null){
            airport.setCity(airportDTO.getCity());
        }

        return airport;
    }

    @Override
    public boolean isCityValid(String city) {
        return city != null && !city.trim().isEmpty();
    }


    //other imp emthods will be added
}

