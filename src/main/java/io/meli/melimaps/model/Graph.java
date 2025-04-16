package io.meli.melimaps.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Graph {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    private List<Vertex> vertices;

    public Graph() {
        List<Vertex> graph = new ArrayList<>();
        this.vertices = graph;
    }

    public List<Vertex> getVertices() {
        return vertices;
    }    

    public void addVertices(List<Vertex> graph) {
        this.vertices.addAll(graph);
    }    
    public void addVertices(Vertex vertex) {
        this.vertices.add(vertex);
    }    

    public Vertex findPlaceByName(String name) {
    
        List<Vertex> graph = this.getVertices();
        Vertex vertex = graph.stream().filter(v -> v.getName() == name).collect(Collectors.toList()).get(0);

        return vertex;
    }

}