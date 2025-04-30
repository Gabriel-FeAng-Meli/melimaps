package io.meli.melimaps.strategy;

import io.meli.melimaps.enums.EnumTransport;
import io.meli.melimaps.interfaces.Graph;
import io.meli.melimaps.model.Route;
import io.meli.melimaps.model.Vertex;

public class RailwayTransportStrategy extends AbstractTransportStrategy {
    
    public RailwayTransportStrategy() {
        super.type = EnumTransport.RAILWAY;
    }


    @Override
    public Route calculateBestRoute(Vertex origin, Vertex destination, Graph map) {

        var result = getShortestPathBetween(origin, destination, map.getVertices());

        return result;
    }
    
}
