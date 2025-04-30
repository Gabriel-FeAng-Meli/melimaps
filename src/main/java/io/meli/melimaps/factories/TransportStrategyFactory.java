package io.meli.melimaps.factories;

import java.util.List;

import io.meli.melimaps.decorator.DistanceDecorator;
import io.meli.melimaps.enums.EnumDecoration;
import io.meli.melimaps.enums.EnumTransport;
import io.meli.melimaps.interfaces.TransportStrategy;
import io.meli.melimaps.strategy.BikeTransportStrategy;
import io.meli.melimaps.strategy.BusTransportStrategy;
import io.meli.melimaps.strategy.CarTransportStrategy;
import io.meli.melimaps.strategy.FootTransportStrategy;
import io.meli.melimaps.strategy.RailwayTransportStrategy;

public class TransportStrategyFactory { // Factory, Decision-Tree

    public TransportStrategy instantiateRightStrategy(EnumTransport transport, List<EnumDecoration> decorations) {

        if(transport.equals(EnumTransport.ANY)) {
            transport = chooseBestTransport(decorations);
        }

        return switch (transport) {
            case CAR -> new CarTransportStrategy();
            case BUS -> new BusTransportStrategy();
            case BIKE -> new BikeTransportStrategy();
            case RAILWAY -> new RailwayTransportStrategy();
            case FOOT -> new FootTransportStrategy();
            default -> new DistanceDecorator();
        };
    }

    private EnumTransport chooseBestTransport(List<EnumDecoration> decorations) {

        if (!decorations.contains(EnumDecoration.BUDGET)) {
            return EnumTransport.CAR; 
        }
        
        if (!decorations.contains(EnumDecoration.ECO)) {
            return EnumTransport.BUS;
        }

        if (!decorations.contains(EnumDecoration.ACCESSIBILITY)) {
            return EnumTransport.BIKE;
        }

        if (!decorations.contains(EnumDecoration.TIME)) {
            return EnumTransport.FOOT;
        }

        return EnumTransport.RAILWAY;

    }



}
