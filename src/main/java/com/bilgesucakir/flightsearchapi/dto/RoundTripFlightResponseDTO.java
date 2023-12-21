package com.bilgesucakir.flightsearchapi.dto;

import java.util.List;

public class RoundTripFlightResponseDTO {
    private List<FlightResponseDTO> departureFlights;

    private List<FlightResponseDTO> returnFlights;

    public RoundTripFlightResponseDTO(){}

    public RoundTripFlightResponseDTO(List<FlightResponseDTO> departureFlights, List<FlightResponseDTO> returnFlights) {
        this.departureFlights = departureFlights;
        this.returnFlights = returnFlights;
    }

    public List<FlightResponseDTO> getDepartureFlights() {
        return departureFlights;
    }

    public void setDepartureFlights(List<FlightResponseDTO> departureFlights) {
        this.departureFlights = departureFlights;
    }

    public List<FlightResponseDTO> getReturnFlights() {
        return returnFlights;
    }

    public void setReturnFlights(List<FlightResponseDTO> returnFlights) {
        this.returnFlights = returnFlights;
    }
}
