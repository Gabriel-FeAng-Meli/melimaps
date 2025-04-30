package io.meli.melimaps.factories;

import io.meli.melimaps.enums.EnumTransport;
import io.meli.melimaps.interfaces.TransportStrategy;
import io.meli.melimaps.model.UserPreferences;
import io.meli.melimaps.strategy.BikeTransportStrategy;
import io.meli.melimaps.strategy.BusTransportStrategy;
import io.meli.melimaps.strategy.CarTransportStrategy;
import io.meli.melimaps.strategy.FootTransportStrategy;
import io.meli.melimaps.strategy.RailwayTransportStrategy;

public class TransportStrategyFactory {


    public TransportStrategy instantiateRightStrategy(EnumTransport transport, UserPreferences transportPreferences) {

        if(transport.equals(EnumTransport.ANY)) {
            transport = chooseBestTransport(transportPreferences);
        }

        return switch (transport) {
            case CAR -> new CarTransportStrategy();
            case BUS -> new BusTransportStrategy();
            case BIKE -> new BikeTransportStrategy();
            case RAILWAY -> new RailwayTransportStrategy();
            case FOOT -> new FootTransportStrategy();
            default -> throw new IllegalArgumentException("Unexpected value: " + transport);
        };
    }

    private EnumTransport chooseBestTransport(UserPreferences transportPreferences) {

        if (!transportPreferences.preferLowCostTransport()) return EnumTransport.CAR; 
        if (!transportPreferences.preferLowCO2EmissionTransport()) return EnumTransport.BUS;
        if (!transportPreferences.preferAccessibleTransport()) {
            return transportPreferences.preferFasterTransport() ?
                EnumTransport.BIKE : EnumTransport.FOOT;
        }
        return EnumTransport.RAILWAY;

    }


}
