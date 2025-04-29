package io.meli.melimaps.decorator;

import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import io.meli.melimaps.enums.EnumPreference;
import io.meli.melimaps.enums.EnumTransport;
import io.meli.melimaps.interfaces.GraphStructure;
import io.meli.melimaps.model.Path;
import io.meli.melimaps.model.Route;
import io.meli.melimaps.model.Vertex;

public class AccessibilityDecorator extends BaseDecorator {

    public AccessibilityDecorator(EnumTransport transport) {
        this.priority = EnumPreference.ACCESSIBILITY;
        this.transport = transport;
    }


    @Override
    public Route calculateBestRoute(Vertex origin, Vertex destination, GraphStructure map) {
        return getOptimalPathBetween(origin, destination, map.getVertices());
    }

    @Override
    public List<Route> calculateMostOptimalPathToEachVertex(Vertex source, List<Vertex> map) {
        source.setWeight(0);

        Set<Vertex> settledNodes = new HashSet<>();
        Queue<Vertex> unsettledNodes = new PriorityQueue<>(Set.of(source));

        while (!unsettledNodes.isEmpty()) {
            Vertex current = unsettledNodes.poll();
            current.getPathToChildren().entrySet().stream().filter(
                    entry -> !settledNodes.contains(entry.getKey())).forEach(entry -> {
                        Vertex v = entry.getKey();
                        Path p = entry.getValue();

                        p.setWeight(p.getDistance() * transport.badAccessibilityScore());

                        evaluatePathWeight(v, current);
                        unsettledNodes.add(v);
                    });

            settledNodes.add(current);
        }

        return returnOptimalRoutesOnTheMap(source, map);
    }
    
}
