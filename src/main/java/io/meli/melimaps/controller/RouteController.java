package io.meli.melimaps.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.meli.melimaps.model.RequestOptimalRoute;
import io.meli.melimaps.service.RouteService;

@RestController
@RequestMapping("/route")
public class RouteController {

    @Autowired
    private RouteService routeService;

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody RequestOptimalRoute req) {

        var response = routeService.generateOptimalRouteForUser(req.userId(), req.originName(), req.destinationName());

        return new ResponseEntity<>(response.get(0).toString(),HttpStatus.CREATED);
    }
    
}
