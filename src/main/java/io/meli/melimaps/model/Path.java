package io.meli.melimaps.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.meli.melimaps.enums.EnumTransport;

public class Path implements Comparable<Path>{
    
    private final List<EnumTransport> transports = new ArrayList<>();
    private final Integer distance;
    private final Vertex origin;
    private final Vertex destination;
    private Integer weight;

    public Path(Vertex origin, Integer distance,  Vertex destination, EnumTransport... transport) {
        this.transports.add(EnumTransport.FOOT);
        this.transports.addAll(Arrays.asList(transport));
        this.origin = origin;
        this.destination = destination;
        this.distance = distance;
        this.weight = Integer.MAX_VALUE;
    }

    public List<EnumTransport> getTransports() {
        return transports;
    }


    public Integer getDistance() {
        return distance;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Vertex getOrigin() {
        return origin;
    }

    public Vertex getDestination() {
        return destination;
    }

    @Override
    public int compareTo(Path path) {
        return Integer.compare(this.weight, path.getWeight());
    }

}
