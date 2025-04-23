package io.meli.melimaps.factories;

import org.springframework.stereotype.Component;

import io.meli.melimaps.enums.EnumTransport;
import io.meli.melimaps.interfaces.TransportStrategy;
import io.meli.melimaps.model.UserPreferences;
import io.meli.melimaps.strategy.BusTransportStrategy;
import io.meli.melimaps.strategy.CarTransportStrategy;
import io.meli.melimaps.strategy.FootTransportStrategy;
import io.meli.melimaps.strategy.RailwayTransportStrategy;

@Component
public class TransportStrategyFactory {


    public TransportStrategy instantiateRightStrategy(UserPreferences preferences) {    

        EnumTransport type = chooseBestTransport(preferences);

        return switch (type) {
            case CAR -> new CarTransportStrategy();
            case BUS -> new BusTransportStrategy();
            case BIKE -> new BusTransportStrategy();
            case RAILWAY -> new RailwayTransportStrategy();
            case FOOT -> new FootTransportStrategy();
        };
    }

    private EnumTransport chooseBestTransport(UserPreferences preferences) {

        if (!preferences.transport().isBlank()) {
            return EnumTransport.valueOf(preferences.transport());
        }
        
        if (preferences.accessibility()) {
            return preferences.eco() ? EnumTransport.RAILWAY 
                : preferences.budget() ? EnumTransport.BUS 
                    : EnumTransport.CAR;
        } 
        
        if (preferences.budget()) {
            return preferences.eco() ? EnumTransport.FOOT
                : EnumTransport.BUS;
        } 
        
        if (preferences.eco()) {
            return EnumTransport.BIKE;
        }

        return EnumTransport.CAR;
        




    }
}
