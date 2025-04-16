package io.meli.melimaps.strategy;

import java.util.List;

import io.meli.melimaps.enums.EnumTransport;
import io.meli.melimaps.interfaces.TransportStrategy;
import io.meli.melimaps.model.User;
import io.meli.melimaps.model.Vertex;

public abstract class AbstractTransportStrategy implements TransportStrategy {

    protected EnumTransport type;
    protected boolean accessible;
    protected boolean ecologic;

    public abstract void defineBestRouteConditionsForUserPreference(User user, List<Vertex> graph);
    public abstract List<Vertex> calculateBestRoute();

    public final void build() {
        defineBestRouteConditionsForUserPreference(null, null);
        calculateBestRoute();
    }
}
