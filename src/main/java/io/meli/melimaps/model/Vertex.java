package io.meli.melimaps.model;

import java.util.ArrayList;
import java.util.List;

import io.meli.melimaps.enums.EnumTransport;

public class Vertex{

    private String name;
    private boolean visited;
    private int weight = Integer.MAX_VALUE;
    private final List<Vertex> parents = new ArrayList<>();
    private final List<Path> paths = new ArrayList<>();
    private final List<Vertex> children = new ArrayList<>();
    private Vertex previous = null;

    public Vertex getPrevious() {
        return previous;
    }

    public void setPrevious(Vertex previous) {
        this.previous = previous;
    }

    public Vertex() {}

    public static Vertex createRootVertex(String name) {
        Vertex root = new Vertex(name);
        root.visited = false;
        root.weight = Integer.MAX_VALUE;
        return root;
    };

    public static Vertex buildRoadTowardsNewChild(Vertex parent, String childName, Integer distanceFromParent, EnumTransport... availableTransportation) {
        Vertex child = new Vertex(childName);
        child.visited = false;
        child.parents.add(parent);

        Path road = Path.buildRoadConnecting(distanceFromParent, child, parent, availableTransportation);

        parent.paths.add(road);
        parent.children.add(child);

        return child;
    }

    public static void buildRoadTowardsExistingChild(Vertex parent, Vertex child, Integer distanceFromParent, EnumTransport... availableTransportation) {
        child.visited = false;
        child.parents.add(parent);

        Path road = Path.buildRoadConnecting(distanceFromParent, child, parent, availableTransportation);

        parent.paths.add(road);
        parent.children.add(child);
    }

    private Vertex(String name) {
        this.name = name;
    }
    
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
    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public List<Vertex> getParents() {
        return parents;
    }

    public void addParent(Vertex parent) {
        this.parents.add(parent);
    }

    public List<Path> getPaths() {
        return paths;
    }

    public List<Vertex> getChildren() {
        return children;
    }

}
