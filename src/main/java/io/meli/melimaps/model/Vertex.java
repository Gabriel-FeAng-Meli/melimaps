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
    private List<Path> paths;
    private final Map<Vertex, Integer> childVerticesAndDistance;

    public Vertex(String name) {
        this.name = name;
        this.weight = Integer.MAX_VALUE;
        this.weightedVerticesInReachOrder = new LinkedList<>();
        this.paths = new LinkedList<>();
        this.childVerticesAndDistance = new HashMap<>();
    }

    public void addChildVertex(Vertex vertex, int distance, EnumTransport transport) {
        childVerticesAndDistance.put(vertex, distance);
        Path path = new Path(transport, vertex, distance);
        paths.add(path);
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

    public List<Path> getPaths() {
        return paths;
    }

    public void setWeightedVerticesInReachOrder(List<Vertex> list) {
        this.weightedVerticesInReachOrder = list;
    }

    public void setPaths(List<Path> paths) {
        this.paths = paths;
    }

}
