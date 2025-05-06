package io.meli.melimaps.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Requisition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "user_id", updatable = false)
    private User user;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "route_id", updatable = false)
    private Route route;

    private Integer timesRequested;

    public Requisition(User user, Route route) {
        this.user = user;
        this.route = route;
    }


    public Requisition(User user, Route route, Integer timesRequested) {
        this.user = user;
        this.route = route;
    }


    public Requisition(Integer id, User user, Route route, Integer timesRequested) {
        this.id = id;
        this.user = user;
        this.route = route;
        this.timesRequested = timesRequested;
    }


    public Requisition() {
        this.timesRequested = 0;
    }

    public Integer getTimesRequested() {
        return timesRequested;
    }

    public void setTimesRequested(Integer timesUserRequestedRoute) {
        this.timesRequested = timesUserRequestedRoute;
    }

    public User getUser() {
        return user;
    }


    public Route getRoute() {
        return route;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public void setUser(User user) {
        this.user = user;
    }


    public void setRoute(Route route) {
        this.route = route;
    }



}
