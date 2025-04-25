package io.meli.melimaps.strategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import io.meli.melimaps.enums.EnumPreferences;
import io.meli.melimaps.enums.EnumTransport;
import io.meli.melimaps.interfaces.GraphStructure;
import io.meli.melimaps.interfaces.TransportStrategy;
import io.meli.melimaps.model.Route;
import io.meli.melimaps.model.Vertex;

public abstract class AbstractTransportStrategy implements TransportStrategy {

    protected EnumTransport type;

    protected List<EnumPreferences> preferences;

    @Override
    public EnumTransport getStrategyType() {
        return this.type;
    }

    public void setTransport(EnumTransport transport) {
        this.type = transport;
    }
    

    @Override
    public Map<String, Route> returnRoutesConsideringPreferences(Vertex origin, Vertex destination, GraphStructure graph, Map<String, Route> rawRoutes, List<EnumPreferences> preferences) {
        Map<String, Route> result = new HashMap<>();
        result.putAll(rawRoutes);
        return result;
    }

    protected Map<String, Route> applyPreferenceToRoute(Route route, EnumPreferences preference) {
        Map<String, Route> result = new HashMap<>();
        result.put("Best route considering %s : ".formatted(preference.name()), route);
        return result;
    }

    protected Route getShortestPathBetween(Vertex source, Vertex destination, List<Vertex> map) {
        Route bestRoute = calculateShortestPathToEachVertex(source, map).stream()
                .filter(route -> route.getDestinationName().equalsIgnoreCase(destination.getName()) && route.getOriginName().equalsIgnoreCase(source.getName())).findFirst().orElseThrow();

        return bestRoute;
    }

    @Override
    public abstract Route calculateBestRoute(Vertex origin, Vertex destination, GraphStructure map);

    protected String calculateTotalCost(Long costForTransportation) {

        Double totalCostForTransportation = costForTransportation.doubleValue()/100;

        String cost = String.format("R$%.02f", totalCostForTransportation);

        return cost;
    }


    protected Long calculateMinutes(Integer distanceInKilometers) {

        Long timeInMinutes = Math.round(60 * distanceInKilometers / type.transportSpeedInKmPerHour().doubleValue());
        timeInMinutes += type.minutesStoppedAtEachPoint();

        return timeInMinutes;
    }

    protected Long calculateCost(Integer distance) {

        Double totalCostForTransportation = type.costPerStop() + distance * type.costPerKm();

        return Math.round(totalCostForTransportation);
    }


    protected String calculateTimeToTravel(Long timeInMinutes) {
        

        Long minutes = timeInMinutes % 60;
        Long hours = (timeInMinutes - minutes) / 60;


        String result = String.format("%s hours and %s minutes", hours, minutes);

        return result;
    }

    protected List<Route> returnAllPossibleRoutesFromSource(Vertex source, List<Vertex> weightedMap) {

        List<Route> paths = new ArrayList<>();
        weightedMap.forEach(node -> {

            String path = node.getWeightedVerticesInReachOrder().stream().map((vertex) -> {
                return vertex.getName();
            }).collect(Collectors.joining(" -> "));

            String routePath = "%s -> %s".formatted(path, node.getName());
            String distance = "%s kilometers".formatted(node.getWeight());
            Long minutes = calculateMinutes(node.getWeight());
            Long cents = calculateCost(node.getWeight());
            String estimatedTime = calculateTimeToTravel(minutes);
            String estimatedCost = calculateTotalCost(cents);

            Route routeToNode = new Route(type, source.getName(), node.getName(), distance, estimatedTime,
                    estimatedCost, routePath);

            paths.add(routeToNode);
        });
        return paths;
    }

    protected void evaluateDistanceAndPath(Vertex adjacentNode, Integer edgeWeight, Vertex sourceNode) {
        Integer newDistance = sourceNode.getWeight() + edgeWeight;
        if (newDistance < adjacentNode.getWeight()) {
            adjacentNode.setWeight(newDistance);
            adjacentNode.setWeightedVerticesInReachOrder(Stream
                    .concat(sourceNode.getWeightedVerticesInReachOrder().stream(), Stream.of(sourceNode)).toList());
        }
    }

    protected List<Route> calculateShortestPathToEachVertex(Vertex source, List<Vertex> map) {
        source.setWeight(0);

        Set<Vertex> settledNodes = new HashSet<>();
        Queue<Vertex> unsettledNodes = new PriorityQueue<>(Collections.singleton(source));

        while (!unsettledNodes.isEmpty()) {
            Vertex current = unsettledNodes.poll();
            current.getPathToChildren().entrySet().stream().filter(
                    entry -> !settledNodes.contains(entry.getKey())).forEach(entry -> {
                        evaluateDistanceAndPath(entry.getKey(), entry.getValue().getDistance(), current);
                        unsettledNodes.add(entry.getKey());
                    });

            settledNodes.add(current);
        }

        return returnAllPossibleRoutesFromSource(source, map);
    }

}
