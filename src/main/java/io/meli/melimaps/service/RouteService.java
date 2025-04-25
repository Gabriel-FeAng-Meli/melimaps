package io.meli.melimaps.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.meli.melimaps.decorator.BaseDecorator;
import io.meli.melimaps.enums.EnumPreferences;
import io.meli.melimaps.enums.EnumTransport;
import io.meli.melimaps.factories.TransportStrategyFactory;
import io.meli.melimaps.interfaces.TransportStrategy;
import io.meli.melimaps.model.CompleteRoute;
import io.meli.melimaps.model.Graph;
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

    private EnumTransport transport;
    private Vertex origin;
    private Vertex destination;


    public String generateOptimalRouteForUser(Integer userId, String originName, String destinationName, List<EnumPreferences> preferenceList) throws JsonProcessingException {

        Graph graph = new Graph().build();

        User user = userService.getUserById(userId);
        UserPreferences preferences = new UserPreferences(user.getTransport(), user.getEcologic(), user.getAccessibility(), false);

        TransportStrategy strategy = transportStrategyFactory.instantiateRightStrategy(preferences);

        transport = strategy.getStrategyType();
        origin = graph.findPlaceByName(originName);
        destination = graph.findPlaceByName(destinationName);

        Map<String, CompleteRoute> recommendedRoutes = new HashMap<>();

        CompleteRoute bestRoute;

        // if (routeRepository.existsByTransportAndOriginNameAndDestinationNameAllIgnoreCase(transport.name(), originName, destinationName)) {
        //     bestRoute = routeRepository.findByTransportAndOriginNameAndDestinationNameAllIgnoreCase(transport.name(), originName, destinationName);
        // } else {
            bestRoute = strategy.calculateBestRoute(origin, destination, graph);
        // routeRepository.save(bestRoute);
        // }

        recommendedRoutes.put("Best route considering DISTANCE: ", bestRoute);

        if (!preferenceList.isEmpty()) {
            
            BaseDecorator decoration = new BaseDecorator(strategy, transport);
            
            recommendedRoutes.putAll(decoration.returnRoutesConsideringPreferences(origin, destination, graph, recommendedRoutes, preferenceList));
        }
        
        String result = Json.mapper().writeValueAsString(recommendedRoutes);

        return result;
    }

    
}
