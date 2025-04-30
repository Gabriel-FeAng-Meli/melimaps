package io.meli.melimaps.strategy;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import io.meli.melimaps.enums.EnumTransport;
import io.meli.melimaps.interfaces.Graph;
import io.meli.melimaps.interfaces.TransportStrategy;
import io.meli.melimaps.model.Route;
import io.meli.melimaps.model.Vertex;

public abstract class AbstractTransportStrategy implements TransportStrategy {

    protected EnumTransport type;
    protected Vertex destination;
    private Long minutes;
    private Long cents;
    private Integer distance;

    @Override
    public EnumTransport getStrategyType() {
        return this.type;
    }

    public void setTransport(EnumTransport transport) {
        this.type = transport;
    }

    protected Route getShortestPathBetween(Vertex source, Vertex destination, List<Vertex> map) {
        Route bestRoute = calculateShortestPathToEachVertex(source, map).stream()
                .filter(route -> route.getDestinationName().equalsIgnoreCase(destination.getName()) && route.getOriginName().equalsIgnoreCase(source.getName())).findFirst().orElseThrow();

        return bestRoute;
    }

    @Override
    public abstract Route calculateBestRoute(Vertex origin, Vertex destination, Graph map);

    protected List<Route> returnOptimalRoutesOnTheMap(Vertex source, List<Vertex> weightedMap) {

        List<Route> paths = new ArrayList<>();
        weightedMap.forEach(node -> {

            distance = 0;
            minutes = 0L;
            cents = 0L;

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

                    String pathing = ": (%s) from %s to %s for %s kilometers :".formatted(transportUsedOnPath, path.getOrigin().getName(), path.getDestination().getName(), path.getDistance());
                    return pathing;
                }).collect(Collectors.joining(" -> "));
                
            Route routeToNode = new Route.Builder()
                .transport(type)
                .originName(source)
                .destinationName(destination)
                .distance(distance)
                .totalCost(cents)
                .timeToReach(minutes)
                .path(pathString)
                .build();

            paths.add(routeToNode);
        });
        return paths;
    }

    protected List<Route> calculateShortestPathToEachVertex(Vertex source, List<Vertex> map) {
        source.setWeight(0);

        Set<Vertex> settledNodes = new HashSet<>();
        Queue<Vertex> unsettledNodes = new PriorityQueue<>(Set.of(source));

        while (!unsettledNodes.isEmpty()) {
            Vertex current = unsettledNodes.poll();

            current.getPathToChildren().entrySet().stream().filter(
                    entry -> !settledNodes.contains(entry.getKey())).forEach(entry -> {

                            evaluatePathWeight(current, entry.getKey());
                            unsettledNodes.add(entry.getKey());
    
                    });

            settledNodes.add(current);
        }

        return returnOptimalRoutesOnTheMap(source, map);
    }

    protected void evaluatePathWeight(Vertex childVertex, Vertex parentVertex) {
        Integer newWeight = parentVertex.getWeight() + parentVertex.getPathToChild(childVertex).getWeight();
        if (newWeight < childVertex.getWeight()) {
            parentVertex.getPathToChild(childVertex).setWeight(newWeight);
            childVertex.setWeight(newWeight);
            childVertex.setPathsInReachOrder(Stream.concat(parentVertex.getPathsInReachOrder().stream(), Stream.of(parentVertex.getPathToChild(childVertex))).toList());
        }
    }

}
