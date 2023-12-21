package com.bilgesucakir.flightsearchapi.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class FlightRequestDTO {
    private Integer id;
    private Integer departureAirportId;

    private Integer arrivalAirportId;

    private OffsetDateTime departureDateTime;

    private OffsetDateTime arrivalDateTime;

    private BigDecimal price;

    public FlightRequestDTO(){}

    public FlightRequestDTO(Integer id, Integer departureAirportId, Integer arrivalAirportId, OffsetDateTime departureDateTime, OffsetDateTime arrivalDateTime, BigDecimal price) {
        this.id = id;
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
