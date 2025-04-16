package io.meli.melimaps.model;

import io.meli.melimaps.enums.EnumTransport;

public class Path {
    
    private int weight;
    private EnumTransport type = EnumTransport.FOOT;
    private boolean ecologic = false;
    private boolean accessible = false;
    private Vertex origin;
    private Vertex destination;

    public Path(Vertex origin, Vertex destination, EnumTransport transport, boolean eco, boolean accessible) {
        this.weight = 0;
        this.type = transport;
        this.ecologic = eco;
        this.accessible = accessible;
        this.origin = origin;
        this.destination = destination;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
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

    public EnumTransport getType() {
        return type;
    }

    public void setType(EnumTransport type) {
        this.type = type;
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
    
}
