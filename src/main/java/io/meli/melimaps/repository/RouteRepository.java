package io.meli.melimaps.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import io.meli.melimaps.model.CompleteRoute;




@Repository
@EnableJpaRepositories
public interface RouteRepository extends JpaRepository<CompleteRoute, Integer>{

    Boolean existsByTransportAndOriginNameAndDestinationNameAllIgnoreCase(String transport, String originName, String destinationName);

    CompleteRoute findByTransportAndOriginNameAndDestinationNameAllIgnoreCase(String transport, String originName, String destinationName);
    
}
