package io.meli.melimaps.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import io.meli.melimaps.enums.EnumTransport;

public class Vertex implements Comparable<Vertex>{
    
    private final String name;
    private Integer weight;
    private List<Path> pathsInReachOrder;
    private Map<Vertex, Integer> childrenVertices;
    private Map<Vertex, Path> pathToChildren;

    public Vertex(String name) {
        this.name = name;
        this.weight = 1000;
        this.pathsInReachOrder = new LinkedList<>();
        this.childrenVertices = new HashMap<>();
        this.pathToChildren = new HashMap<>();
    }

    public void addChildVertex(Vertex vertex, int distance, EnumTransport... transports) {

        Path path = new Path(this, distance, vertex, transports);
        childrenVertices.put(vertex, distance);
        pathToChildren.put(vertex, path);
    }

    public void setPaths(Map<Vertex, Path> paths) {
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

    public Map<Vertex, Path> getPathToChildren() {
        return pathToChildren;
    }

    public Path getPathToChild(Vertex v) {
        Path pathToChild = getPathToChildren().get(v);
        return pathToChild;
    }

    public void removePath(Path path) {

        Map<Vertex, Path> paths = this.pathToChildren;

        paths = paths.entrySet().stream().filter(entry -> !entry.getValue().equals(path)).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        this.pathToChildren = paths;
    }

    public List<Path> getPathsInReachOrder() {
        return pathsInReachOrder;
    }

    public void setPathsInReachOrder(List<Path> pathsInReachOrder) {
        this.pathsInReachOrder = pathsInReachOrder;
    }

    public Map<Vertex, Integer> getChildrenVertices() {
        return childrenVertices;
    }

    public void setChildrenVertices(Map<Vertex, Integer> childrenVertices) {
        this.childrenVertices = childrenVertices;
    }

}
