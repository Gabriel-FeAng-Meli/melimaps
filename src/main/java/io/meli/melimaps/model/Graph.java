package io.meli.melimaps.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Graph {

    private List<Vertex> vertices;

    public Graph() {
        this.vertices = new ArrayList<>();
    }

    public List<Vertex> getVertices() {
        return this.vertices;
    }    

    public void addVertices(Vertex... v) {
        this.vertices.addAll(Arrays.asList(v));
    }

    public Vertex findPlaceByName(String name) {
    
        List<Vertex> graph = this.getVertices();
        Vertex vertex = graph.stream().filter(v -> v.getName().equalsIgnoreCase(name)).collect(Collectors.toList()).get(0);

        return vertex;
    }

}