package io.meli.melimaps.strategy;

import io.meli.melimaps.enums.EnumTransport;
import io.meli.melimaps.interfaces.GraphStructure;
import io.meli.melimaps.model.CompleteRoute;
import io.meli.melimaps.model.Vertex;

public class CarTransportStrategy extends AbstractTransportStrategy {

    public CarTransportStrategy() {
        super.type = EnumTransport.CAR;
    }

    @Override
    public CompleteRoute calculateBestRoute(Vertex origin, Vertex destination, GraphStructure map) {

        var result = getShortestPathBetween(origin, destination, map.getVertices());

        return result;
    }
}
