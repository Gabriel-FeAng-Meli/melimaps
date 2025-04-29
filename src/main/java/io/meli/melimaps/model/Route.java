package io.meli.melimaps.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.meli.melimaps.enums.EnumDecoration;
import io.meli.melimaps.enums.EnumTransport;
import io.meli.melimaps.interfaces.OptimizationInterface;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Route {

    public void setTransport(String transport) {
        this.transport = transport;
    }

    public void setOriginName(String originName) {
        this.originName = originName;
    }

    public void setDestinationName(String destinationName) {
        this.destinationName = destinationName;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public void setPrioritize(String prioritize) {
        this.prioritize = prioritize;
    }

    public void setTimeToReach(String timeToReach) {
        this.timeToReach = timeToReach;
    }

    public void setTotalCost(String totalCost) {
        this.totalCost = totalCost;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public static class Builder {

        private String transport;
        private String originName;
        private String destinationName;
        private String distance;
        private String prioritize;
        private String timeToReach;
        private String totalCost;
        private String path;

        public Builder transport(String transport) {
            this.transport = transport;
            return this;
        }
        public Builder transport(EnumTransport transport) {
            this.transport = transport.name();
            return this;
        }

        public Builder originName(String originName) {
            this.originName = originName;
            return this;
        }
        public Builder originName(Vertex origin) {
            this.originName = origin.getName();
            return this;
        }

        public Builder destinationName(String destinationName) {
            this.destinationName = destinationName;
            return this;
        }
        public Builder destinationName(Vertex destination) {
            this.destinationName = destination.getName();
            return this;
        }

        public Builder distance(String distance) {
            this.distance = distance;
            return this;
        }
        public Builder distance(Integer distance) {
            this.distance = "%s kilometers".formatted(distance);
            return this;
        }

        public Builder prioritize(String prioritize) {
            this.prioritize = prioritize;
            return this;
        }
        public Builder prioritize(EnumDecoration prioritize) {
            this.prioritize = prioritize.name();
            return this;
        }

        public Builder timeToReach(String timeToReach) {
            this.timeToReach = timeToReach;
            return this;
        }
        public Builder timeToReach(Long timeToReach) {
            this.timeToReach = OptimizationInterface.calculateTimeToTravel(timeToReach);
            return this;
        }

        public Builder totalCost(String totalCost) {
            this.totalCost = totalCost;
            return this;
        }
        public Builder totalCost(Long totalCost) {
            this.totalCost = OptimizationInterface.calculateTotalCost(totalCost);
            return this;
        }

        public Builder path(String path) {
            this.path = path;
            return this;
        }

        public Route build() {
            if (this.prioritize == null) {
                this.prioritize = "DISTANCE";
            }
            return new Route(this);
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Integer id;
    private String transport;
    private String originName;
    private String destinationName;
    private String distance;
    private String prioritize;
    private String timeToReach;
    private String totalCost;
    private String path;

    private Route(Builder builder) {
        this.transport = builder.transport;
        this.originName = builder.originName;
        this.destinationName = builder.destinationName;
        this.distance = builder.distance;
        this.prioritize = builder.prioritize;
        this.timeToReach = builder.timeToReach;
        this.totalCost = builder.totalCost;
        this.path = builder.path;
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
    public String getOriginName() {
        return originName;
    }
    public String getDestinationName() {
        return destinationName;
    }
    public String getDistance() {
        return distance;
    }
    public String getTimeToReach() {
        return timeToReach;
    }
    public String getTotalCost() {
        return totalCost;
    }
    public String getPath() {
        return path;
    }

    public String getPrioritize() {
        return prioritize;
    }

}
