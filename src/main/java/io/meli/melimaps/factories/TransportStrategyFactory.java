package io.meli.melimaps.factories;

import java.util.List;

import io.meli.melimaps.enums.EnumPreference;
import io.meli.melimaps.enums.EnumTransport;
import io.meli.melimaps.interfaces.TransportStrategy;
import io.meli.melimaps.strategy.BikeTransportStrategy;
import io.meli.melimaps.strategy.BusTransportStrategy;
import io.meli.melimaps.strategy.CarTransportStrategy;
import io.meli.melimaps.strategy.FootTransportStrategy;
import io.meli.melimaps.strategy.RailwayTransportStrategy;

public class TransportStrategyFactory {


    public TransportStrategy instantiateRightStrategy(EnumTransport transport, List<EnumPreference> preferences) {

        if(transport.equals(EnumTransport.ANY)) {
            transport = chooseBestTransport(preferences);
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

    private EnumTransport chooseBestTransport(List<EnumPreference> preferences) {

        if (!preferences.contains(EnumPreference.BUDGET)) {
            return EnumTransport.CAR; 
        }
        
        if (!preferences.contains(EnumPreference.ECO)) {
            return EnumTransport.BUS;
        }

        if (!preferences.contains(EnumPreference.ACCESSIBILITY)) {
            return EnumTransport.BIKE;
        }

        if (!preferences.contains(EnumPreference.TIME)) {
            return EnumTransport.FOOT;
        }

        return EnumTransport.RAILWAY;

    }


}
