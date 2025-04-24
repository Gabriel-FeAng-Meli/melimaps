package io.meli.melimaps.strategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import io.meli.melimaps.enums.EnumTransport;
import io.meli.melimaps.interfaces.TransportStrategy;
import io.meli.melimaps.model.Route;
import io.meli.melimaps.model.Vertex;

public abstract class AbstractTransportStrategy implements TransportStrategy {

    protected EnumTransport type;

    @Override
    public EnumTransport getStrategyType() {
        return this.type;
    }

    protected Route getShortestPathBetween(Vertex source, Vertex destination, List<Vertex> map) {
        List<Route> allPaths = calculateShortestPathToEachVertex(source, map);
        Route result = allPaths.stream()
                .filter(route -> route.getDestinationName().equalsIgnoreCase(destination.getName()) && route.getOriginName().equalsIgnoreCase(source.getName())).findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return result;
    }

    private String calculateTotalCost(Long costForTransportation) {

        Double totalCostForTransportation = costForTransportation.doubleValue()/100;

        String cost = String.format("R$%.02f", totalCostForTransportation);

        return cost;
    }

    private String calculateTimeToTravel(Long timeInMinutes) {

        Long minutes = timeInMinutes % 60;
        Long hours = (timeInMinutes - minutes) / 60;


        String result = String.format("%s hours and %s minutes", hours, minutes);

        return result;
    }

    private List<Route> returnAllPossibleRoutesFromSource(Vertex source, List<Vertex> weightedMap) {

        List<Route> paths = new ArrayList<>();
        weightedMap.forEach(node -> {

            String path = node.getWeightedVerticesInReachOrder().stream().map((vertex) -> {
                return vertex.getName();
            }).collect(Collectors.joining(" -> "));

            String routePath = "%s -> %s".formatted(path, node.getName());
            String distance = "%s kilometers".formatted(node.getWeight());
            String estimatedTime = calculateTimeToTravel(node.getWeight().longValue());
            String estimatedCost = calculateTotalCost(node.getWeight().longValue());

            Route routeToNode = new Route(type, source.getName(), node.getName(), distance, estimatedTime,
                    estimatedCost, routePath);

            paths.add(routeToNode);
        });
        return paths;
    }

    private void evaluateDistanceAndPath(Vertex adjacentNode, Integer edgeWeight, Vertex sourceNode) {
        Integer newDistance = sourceNode.getWeight() + edgeWeight;
        if (newDistance < adjacentNode.getWeight()) {
            adjacentNode.setWeight(newDistance);
            adjacentNode.setWeightedVerticesInReachOrder(Stream
                    .concat(sourceNode.getWeightedVerticesInReachOrder().stream(), Stream.of(sourceNode)).toList());
        }
    }

    private List<Route> calculateShortestPathToEachVertex(Vertex source, List<Vertex> map) {
        source.setWeight(0);

        Set<Vertex> settledNodes = new HashSet<>();
        Queue<Vertex> unsettledNodes = new PriorityQueue<>(Collections.singleton(source));

        while (!unsettledNodes.isEmpty()) {
            Vertex current = unsettledNodes.poll();
            current.getChildVerticesAndDistance().entrySet().stream().filter(
                    entry -> !settledNodes.contains(entry.getKey())).forEach(entry -> {
                        evaluateDistanceAndPath(entry.getKey(), entry.getValue(), current);
                        unsettledNodes.add(entry.getKey());
                    });

            settledNodes.add(current);
        }

        return returnAllPossibleRoutesFromSource(source, map);
    }

}
