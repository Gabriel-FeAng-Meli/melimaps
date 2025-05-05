package io.meli.melimaps.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.relational.core.mapping.Table;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity
@Table
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String transport;
    
    private Boolean hurry = false;

    private Boolean accessibility = false;

    private Boolean ecologic = false;
    
    private Boolean budget = false;

    @ManyToMany(mappedBy = "users")
    private List<Route> routes = new ArrayList<>();

    public User(String name, String transport,Boolean hurry, Boolean accessibility, Boolean ecologic, Boolean budget) {
        this.name = name;
        this.transport = transport;
        this.hurry = hurry;
        this.accessibility = accessibility;
        this.ecologic = ecologic;
        this.budget = budget;
    }
    
    public User() {
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTransport() {
        return transport;
    }

    public void setTransport(String transport) {
        this.transport = transport;
    }

    public Boolean getAccessibility() {
        return accessibility;
    }

    public void setAccessibility(Boolean accessibility) {
        this.accessibility = accessibility;
    }

    public Boolean getEcologic() {
        return ecologic;
    }

    public void setEcologic(Boolean ecologic) {
        this.ecologic = ecologic;
    }

    public Boolean getBudget() {
        return budget;
    }

    public void setBudget(Boolean budget) {
        this.budget = budget;
    }

    public Boolean getHurry() {
        return hurry;
    }

    public void setHurry(Boolean hurry) {
        this.hurry = hurry;
    }

    public List<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(List<Route> routes) {
        this.routes = routes;
    }

}
