package io.meli.melimaps.model;

import io.meli.melimaps.enums.EnumTransport;

public class Path {
    
    private final EnumTransport transport;
    private final Vertex destination;
    private final Integer distance;
    private final Long timeToReachInMinutes;
    private final Long totalCostInCents;
    private final Boolean ecologic;
    private final Boolean accessible;

    public Path(EnumTransport transport, Vertex destination, Integer distance) {
        this.transport = transport;
        this.destination = destination;
        this.distance = distance;
        this.timeToReachInMinutes = calculateMinutes(distance);
        this.totalCostInCents = calculateCost(distance);
        this.ecologic = transport.isEco();
        this.accessible = transport.isAccessible();
    }

    private Long calculateMinutes(Integer distanceInKilometers) {

        Long timeInMinutes = Math.round(60 * distanceInKilometers / transport.transportSpeedInKmPerHour().doubleValue());
        timeInMinutes += transport.minutesStoppedAtEachPoint();

        return timeInMinutes;
    }

    private Long calculateCost(Integer distance) {

        Double totalCostForTransportation = transport.costPerStop() + distance * transport.costPerKm();

        return Math.round(totalCostForTransportation);
    }

    public EnumTransport getTransport() {
        return transport;
    }


    public Integer getDistance() {
        return distance;
    }

    public Long getTimeToReachInMinutes() {
        return timeToReachInMinutes;
    }

    public Long getTotalCostInCents() {
        return totalCostInCents;
    }

    public Boolean getEcologic() {
        return ecologic;
    }

    public Boolean getAccessible() {
        return accessible;
    }

    public Vertex getDestination() {
        return destination;
    }

}
