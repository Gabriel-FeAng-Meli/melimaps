package io.meli.melimaps.factories;

import io.meli.melimaps.enums.EnumTransport;
import io.meli.melimaps.interfaces.TransportStrategy;
import io.meli.melimaps.strategy.BikeTransportStrategy;
import io.meli.melimaps.strategy.BusTransportStrategy;
import io.meli.melimaps.strategy.FootTransportStrategy;
import io.meli.melimaps.strategy.RailwayTransportStrategy;

public class TransportStrategyFactory {

    public TransportStrategy instantiateRightStrategy(EnumTransport type) {
        return switch (type) {
            case BUS -> new BusTransportStrategy();
            case BIKE -> new BikeTransportStrategy();
            case RAILWAY -> new RailwayTransportStrategy();
            default -> new FootTransportStrategy();
        };

    }
}
