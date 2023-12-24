package com.bilgesucakir.flightsearchapi.dto;

import java.util.List;

/***
 * Data transfer object for Flight search results
 * returnFlights = null or not differs the search type as one-way or two-way flights search
 */
public class FlightSearchResponseDTO {
    private List<FlightResponseDTO> departureFlights;

    private List<FlightResponseDTO> returnFlights;

    public FlightSearchResponseDTO(){}

    public FlightSearchResponseDTO(List<FlightResponseDTO> departureFlights, List<FlightResponseDTO> returnFlights) {
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
