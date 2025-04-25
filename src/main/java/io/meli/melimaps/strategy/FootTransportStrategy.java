package io.meli.melimaps.strategy;

import java.util.List;

import io.meli.melimaps.enums.EnumTransport;
import io.meli.melimaps.model.Route;
import io.meli.melimaps.model.Vertex;

public class FootTransportStrategy extends AbstractTransportStrategy {

    public FootTransportStrategy() {
        super.type = EnumTransport.FOOT;
    }

    
    @Override
    public Route calculateBestRoute(Vertex origin, Vertex destination, List<Vertex> map) {

        var result = getShortestPathBetween(origin, destination, map);

        return result;
    }
}
