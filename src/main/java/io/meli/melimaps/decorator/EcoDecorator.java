package io.meli.melimaps.decorator;

import io.meli.melimaps.factories.TransportStrategyFactory;
import io.meli.melimaps.interfaces.GraphStructure;
import io.meli.melimaps.interfaces.TransportStrategy;
import io.meli.melimaps.model.Route;
import io.meli.melimaps.model.UserPreferences;
import io.meli.melimaps.model.Vertex;

public class EcoDecorator extends BaseDecorator {

        public EcoDecorator(TransportStrategy decoratedStrategy) {
            super(decoratedStrategy);
        }

        @Override
        public Route calculateBestRoute(Vertex origin, Vertex destination, GraphStructure map) {
            TransportStrategyFactory factory = new TransportStrategyFactory();

            UserPreferences p = new UserPreferences(transport.name(), false, false, false);

            TransportStrategy strategy = factory.instantiateRightStrategy(p);

            

            strategy.calculateBestRoute(origin, destination, map);



        return null;
        }
    
    
}
