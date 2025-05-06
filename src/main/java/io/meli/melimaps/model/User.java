package io.meli.melimaps.model;

import org.springframework.data.relational.core.mapping.Table;
import org.springframework.scheduling.annotation.Async;

import io.meli.melimaps.interfaces.Observer;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
@Table
public class User implements Observer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String transport;
    
    private Boolean hurry = false;
    
    private Boolean accessibility = false;
    
    private Boolean ecologic = false;
    
    private Boolean budget = false;

    private String email = "gabriel.feang@gmail.com";
    
    public void setEmail(String email) {
        this.email = email;
    }

    // private Set<String> favorites = new HashSet<>();

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

    @Override
    @Async
    public void update() {

    }

    // public Set<String> getFavorites() {
    //     return favorites;
    // }

    // public void setFavorites(Set<String> listOfFavoriteRouteRequestParameters) {
    //     this.favorites = listOfFavoriteRouteRequestParameters;
    // }

    // public void addFavorite(Route route, Set<EnumPreference> preferences) {

    //     String requestParameters = Route.generateRequestProperties(EnumTransport.valueOf(route.getTransport()), route.getOriginName(), route.getDestinationName(), preferences);

    //     favorites.add(requestParameters);
    // }

    public String getEmail() {
        return email;
    }

}
