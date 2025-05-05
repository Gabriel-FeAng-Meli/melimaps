package io.meli.melimaps.interfaces;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Stream;

import io.meli.melimaps.enums.EnumTransport;
import io.meli.melimaps.model.Path;
import io.meli.melimaps.model.Route;
import io.meli.melimaps.model.Vertex;

public interface TransportStrategy {

    Route calculateBestRoute(Vertex origin, Vertex destination, GraphStructure map);

    EnumTransport getStrategyType();

    static void evaluatePathWeight(Vertex childVertex, Vertex parentVertex, Integer multiplyingFactor, Integer additionFactor) {

        Path p = parentVertex.getPathToChild(childVertex);
        Integer newWeight = parentVertex.getWeight() + (p.getDistance() * multiplyingFactor)  + additionFactor;
        if (newWeight < childVertex.getWeight()) {
            childVertex.setWeight(newWeight);
            p.setWeight(newWeight);
            childVertex.setPathsInReachOrder(Stream
                    .concat(parentVertex.getPathsInReachOrder().stream(), Stream.of(parentVertex.getPathToChild(childVertex))).toList());
        }
    }

        static Vertex calculateShortestPathToEachVertex(Vertex origin, Vertex destination, List<Vertex> map, EnumTransport type, Integer byDistanceFactor, Integer byStopFactor) {

        Vertex source = origin;

        source.setWeight(0);

        Set<Path> settledPaths = new HashSet<>();
        Set<Vertex> settledNodes = new HashSet<>();
        Queue<Vertex> unsettledNodes = new PriorityQueue<>(Collections.singleton(source));
        Queue<Path> unsettledPaths = new PriorityQueue<>(new HashSet<>());

        while (!unsettledNodes.isEmpty()) {
            Vertex current = unsettledNodes.poll();
            current.getPathToChildren().forEach((vertex, path) -> {
                unsettledPaths.add(path);
            });

            while (!unsettledPaths.isEmpty()) {
                Path p = unsettledPaths.poll();
                
                if (!settledPaths.contains(p) && p.getTransports().contains(type)){
                    evaluatePathWeight(p.getDestination(), current, byDistanceFactor, byStopFactor);
                    unsettledNodes.add(p.getDestination());
                    settledPaths.add(p);
                }

            }

            settledNodes.add(current);
        }

        return origin;
    }
    


}
