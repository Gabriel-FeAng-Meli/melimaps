package io.meli.melimaps.model;

public class Path {

    private String name;
    private int distance;
    private boolean ecologic;
    private boolean accessible;
    private Vertex origin;
    private Vertex destination;

    public Path(String name, int distance, Vertex origin, Vertex destination, boolean isEcoFriendly, boolean isAccessible) {
        this.name = name;
        this.distance = distance;
        this.ecologic = isEcoFriendly;
        this.accessible = isAccessible;
        this.origin = origin;
        this.destination = destination;
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

    public boolean isEcologic() {
        return ecologic;
    }

    public void setEcologic(boolean ecologic) {
        this.ecologic = ecologic;
    }

    public boolean isAccessible() {
        return accessible;
    }

    public void setAccessible(boolean accessible) {
        this.accessible = accessible;
    }


    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    
}
