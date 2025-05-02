package io.meli.melimaps.model;

import io.meli.melimaps.enums.EnumTransport;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="route")
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String transport;
    private String originName;
    private String destinationName;
    private String distance;
    private String prioritize = "DISTANCE";
    private String timeToReach;
    private String totalCost;
    private String path;

    public Route(Integer id, String transport, String originName, String destinationName, String distance,
    String timeToReach, String totalCost, String path, String prioritize) {
        this.id = id;
        this.transport = transport;
        this.originName = originName;
        this.destinationName = destinationName;
        this.distance = distance;
        this.timeToReach = timeToReach;
        this.totalCost = totalCost;
        this.path = path;
        this.prioritize = prioritize;
    }

    public Route(EnumTransport transport, String origin, String destination, String distance, String timeToReach, String cost, String path) {
        this.transport = transport.name();
        this.originName = origin;
        this.destinationName = destination;
        this.distance = distance;
        this.timeToReach = timeToReach;
        this.totalCost = cost;
        this.path = path;
    }

    public Route() {
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getTransport() {
        return transport;
    }
    public void setTransport(String transport) {
        this.transport = transport;
    }
    public String getOriginName() {
        return originName;
    }
    public void setOriginName(String originName) {
        this.originName = originName;
    }
    public String getDestinationName() {
        return destinationName;
    }
    public void setDestinationName(String destinationName) {
        this.destinationName = destinationName;
    }
    public String getDistance() {
        return distance;
    }
    public void setDistance(String distance) {
        this.distance = distance;
    }
    public String getTimeToReach() {
        return timeToReach;
    }
    public void setTimeToReach(String timeToReach) {
        this.timeToReach = timeToReach;
    }
    public String getTotalCost() {
        return totalCost;
    }
    public void setTotalCost(String totalCost) {
        this.totalCost = totalCost;
    }
    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }

    public String getPrioritize() {
        return prioritize;
    }

    public void setPrioritize(String prioritize) {
        this.prioritize = prioritize;
    }

}
