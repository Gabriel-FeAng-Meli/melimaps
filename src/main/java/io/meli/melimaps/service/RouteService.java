package io.meli.melimaps.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.meli.melimaps.enums.EnumPreference;
import io.meli.melimaps.enums.EnumTransport;
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

    private final TransportStrategyFactory transportStrategyFactory = new TransportStrategyFactory();

    @Autowired
    private UserService userService;

    
    public String generateOptimalRouteForUser(Integer userId, String originName, String destinationName, List<EnumPreference> preferenceList) throws JsonProcessingException {
        
        EnumTransport transport;
        Vertex origin;
        Vertex destination;
        Graph graph = new Graph().build();
        
        User user = userService.getUserById(userId);
        UserPreferences transportPreferences = new UserPreferences(user.getTransport(), user.getEcologic(), user.getHurry(), user.getAccessibility(), user.getBudget());

        if (user.getTransport().isEmpty()) {
            user.setTransport("ANY");
        }

        TransportStrategy strategy = transportStrategyFactory.instantiateRightStrategy(EnumTransport.valueOf(user.getTransport()), transportPreferences);

        transport = strategy.getStrategyType();
        origin = graph.findPlaceByName(originName);
        destination = graph.findPlaceByName(destinationName);

        graph.getGraphWithVerticesAvailableForTransport(transport);

        Map<String, Route> recommendedRoutes = new HashMap<>();

        preferenceList.forEach((preference) -> {
            recommendedRoutes.put("Best route considering %s".formatted(preference.name()), preference.chooseDecorator(strategy).calculateBestRoute(origin, destination, graph));
        });

        
        
        String result = Json.mapper().writeValueAsString(recommendedRoutes);
        
        return result;
    }
    
    
}
