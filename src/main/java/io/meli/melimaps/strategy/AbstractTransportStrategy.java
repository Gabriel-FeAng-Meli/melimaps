package io.meli.melimaps.strategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

import io.meli.melimaps.enums.EnumDecoration;
import io.meli.melimaps.enums.EnumTransport;
import io.meli.melimaps.interfaces.Graph;
import io.meli.melimaps.interfaces.TransportStrategy;
import io.meli.melimaps.model.Path;
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

    protected Map<String, Route> applyPreferenceToRoute(Route route, EnumDecoration decoration) {
        Map<String, Route> result = new HashMap<>();
        result.put("Best route considering %s : ".formatted(decoration.name()), route);
        return result;
    }

    protected Route getShortestPathBetween(Vertex source, Vertex destination, List<Vertex> map) {
        Route bestRoute = calculateShortestPathToEachVertex(source, map).stream()
                .filter(route -> route.getDestinationName().equalsIgnoreCase(destination.getName()) && route.getOriginName().equalsIgnoreCase(source.getName())).findFirst().orElseThrow();

        return bestRoute;
    }

    @Override
    public abstract Route calculateBestRoute(Vertex origin, Vertex destination, Graph map);

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

            Route routeToNode = new Route.Builder()
                .destinationName(node.getName())
                .originName(source.getName())
                .distance(estimatedTime)
                .totalCost(estimatedCost)
                .timeToReach(estimatedTime)
                .path(pathString).transport(type.name())
                .build();

            paths.add(routeToNode);
        });
        return paths;
    }

    protected void evaluatePathWeight(Vertex childVertex, Vertex parentVertex) {
        Integer newWeight = parentVertex.getWeight() + parentVertex.getPathToChild(childVertex).getDistance();
        if (newWeight < childVertex.getWeight()) {
            childVertex.setWeight(newWeight);

            List<Path> a = new LinkedList<>();
            a.add(parentVertex.getPathToChild(childVertex));

            parentVertex.setPathsInReachOrder(a);
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
