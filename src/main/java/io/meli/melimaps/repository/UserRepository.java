package io.meli.melimaps.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import io.meli.melimaps.model.User;


@Repository
@EnableJpaRepositories
public interface UserRepository extends JpaRepository<User, Integer>{
    
}
