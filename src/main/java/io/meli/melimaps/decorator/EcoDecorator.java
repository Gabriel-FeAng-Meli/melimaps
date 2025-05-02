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

public class EcoDecorator extends Decorator {

    public EcoDecorator(TransportStrategy strategy) {
        super(strategy);
        this.priority = EnumPreference.ECO;
    }

    @Override
    public Route calculateBestRoute(Vertex origin, Vertex destination, GraphStructure map) {
        return getOptimalPathBetween(origin, destination, map.getVertices());
    }

    @Override
    public Vertex calculateMostOptimalPathToEachVertex(Vertex source) {
        source.setWeight(0);
        Set<Vertex> settledNodes = new HashSet<>();
        Queue<Vertex> unsettledNodes = new PriorityQueue<>(Set.of(source));

        while (!unsettledNodes.isEmpty()) {
            Vertex current = unsettledNodes.poll();
            current.getPathToChildren().entrySet().stream().filter(
                    entry -> !settledNodes.contains(entry.getKey())).forEach(entry -> {
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

                        p.setWeight(p.getDistance() * factorOfTransportChoice * transportForPath.polutionScore() + pathUnavailableForCarFactor);

                        TransportStrategy.evaluatePathWeight(v, current);
                        unsettledNodes.add(v);
                    });

            settledNodes.add(current);
        }

        return source;
    }

    
}
