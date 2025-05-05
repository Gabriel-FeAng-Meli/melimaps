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

import io.meli.melimaps.enums.EnumPreference;
import io.meli.melimaps.enums.EnumTransport;
import io.meli.melimaps.interfaces.GraphStructure;
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

    protected Map<String, Route> applyPreferenceToRoute(Route route, EnumPreference preference) {
        Map<String, Route> result = new HashMap<>();
        result.put("Best route considering %s : ".formatted(preference.name()), route);
        return result;
    }

    protected Route getShortestPathBetween(Vertex source, Vertex destination, List<Vertex> map) {
        List<Route> bestRoutes = calculateShortestPathToEachVertex(source, destination, map);
        List<Route> a = bestRoutes.stream()
                .filter(route -> route.getDestinationName().equalsIgnoreCase(destination.getName())
                        && route.getOriginName().equalsIgnoreCase(source.getName()))
                .toList();
        Route bestRoute = bestRoutes.stream()
                .filter(route -> route.getDestinationName().equalsIgnoreCase(destination.getName())
                        && route.getOriginName().equalsIgnoreCase(source.getName()))
                .findFirst().orElseThrow();

        return bestRoute;
    }

    @Override
    public abstract Route calculateBestRoute(Vertex origin, Vertex destination, GraphStructure map);

    protected String calculateTotalCost(Long costForTransportation) {

        Double totalCostForTransportation = costForTransportation.doubleValue() / 100;

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

                        cents += transportUsedOnPath.costPerKmInCents() * path.getDistance();
                        cents += transportUsedOnPath.costPerStopInCents();

                        distance += path.getDistance();

                        minutes += transportUsedOnPath.minutesStoppedAtEachPoint();
                        minutes += 60 * path.getDistance() / transportUsedOnPath.transportSpeedInKmPerHour();

                        String pathing = ": (%s) from %s to %s for %s kilometers :".formatted(transportUsedOnPath,
                                path.getOrigin().getName(), path.getDestination().getName(), path.getDistance());

                                
                        return pathing;
                    }).collect(Collectors.joining(" -> "));

            Route routeToNode;
            if (distance.equals(0) && pathString.isBlank()) {
                routeToNode = new Route(type, source.getName(), node.getName(), "?", "?", "?", "Sorry, no available paths meet the conditions to reach the desired destination.");
            } else {
    
                estimatedCost = calculateTotalCost(cents);
                estimatedTime = calculateTimeToTravel(minutes);
    
                routeToNode = new Route(type, source.getName(), node.getName(), "%s kilometers".formatted(distance), estimatedTime, estimatedCost, pathString);

            }

            paths.add(routeToNode);
        });
        return paths;
    }

    protected void evaluatePathWeight(Vertex childVertex, Vertex parentVertex) {
        Integer newWeight = parentVertex.getWeight() + parentVertex.getPathToChild(childVertex).getDistance();
        if (newWeight < childVertex.getWeight()) {
            childVertex.setWeight(newWeight);
            childVertex.setPathsInReachOrder(Stream
                    .concat(parentVertex.getPathsInReachOrder().stream(),
                            Stream.of(parentVertex.getPathToChild(childVertex)))
                    .toList());
        }
    }

    protected List<Route> calculateShortestPathToEachVertex(Vertex origin, Vertex destination, List<Vertex> map) {

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
                
                if (p.getDestination().equals(destination)) {
                    doThing(p);
                }
                if (!settledNodes.contains(p.getDestination()) && p.getTransports().contains(type)){
                    evaluatePathWeight(p.getDestination(), current);
                    unsettledNodes.add(p.getDestination());
                    settledPaths.add(p);
                }

            }

            settledNodes.add(current);
        }

        settledPaths.forEach((path) -> {
            System.out.println(path.getOrigin().getName() + " -> " + path.getDestination().getName() + ":"
                    + path.getDistance().toString());
        });


        return returnOptimalRoutesOnTheMap(source, map);
    }

    private void doThing(Path pathing) {

            Vertex node = pathing.getDestination();

            String pathString = node.getPathsInReachOrder().stream().map((path) -> {

                distance = 0;
                minutes = 0L;
                cents = 0L;
                estimatedCost = "";
                estimatedTime = "";

                EnumTransport transportUsedOnPath = EnumTransport.FOOT;
                if (path.getTransports().contains(type)) {
                    transportUsedOnPath = type;
                }

                cents += transportUsedOnPath.costPerKmInCents() * path.getDistance();
                cents += transportUsedOnPath.costPerStopInCents();

                distance += path.getDistance();

                minutes += transportUsedOnPath.minutesStoppedAtEachPoint();
                minutes += 60 * path.getDistance() / transportUsedOnPath.transportSpeedInKmPerHour();

                String pathStringy = ": (%s) from %s to %s for %s kilometers :".formatted(transportUsedOnPath,
                        path.getOrigin().getName(), path.getDestination().getName(), path.getDistance());
                return pathStringy;
            }).collect(Collectors.joining(" -> "));
        
            System.out.println(pathString);
            
    }

}


