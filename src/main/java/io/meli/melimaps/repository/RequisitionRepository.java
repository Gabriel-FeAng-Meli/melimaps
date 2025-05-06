package io.meli.melimaps.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.meli.melimaps.model.Requisition;
import io.meli.melimaps.model.Route;
import io.meli.melimaps.model.User;


@Repository
public interface RequisitionRepository extends JpaRepository<Requisition, Integer>{

    Requisition findByRouteAndUser(Route route, User user);

}
