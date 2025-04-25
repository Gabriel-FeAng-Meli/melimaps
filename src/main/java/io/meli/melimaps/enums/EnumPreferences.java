package io.meli.melimaps.enums;

public enum EnumPreferences {
    ECO,
    BUDGET,
    TIME,
    ACCESSIBILITY;

    public Double factorDistanceIntoWeight(EnumTransport t, Integer distanceInKilometers) {

        Double distance = distanceInKilometers.doubleValue();

        return switch (this) {
            case ECO -> distance * t.polutionScore();
            case ACCESSIBILITY -> distance * t.accessibilityScore();
            case BUDGET -> (distance * t.costPerKm()) + t.costPerStop();
            case TIME -> distance / t.transportSpeedInKmPerHour();
        };
    }
}
