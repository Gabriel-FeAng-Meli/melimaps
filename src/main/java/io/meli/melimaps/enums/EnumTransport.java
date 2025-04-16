package io.meli.melimaps.enums;

public enum EnumTransport {
    BUS,
    RAILWAY,
    FOOT,
    BIKE;

    public static Integer transportSpeedInKmPerHour(EnumTransport transport) {
        return switch (transport) {
            case BUS -> 40;
            case RAILWAY -> 120;
            case FOOT -> 4;
            case BIKE -> 18;
            default -> 0;
        };
    }

    public static String toString(EnumTransport transport) {
        return switch (transport) {
            case BUS -> "bus";
            case RAILWAY -> "railway";
            case BIKE -> "bike";
            case FOOT -> "foot";
            default -> null;
        };
    }

    public static EnumTransport toEnum(String type) {
        return switch (type) {
            case "bus" -> BUS;
            case "railway" -> BUS;
            case "bike" -> BUS;
            case "foot" -> FOOT;
            default -> null;
        };
    }

}
