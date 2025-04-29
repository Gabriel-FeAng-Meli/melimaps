package io.meli.melimaps.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import io.meli.melimaps.enums.EnumTransport;
import io.meli.melimaps.interfaces.Graph;

public class MeliMap implements Graph {

    private List<Vertex> vertices;

    public MeliMap() {
        this.vertices = new ArrayList<>();
    }

    @Override
    public List<Vertex> getVertices() {
        return this.vertices;
    }    

    @Override
    public void addVertices(final Vertex... v) {
        this.vertices.addAll(Arrays.asList(v));
    }

    @Override
    public void addVertices(final List<Vertex> vertices) {
        this.vertices.addAll(vertices);
    }

    @Override
    public void getGraphWithVerticesAvailableForTransport(final EnumTransport transport) {

        final List<Vertex> weightedMap = vertices.stream().map((v) -> {
            v.setPaths(v.getPathToChildren().entrySet().stream().filter(entry -> entry.getKey().getTransports().contains(transport)).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
            return v;
        }).collect(Collectors.toList());

        this.vertices = weightedMap;
    }

    @Override
    public Vertex findPlaceByName(final String name) {
    
        final List<Vertex> graph = this.getVertices();
        final Vertex vertex = graph.stream().filter(v -> v.getName().equalsIgnoreCase(name)).collect(Collectors.toList()).get(0);

        return vertex;
    }

    public void clear() {
        this.vertices = null;
    }

    public MeliMap build() {

        final MeliMap map = new MeliMap();

        final Vertex a = new Vertex("A");
        final Vertex b = new Vertex("B");
        final Vertex c = new Vertex("C");
        final Vertex d = new Vertex("D");
        final Vertex e = new Vertex("E");
        final Vertex f = new Vertex("F");
        final Vertex g = new Vertex("G");

        a.addChildVertex(b, 2, EnumTransport.FOOT, EnumTransport.BIKE, EnumTransport.CAR, EnumTransport.BUS);
        a.addChildVertex(f, 25, EnumTransport.RAILWAY, EnumTransport.BUS);
        a.addChildVertex(c, 9, EnumTransport.FOOT, EnumTransport.BIKE, EnumTransport.CAR, EnumTransport.BUS);
        b.addChildVertex(c, 5, EnumTransport.FOOT, EnumTransport.BIKE, EnumTransport.CAR, EnumTransport.BUS);
        b.addChildVertex(d, 3, EnumTransport.FOOT, EnumTransport.CAR, EnumTransport.BIKE);
        b.addChildVertex(e, 2, EnumTransport.FOOT, EnumTransport.CAR, EnumTransport.BIKE);
        c.addChildVertex(d, 12, EnumTransport.FOOT, EnumTransport.BIKE, EnumTransport.CAR, EnumTransport.BUS);
        d.addChildVertex(e, 4, EnumTransport.FOOT, EnumTransport.BIKE, EnumTransport.CAR, EnumTransport.BUS);
        d.addChildVertex(f, 22, EnumTransport.FOOT, EnumTransport.BIKE, EnumTransport.CAR, EnumTransport.BUS);
        e.addChildVertex(f, 5, EnumTransport.FOOT, EnumTransport.BIKE, EnumTransport.CAR, EnumTransport.BUS);
        
        a.addChildVertex(g, 15, EnumTransport.RAILWAY, EnumTransport.BUS);
        g.addChildVertex(f, 5, EnumTransport.RAILWAY, EnumTransport.BUS);


        map.addVertices(a, b, c, d, e, f, g);

        return map;

    }

}