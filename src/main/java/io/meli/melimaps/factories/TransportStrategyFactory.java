package io.meli.melimaps.factories;

import io.meli.melimaps.enums.EnumTransport;
import io.meli.melimaps.interfaces.TransportStrategy;
import io.meli.melimaps.strategy.FootTransportStrategy;

public class TransportStrategyFactory {

    public TransportStrategy instantiateRightStrategy(EnumTransport type) {
        return switch (type) {
            case BUS -> new FootTransportStrategy();
            case BIKE -> new FootTransportStrategy();
            case RAILWAY -> new FootTransportStrategy();
            default -> new FootTransportStrategy();
        };

    }
}
