package com.bilgesucakir.flightsearchapi.dto;

import com.bilgesucakir.flightsearchapi.entity.Flight;
import java.util.List;

/***
 * Data transfer object for Airport entity both used in requests and responses
 */
public class AirportDTO {

    private Integer id;

    private String city;
    public AirportDTO(){}

    public AirportDTO(Integer id, String city, List<Flight> departingFlights, List<Flight> arrivingFlights) {
        this.id = id;
        this.city = city;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "Airport{" +
                "id=" + id +
                ", city='" + city + '\'' +
                '}';
    }

}
