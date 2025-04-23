package io.meli.melimaps.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.meli.melimaps.model.Graph;
import io.meli.melimaps.model.Vertex;

@Configuration
public class GraphConfig {

    @Bean
    public Graph graph() {

        Graph map = new Graph();

        Vertex a = new Vertex("A");
        Vertex b = new Vertex("B");
        Vertex c = new Vertex("C");
        Vertex d = new Vertex("D");
        Vertex e = new Vertex("E");
        Vertex f = new Vertex("F");

        a.addAdjacentVertex(b, 4);
        a.addAdjacentVertex(c, 9);
        b.addAdjacentVertex(c, 5);
        b.addAdjacentVertex(d, 3);
        b.addAdjacentVertex(e, 2);
        c.addAdjacentVertex(d, 12);
        d.addAdjacentVertex(e, 4);
        d.addAdjacentVertex(f, 22);
        e.addAdjacentVertex(f, 5);


        map.addVertices(a, b, c, d, e, f);

        return map;
    }
}
