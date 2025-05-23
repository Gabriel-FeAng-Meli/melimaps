package io.meli.melimaps.decorator;

import io.meli.melimaps.enums.EnumDecoration;
import io.meli.melimaps.enums.EnumTransport;
import io.meli.melimaps.interfaces.Graph;
import io.meli.melimaps.model.Route;
import io.meli.melimaps.model.Vertex;

public class EcoDecorator extends BaseDecorator {

    public EcoDecorator(EnumTransport transport) {
        if (transport.isEco()) {
            this.transport = transport;
        } else {
            this.transport = EnumTransport.FOOT;
        }
        this.factorPerKm = transport.polutionScore();
        this.factorPerStop = 0;
        this.priority = EnumDecoration.ECO;
    }

    @Override
    public Route calculateBestRoute(Vertex origin, Vertex destination, Graph map) {
        return getOptimalPathBetween(origin, destination, map.getVertices());
    }
    
}
