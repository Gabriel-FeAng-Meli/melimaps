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

import io.meli.melimaps.enums.EnumPreference;
import io.meli.melimaps.enums.EnumTransport;
import io.meli.melimaps.interfaces.GraphStructure;
import io.meli.melimaps.interfaces.TransportStrategy;
import io.meli.melimaps.model.Route;
import io.meli.melimaps.model.Vertex;

public abstract class AbstractTransportStrategy implements TransportStrategy {

    protected EnumTransport type;

    protected Route optimalRoute;

    @Override
    public EnumTransport getStrategyType() {
        return this.type;
    }

    public void setTransport(EnumTransport transport) {
        this.type = transport;
    }

    protected Map<String, Route> applyPreferenceToRoute(Route route, EnumPreference preference) {
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

    protected String calculateTimeToTravel(Long timeInMinutes) {
        

        Long min = timeInMinutes;


        String result = String.format("%s minutes", min);

        return result;
    }
    
    private Long minutes;
    private Long cents;
    private String estimatedTime;
    private String estimatedCost;
    private Integer distance;

    protected List<Route> returnOptimalRoutesOnTheMap(Vertex source, List<Vertex> weightedMap) {

        List<Route> paths = new ArrayList<>();
        weightedMap.forEach(node -> {

            distance = 0;
            minutes = 0L;
            cents = 0L;
            estimatedCost = "";
            estimatedTime = "";

            String pathString = node.getPathsInReachOrder()
                .stream().map((path) -> {

                    EnumTransport transportUsedOnPath = EnumTransport.FOOT;
                    if (path.getTransports().contains(type)) {
                        transportUsedOnPath = type;
                    }

                    cents += type.costPerKmInCents() * path.getDistance();
                    cents += type.costPerStopInCents();

                    distance += path.getDistance();

                    minutes += type.minutesStoppedAtEachPoint();
                    minutes += 60 * path.getDistance() / type.transportSpeedInKmPerHour();

                    String pathing = ": (%s) from %s to %s for %s kilometers :".formatted(transportUsedOnPath, path.getOrigin().getName(), path.getDestination().getName(), path.getDistance());
                    return pathing;
                }).collect(Collectors.joining(" -> "));

            estimatedCost = calculateTotalCost(cents);
            estimatedTime = calculateTimeToTravel(minutes);

            Route routeToNode = new Route(type, source.getName(), node.getName(), "%s kilometers".formatted(distance), estimatedTime,
                    estimatedCost, pathString);

            paths.add(routeToNode);
        });
        return paths;
    }

    protected void evaluatePathWeight(Vertex childVertex, Vertex parentVertex) {
        Integer newWeight = parentVertex.getWeight() + parentVertex.getPathToChild(childVertex).getDistance();
        if (newWeight < childVertex.getWeight()) {
            childVertex.setWeight(newWeight);
            childVertex.setPathsInReachOrder(Stream
                    .concat(parentVertex.getPathsInReachOrder().stream(), Stream.of(parentVertex.getPathToChild(childVertex))).toList());
        }
    }

    protected List<Route> calculateShortestPathToEachVertex(Vertex source, List<Vertex> map) {
        source.setWeight(0);

        Set<Vertex> settledNodes = new HashSet<>();
        Queue<Vertex> unsettledNodes = new PriorityQueue<>(Set.of(source));

        while (!unsettledNodes.isEmpty()) {
            Vertex current = unsettledNodes.poll();

            current.getPathToChildren().entrySet().stream().filter(entry -> entry.getValue().getTransports().contains(type)).filter(
                    entry -> !settledNodes.contains(entry.getKey())).forEach(entry -> {

                            evaluatePathWeight(entry.getKey(), current);
                            unsettledNodes.add(entry.getKey());
    
                    });

            settledNodes.add(current);
        }

        return returnOptimalRoutesOnTheMap(source, map);
    }

}
