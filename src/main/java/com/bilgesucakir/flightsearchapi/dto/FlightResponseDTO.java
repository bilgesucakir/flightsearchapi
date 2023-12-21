package com.bilgesucakir.flightsearchapi.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class FlightResponseDTO {

    private Integer id;
    private String departureAirport;

    private String arrivalAirport;

    private OffsetDateTime departureDateTime;

    private OffsetDateTime arrivalDateTime;

    private BigDecimal price;

    public FlightResponseDTO(){}

    public FlightResponseDTO(Integer id, String departureAirport, String arrivalAirport, OffsetDateTime departureDateTime, OffsetDateTime arrivalDateTime, BigDecimal price) {
        this.id = id;
        this.departureAirport = departureAirport;
        this.arrivalAirport = arrivalAirport;
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

    public String getDepartureAirport() {
        return departureAirport;
    }

    public void setDepartureAirport(String departureAirport) {
        this.departureAirport = departureAirport;
    }

    public String getArrivalAirport() {
        return arrivalAirport;
    }

    public void setArrivalAirport(String arrivalAirport) {
        this.arrivalAirport = arrivalAirport;
    }

    public OffsetDateTime getDepartureDateTime() {
        return departureDateTime;
    }

    public void setDepartureDateTime(OffsetDateTime departureDateTime) {
        this.departureDateTime = departureDateTime;
    }

    public OffsetDateTime getArrivalDateTime() {
        return arrivalDateTime;
    }

    public void setArrivalDateTime(OffsetDateTime arrivalDateTime) {
        this.arrivalDateTime = arrivalDateTime;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
