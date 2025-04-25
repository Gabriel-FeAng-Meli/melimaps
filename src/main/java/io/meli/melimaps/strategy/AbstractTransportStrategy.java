package io.meli.melimaps.strategy;

import java.util.ArrayList;
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
import io.meli.melimaps.model.CompleteRoute;
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
    public Map<String, CompleteRoute> returnRoutesConsideringPreferences(Vertex origin, Vertex destination, GraphStructure graph, Map<String, CompleteRoute> rawRoutes, List<EnumPreferences> preferences) {
        return rawRoutes;
    }

    protected Map<String, CompleteRoute> applyPreferenceToRoute(CompleteRoute route, EnumPreferences preference) {
        Map<String, CompleteRoute> result = new HashMap<>();
        result.put("Best route considering %s : ".formatted(preference.name()), route);
        return result;
    }

    protected CompleteRoute getShortestPathBetween(Vertex source, Vertex destination, List<Vertex> map) {
        CompleteRoute bestRoute = calculateShortestPathToEachVertex(source, map).stream()
                .filter(route -> route.getDestinationName().equalsIgnoreCase(destination.getName()) && route.getOriginName().equalsIgnoreCase(source.getName())).findFirst().orElseThrow();

        return bestRoute;
    }

    @Override
    public abstract CompleteRoute calculateBestRoute(Vertex origin, Vertex destination, GraphStructure map);

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

    protected List<CompleteRoute> returnAllPossibleRoutesFromSource(Vertex source, List<Vertex> weightedMap) {

        List<CompleteRoute> paths = new ArrayList<>();
        weightedMap.forEach(node -> {

            String path = node.getWeightedVerticesInReachOrder().stream().map((vertex) -> {
                return vertex.getName();
            }).collect(Collectors.joining(" -> "));

            String routePath = "%s -> %s".formatted(path, node.getName());
            String distance = "%s kilometers".formatted(node.getWeight());
            Long minutes = calculateMinutes(source.getPathToChild(node).getDistance());
            Long cents = calculateCost(node.getWeight());
            String estimatedTime = calculateTimeToTravel(minutes);
            String estimatedCost = calculateTotalCost(cents);

            CompleteRoute routeToNode = new CompleteRoute(type, source.getName(), node.getName(), distance, estimatedTime,
                    estimatedCost, routePath);

            paths.add(routeToNode);
        });
        return paths;
    }

    protected void evaluatePathWeight(Vertex childVertex, Vertex parentVertex) {
        Integer newWeight = parentVertex.getWeight() + parentVertex.getPathToChild(childVertex).getWeight();
        if (newWeight < childVertex.getWeight()) {
            childVertex.setWeight(newWeight);
            childVertex.setPathsInReachOrder(Stream
                    .concat(parentVertex.getPathsInReachOrder().stream(), Stream.of(parentVertex.getPathToChild(childVertex))).toList());
        }
    }

    protected List<CompleteRoute> calculateShortestPathToEachVertex(Vertex source, List<Vertex> map) {
        source.setWeight(0);

        Set<Vertex> settledNodes = new HashSet<>();
        Queue<Vertex> unsettledNodes = new PriorityQueue<>(Set.of(source));

        while (!unsettledNodes.isEmpty()) {
            Vertex current = unsettledNodes.poll();
            current.getPathToChildren().entrySet().stream().filter(
                    entry -> !settledNodes.contains(entry.getKey())).forEach(entry -> {
                        evaluatePathWeight(entry.getKey(), current);
                        unsettledNodes.add(entry.getKey());
                    });

            settledNodes.add(current);
        }

        return returnAllPossibleRoutesFromSource(source, map);
    }

}
