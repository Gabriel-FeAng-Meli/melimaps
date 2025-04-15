package io.meli.melimaps.enums;

public enum EnumTransport {
    BUS,
    RAILWAY,
    FOOT,
    BIKE;

    public static Integer transportSpeedInKmPerHour(EnumTransport transport) {
        switch (transport) {
            case BUS: return 40;
            case RAILWAY: return 120;
            case FOOT: return 4;
            case BIKE: return 18;
            default: return 0;
        }
    }

}
