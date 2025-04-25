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
        Map<String, Route> decoratedRoutes = new HashMap<>();
        decoratedRoutes.putAll(rawRoutes);

        if (preferences.isEmpty()) {
            return decoratedRoutes;
        }

        preferences.forEach((preference) -> {
            switch (preference) {
                case ECO ->  this.decoratedStrategy = new AccessibilityDecorator(decoratedStrategy);
                case ACCESSIBILITY -> this.decoratedStrategy = new AccessibilityDecorator(decoratedStrategy);
                case BUDGET -> this.decoratedStrategy = new BudgetDecorator(decoratedStrategy);
                case TIME -> this.decoratedStrategy = new TimeDecorator(decoratedStrategy);
            }

            Route newRoute = decoratedStrategy.calculateBestRoute(origin, destination, map);
            decoratedRoutes.put("Best route considering %s".formatted(preference.name()), newRoute);     
        });

        this.allDecoratedRoutes = decoratedRoutes;
        
        return allDecoratedRoutes;
    }

}
