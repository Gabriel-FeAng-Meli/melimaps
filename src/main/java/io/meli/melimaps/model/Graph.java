package io.meli.melimaps.model;

import java.util.List;
import java.util.stream.Collectors;

public class Graph {

    private List<Vertex> vertices;

    public List<Vertex> getVertices() {
        return this.vertices;
    }    

    public void addVertices(List<Vertex> graph) {
        this.vertices.addAll(graph);
    }

    public void addVertices(Vertex vertex) {
        this.vertices.add(vertex);
    }    

    public Vertex findPlaceByName(String name) {
    
        List<Vertex> graph = this.getVertices();
        Vertex vertex = graph.stream().filter(v -> v.getName().equalsIgnoreCase(name)).collect(Collectors.toList()).get(0);

        return vertex;
    }

}