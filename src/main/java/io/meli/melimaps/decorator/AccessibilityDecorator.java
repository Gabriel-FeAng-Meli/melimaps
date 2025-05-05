package io.meli.melimaps.decorator;

import java.util.List;

import io.meli.melimaps.enums.EnumPreference;
import io.meli.melimaps.enums.EnumTransport;
import io.meli.melimaps.interfaces.GraphStructure;
import io.meli.melimaps.interfaces.TransportStrategy;
import io.meli.melimaps.model.Route;
import io.meli.melimaps.model.Vertex;

public class AccessibilityDecorator extends Decorator {

    public AccessibilityDecorator(TransportStrategy strategy) {
        super(strategy);
        super.priority = EnumPreference.ACCESSIBILITY;
        
    }

    @Override
    public Route calculateBestRoute(Vertex origin, Vertex destination, GraphStructure map) {
        return getOptimalPathBetween(origin, destination, map.getVertices());
    }

    @Override
    public Vertex calculateMostOptimalPathToEachVertex(Vertex source, Vertex destination, List<Vertex> map, EnumTransport transport) {
        Integer byDistanceFactor = 1;
        Integer byStopFactor = transport.badAccessibilityScore();
        return TransportStrategy.calculateShortestPathToEachVertex(source, source, map, transport, byDistanceFactor, byStopFactor);
    }

    
}
