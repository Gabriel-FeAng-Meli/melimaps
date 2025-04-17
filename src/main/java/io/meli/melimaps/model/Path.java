package io.meli.melimaps.model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import io.meli.melimaps.enums.EnumTransport;

public class Path {

    private String name;
    private Integer distance;
    private Vertex origin;
    private Vertex destination;
    private Set<EnumTransport> availableTransportation;

    private Path(int distance, Vertex origin, Vertex destination, EnumTransport... availableTransportation) {
        this.name = origin.getName() + " -> " + destination.getName();
        this.distance = distance;
        this.origin = origin;
        this.destination = destination;
        Set<EnumTransport> transports = new HashSet<>();
        transports.addAll(Set.of(availableTransportation));
        this.availableTransportation = transports; 
    }

    public static Path buildRoadConnecting(Integer roadLengthInKm, Vertex origin, Vertex destination, EnumTransport... transport) {
        Path road = new Path(roadLengthInKm, origin, destination, transport);
        return road;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public Vertex getOrigin() {
        return origin;
    }

    public void setOrigin(Vertex origin) {
        this.origin = origin;
    }

    public Vertex getDestination() {
        return destination;
    }

    public void setDestination(Vertex destination) {
        this.destination = destination;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Set<EnumTransport> getAvailableTransportation() {
        return availableTransportation;
    }

    public boolean acceptsTransport(EnumTransport transport) {
        return this.availableTransportation.contains(transport);
    }

    public void setAvailableTransportation(EnumTransport... availableTransportation) {
        Arrays.asList(availableTransportation);
        this.availableTransportation = Set.of(availableTransportation);
    }
    
}
