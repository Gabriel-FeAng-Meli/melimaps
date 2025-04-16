package io.meli.melimaps.interfaces;

import java.util.List;

import io.meli.melimaps.model.Vertex;

public interface TransportStrategy {

    List<Vertex> calculateBestRoute();
}
