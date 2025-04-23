package io.meli.melimaps.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Vertex implements Comparable<Vertex>{
    
    private final String name;
    private Integer weight;
    private List<Vertex> weightedVerticesInReachOrder;
    private Map<Vertex, Integer> childVerticesAndDistance;

    public Vertex(String name) {
        this.name = name;
        this.weight = Integer.MAX_VALUE;
        this.weightedVerticesInReachOrder = new LinkedList<>();
        this.childVerticesAndDistance = new HashMap<>();
    }

    public void addAdjacentVertex(Vertex vertex, int distance) {
        childVerticesAndDistance.put(vertex, distance);
    }

    @Override
    public int compareTo(Vertex vertex) {
        return Integer.compare(this.weight, vertex.getWeight());
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

    public List<Vertex> getWeightedVerticesInReachOrder() {
        return weightedVerticesInReachOrder;
    }

    public void setWeightedVerticesInReachOrder(List<Vertex> shortestPath) {
        this.weightedVerticesInReachOrder = shortestPath;
    }

    public Map<Vertex, Integer> getChildVerticesAndDistance() {
        return childVerticesAndDistance;
    }

    public void setChildVerticesAndDistance(Map<Vertex, Integer> adjacentVertices) {
        this.childVerticesAndDistance = adjacentVertices;
    }

}
