package io.meli.melimaps.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.meli.melimaps.model.Route;

public interface RouteRepository extends JpaRepository<Route, Integer> {
    
}
