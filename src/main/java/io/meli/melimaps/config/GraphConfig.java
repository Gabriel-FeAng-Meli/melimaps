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

        a.addAdjacentNode(b, 4);
        a.addAdjacentNode(c, 9);

        b.addAdjacentNode(c, 5);
        b.addAdjacentNode(d, 3);
        b.addAdjacentNode(e, 2);

        c.addAdjacentNode(d, 12);

        d.addAdjacentNode(e, 4);
        d.addAdjacentNode(f, 22);

        e.addAdjacentNode(f, 5);


        map.addVertices(a, b, c, d, e, f);

        return map;
    }
}
