package io.meli.melimaps.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.meli.melimaps.factories.TransportStrategyFactory;
import io.meli.melimaps.interfaces.TransportStrategy;
import io.meli.melimaps.model.Graph;
import io.meli.melimaps.model.Route;
import io.meli.melimaps.model.User;
import io.meli.melimaps.model.UserPreferences;
import io.meli.melimaps.model.Vertex;
import io.meli.melimaps.repository.RouteRepository;
import io.swagger.v3.core.util.Json;

@Service
public class RouteService {

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private TransportStrategyFactory transportStrategyFactory;

    @Autowired
    private UserService userService;

    @Autowired
    private Graph graph;

    public String generateOptimalRouteForUser(Integer userId, String originName, String destinationName) throws JsonProcessingException {

        
        User user = userService.getUserById(userId);
        UserPreferences preferences = new UserPreferences(user.getTransport(), user.getEcologic(), user.getAccessibility(), false);
        
        TransportStrategy strategy = transportStrategyFactory.instantiateRightStrategy(preferences);
        Vertex origin = graph.findPlaceByName(originName);
        Vertex destination = graph.findPlaceByName(destinationName);
        
        Route route;

        if (routeRepository.existsByTransportAndOriginNameAndDestinationName(strategy.getStrategyType().name(), originName, destinationName)) {
            route = routeRepository.findByTransportAndOriginNameAndDestinationNameIgnoringCase(strategy.getStrategyType().name(), originName, destinationName).orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR));
        } else {
            route = strategy.calculateBestRoute(origin, destination, graph.getVertices());
            routeRepository.save(route);
        }

        String result = Json.mapper().writeValueAsString(route);

        return result;
    }

    
}
