package io.meli.melimaps.model;

import java.util.List;

import java.util.ArrayList;

public class Vertex{

    private String name;
    private boolean visited = false;
    private int weight = Integer.MAX_VALUE;
    private Vertex parent;
    private List<Path> paths = new ArrayList<Path>();
    private List<Vertex> children = new ArrayList<Vertex>();
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    
    public boolean alreadyVisited() {
        return visited;
    }
    public void visit() {
        this.visited = true;
    }

    public Integer getWeight() {
        return weight;
    }
    public void setDistance(Integer weight) {
        this.weight = weight;
    }

    public Vertex getParent() {
        return parent;
    }
    public void setParent(Vertex parent) {
        parent.addChildren(this);
        this.parent = parent;
    }

    public List<Path> getPaths() {
        return paths;
    }
    public void addPaths(List<Path> paths) {
        this.paths.addAll(paths);
    }
    public void addPaths(Path path) {
        this.paths.add(path);
    }

    public List<Vertex> getChildren() {
        return children;
    }

    public void addChildren(List<Vertex> children) {
        this.children.addAll(children);
    }
    public void addChildren(Vertex child) {
        this.children.add(child);
    }

    public int compareTo(Vertex otherVertex) {
        if (this.getWeight() < otherVertex.getWeight()) {
            return -1;
        } else if (this.getWeight() == otherVertex.getWeight()) {
            return 0;
        }
        return 1;
    }

}
