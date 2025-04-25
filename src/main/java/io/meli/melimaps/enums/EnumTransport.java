package io.meli.melimaps.enums;

public enum EnumTransport {
    CAR,
    BUS,
    RAILWAY,
    FOOT,
    BIKE;

    public boolean isEco() {
        return switch (this) {
            case RAILWAY, BIKE, FOOT -> true;
            default -> false;
        };
    }

    public boolean isAccessible() {
        return switch(this) {
            case CAR, BUS, RAILWAY -> true;
            default -> false;
        };
    }

    public Double costPerStop() {
        return switch (this) {
            case BUS -> 5.80;
            case RAILWAY -> 2.60;
            case FOOT, BIKE, CAR -> 0.0;
        };
    }

    public Double costPerKm() {
        return switch(this) {
            case CAR -> 1.20;
            default -> 0.0;
        };
    }

    public Integer minutesStoppedAtEachPoint() {
        return switch(this) {
            case CAR, FOOT, BIKE -> 0;
            case BUS -> 15;
            case RAILWAY -> 5;
        };
    }

    public Integer transportSpeedInKmPerHour() {
        return switch (this) {
            case CAR -> 80;
            case BUS -> 40;
            case RAILWAY -> 120;
            case FOOT -> 4;
            case BIKE -> 18;
        };
    }

    public Integer accessibilityScore() {
        return switch (this) {
            case CAR -> 1;
            case BIKE -> 100;
            case BUS -> 2;
            case FOOT -> 100;
            case RAILWAY -> 2;
        };
    }

    public Integer polutionScore() {
        return switch (this) {
            case CAR -> 100;
            case BUS -> 2;
            case RAILWAY -> 2;
            case FOOT -> 1;
            case BIKE -> 1;
        };
    }


}
