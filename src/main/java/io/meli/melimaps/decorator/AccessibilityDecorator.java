package io.meli.melimaps.decorator;

import io.meli.melimaps.enums.EnumDecoration;
import io.meli.melimaps.enums.EnumTransport;
import io.meli.melimaps.interfaces.Graph;
import io.meli.melimaps.model.Route;
import io.meli.melimaps.model.Vertex;

public class AccessibilityDecorator extends BaseDecorator {

    public AccessibilityDecorator(EnumTransport transport) {
        this.priority = EnumDecoration.ACCESSIBILITY;
        this.factorPerKm = transport.badAccessibilityScore();
        this.factorPerStop = 0;

        if(transport.isAccessible()) {
            this.transport = transport;
        } else {
            this.transport = EnumTransport.CAR;
        }
    }


    @Override
    public Route calculateBestRoute(Vertex origin, Vertex destination, Graph map) {
        map.getGraphWithVerticesAvailableForTransport(transport);
        return getOptimalPathBetween(origin, destination, map.getVertices());
    }
    
}
