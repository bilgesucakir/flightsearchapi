package com.bilgesucakir.flightsearchapi.service;

import com.bilgesucakir.flightsearchapi.entity.Airport;
import com.bilgesucakir.flightsearchapi.entity.Flight;
import com.bilgesucakir.flightsearchapi.repository.AirportRepository;
import com.bilgesucakir.flightsearchapi.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class ScheduledJobServiceImpl implements ScheduledJobService {
    private FlightRepository flightRepository;
    private AirportRepository airportRepository;

    @Autowired
    public ScheduledJobServiceImpl(FlightRepository flightRepository, AirportRepository airportRepository) {
        this.flightRepository = flightRepository;
        this.airportRepository = airportRepository;
    }


    @Override
    @Scheduled(cron = "0 0 0 * * *")
    public void importFlightsDaily() {
        List<Flight> receivedFlightsFromExternalAPI = mockAPICall();

        flightRepository.saveAll(receivedFlightsFromExternalAPI);
    }

    @Override
    public List<Flight> mockAPICall() {//generate 20 flights each day

        Random random = new Random();

        List<Flight> generatedFlightData = new ArrayList<>();

        List<Airport> airports = airportRepository.findAll();
        int len = airports.size();

        OffsetDateTime dateTime1 = OffsetDateTime.now().plusDays(7).withHour(0).withMinute(0);
        OffsetDateTime dateTime2 = dateTime1.plusHours(1).plusMinutes(20);

        for(int i=0; i<5; i++){

            BigDecimal price = new BigDecimal(random.nextInt(500,5000));
            BigDecimal price2 = new BigDecimal(random.nextInt(500,5000));
            BigDecimal price3 = new BigDecimal(random.nextInt(500,5000));
            BigDecimal price4 = new BigDecimal(random.nextInt(500,5000));

            Flight flight1 = new Flight(0, airports.get(0), airports.get(1), dateTime1, dateTime2, price);
            Flight flight2 = new Flight(0, airports.get(1), airports.get(0), dateTime1.minusMinutes(30), dateTime2.plusMinutes(30), price2);

            Flight flight3 = new Flight(0, airports.get(len-2), airports.get(len-1), dateTime1, dateTime2, price3);
            Flight flight4 = new Flight(0, airports.get(len-1), airports.get(len-2), dateTime1.plusMinutes(30), dateTime2.plusMinutes(30), price4);

            generatedFlightData.add(flight1);
            generatedFlightData.add(flight2);
            generatedFlightData.add(flight3);
            generatedFlightData.add(flight4);

            dateTime1 = dateTime1.plusHours(4);
            dateTime2 = dateTime1.plusHours(1).plusMinutes(20);
        }


        return generatedFlightData;
    }
}
