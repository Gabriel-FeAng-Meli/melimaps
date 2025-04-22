package io.meli.melimaps.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.meli.melimaps.factories.TransportStrategyFactory;
import io.meli.melimaps.model.Graph;
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
        // transportStategyFactory picks preferences and instantiates the right one to modify the weight on each road;
        transportStrategyFactory.instantiateRightStrategy(preferences);



        Vertex origin = graph.findPlaceByName(originName);
        Vertex destination = graph.findPlaceByName(destinationName);

        return Map.of("Best path considering user prefferences: ",Dijkstra.getShortestPathBetween(origin, destination, graph.getVertices()));
    }

    
}
