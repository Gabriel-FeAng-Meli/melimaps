package io.meli.melimaps.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.meli.melimaps.model.Graph;

public interface GraphRepository extends JpaRepository<Graph, Integer> {
    
}
