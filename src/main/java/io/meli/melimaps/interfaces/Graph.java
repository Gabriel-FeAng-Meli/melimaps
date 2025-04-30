package io.meli.melimaps.interfaces;

import java.util.List;

import io.meli.melimaps.model.Vertex;

public interface Graph {

    List<Vertex> getVertices();
    void addVertices(Vertex... vertices);
    void addVertices(List<Vertex> vertices);
    Vertex findPlaceByName(String name);

}
