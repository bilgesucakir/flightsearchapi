package com.bilgesucakir.flightsearchapi.service;

import com.bilgesucakir.flightsearchapi.entity.Flight;

import java.util.List;

/**
 * Scheduled job service
 */
public interface ScheduledJobService {

    void importFlightsDaily();

    List<Flight> mockAPICall();

}
