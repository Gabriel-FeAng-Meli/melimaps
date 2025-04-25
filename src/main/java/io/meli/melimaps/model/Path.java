package io.meli.melimaps.model;

import io.meli.melimaps.enums.EnumTransport;

public class Path {
    
    private final EnumTransport transport;
    private final Integer distance;
    private final Long timeToReachInMinutes;
    private final Long totalCostInCents;
    private final Integer ecologicScore;
    private final Integer badAccessibilityScore;
    private Integer weight;

    public Path(EnumTransport transport, Integer distance) {
        this.transport = transport;
        this.distance = distance;
        this.timeToReachInMinutes = calculateMinutes(distance);
        this.totalCostInCents = calculateCost(distance);
        this.ecologicScore = transport.polutionScore();
        this.badAccessibilityScore = transport.badAccessibilityScore();
        this.weight = distance;
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


    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getEcologicScore() {
        return ecologicScore;
    }

    public Integer getBadAccessibilityScore() {
        return badAccessibilityScore;
    }

}
