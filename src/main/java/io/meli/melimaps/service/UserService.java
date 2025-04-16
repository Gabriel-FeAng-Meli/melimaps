package io.meli.melimaps.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import io.meli.melimaps.dto.UserDTO;
import io.meli.melimaps.model.User;
import io.meli.melimaps.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public List<UserDTO> getAllUsers() {

        List<User> users = userRepository.findAll();
        
        List<UserDTO> dtos = users
            .stream()
            .map((user) -> {return user.toDTO();})
            .collect(Collectors.toList());

        return dtos;
    }


    public UserDTO getUserById(Integer id) {

        User user = userRepository.findById(id).orElseThrow(() -> 
            new ResponseStatusException(HttpStatus.NOT_FOUND)
        );
        
        return user.toDTO();
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }
    
}
