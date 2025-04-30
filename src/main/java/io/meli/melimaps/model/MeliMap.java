package io.meli.melimaps.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
    public void addVertices(Vertex... v) {
        this.vertices.addAll(Arrays.asList(v));
    }

    @Override
    public void addVertices(List<Vertex> vertices) {
        this.vertices.addAll(vertices);
    }

    @Override
    public Vertex findPlaceByName(String name) {
    
        List<Vertex> graph = this.getVertices();
        Vertex vertex = graph.stream().filter(v -> v.getName().equalsIgnoreCase(name)).collect(Collectors.toList()).get(0);

        return vertex;
    }

    public void clear() {
        this.vertices = null;
    }

    public MeliMap build() {

        MeliMap map = new MeliMap();

        Vertex a = new Vertex("A");
        Vertex b = new Vertex("B");
        Vertex c = new Vertex("C");
        Vertex d = new Vertex("D");
        Vertex e = new Vertex("E");
        Vertex f = new Vertex("F");
        Vertex g = new Vertex("G");

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