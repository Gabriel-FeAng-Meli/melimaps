package io.meli.melimaps.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.meli.melimaps.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    
}