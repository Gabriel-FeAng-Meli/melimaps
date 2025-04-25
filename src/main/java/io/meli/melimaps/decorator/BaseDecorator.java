package io.meli.melimaps.decorator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.meli.melimaps.enums.EnumPreferences;
import io.meli.melimaps.enums.EnumTransport;
import io.meli.melimaps.interfaces.GraphStructure;
import io.meli.melimaps.interfaces.TransportStrategy;
import io.meli.melimaps.model.CompleteRoute;
import io.meli.melimaps.model.Vertex;

public class BaseDecorator implements TransportStrategy {

    protected TransportStrategy decoratedStrategy;
    protected List<EnumPreferences> preferences;
    protected Map<String, CompleteRoute> allDecoratedRoutes;
    protected EnumTransport transport;

    public BaseDecorator(TransportStrategy decoratedStrategy, EnumTransport transport) {
        this.decoratedStrategy = decoratedStrategy;
        this.transport = transport;
    }

    @Override
    public EnumTransport getStrategyType() {
        return this.decoratedStrategy.getStrategyType();
    }

    @Override
    public Map<String, CompleteRoute> returnRoutesConsideringPreferences(Vertex origin, Vertex destination, GraphStructure map, Map<String, CompleteRoute> rawRoutes, List<EnumPreferences> preferences) {
        Map<String, CompleteRoute> decoratedRoutes = new HashMap<>();
        decoratedRoutes.putAll(rawRoutes);

        if (preferences.isEmpty() || (preferences.size() == 1 && preferences.get(0).equals(EnumPreferences.DISTANCE))) {
            return decoratedRoutes;
        }

        preferences.forEach((preference) -> {
            switch (preference) {
                case ECO ->  this.decoratedStrategy = new EcoDecorator(decoratedStrategy, transport);
                case ACCESSIBILITY -> this.decoratedStrategy = new AccessibilityDecorator(decoratedStrategy, transport);
                case BUDGET -> this.decoratedStrategy = new BudgetDecorator(decoratedStrategy, transport);
                case TIME -> this.decoratedStrategy = new TimeDecorator(decoratedStrategy, transport);
                default -> {}
            }

            CompleteRoute newRoute = decoratedStrategy.calculateBestRoute(origin, destination, map);
            newRoute.setPrioritize(preference.name());
            decoratedRoutes.put("Best route considering %s".formatted(preference.name()), newRoute);     
        });

        this.allDecoratedRoutes = decoratedRoutes;
        
        return allDecoratedRoutes;
    }

    @Override
    public CompleteRoute calculateBestRoute(Vertex origin, Vertex destination, GraphStructure map) {
        return decoratedStrategy.calculateBestRoute(origin, destination, map);
    }

}
