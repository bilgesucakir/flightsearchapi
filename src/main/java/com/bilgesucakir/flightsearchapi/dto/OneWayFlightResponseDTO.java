package com.bilgesucakir.flightsearchapi.dto;

import java.util.List;

public class OneWayFlightResponseDTO {

    private List<FlightResponseDTO> oneWayFlights;

    public OneWayFlightResponseDTO(){}

    public OneWayFlightResponseDTO(List<FlightResponseDTO> oneWayFlights) {
        this.oneWayFlights = oneWayFlights;
    }

    public List<FlightResponseDTO> getOneWayFlights() {
        return oneWayFlights;
    }

    public void setOneWayFlights(List<FlightResponseDTO> oneWayFlights) {
        this.oneWayFlights = oneWayFlights;
    }
}
