package io.meli.melimaps.enums;

public enum EnumTransport {
    BUS,
    RAILWAY,
    FOOT,
    BIKE;

    public boolean isEco() {
        return switch (this) {
            case BUS -> false;
            case RAILWAY -> true;
            case BIKE -> true;
            case FOOT -> true;
        };
    }

    public boolean isAccessible() {
        return switch(this) {
            case BUS -> true;
            case RAILWAY -> true;
            case BIKE -> false;
            case FOOT -> false;
        };
    }

    public Double costPerKm() {
        return switch (this) {
            case BUS -> 3.20;
            case RAILWAY -> 0.8;
            case FOOT -> 0.0;
            case BIKE -> 0.0;
        };
    }

    public Integer transportSpeedInKmPerHour() {
        return switch (this) {
            case BUS -> 40;
            case RAILWAY -> 120;
            case FOOT -> 4;
            case BIKE -> 18;
        };
    }

}
