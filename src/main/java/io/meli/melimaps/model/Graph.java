package io.meli.melimaps.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import io.meli.melimaps.enums.EnumPreferences;
import io.meli.melimaps.enums.EnumTransport;
import io.meli.melimaps.interfaces.GraphStructure;

public class Graph implements GraphStructure {

    private final List<Vertex> vertices;

    public Graph() {
        this.vertices = new ArrayList<>();
    }

    @Override
    public List<Vertex> getVertices() {
        return this.vertices;
    }    

    @Override
    public void addVertices(Vertex... v) {
        this.vertices.addAll(Arrays.asList(v));
    }

    @Override
    public void addVertices(List<Vertex> vertices) {
        this.vertices.addAll(vertices);
    }

    @Override
    public GraphStructure getWeightedGraphByPreference(EnumPreferences preferences, EnumTransport transport) {

        List<Vertex> weightedMap = vertices.stream().map((v) -> {
            v.getPathToChildren().entrySet().stream().map((entry) -> {
                Double weight = preferences.factorDistanceIntoWeight(transport, entry.getValue().getDistance());
                entry.getValue().setWeight(weight.intValue());
                return entry;
            }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

            return v;
        }).collect(Collectors.toList());

        Graph g = new Graph();
        g.addVertices(weightedMap);

        return g;
    }

    @Override
    public GraphStructure getGraphWithVerticesAvailableForTransport(EnumTransport transport) {

        List<Vertex> weightedMap = vertices.stream().map((v) -> {
            v.getPathToChildren().entrySet().stream().filter(entry -> entry.getValue().getTransport().equals(transport));
            return v;
        }).collect(Collectors.toList());

        Graph g = new Graph();

        g.addVertices(weightedMap);

        return g;
    }

    @Override
    public Vertex findPlaceByName(String name) {
    
        List<Vertex> graph = this.getVertices();
        Vertex vertex = graph.stream().filter(v -> v.getName().equalsIgnoreCase(name)).collect(Collectors.toList()).get(0);

        return vertex;
    }

    public Graph build() {

        Graph map = new Graph();

        Vertex a = new Vertex("A");
        Vertex b = new Vertex("B");
        Vertex c = new Vertex("C");
        Vertex d = new Vertex("D");
        Vertex e = new Vertex("E");
        Vertex f = new Vertex("F");
        Vertex g = new Vertex("G");

        a.addChildVertex(b, 4, EnumTransport.FOOT, EnumTransport.BIKE, EnumTransport.CAR, EnumTransport.BUS);
        a.addChildVertex(c, 9, EnumTransport.FOOT, EnumTransport.BIKE, EnumTransport.CAR, EnumTransport.BUS);
        b.addChildVertex(c, 5, EnumTransport.FOOT, EnumTransport.BIKE, EnumTransport.CAR, EnumTransport.BUS);
        b.addChildVertex(d, 3, EnumTransport.FOOT, EnumTransport.BIKE, EnumTransport.CAR, EnumTransport.BUS);
        b.addChildVertex(e, 2, EnumTransport.FOOT, EnumTransport.BIKE, EnumTransport.CAR, EnumTransport.BUS);
        c.addChildVertex(d, 12, EnumTransport.FOOT, EnumTransport.BIKE, EnumTransport.CAR, EnumTransport.BUS);
        d.addChildVertex(e, 4, EnumTransport.FOOT, EnumTransport.BIKE, EnumTransport.CAR, EnumTransport.BUS);
        d.addChildVertex(f, 22, EnumTransport.FOOT, EnumTransport.BIKE, EnumTransport.CAR, EnumTransport.BUS);
        e.addChildVertex(f, 5, EnumTransport.FOOT, EnumTransport.BIKE, EnumTransport.CAR, EnumTransport.BUS);
        
        a.addChildVertex(g, 10, EnumTransport.RAILWAY);
        g.addChildVertex(f, 5, EnumTransport.RAILWAY);


        map.addVertices(a, b, c, d, e, f, g);

        return map;

    }

}