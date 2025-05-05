package io.meli.melimaps.interfaces;

import java.util.stream.Stream;

import io.meli.melimaps.enums.EnumTransport;
import io.meli.melimaps.model.Route;
import io.meli.melimaps.model.Vertex;

public interface TransportStrategy {

    Route calculateBestRoute(Vertex origin, Vertex destination, GraphStructure map);

    EnumTransport getStrategyType();

    static void evaluatePathWeight(Vertex childVertex, Vertex parentVertex, Integer preferenceFactor) {
        Integer newWeight = parentVertex.getWeight() + parentVertex.getPathToChild(childVertex).getWeight() + preferenceFactor;
        if (newWeight < childVertex.getWeight()) {
            childVertex.setWeight(newWeight);
            childVertex.setPathsInReachOrder(Stream
                    .concat(parentVertex.getPathsInReachOrder().stream(), Stream.of(parentVertex.getPathToChild(childVertex))).toList());
        }
    }


}
