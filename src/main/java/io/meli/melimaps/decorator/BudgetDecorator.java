package io.meli.melimaps.decorator;

import io.meli.melimaps.enums.EnumDecoration;
import io.meli.melimaps.enums.EnumTransport;
import io.meli.melimaps.interfaces.Graph;
import io.meli.melimaps.model.Route;
import io.meli.melimaps.model.Vertex;

public class BudgetDecorator extends BaseDecorator {

    public BudgetDecorator(EnumTransport transport) {
        this.transport = transport;
        this.priority = EnumDecoration.BUDGET;
        this.factorPerKm = transport.costPerKmInCents();
        this.factorPerStop = transport.costPerStopInCents();
    }

    @Override
    public Route calculateBestRoute(Vertex origin, Vertex destination, Graph map) {
        return getOptimalPathBetween(origin, destination, map.getVertices());
    }
    
}
