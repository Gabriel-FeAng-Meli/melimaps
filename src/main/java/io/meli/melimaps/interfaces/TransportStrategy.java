package io.meli.melimaps.interfaces;

import java.util.List;
import java.util.Map;

import io.meli.melimaps.enums.EnumPreferences;
import io.meli.melimaps.enums.EnumTransport;
import io.meli.melimaps.model.Route;
import io.meli.melimaps.model.Vertex;

public interface TransportStrategy {

    Route calculateBestRoute(Vertex origin, Vertex destination, GraphStructure map);

    EnumTransport getStrategyType();

    Map<String, Route> returnRoutesConsideringPreferences(Vertex origin, Vertex destination, GraphStructure map, Map<String, Route> rawRoutes, List<EnumPreferences> preferences);

}
