package io.meli.melimaps.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import io.meli.melimaps.model.User;
import io.meli.melimaps.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public List<User> getAllUsers() {

        List<User> users = userRepository.findAll();
        
        return users;
    }


    public User getUserById(Integer id) {

        User user = userRepository.findById(id).orElseThrow(() -> 
            new ResponseStatusException(HttpStatus.NOT_FOUND)
        );
        
        return user;
    }

    public User createUser(User user) {
        userRepository.save(user);
        return user;
    }
    
}
