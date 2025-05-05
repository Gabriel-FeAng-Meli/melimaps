package io.meli.melimaps.strategy;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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


    protected Route getShortestPathBetween(Vertex source, Vertex destination, List<Vertex> map) {
        Vertex weightedSource = TransportStrategy.calculateShortestPathToEachVertex(source, destination, map, type, 1,0);
        List<Route> bestRoutes = returnOptimalRoutesOnTheMap(weightedSource, map);
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
                routeToNode = new Route(type.name(), source.getName(), node.getName(), "?", "?", "?", "Sorry, no available paths meet the conditions to reach the desired destination.");
            } else {
    
                estimatedCost = calculateTotalCost(cents);
                estimatedTime = calculateTimeToTravel(minutes);
    
                routeToNode = new Route(type.name(), source.getName(), node.getName(), "%s kilometers".formatted(distance), estimatedTime, estimatedCost, pathString);

            }

            paths.add(routeToNode);
        });
        return paths;
    }


}


