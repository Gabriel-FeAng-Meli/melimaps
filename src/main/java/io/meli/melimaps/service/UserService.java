package io.meli.melimaps.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.meli.melimaps.model.User;
import io.meli.melimaps.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User getUserById(Integer id) {

        User user = userRepository.findById(id).get();
        
        return user;
    }

    public User createUser(User user) {
        userRepository.save(user);
        return user;
    }
    
}
