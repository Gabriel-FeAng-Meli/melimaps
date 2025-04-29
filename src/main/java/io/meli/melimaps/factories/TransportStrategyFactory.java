package io.meli.melimaps.factories;

import java.util.List;

import io.meli.melimaps.enums.EnumDecoration;
import io.meli.melimaps.enums.EnumTransport;
import io.meli.melimaps.interfaces.OptimizationInterface;
import io.meli.melimaps.strategy.BikeTransportStrategy;
import io.meli.melimaps.strategy.BusTransportStrategy;
import io.meli.melimaps.strategy.CarTransportStrategy;
import io.meli.melimaps.strategy.FootTransportStrategy;
import io.meli.melimaps.strategy.RailwayTransportStrategy;

public class TransportStrategyFactory {


    public OptimizationInterface instantiateRightStrategy(EnumTransport transport, List<EnumDecoration> preferences) {

        return switch (transport) {
            case CAR -> new CarTransportStrategy();
            case BUS -> new BusTransportStrategy();
            case BIKE -> new BikeTransportStrategy();
            case RAILWAY -> new RailwayTransportStrategy();
            case FOOT -> new FootTransportStrategy();
            case ANY -> new BikeTransportStrategy();
        };
    }


}
