package io.meli.melimaps.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import io.meli.melimaps.model.Route;




@Repository
@EnableJpaRepositories
public interface RouteRepository extends JpaRepository<Route, Integer>{

    Boolean existsByTransportAndOriginNameAndDestinationName(String transport, String originName, String destinationName);

    Optional<Route> findByTransportIgnoreCaseAndOriginNameIgnoreCaseAndDestinationNameIgnoreCase(String transport, String originName, String destinationName);
    
}
