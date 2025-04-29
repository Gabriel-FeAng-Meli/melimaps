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
            return 2;
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
            case CAR -> false;
            default -> true;
        };
    }

    public boolean isAccessible() {
        return switch(this) {
            case CAR, RAILWAY, BUS -> true;
            default -> false;
        };
    }

    public Integer costPerStopInCents() {
        return switch (this) {
            case ANY -> null;
            case BUS -> 580;
            case RAILWAY -> 260;
            case FOOT, BIKE, CAR -> 00;
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
            case ANY -> null;
            case CAR, FOOT, BIKE -> 0;
            case BUS -> 15;
            case RAILWAY -> 5;
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
            case ANY -> null;
            case CAR -> 1;
            case BIKE -> 100;
            case BUS -> 2;
            case FOOT -> 100;
            case RAILWAY -> 2;
        };
    }

    public Integer polutionScore() {
        return switch (this) {
            case ANY -> null;
            case CAR -> 10;
            case BUS -> 3;
            case RAILWAY -> 2;
            case FOOT -> 1;
            case BIKE -> 1;
        };
    }


}
