package com.bilgesucakir.flightsearchapi.service;

import com.bilgesucakir.flightsearchapi.entity.Flight;

import java.util.List;

public interface ScheduledJobService {

    void importFlightsDaily();

    List<Flight> mockAPICall();

}
