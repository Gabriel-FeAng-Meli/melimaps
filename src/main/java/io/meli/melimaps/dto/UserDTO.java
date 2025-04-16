package io.meli.melimaps.dto;

import io.meli.melimaps.enums.EnumTransport;
import io.meli.melimaps.model.User;

public class UserDTO {
    private Integer id;
    private String name;
    private EnumTransport transport;
    private Boolean accessibility;
    private Boolean ecologic;

    public UserDTO(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.transport = user.getPrefferedTransport();
        this.accessibility = user.considerAccessibility();
        this.ecologic = user.isEcologic();
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

    public EnumTransport getTransport() {
        return transport;
    }

    public void setTransport(EnumTransport transport) {
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
