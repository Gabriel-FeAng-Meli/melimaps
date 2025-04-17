package io.meli.melimaps.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.meli.melimaps.enums.EnumTransport;
import io.meli.melimaps.model.Graph;
import io.meli.melimaps.model.Vertex;

@Configuration
public class GraphConfig {

    @Bean
    public Graph graph() {

        Graph map = new Graph();

        Vertex a = Vertex.createRootVertex("A");
        Vertex b0 = Vertex.buildRoadTowardsNewChild(a,"B0", 4, EnumTransport.BUS);
        Vertex c00 = Vertex.buildRoadTowardsNewChild(b0, "C00", 15, EnumTransport.BUS);
        Vertex c01 = Vertex.buildRoadTowardsNewChild(b0, "C01", 5, EnumTransport.BUS);
        Vertex c02 = Vertex.buildRoadTowardsNewChild(b0, "C02", 2, EnumTransport.BUS);
        Vertex d020 = Vertex.buildRoadTowardsNewChild(c02, "D020", 3, EnumTransport.BUS);
        Vertex b1 = Vertex.buildRoadTowardsNewChild(a,"B1", 25, EnumTransport.BUS);
        Vertex c10 = Vertex.buildRoadTowardsNewChild(b1, "C10", 35, EnumTransport.BUS);
        Vertex c11 = Vertex.buildRoadTowardsNewChild(b1, "C11", 10, EnumTransport.BUS);

        Vertex.buildRoadTowardsExistingChild(c00, a, 60, EnumTransport.BUS);
        Vertex.buildRoadTowardsExistingChild(c01, a, 9, EnumTransport.BUS);
        Vertex.buildRoadTowardsExistingChild(c01, c11, 8, EnumTransport.BUS);
        Vertex.buildRoadTowardsExistingChild(c01, d020, 18, EnumTransport.BUS);
        Vertex.buildRoadTowardsExistingChild(c02, c10, 1, EnumTransport.BUS);
        Vertex.buildRoadTowardsExistingChild(c11, c00, 8, EnumTransport.BUS);
        Vertex.buildRoadTowardsExistingChild(c10, d020, 2, EnumTransport.BUS);
        Vertex.buildRoadTowardsExistingChild(d020, c00, 3, EnumTransport.BUS);

        map.addVertices(a, b0, c00, c01, c02, d020, b1, c10, c11);

        return map;
    }
}
