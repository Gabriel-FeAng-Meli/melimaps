package io.meli.melimaps.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.data.relational.core.mapping.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.meli.melimaps.enums.EnumPreference;
import io.meli.melimaps.enums.EnumTransport;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

@Entity
@Table
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Integer id;

    @JsonIgnore
    private String transport;
    private String originName;
    private String destinationName;
    private String distance;
    private String timeToReach;
    private String totalCost;
    private String path;

    @JsonIgnore
    private boolean available = true;
    
    @JsonIgnore
    private String requestProperties;
    
    @JsonIgnore
    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
        name = "requisition", joinColumns = @JoinColumn(name = "route_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id")
    )
    private List<User> users = new ArrayList<>();

    public static String generateRequestProperties(EnumTransport transport, String originName, String destinationName, Set<EnumPreference> preferences) {
        String properties = "R-%s-%s-%s/P-%s".formatted(transport.name(), originName, destinationName, preferences);
        return properties;
    }

    public Route(Integer id, String transport, String originName, String destinationName, String distance,
            String timeToReach, String totalCost, String path, String requestProperties, List<User> users) {
        this.id = id;
        this.transport = transport;
        this.originName = originName;
        this.destinationName = destinationName;
        this.distance = distance;
        this.timeToReach = timeToReach;
        this.totalCost = totalCost;
        this.path = path;
        this.requestProperties = requestProperties;
        this.users = users;
    }

    public Route(String transport, String originName, String destinationName, String distance, String timeToReach,
            String totalCost, String path) {
        this.transport = transport;
        this.originName = originName;
        this.destinationName = destinationName;
        this.distance = distance;
        this.timeToReach = timeToReach;
        this.totalCost = totalCost;
        this.path = path;
    }

    public Route(Integer id, String transport, String originName, String destinationName, String distance,
    String timeToReach, String totalCost, String path, String prioritize, String requestProperties) {
        this.id = id;
        this.transport = transport;
        this.originName = originName;
        this.destinationName = destinationName;
        this.distance = distance;
        this.timeToReach = timeToReach;
        this.totalCost = totalCost;
        this.path = path;
        this.requestProperties = requestProperties;
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

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public void addUser(User user) {
        this.users.add(user);
    }

    public String getRequestProperties() {
        return requestProperties;
    }

    public void setRequestProperties(String requestProperties) {
        this.requestProperties = requestProperties;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean active) {
        this.available = active;
    }


}
