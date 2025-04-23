package io.meli.melimaps.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.meli.melimaps.factories.TransportStrategyFactory;
import io.meli.melimaps.interfaces.TransportStrategy;
import io.meli.melimaps.model.Graph;
import io.meli.melimaps.model.Route;
import io.meli.melimaps.model.User;
import io.meli.melimaps.model.UserPreferences;
import io.meli.melimaps.model.Vertex;

@Service
public class RouteService {

    @Autowired
    private TransportStrategyFactory transportStrategyFactory;

    @Autowired
    private UserService userService;

    @Autowired
    private Graph graph;

    public Map<String, String> generateOptimalRouteForUser(Integer userId, String originName, String destinationName) {
        User user = userService.getUserById(userId);
        UserPreferences preferences = new UserPreferences(user.getTransport(), user.getEcologic(), user.getAccessibility(), false);

        TransportStrategy strategy = transportStrategyFactory.instantiateRightStrategy(preferences);

        Vertex origin = graph.findPlaceByName(originName);
        Vertex destination = graph.findPlaceByName(destinationName);

        Route route = strategy.calculateBestRoute(origin, destination);

        return Map.of("Best path considering user prefferences: ", route.getPath());
    }

    
}
