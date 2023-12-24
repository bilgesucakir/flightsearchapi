package com.bilgesucakir.flightsearchapi.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/***
 * Data transfer object for Flight entity, returned in responses
 */
public class FlightResponseDTO {

    private Integer id;

    private Integer departureAirportId;
    private String departureAirportCity;

    private Integer arrivalAirportId;

    private String arrivalAirportCity;

    private LocalDateTime departureDateTime;

    private LocalDateTime arrivalDateTime;

    private BigDecimal price;

    public FlightResponseDTO(){}

    public FlightResponseDTO(Integer id, String departureAirportCity, String arrivalAirportCity, Integer departureAirportId, Integer arrivalAirportId, LocalDateTime departureDateTime, LocalDateTime arrivalDateTime, BigDecimal price) {
        this.id = id;
        this.departureAirportCity = departureAirportCity;
        this.arrivalAirportCity = arrivalAirportCity;
        this.departureAirportId = departureAirportId;
        this.arrivalAirportId = arrivalAirportId;
        this.departureDateTime = departureDateTime;
        this.arrivalDateTime = arrivalDateTime;
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDepartureAirportCity() {
        return departureAirportCity;
    }

    public void setDepartureAirportCity(String departureAirportCity) {
        this.departureAirportCity = departureAirportCity;
    }

    public String getArrivalAirportCity() {
        return arrivalAirportCity;
    }

    public void setArrivalAirportCity(String arrivalAirportCity) {
        this.arrivalAirportCity = arrivalAirportCity;
    }

    public LocalDateTime getDepartureDateTime() {
        return departureDateTime;
    }

    public Integer getDepartureAirportId() {
        return departureAirportId;
    }

    public void setDepartureAirportId(Integer departureAirportId) {
        this.departureAirportId = departureAirportId;
    }

    public Integer getArrivalAirportId() {
        return arrivalAirportId;
    }

    public void setArrivalAirportId(Integer arrivalAirportId) {
        this.arrivalAirportId = arrivalAirportId;
    }

    public void setDepartureDateTime(LocalDateTime departureDateTime) {
        this.departureDateTime = departureDateTime;
    }

    public LocalDateTime getArrivalDateTime() {
        return arrivalDateTime;
    }

    public void setArrivalDateTime(LocalDateTime arrivalDateTime) {
        this.arrivalDateTime = arrivalDateTime;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
