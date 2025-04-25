package io.meli.melimaps.decorator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.meli.melimaps.enums.EnumPreferences;
import io.meli.melimaps.enums.EnumTransport;
import io.meli.melimaps.interfaces.GraphStructure;
import io.meli.melimaps.interfaces.TransportStrategy;
import io.meli.melimaps.model.Route;
import io.meli.melimaps.model.Vertex;

public abstract class BaseDecorator implements TransportStrategy {

    protected TransportStrategy decoratedStrategy;
    protected List<EnumPreferences> preferences;
    protected Map<String, Route> allDecoratedRoutes;
    protected EnumTransport transport;

    protected BaseDecorator(TransportStrategy decoratedStrategy) {
        this.decoratedStrategy = decoratedStrategy;
    }

    @Override
    public EnumTransport getStrategyType() {
        return this.decoratedStrategy.getStrategyType();
    }

    @Override
    public Map<String, Route> returnRoutesConsideringPreferences(Vertex origin, Vertex destination, GraphStructure map, Map<String, Route> rawRoutes, List<EnumPreferences> preferences) {
        Map<String, Route> decoratedRoute = new HashMap<>();

        if (preferences.isEmpty()) {
            return rawRoutes;
        }

        preferences.forEach((preference) -> {

            switch (preference) {
                case ECO: {
                    this.decoratedStrategy = new EcoDecorator(decoratedStrategy);
                    break;
                }
            }

            decoratedStrategy.calculateBestRoute(origin, destination, map);
            
        }
        );
        this.allDecoratedRoutes = decoratedRoute;
        
        return allDecoratedRoutes;
    }

}
