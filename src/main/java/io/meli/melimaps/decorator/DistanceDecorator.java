package io.meli.melimaps.decorator;

import io.meli.melimaps.enums.EnumDecoration;
import io.meli.melimaps.enums.EnumTransport;
import io.meli.melimaps.interfaces.Graph;
import io.meli.melimaps.model.Route;
import io.meli.melimaps.model.Vertex;

public class DistanceDecorator extends BaseDecorator {

    public DistanceDecorator() {
        this.priority = EnumDecoration.DISTANCE;
        this.transport = EnumTransport.CAR;
        this.factorPerKm = 1;
        this.factorPerStop = 0;
    }


    @Override
    public Route calculateBestRoute(Vertex origin, Vertex destination, Graph map) {
        return getOptimalPathBetween(origin, destination, map.getVertices());
    }

}
