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

    private Route bestRoute;

    public String generateOptimalRouteForUser(Integer userId, String originName, String destinationName, List<EnumPreference> preferenceList) throws JsonProcessingException {
        
        Vertex origin;
        Vertex destination;
        Graph graph = new Graph().build();
        
        User user = userService.getUserById(userId);
        UserPreferences transportPreferences = new UserPreferences(user.getTransport(), user.getEcologic(), user.getHurry(), user.getAccessibility(), user.getBudget());

        if (user.getTransport().isEmpty()) {
            user.setTransport("ANY");
        }
        
        
        TransportStrategy strategy = transportStrategyFactory.instantiateRightStrategy(EnumTransport.valueOf(user.getTransport()), transportPreferences);
        
        origin = graph.findPlaceByName(originName);
        destination = graph.findPlaceByName(destinationName);
        
        String title = "Best route considering %s".formatted(preferenceList);
        
        preferenceList.forEach((preference) -> {
            bestRoute = preference.chooseDecorator(strategy).calculateBestRoute(origin, destination, graph);
        });
        
        Map<String, Route> recommendedRoutes = new HashMap<>();
        recommendedRoutes.put(title, bestRoute);
        String result = Json.mapper().writeValueAsString(recommendedRoutes);

        bestRoute = null;
        
        return result;
    }
    
    
}
