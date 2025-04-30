package io.meli.melimaps.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.meli.melimaps.decorator.BaseDecorator;
import io.meli.melimaps.enums.EnumDecoration;
import io.meli.melimaps.enums.EnumTransport;
import io.meli.melimaps.factories.TransportStrategyFactory;
import io.meli.melimaps.interfaces.TransportStrategy;
import io.meli.melimaps.model.MeliMap;
import io.meli.melimaps.model.Route;
import io.meli.melimaps.model.User;
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

    private EnumTransport transport;
    private Vertex origin;
    private Vertex destination;

    
    public String generateOptimalRouteForUser(Integer userId, String originName, String destinationName, List<EnumDecoration> decorationList) throws JsonProcessingException {
        
        MeliMap graph = new MeliMap().build();
        
        User user = userService.getUserById(userId);

        if (user.getTransport().isEmpty()) {
            user.setTransport("ANY");
        }

        TransportStrategy strategy = transportStrategyFactory.instantiateRightStrategy(EnumTransport.valueOf(user.getTransport()), decorationList);

        transport = strategy.getStrategyType();
        origin = graph.findPlaceByName(originName);
        destination = graph.findPlaceByName(destinationName);

        graph.getGraphWithVerticesAvailableForTransport(transport);

        Map<String, Route> recommendedRoutes = new HashMap<>();

        if (!decorationList.isEmpty()) {
            
            List<Route> decoratedRoutes = BaseDecorator.calculateRoutesForEachPreference(transport, decorationList, origin, destination, graph);
            decoratedRoutes.forEach(route -> {

                recommendedRoutes.put("Best route considering %s".formatted(route.getPrioritize()), route);
            });
            
        }
     

        
        Route bestRoute = strategy.calculateBestRoute(origin, destination, graph);
        
        recommendedRoutes.put("Best route considering DISTANCE: ", bestRoute);
        
        
        String result = Json.mapper().writeValueAsString(recommendedRoutes);

        return result;
    }

    
}
