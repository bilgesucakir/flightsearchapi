package com.bilgesucakir.flightsearchapi.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.time.OffsetDateTime;
import java.math.BigDecimal;

@Entity
@Table(name = "flight")
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "departure_airport_id")
    @JsonBackReference
    private Airport departureAirport;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "arrival_airport_id")
    @JsonBackReference
    private Airport arrivalAirport;

    @Column(name = "departure_datetime")
    private OffsetDateTime departureDateTime;

    @Column(name = "arrival_datetime")
    private OffsetDateTime arrivalDateTime;

    @Column(name = "price", precision = 10, scale=2)
    private BigDecimal price;

    public Flight(){}
    public Flight(int id, Airport departureAirport, Airport arrivalAirport, OffsetDateTime departureDateTime, OffsetDateTime arrivalDateTime, BigDecimal price) {
        this.id = id;
        this.departureAirport = departureAirport;
        this.arrivalAirport = arrivalAirport;
        this.departureDateTime = departureDateTime;
        this.arrivalDateTime = arrivalDateTime;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Airport getDepartureAirport() {
        return departureAirport;
    }

    public void setDepartureAirport(Airport departureAirport) {
        this.departureAirport = departureAirport;
    }

    public Airport getArrivalAirport() {
        return arrivalAirport;
    }

    public void setArrivalAirport(Airport arrivalAirport) {
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

    @Override
    public String toString() {
        return "Flight{" +
                "id=" + id +
                ", departureAirport='" + departureAirport + '\'' +
                ", arrivalAirport='" + arrivalAirport + '\'' +
                ", departureDateTime=" + departureDateTime +
                ", returnDateTime=" + arrivalDateTime +
                ", price=" + price +
                '}';
    }
}
