package io.meli.melimaps.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import io.meli.melimaps.enums.EnumTransport;

public class Vertex implements Comparable<Vertex>{
    
    private final String name;
    private Integer weight;
    private List<Vertex> weightedVerticesInReachOrder;
    private final Map<Vertex, Path> pathToChildren;
    private final Map<Vertex, Integer> childVerticesAndDistance;

    public Vertex(String name) {
        this.name = name;
        this.weight = Integer.MAX_VALUE;
        this.weightedVerticesInReachOrder = new LinkedList<>();
        this.pathToChildren = new HashMap<>();
        this.childVerticesAndDistance = new HashMap<>();
    }

    public void addChildVertex(Vertex vertex, int distance, EnumTransport transport) {
        childVerticesAndDistance.put(vertex, distance);
        Path path = new Path(transport, distance);
        pathToChildren.put(vertex, path);
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

    public Map<Vertex, Integer> getChildVerticesAndDistance() {
        return childVerticesAndDistance;
    }

    public Map<Vertex, Path> getPathToChildren() {
        return pathToChildren;
    }

    public Path getPathToChild(Vertex v) {
        Path pathToChild = getPathToChildren().get(v);
        return pathToChild;
    }

    public void setWeightedVerticesInReachOrder(List<Vertex> list) {
        this.weightedVerticesInReachOrder = list;
    }

}
