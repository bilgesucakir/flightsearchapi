package com.bilgesucakir.flightsearchapi.entity;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "airport")
public class Airport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "city")
    private String city;

    @OneToMany(mappedBy = "departureAirport", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Flight> departingFlights;

    @OneToMany(mappedBy = "arrivalAirport", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Flight> arrivingFlights;

    public Airport(){}

    public Airport(int id, String city, List<Flight> departingFlights, List<Flight> arrivingFlights) {
        this.id = id;
        this.city = city;
        this.departingFlights = departingFlights;
        this.arrivingFlights = arrivingFlights;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<Flight> getDepartingFlights() {
        return departingFlights;
    }

    public void setDepartingFlights(List<Flight> departingFlights) {
        this.departingFlights = departingFlights;
    }

    public void addDepartingFlights(Flight flight){
        if(departingFlights == null){
            departingFlights = new ArrayList<>();
        }
        departingFlights.add(flight);

        flight.setDepartureAirport(this);
    }

    public List<Flight> getArrivingFlights() {
        return arrivingFlights;
    }

    public void setArrivingFlights(List<Flight> arrivingFlights) {
        this.arrivingFlights = arrivingFlights;
    }

    public void addArrivingFlights(Flight flight){
        if(arrivingFlights == null){
            arrivingFlights = new ArrayList<>();
        }
        arrivingFlights.add(flight);

        flight.setArrivalAirport(this);
    }

    @Override
    public String toString() {
        return "Airport{" +
                "id=" + id +
                ", city='" + city + '\'' +
                '}';
    }

}
