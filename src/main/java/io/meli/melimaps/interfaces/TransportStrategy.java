package io.meli.melimaps.interfaces;

import java.util.List;
import java.util.Map;

import io.meli.melimaps.enums.EnumPreferences;
import io.meli.melimaps.enums.EnumTransport;
import io.meli.melimaps.model.CompleteRoute;
import io.meli.melimaps.model.Vertex;

public interface TransportStrategy {

    CompleteRoute calculateBestRoute(Vertex origin, Vertex destination, GraphStructure map);

    EnumTransport getStrategyType();

    Map<String, CompleteRoute> returnRoutesConsideringPreferences(Vertex origin, Vertex destination, GraphStructure map, Map<String, CompleteRoute> rawRoutes, List<EnumPreferences> preferences);

}
