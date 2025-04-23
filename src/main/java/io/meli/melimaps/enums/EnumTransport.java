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
            case BUS -> 3.20;
            case RAILWAY -> 0.8;
            case FOOT, BIKE, CAR -> 0.0;
        };
    }

    public Double costPerKm() {
        return switch(this) {
            case CAR -> 15.0;
            default -> 0.0;
        };
    }

    public Integer minutesStoppedAtEachPoint() {
        return switch(this) {
            case CAR, FOOT, BIKE -> 0;
            case BUS -> 2;
            case RAILWAY -> 4;
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


}
