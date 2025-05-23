package io.meli.melimaps.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.meli.melimaps.enums.EnumDecoration;
import io.meli.melimaps.model.RequestOptimalRoute;
import io.meli.melimaps.service.RouteService;

@RestController
@RequestMapping("/route")
public class RouteController {

    @Autowired
    private RouteService routeService;

    @PostMapping
    public ResponseEntity<?> obtainBestRoute(@RequestBody RequestOptimalRoute req, @RequestParam(required=false, defaultValue="TIME") EnumDecoration... decorations) throws JsonProcessingException {
        

        List<EnumDecoration> preferenceList = Arrays.asList(decorations);
        var result = routeService.generateOptimalRouteForUser(req.userId(), req.originName(), req.destinationName(), preferenceList);


        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }
    
}
