package io.meli.melimaps.strategy;

import io.meli.melimaps.enums.EnumTransport;
import io.meli.melimaps.interfaces.Graph;
import io.meli.melimaps.model.Route;
import io.meli.melimaps.model.Vertex;

public class CarTransportStrategy extends AbstractTransportStrategy {

    public CarTransportStrategy() {
        super.type = EnumTransport.CAR;
    }

    @Override
    public Route calculateBestRoute(Vertex origin, Vertex destination, Graph map) {

        var result = getShortestPathBetween(origin, destination, map.getVertices());

        return result;
    }
}
