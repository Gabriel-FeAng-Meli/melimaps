package io.meli.melimaps.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import io.meli.melimaps.enums.EnumTransport;

public class Graph {

    private final List<Vertex> vertices;

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

    public static Graph build() {

        Graph map = new Graph();

        Vertex a = new Vertex("A");
        Vertex b = new Vertex("B");
        Vertex c = new Vertex("C");
        Vertex d = new Vertex("D");
        Vertex e = new Vertex("E");
        Vertex f = new Vertex("F");
        Vertex g = new Vertex("G");

        a.addChildVertex(b, 4, EnumTransport.FOOT);
        a.addChildVertex(c, 9, EnumTransport.FOOT);
        b.addChildVertex(c, 5, EnumTransport.FOOT);
        b.addChildVertex(d, 3, EnumTransport.FOOT);
        b.addChildVertex(e, 2, EnumTransport.FOOT);
        c.addChildVertex(d, 12, EnumTransport.FOOT);
        d.addChildVertex(e, 4, EnumTransport.FOOT);
        d.addChildVertex(f, 22, EnumTransport.FOOT);
        e.addChildVertex(f, 5, EnumTransport.FOOT);
        
        a.addChildVertex(g, 50, EnumTransport.RAILWAY);
        g.addChildVertex(f, 30, EnumTransport.RAILWAY);


        map.addVertices(a, b, c, d, e, f, g);

        return map;

    }

}