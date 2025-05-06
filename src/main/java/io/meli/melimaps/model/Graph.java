package io.meli.melimaps.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import io.meli.melimaps.enums.EnumTransport;
import io.meli.melimaps.interfaces.GraphStructure;

public class Graph implements GraphStructure {

    private List<Vertex> vertices;
    private List<Path> allPaths;

    public List<Path> getAllPaths() {
        return allPaths;
    }

    public void setAllPathsFromOwnVertices() {

        List<Path> list = new ArrayList<>();
        vertices.forEach(vertex -> {
            vertex.getPathToChildren().entrySet().forEach((entry) -> {

            });
        });

        this.allPaths = list;
    }

    public Graph() {
        this.vertices = new ArrayList<>();
        this.allPaths = new ArrayList<>();
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

    public void getGraphWithVerticesAvailableForTransport(EnumTransport transport) {

        List<Vertex> weightedMap = vertices.stream().map((v) -> {
            v.setPaths(v.getPathToChildren().entrySet().stream().filter(entry -> entry.getValue().getTransports().contains(transport) || entry.getValue().getTransports().contains(EnumTransport.FOOT)).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
            return v;
        }).collect(Collectors.toList());

        this.vertices = weightedMap;
    }

    @Override
    public Vertex findPlaceByName(String name) {
    
        List<Vertex> graph = this.getVertices();
        Vertex vertex = graph.stream().filter(v -> v.getName().equalsIgnoreCase(name)).collect(Collectors.toList()).get(0);

        return vertex;
    }

    public Graph build() {

        Graph map = new Graph();

        Vertex a = new Vertex.Builder().name("A").build();
        Vertex b = new Vertex.Builder().name("B").build();
        Vertex c = new Vertex.Builder().name("C").build();
        Vertex d = new Vertex.Builder().name("D").build();
        Vertex e = new Vertex.Builder().name("E").build();
        Vertex f = new Vertex.Builder().name("F").build();
        Vertex g = new Vertex.Builder().name("G").build();

        a.addChildVertex(b, 2, EnumTransport.FOOT, EnumTransport.BIKE, EnumTransport.CAR, EnumTransport.BUS);
        a.addChildVertex(c, 9, EnumTransport.FOOT, EnumTransport.BIKE, EnumTransport.CAR, EnumTransport.BUS);
        a.addChildVertex(f, 35, EnumTransport.RAILWAY, EnumTransport.BUS);
        b.addChildVertex(c, 5, EnumTransport.FOOT, EnumTransport.BIKE, EnumTransport.CAR, EnumTransport.BUS);
        b.addChildVertex(d, 3, EnumTransport.FOOT, EnumTransport.BIKE, EnumTransport.CAR, EnumTransport.BUS);
        b.addChildVertex(e, 2, EnumTransport.FOOT, EnumTransport.BIKE, EnumTransport.CAR, EnumTransport.BUS);
        c.addChildVertex(d, 12, EnumTransport.FOOT, EnumTransport.BIKE, EnumTransport.CAR, EnumTransport.BUS);
        d.addChildVertex(e, 4, EnumTransport.FOOT, EnumTransport.BIKE, EnumTransport.CAR, EnumTransport.BUS);
        d.addChildVertex(f, 22, EnumTransport.FOOT, EnumTransport.BIKE, EnumTransport.CAR, EnumTransport.BUS);
        e.addChildVertex(f, 5, EnumTransport.FOOT, EnumTransport.BIKE, EnumTransport.CAR, EnumTransport.BUS);
        
        a.addChildVertex(g, 10, EnumTransport.RAILWAY, EnumTransport.BUS);
        g.addChildVertex(f, 5, EnumTransport.RAILWAY, EnumTransport.BUS);


        map.addVertices(a, b, c, d, e, f, g);

        return map;

    }

}