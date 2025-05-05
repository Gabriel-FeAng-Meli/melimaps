package io.meli.melimaps.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.meli.melimaps.enums.EnumPreference;
import io.meli.melimaps.model.RequestOptimalRoute;
import io.meli.melimaps.service.RouteService;

@RestController
@RequestMapping("/route")
public class RouteController {

    @Autowired
    private RouteService routeService;

    @PostMapping
    public ResponseEntity<?> obtainBestRoute(@RequestBody RequestOptimalRoute req, @RequestParam(required=false, defaultValue="DISTANCE") EnumPreference... preferences) throws JsonProcessingException {
        
        Set<EnumPreference> preferenceSet = Set.of(preferences);
        var result = routeService.generateOptimalRouteForUser(req.userId(), req.originName(), req.destinationName(), preferenceSet);

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }
    
}
