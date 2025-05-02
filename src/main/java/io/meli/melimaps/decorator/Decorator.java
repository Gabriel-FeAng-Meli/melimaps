package io.meli.melimaps.decorator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import io.meli.melimaps.enums.EnumPreference;
import io.meli.melimaps.enums.EnumTransport;
import io.meli.melimaps.interfaces.TransportStrategy;
import io.meli.melimaps.model.Route;
import io.meli.melimaps.model.Vertex;

public abstract class Decorator implements TransportStrategy { // Bridge e Decorator

    protected TransportStrategy strategy;
    protected EnumPreference priority;
    protected EnumTransport transport;

    protected Decorator(TransportStrategy strategy) {
        this.strategy = strategy;
        this.transport = strategy.getStrategyType();
    }
    
    @Override
    public EnumTransport getStrategyType() {
        return this.strategy.getStrategyType();
    };
    
    protected Route getOptimalPathBetween(Vertex source, Vertex destination, List<Vertex> map) {
        Vertex weightedSource = calculateMostOptimalPathToEachVertex(source);
        List<Route> routesFromVertex = returnOptimalRoutesOnTheMap(weightedSource, map);
        routesFromVertex = routesFromVertex.stream()
            .filter(route -> route.getDestinationName().equalsIgnoreCase(destination.getName()) && route.getOriginName().equalsIgnoreCase(source.getName())).toList();
        if (routesFromVertex.size() != 1) {
            System.out.println("MAIS D UM");
        }
        
        Route bestRoute = routesFromVertex.get(0);

        return bestRoute;
    }


    protected Map<String, Route> applyPreferenceToRoute(Route route, EnumPreference preference) {
        Map<String, Route> result = new HashMap<>();
        result.put("Best route considering %s: ".formatted(preference.name()), route);
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

        String totalCost = "R$%.02f".formatted(cost.doubleValue()/100);
        String totalTime = "%s minutes".formatted(minutes);
        String totalDistance = "%s kilometers".formatted(kilometers);

        Route routeToNode = new Route(0, transport.name(), source.getName(), node.getName(), totalDistance, totalTime, totalCost, pathString, priority.name());

        paths.add(routeToNode);

        cost = 0;
        kilometers = 0;
        minutes = 0;
        });
        return paths;
    }

    public abstract Vertex calculateMostOptimalPathToEachVertex(Vertex v);

}
