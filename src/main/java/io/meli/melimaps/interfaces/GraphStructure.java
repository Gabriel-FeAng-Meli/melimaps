package io.meli.melimaps.interfaces;

import java.util.List;

import io.meli.melimaps.enums.EnumPreferences;
import io.meli.melimaps.enums.EnumTransport;
import io.meli.melimaps.model.Vertex;

public interface GraphStructure {

    List<Vertex> getVertices();
    void addVertices(Vertex... vertices);
    Vertex findPlaceByName(String name);

    List<Vertex> getWeightedVerticesByPreference(List<Vertex> mapByDistance, EnumPreferences preferences, EnumTransport transport);

    List<Vertex> getVerticesAvailableForTransport(List<Vertex> map, EnumTransport transport);
}
