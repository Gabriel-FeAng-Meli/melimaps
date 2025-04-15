package io.meli.melimaps.domain.vertex;

import java.util.List;
import java.util.Map;

import io.meli.melimaps.enums.EnumTransport;

import java.util.ArrayList;

public class Vertex{
    private List<Vertex> children = new ArrayList<Vertex>();
    private Vertex parent;
    private VertexData data;

    public static Vertex buildRootVertex() {
        VertexData rootData = new VertexData(
            0,
            Map.of(
               EnumTransport.BIKE, 0,
               EnumTransport.BUS, 0,
               EnumTransport.FOOT, 0,
               EnumTransport.RAILWAY, 0
            ),
            true,
            true
        );

        return new Vertex(rootData, null);
    }

    public Vertex(VertexData data, Vertex parent) {
        this.data = data;
        this.parent = parent;
    }

    private void setParent(Vertex parent) {
        this.parent = parent;
    }

    public List<Vertex> getChildren() {
        return children;
    }

    public void addNewChild(VertexData data) {
        Vertex child = new Vertex(data, this);
        this.children.add(child);
    }

    public void addChild(Vertex child) {
        child.setParent(this);
        this.children.add(child);
    }
    
    public void addChildren(List<Vertex> children) {
        this.children.addAll(children);
    }

    public VertexData getData() {
        return this.data;
    }

    public void setData(VertexData data) {
        this.data = data;
    }

    public boolean isRoot() {
        return (this.parent == null);
    }

    public boolean isDestination() {
        return this.children.size() == 0;
    }

    public void removeParent() {
        this.parent = null;
    }
}
