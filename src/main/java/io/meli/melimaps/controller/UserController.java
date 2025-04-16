package io.meli.melimaps.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.meli.melimaps.dto.UserDTO;
import io.meli.melimaps.model.User;
import io.meli.melimaps.service.UserService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;


@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;
    
    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@RequestParam Integer id) {
        UserDTO user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }


    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user) {
        
        return ResponseEntity.ok(userService.createUser(user));
    }
    
}
