package io.meli.melimaps.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import io.meli.melimaps.enums.EnumTransport;

public class Vertex implements Comparable<Vertex>{
    
    private final String name;
    private Integer weight;
    private List<Path> pathsInReachOrder;
    private Map<Path, Integer> pathToChildren;

    public Vertex(String name) {
        this.name = name;
        this.weight = Integer.MAX_VALUE;
        this.pathsInReachOrder = new LinkedList<>();
        this.pathToChildren = new HashMap<>();
    }

    public void addChildVertex(Vertex vertex, int distance, EnumTransport... transports) {
        Path path = new Path(this, distance, vertex, transports);
        pathToChildren.put(path, path.getWeight());
    }

    public void setPaths(Map<Path, Integer> paths) {
        this.pathToChildren = paths;
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

    public Map<Path, Integer> getPathToChildren() {
        return pathToChildren;
    }

    public Path getPathsToChild(Vertex v) {

        Path pathsToChild = this.getPathToChildren().entrySet().stream().filter(entry -> entry.getKey().getDestination().equals(v)).map(entry -> {
            return entry.getKey();
        }).findAny().get();
        return pathsToChild;
    }

    public void removePath(Path path) {

    }

    public List<Path> getPathsInReachOrder() {
        return pathsInReachOrder;
    }

    public void setPathsInReachOrder(List<Path> pathsInReachOrder) {
        this.pathsInReachOrder = pathsInReachOrder;
    }

}
