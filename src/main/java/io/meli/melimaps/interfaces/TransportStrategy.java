package io.meli.melimaps.interfaces;

import io.meli.melimaps.enums.EnumTransport;
import io.meli.melimaps.model.Route;
import io.meli.melimaps.model.Vertex;

public interface TransportStrategy {
    Route calculateBestRoute(Vertex origin, Vertex destination, Graph map);

    EnumTransport getStrategyType();
}

