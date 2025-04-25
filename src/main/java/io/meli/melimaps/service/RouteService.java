package io.meli.melimaps.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.meli.melimaps.enums.EnumPreferences;
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

    @Autowired
    private TransportStrategyFactory transportStrategyFactory;

    @Autowired
    private UserService userService;

    private EnumTransport transport;
    private Vertex origin;
    private Vertex destination;


    public String generateOptimalRouteForUser(Integer userId, String originName, String destinationName, List<EnumPreferences> preferenceList) throws JsonProcessingException {

        Graph graph = Graph.build();

        User user = userService.getUserById(userId);
        UserPreferences preferences = new UserPreferences(user.getTransport(), user.getEcologic(), user.getAccessibility(), false);

        TransportStrategy strategy = transportStrategyFactory.instantiateRightStrategy(preferences);

        transport = strategy.getStrategyType();
        origin = graph.findPlaceByName(originName);
        destination = graph.findPlaceByName(destinationName);

        Route bestRoute;

        if (routeRepository.existsByTransportAndOriginNameAndDestinationNameAndPathAllIgnoreCase(transport.name(), originName, destinationName)) {
            bestRoute = routeRepository.findByTransportAndOriginNameAndDestinationNameAndPathAllIgnoreCase(transport.name(), originName, destinationName);
        } else {
            bestRoute = strategy.calculateBestRoute(origin, destination, graph.getVertices());
            routeRepository.save(bestRoute);
        }

        String result = Json.mapper().writeValueAsString(bestRoute);

        return result;
    }

    
}
