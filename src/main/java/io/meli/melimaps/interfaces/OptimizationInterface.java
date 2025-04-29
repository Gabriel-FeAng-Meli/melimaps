package io.meli.melimaps.interfaces;

import java.util.stream.Stream;

import io.meli.melimaps.enums.EnumTransport;
import io.meli.melimaps.model.Route;
import io.meli.melimaps.model.Vertex;

public interface OptimizationInterface {

    Route calculateBestRoute(Vertex origin, Vertex destination, Graph map);

    EnumTransport getStrategyType();

    public static void evaluatePathWeight(Vertex childVertex, Vertex parentVertex) {
        Integer newWeight = parentVertex.getWeight() + parentVertex.getPathsToChild(childVertex).getWeight();
        if (newWeight < childVertex.getWeight()) {
            parentVertex.getPathsToChild(childVertex).setWeight(newWeight);
            childVertex.setWeight(newWeight);
            childVertex.setPathsInReachOrder(Stream.concat(parentVertex.getPathsInReachOrder().stream(), Stream.of(parentVertex.getPathsToChild(childVertex))).toList());
        }
    }

    static String calculateTotalCost(Long costForTransportation) {

        Double totalCostForTransportation = costForTransportation.doubleValue()/100;

        String cost = String.format("R$%.02f", totalCostForTransportation);

        return cost;
    }

    static String calculateTimeToTravel(Long timeInMinutes) {
        

        Long min = timeInMinutes;


        String result = String.format("%s minutes", min);

        return result;
    }
    

    
}
