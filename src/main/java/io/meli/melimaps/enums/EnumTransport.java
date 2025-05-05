package io.meli.melimaps.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum EnumTransport {
    ANY,
    CAR,
    BUS,
    RAILWAY,
    FOOT,
    BIKE;

    public Integer factorTransportPreference(List<EnumTransport> transportationMethodsAvailableOnPath) {
        if (!transportationMethodsAvailableOnPath.contains(this)) {
            return 3;
        }
        return 1;
    }

    public List<EnumTransport> toList() {
        if (this.equals(ANY)) {
            List<EnumTransport> list = new ArrayList<>();
            list.addAll(Arrays.asList(EnumTransport.values()));
            list.remove(ANY);

            return list;
        } else {
            return List.of(this);
        }
    }

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

    public Integer costPerStopInCents() {
        return switch (this) {
            case BUS -> 580;
            case RAILWAY -> 260;
            default -> 0;
        };
    }

    public Integer costPerKmInCents() {
        return switch(this) {
            case CAR -> 120;
            default -> 0;
        };
    }

    public Integer minutesStoppedAtEachPoint() {
        return switch(this) {
            case BUS -> 25;
            case RAILWAY -> 5;
            default -> 0;
        };
    }

    public Integer transportSpeedInKmPerHour() {
        return switch (this) {
            case ANY -> null;
            case CAR -> 80;
            case BUS -> 40;
            case RAILWAY -> 120;
            case FOOT -> 4;
            case BIKE -> 18;
        };
    }

    public Integer badAccessibilityScore() {
        return switch (this) {
            case BIKE, FOOT -> 10;
            default -> 1;
        };
    }

    public Integer polutionScore() {
        return switch (this) {
            case CAR -> 10;
            case BUS, RAILWAY -> 2;
            default -> 1;
        };
    }


}
