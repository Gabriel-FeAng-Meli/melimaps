package io.meli.melimaps.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Vertex implements Comparable<Vertex>{
    
    private final String name;
    private Integer weight;
    private List<Vertex> shortestPathToEachVertex;
    private Map<Vertex, Integer> adjacentNodesAndDistance;

    public Vertex(String name) {
        this.name = name;
        this.weight = Integer.MAX_VALUE;
        this.shortestPathToEachVertex = new LinkedList<>();
        this.adjacentNodesAndDistance = new HashMap<>();
    }

    public void addAdjacentNode(Vertex node, int distance) {
        adjacentNodesAndDistance.put(node, distance);
    }

    @Override
    public int compareTo(Vertex node) {
        return Integer.compare(this.weight, node.getWeight());
    }

    public String getName() {
        return name;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer distance) {
        this.weight = distance;
    }

    public List<Vertex> getShortestPathToEachVertex() {
        return shortestPathToEachVertex;
    }

    public void setShortestPathToEachVertex(List<Vertex> shortestPath) {
        this.shortestPathToEachVertex = shortestPath;
    }

    public Map<Vertex, Integer> getAdjacentNodesAndDistance() {
        return adjacentNodesAndDistance;
    }

    public void setAdjacentNodesAndDistance(Map<Vertex, Integer> adjacentNodes) {
        this.adjacentNodesAndDistance = adjacentNodes;
    }

}
