package com.bilgesucakir.flightsearchapi.entity;

import jakarta.persistence.*;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;

/**
 * Role entity for endpoint access
 */
@Entity
@Table(name="roles")
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    public RoleEntity(){}

    public RoleEntity(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
