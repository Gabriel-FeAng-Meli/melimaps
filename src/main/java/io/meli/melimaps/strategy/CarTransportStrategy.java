package io.meli.melimaps.strategy;

import java.util.List;

import io.meli.melimaps.enums.EnumTransport;
import io.meli.melimaps.model.Route;
import io.meli.melimaps.model.Vertex;

public class CarTransportStrategy extends AbstractTransportStrategy {

    public CarTransportStrategy() {
        super.type = EnumTransport.CAR;
    }

    @Override
    public Route calculateBestRoute(Vertex origin, Vertex destination, List<Vertex> map) {

        var result = getShortestPathBetween(origin, destination, map);

        return result;
    }    
}
