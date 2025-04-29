package io.meli.melimaps.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.meli.melimaps.enums.EnumDecoration;
import io.meli.melimaps.enums.EnumTransport;
import io.meli.melimaps.factories.TransportStrategyFactory;
import io.meli.melimaps.interfaces.Decorator;
import io.meli.melimaps.interfaces.OptimizationInterface;
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

    
    @Autowired
    private UserService userService;
    
    
    public String generateOptimalRouteForUser(Integer userId, String originName, String destinationName, List<EnumDecoration> preferenceList) throws JsonProcessingException {
        
        MeliMap graph = new MeliMap().build();
        TransportStrategyFactory transportStrategyFactory = new TransportStrategyFactory();

        EnumTransport transport;
        Vertex origin;
        Vertex destination;

        
        User user = userService.getUserById(userId);

        if (user.getTransport().isEmpty() || user.getTransport().equals(EnumTransport.ANY.name())) {
            user.setTransport("CAR");
        }

        OptimizationInterface strategy = transportStrategyFactory.instantiateRightStrategy(EnumTransport.valueOf(user.getTransport()), preferenceList);

        transport = strategy.getStrategyType();
        origin = graph.findPlaceByName(originName);
        destination = graph.findPlaceByName(destinationName);

        graph.getGraphWithVerticesAvailableForTransport(transport);

        Map<String, Route> recommendedRoutes = new HashMap<>();

        if (!preferenceList.isEmpty()) {
            
            List<Route> decoratedRoutes = Decorator.calculateRoutesForEachPreference(transport, preferenceList, origin, destination, graph);
            decoratedRoutes.forEach(route -> {

                recommendedRoutes.put("Best route considering %s".formatted(route.getPrioritize()), route);
            });
            
        }
        
        Route bestRoute;

        // if (routeRepository.existsByPrioritizeAndTransportAndOriginNameAndDestinationNameAllIgnoringCase(prioritize, transport.name(), originName, destinationName)) {
        //     bestRoute = routeRepository.findByPrioritizeAndTransportAndOriginNameAndDestinationNameAllIgnoringCase(prioritize, transport.name(), originName, destinationName);
        // } else {
        bestRoute = strategy.calculateBestRoute(origin, destination, graph);
        //     routeRepository.save(bestRoute);
        // }
        
        recommendedRoutes.put("Best route considering DISTANCE: ", bestRoute);
        
        
        String result = Json.mapper().writeValueAsString(recommendedRoutes);

        return result;
    }

    
}
