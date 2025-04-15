package io.meli.melimaps.domain.vertex;

import java.util.Map;

import io.meli.melimaps.enums.EnumTransport;
import lombok.Getter;

@Getter
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

        Map<EnumTransport, Integer> minutes = Map.of(
            EnumTransport.BUS, timeToTravelDistanceByTransport(distance, EnumTransport.BUS),
            EnumTransport.BIKE, timeToTravelDistanceByTransport(distance, EnumTransport.BIKE),
            EnumTransport.RAILWAY, timeToTravelDistanceByTransport(distance, EnumTransport.RAILWAY),
            EnumTransport.FOOT, timeToTravelDistanceByTransport(distance, EnumTransport.FOOT)
        );

        return minutes;
    }

    private Integer timeToTravelDistanceByTransport(Long distanceInMeters, EnumTransport transport) {

        Double minutes = EnumTransport.transportSpeedInKmPerHour(transport) * 3.6 * distanceInMeters;

        return minutes.intValue();

    }


}


