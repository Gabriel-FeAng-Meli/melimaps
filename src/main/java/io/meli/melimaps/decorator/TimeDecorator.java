package io.meli.melimaps.decorator;

import io.meli.melimaps.enums.EnumDecoration;
import io.meli.melimaps.enums.EnumTransport;
import io.meli.melimaps.interfaces.Graph;
import io.meli.melimaps.model.Route;
import io.meli.melimaps.model.Vertex;

public class TimeDecorator extends BaseDecorator {

    public TimeDecorator(EnumTransport transport) {
        this.transport = transport;
        this.factorPerKm = 60 / transport.transportSpeedInKmPerHour();
        this.factorPerStop = transport.minutesStoppedAtEachPoint();
        this.priority = EnumDecoration.TIME;
    }

    @Override
    public Route calculateBestRoute(Vertex origin, Vertex destination, Graph map) {
        
        return getOptimalPathBetween(origin, destination, map.getVertices());
    }
    
}
