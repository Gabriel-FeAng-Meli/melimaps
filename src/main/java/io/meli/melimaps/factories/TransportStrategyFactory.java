package io.meli.melimaps.factories;

import io.meli.melimaps.enums.EnumTransport;
import io.meli.melimaps.interfaces.TransportStrategy;
import io.meli.melimaps.strategy.FootTransportStrategy;

public class TransportStrategyFactory {

    public TransportStrategy instantiateRightStrategy(EnumTransport type) {
        switch (type) {
            case BUS: return new FootTransportStrategy();
            case BIKE: return new FootTransportStrategy();
            case RAILWAY: return new FootTransportStrategy();
            default:
                return new FootTransportStrategy();
        }

    }
}
