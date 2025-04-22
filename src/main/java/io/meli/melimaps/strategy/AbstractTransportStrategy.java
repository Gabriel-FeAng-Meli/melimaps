package io.meli.melimaps.strategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import io.meli.melimaps.interfaces.TransportStrategy;
import io.meli.melimaps.model.User;
import io.meli.melimaps.model.Vertex;

public abstract class AbstractTransportStrategy implements TransportStrategy {

    public abstract void defineBestRouteConditionsForUserPreference(User user, List<Vertex> graph);
    public abstract List<Vertex> calculateBestRoute();

    public final void build() {
        defineBestRouteConditionsForUserPreference(null, null);
        calculateBestRoute();
    }


    public static List<String> writePaths(List<Vertex> nodes) {

        List<String> paths = new ArrayList<>();
        nodes.forEach(node -> {
            String path = node.getShortestPathToEachVertex().stream().map(Vertex::getName).collect(Collectors.joining(" -> "));
            if (!path.isBlank()) {
                paths.add("%s -> %s : %s kilometers".formatted(path, node.getName(), node.getWeight()));
            }
        });
        return paths;
    }

    public static String getShortestPathBetween(Vertex source, Vertex destination, List<Vertex> map) {
        List<String> allPaths = calculateShortestPathToEachNode(source, map);

        String result = allPaths.stream().filter(s -> s.startsWith(source.getName()) && s.contains("-> %s :".formatted(destination.getName()))).findFirst().orElseThrow(() -> new RuntimeException("No path available to reach desired destination starting from inputed origin"));

        return result;

    }

    private static void evaluateDistanceAndPath(Vertex adjacentNode, Integer edgeWeight, Vertex sourceNode) {
        Integer newDistance = sourceNode.getWeight() + edgeWeight;
        if (newDistance < adjacentNode.getWeight()) {
            adjacentNode.setWeight(newDistance);
            adjacentNode.setShortestPathToEachVertex(Stream.concat(sourceNode.getShortestPathToEachVertex().stream(), Stream.of(sourceNode)).toList());
        }
    }

    public static List<String> calculateShortestPathToEachNode(Vertex source, List<Vertex> map) {
        source.setWeight(0);

        Set<Vertex> settledNodes = new HashSet<>();
        Queue<Vertex> unsettledNodes = new PriorityQueue<>(Collections.singleton(source));

        while (!unsettledNodes.isEmpty()) {
            Vertex current = unsettledNodes.poll();
            current.getAdjacentNodesAndDistance().entrySet().stream().filter(
                entry -> !settledNodes.contains(entry.getKey())).forEach(entry -> {
                    evaluateDistanceAndPath(entry.getKey(), entry.getValue(), current);
                    unsettledNodes.add(entry.getKey());
                });

            settledNodes.add(current);
        }

        return writePaths(map);
    }
}
