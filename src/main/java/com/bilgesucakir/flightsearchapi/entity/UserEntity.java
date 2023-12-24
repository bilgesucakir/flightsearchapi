package com.bilgesucakir.flightsearchapi.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

/**
 * User entity for auth operations
 */
@Entity
@Table(name="users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "username")
    private String username;

    @Column(name="password")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinTable(name="user_role", joinColumns = @JoinColumn(name="user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name="role_id", referencedColumnName = "id"))
    private List<RoleEntity> roles;

    public UserEntity(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<RoleEntity> getRoles() {
        return roles;
    }
    public void setRoles(List<RoleEntity> roles) {
        this.roles = roles;
    }

    /*public void addRole(RoleEntity roleEntity){
        if(roles == null){
            roles = new ArrayList<>();
        }
        roles.add(roleEntity);
    }*/
}
