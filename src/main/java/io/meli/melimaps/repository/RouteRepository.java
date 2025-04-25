package io.meli.melimaps.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import io.meli.melimaps.model.Route;




@Repository
@EnableJpaRepositories
public interface RouteRepository extends JpaRepository<Route, Integer>{

    Boolean existsByTransportAndOriginNameAndDestinationNameAndPathAllIgnoreCase(String transport, String originName, String destinationName);

    Route findByTransportAndOriginNameAndDestinationNameAndPathAllIgnoreCase(String transport, String originName, String destinationName);
    
}
