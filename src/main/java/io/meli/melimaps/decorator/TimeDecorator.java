package io.meli.melimaps.decorator;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import io.meli.melimaps.enums.EnumPreference;
import io.meli.melimaps.enums.EnumTransport;
import io.meli.melimaps.interfaces.GraphStructure;
import io.meli.melimaps.interfaces.TransportStrategy;
import io.meli.melimaps.model.Path;
import io.meli.melimaps.model.Route;
import io.meli.melimaps.model.Vertex;

public class TimeDecorator extends Decorator {

    public TimeDecorator(TransportStrategy strategy) {
        super(strategy);
        this.priority = EnumPreference.TIME;
    }

    @Override
    public Route calculateBestRoute(Vertex origin, Vertex destination, GraphStructure map) {
        return getOptimalPathBetween(origin, destination, map.getVertices());
    }

    @Override
    public Vertex calculateMostOptimalPathToEachVertex(Vertex source) {
        source.setWeight(0);

        Set<Path> settledPaths = new HashSet<>();
        Set<Path> a = new HashSet<>();
        source.getPathToChildren().forEach((v, p) -> {
            a.add(p);
        });
        Queue<Path> unsettledPaths = new PriorityQueue<>(a);

        while (!unsettledPaths.isEmpty()) {
            Path current = unsettledPaths.poll();
            current.getOrigin().getPathToChildren().entrySet().stream().filter(
                    entry -> entry.getValue().getTransports().contains(transport) || entry.getValue().getTransports().contains(EnumTransport.FOOT) && !settledPaths.contains(entry.getValue())).forEach(entry -> {
                        Vertex v = entry.getKey();
                        Path p = entry.getValue();

                        EnumTransport transportForPath;
                        Integer pathUnavailableForCarFactor = 0;

                        if (p.getTransports().contains(transport)) {
                            transportForPath = transport;
                        } else if(!p.getTransports().contains(transport) && transport.equals(EnumTransport.CAR)) {
                            transportForPath = EnumTransport.CAR;
                            pathUnavailableForCarFactor = 10000;
                        }
                        else {
                            transportForPath = EnumTransport.FOOT;
                        }

                        Integer factorOfTransportChoice = transport.factorTransportPreference(p.getTransports());
                        Double factorOfPreferenceSpeed = p.getDistance().doubleValue() / transportForPath.transportSpeedInKmPerHour().doubleValue();
                        Integer factorOfTime = factorOfPreferenceSpeed.intValue() + transportForPath.minutesStoppedAtEachPoint();

                        p.setWeight(p.getDistance() * factorOfTransportChoice * factorOfTime + pathUnavailableForCarFactor);

                        TransportStrategy.evaluatePathWeight(v, current.getOrigin());
                        p.setWeight(v.getWeight());

                        settledPaths.add(p);
                    });

        }

        return source;
    }

    
}
