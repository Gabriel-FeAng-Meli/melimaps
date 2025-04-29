package io.meli.melimaps.decorator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import io.meli.melimaps.enums.EnumPreference;
import io.meli.melimaps.enums.EnumTransport;
import io.meli.melimaps.interfaces.GraphStructure;
import io.meli.melimaps.interfaces.TransportStrategy;
import io.meli.melimaps.model.Route;
import io.meli.melimaps.model.Vertex;

public abstract class BaseDecorator implements TransportStrategy {

    protected EnumTransport transport;
    protected EnumPreference priority;

    @Override
    public EnumTransport getStrategyType() {
        return this.transport;
    };

    public static List<Route> calculateRoutesForEachPreference(EnumTransport transport, List<EnumPreference> preferences, Vertex origin, Vertex destination, GraphStructure map) {

        List<Route> routes = new ArrayList<>();

        preferences.forEach(preference -> {
            BaseDecorator decorator = preference.chooseDecorator(transport);

            if (decorator != null) {
                
                routes.add(decorator.calculateBestRoute(origin, destination, map));
            }
        });

        return routes;

    }

    protected Route getOptimalPathBetween(Vertex source, Vertex destination, List<Vertex> map) {
        List<Route> routesBetweenVertices = calculateMostOptimalPathToEachVertex(source, map);
        routesBetweenVertices = routesBetweenVertices.stream()
            .filter(route -> route.getDestinationName().equalsIgnoreCase(destination.getName()) && route.getOriginName().equalsIgnoreCase(source.getName())).toList();
        
        Route bestRoute = routesBetweenVertices.get(0);

        return bestRoute;
    }


    protected Map<String, Route> applyPreferenceToRoute(Route route, EnumPreference preference) {
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

                    cost += transport.costPerKmInCents() * path.getDistance();
                    cost += transport.costPerStopInCents();

                    kilometers += path.getDistance();

                    minutes += transport.minutesStoppedAtEachPoint();
                    minutes += 60 * path.getDistance() / transport.transportSpeedInKmPerHour();

                    return pathing;
                }).collect(Collectors.joining(" -> "));

        String totalCost = "%s cents".formatted(cost);
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

    protected void evaluatePathWeight(Vertex childVertex, Vertex parentVertex) {
        Integer newWeight = parentVertex.getWeight() + parentVertex.getPathToChild(childVertex).getWeight();
        if (newWeight < childVertex.getWeight()) {
            childVertex.setWeight(newWeight);
            childVertex.setPathsInReachOrder(Stream
                    .concat(parentVertex.getPathsInReachOrder().stream(), Stream.of(parentVertex.getPathToChild(childVertex))).toList());
        }
    }

    protected abstract List<Route> calculateMostOptimalPathToEachVertex(Vertex v, List<Vertex> map);

}
