package io.meli.melimaps.interfaces;

import java.util.List;

import io.meli.melimaps.enums.EnumPreferences;
import io.meli.melimaps.enums.EnumTransport;
import io.meli.melimaps.model.Vertex;

public interface GraphStructure {

    List<Vertex> getVertices();
    void addVertices(Vertex... vertices);
    void addVertices(List<Vertex> vertices);
    Vertex findPlaceByName(String name);

    GraphStructure getWeightedGraphByPreference(EnumPreferences preferences, EnumTransport transport);

    GraphStructure getGraphWithVerticesAvailableForTransport(EnumTransport transport);
}
