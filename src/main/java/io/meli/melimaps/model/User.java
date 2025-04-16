package io.meli.melimaps.model;

import io.meli.melimaps.dto.UserDTO;
import io.meli.melimaps.enums.EnumTransport;
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

    private EnumTransport prefferedTransport;
    
    private Boolean considerAccessibility;
    
    private Boolean ecologic;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public EnumTransport getPrefferedTransport() {
        return prefferedTransport;
    }
    public void setPrefferedTransport(EnumTransport prefferedTransport) {
        this.prefferedTransport = prefferedTransport;
    }
    
    public Boolean considerAccessibility() {
        return considerAccessibility;
    }
    public void setConsiderAccessibility(Boolean considerAccessibility) {
        this.considerAccessibility = considerAccessibility;
    }
    
    public Boolean isEcologic() {
        return ecologic;
    }
    public void setEcologic(Boolean considerCO2Emissions) {
        this.ecologic = considerCO2Emissions;
    }

    public UserDTO toDTO() {
        return new UserDTO(this);
    }
}
