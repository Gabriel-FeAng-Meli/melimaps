package io.meli.melimaps.decorator;

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

import io.meli.melimaps.enums.EnumDecoration;
import io.meli.melimaps.enums.EnumTransport;
import io.meli.melimaps.interfaces.Graph;
import io.meli.melimaps.interfaces.TransportStrategy;
import io.meli.melimaps.model.Path;
import io.meli.melimaps.model.Route;
import io.meli.melimaps.model.Vertex;

public abstract class BaseDecorator implements TransportStrategy {

    protected EnumTransport transport;
    protected EnumDecoration priority;
    protected Integer factorPerKm;
    protected Integer factorPerStop;
    protected Vertex destination;

    @Override
    public EnumTransport getStrategyType() {
        return this.transport;
    };
    
    public static List<Route> calculateRoutesForEachPreference(EnumTransport transport, List<EnumDecoration> preferences, Vertex origin, Vertex destination, Graph map) {

        List<Route> routes = new ArrayList<>();

        preferences.forEach(preference -> {
            BaseDecorator decorator = preference.chooseDecorator(transport);

            if (decorator != null) {
                
                routes.add(decorator.calculateBestRoute(origin, destination, map));
            }
        });

        return routes;

    }

    protected void evaluatePathWeight(Vertex childVertex, Vertex parentVertex) {
        Integer newWeight = parentVertex.getWeight() + parentVertex.getPathToChild(childVertex).getWeight();
        if (newWeight < childVertex.getWeight()) {
            childVertex.setWeight(newWeight);
            childVertex.setPathsInReachOrder(Stream
                    .concat(parentVertex.getPathsInReachOrder().stream(), Stream.of(parentVertex.getPathToChild(childVertex))).toList());
        }
    }


    public List<Route> calculateMostOptimalPathToEachVertex(Vertex source, List<Vertex> map) {
        source.setWeight(0);

        Set<Vertex> settledPaths = new HashSet<>();
        Queue<Vertex> unsettledPaths = new PriorityQueue<>(Set.of(source));

        while (!unsettledPaths.isEmpty()) {
            Vertex current = unsettledPaths.poll();
            
            current.getPathToChildren().entrySet().stream().filter(
                    entry -> !settledPaths.contains(entry.getKey())).forEach(entry -> {
                        Vertex v = entry.getKey();
                        Path p = entry.getValue();

                        Integer weight = p.getDistance() * factorPerKm + factorPerStop;

                        current.setWeight(weight);

                        evaluatePathWeight(v, current);
                        
                        unsettledPaths.add(v);
                    });
            settledPaths.add(current);
        }

        return returnOptimalRoutesOnTheMap(source, map);
    }

    protected Route getOptimalPathBetween(Vertex source, Vertex destination, List<Vertex> map) {
        this.destination = destination;
        List<Route> routesBetweenVertices = calculateMostOptimalPathToEachVertex(source, map);
        routesBetweenVertices = routesBetweenVertices.stream()
            .filter(route -> route.getDestinationName().equalsIgnoreCase(destination.getName()) && route.getOriginName().equalsIgnoreCase(source.getName())).toList();
        
        Route bestRoute = routesBetweenVertices.get(0);

        return bestRoute;
    }

    protected Map<String, Route> applyPreferenceToRoute(Route route, EnumDecoration preference) {
        Map<String, Route> result = new HashMap<>();
        result.put("Best route considering %s : ".formatted(preference.name()), route);
        return result;
    }

    Integer cost = 0;
    Integer kilometers = 0;
    Integer minutes = 0;
    
    protected List<Route> returnOptimalRoutesOnTheMap(Vertex source, List<Vertex> weightedMap) {

        List<Route> paths = new ArrayList<>();
        weightedMap.forEach(node -> {
            String pathString = node.getPathsInReachOrder()
                .stream().map((path) -> {
                    EnumTransport transportUsedOnPath = EnumTransport.FOOT;
                    if (path.getTransports().contains(transport)) {
                        transportUsedOnPath = transport;
                    }

                    String pathing = ": (%s) from %s to %s for %s kilometers :".formatted(transportUsedOnPath, path.getOrigin().getName(), path.getDestination().getName(), path.getDistance());

                    cost += transportUsedOnPath.costPerKmInCents() * path.getDistance();
                    cost += transportUsedOnPath.costPerStopInCents();

                    kilometers += path.getDistance();

                    minutes += transportUsedOnPath.minutesStoppedAtEachPoint();
                    minutes += 60 * path.getDistance() / transportUsedOnPath.transportSpeedInKmPerHour();

                    return pathing;
                }).collect(Collectors.joining(" -> "));

            Route routeToNode = new Route.Builder()
                .transport(transport)
                .originName(source)
                .destinationName(destination)
                .distance(kilometers)
                .totalCost(cost.longValue())
                .timeToReach(minutes.longValue())
                .path(pathString)
                .build();

        paths.add(routeToNode);

        cost = 0;
        kilometers = 0;
        minutes = 0;
        });
        return paths;
    }

}
