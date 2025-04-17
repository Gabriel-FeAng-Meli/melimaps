package io.meli.melimaps.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String transport;

    private Boolean accessibility;

    private Boolean ecologic;

    public User(String name, String transport, Boolean accessibility, Boolean ecologic) {
        this.name = name;
        this.transport = transport;
        this.accessibility = accessibility;
        this.ecologic = ecologic;
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
}
