package io.meli.melimaps.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.meli.melimaps.model.Route;


@Repository
public interface RouteRepository extends JpaRepository<Route, Integer>{
    
    boolean existsByRequestProperties(String requestProperties);
    List<Route> findByRequestProperties(String requestProperties);

}
