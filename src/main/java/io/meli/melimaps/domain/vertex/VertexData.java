package io.meli.melimaps.domain.vertex;

import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

import io.meli.melimaps.enums.EnumTransport;

public class VertexData {
    private long metersFromParentVertex = 0;
    private Map<EnumTransport, Integer> minutesToReach;
    private boolean ecologic = false;
    private boolean accessible = false;

    public VertexData(long meters, Boolean ecologic, Boolean accessible) {
        this.metersFromParentVertex = meters;
        this.minutesToReach = mapTimeToReachByTransport(meters);
        this.ecologic = ecologic;
        this.accessible = accessible;
    }
    
    private Map<EnumTransport, Integer> mapTimeToReachByTransport(Long distance) {

        Map<EnumTransport, Integer> minutes = new HashMap<EnumTransport, Integer>();

        return minutes;
    }

    private Integer timeToTravelDistanceByTransport(Long distanceInMeters, EnumTransport transport) {

        Double minutes = EnumTransport.transportSpeedInKmPerHour(transport) * 3.6 * distanceInMeters;

        return minutes.intValue();

    }


}


