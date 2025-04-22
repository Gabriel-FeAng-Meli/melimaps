package io.meli.melimaps.factories;

import io.meli.melimaps.enums.EnumTransport;
import io.meli.melimaps.interfaces.TransportStrategy;
import io.meli.melimaps.model.UserPreferences;
import io.meli.melimaps.strategy.BikeTransportStrategy;
import io.meli.melimaps.strategy.BusTransportStrategy;
import io.meli.melimaps.strategy.FootTransportStrategy;
import io.meli.melimaps.strategy.RailwayTransportStrategy;

public class TransportStrategyFactory {

    private EnumTransport type;

    public TransportStrategy instantiateRightStrategy(UserPreferences preferences) {    
        
        if (!preferences.transport().isEmpty()) {
            type = EnumTransport.valueOf(preferences.transport());
        } else {
            type = EnumTransport.chooseBestTransport(preferences);
        }

        return switch (type) {
            case BUS -> new BusTransportStrategy();
            case BIKE -> new BikeTransportStrategy();
            case RAILWAY -> new RailwayTransportStrategy();
            case FOOT -> new FootTransportStrategy();
        };
    }
}
