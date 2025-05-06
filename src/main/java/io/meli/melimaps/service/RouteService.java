package io.meli.melimaps.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.meli.melimaps.enums.EnumPreference;
import io.meli.melimaps.enums.EnumTransport;
import io.meli.melimaps.factories.TransportStrategyFactory;
import io.meli.melimaps.interfaces.TransportStrategy;
import io.meli.melimaps.model.Graph;
import io.meli.melimaps.model.Requisition;
import io.meli.melimaps.model.Route;
import io.meli.melimaps.model.User;
import io.meli.melimaps.model.UserPreferences;
import io.meli.melimaps.model.Vertex;
import io.meli.melimaps.repository.RequisitionRepository;
import io.meli.melimaps.repository.RouteRepository;
import io.swagger.v3.core.util.Json;

@Service
public class RouteService {

    private final TransportStrategyFactory transportStrategyFactory = new TransportStrategyFactory();

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private RequisitionRepository requisitionRepository;

    @Autowired
    private UserService userService;

    private Route bestRoute;

    public String generateOptimalRouteForUser(Integer userId, String originName, String destinationName, Set<EnumPreference> preferenceList) throws JsonProcessingException {
        
        Vertex origin;
        Vertex destination;
        Graph graph = new Graph().build();
        
        User user = userService.getUserById(userId);
        UserPreferences transportPreferences = new UserPreferences(user.getTransport(), user.getEcologic(), user.getHurry(), user.getAccessibility(), user.getBudget());

        if (user.getTransport().isEmpty()) {
            user.setTransport("ANY");
        }
        TransportStrategy strategy = transportStrategyFactory.instantiateRightStrategy(EnumTransport.valueOf(user.getTransport()), transportPreferences);
        EnumTransport transport = strategy.getStrategyType();

        String requestProperties = Route.generateRequestProperties(transport, originName, destinationName, preferenceList);
        String title = "Best route considering %s".formatted(preferenceList);

        if (routeRepository.existsByRequestProperties(requestProperties)) {
            bestRoute = routeRepository.findByRequestProperties(requestProperties).get(0);
            Requisition req = requisitionRepository.findByRouteAndUser(bestRoute, user);
            req.setTimesRequested(req.getTimesRequested() + 1);
            requisitionRepository.save(req);
            
        } else {

            origin = graph.findPlaceByName(originName);
            destination = graph.findPlaceByName(destinationName);
            
    
            preferenceList.forEach((preference) -> {
                bestRoute = preference.chooseDecorator(strategy).calculateBestRoute(origin, destination, graph);
            });
            
            Route route = new Route();
            route.setOriginName(originName);
            route.setDestinationName(destinationName);
            route.setTransport(bestRoute.getTransport());
            route.setDistance(bestRoute.getDistance());
            route.setTotalCost(bestRoute.getTotalCost());
            route.setPath(bestRoute.getPath());
            route.setTimeToReach(bestRoute.getTimeToReach());
            route.setRequestProperties(requestProperties);
            route.addUser(user);
            
            routeRepository.save(route);
        }

        Map<String, Route> recommendedRoutes = new HashMap<>();
        recommendedRoutes.put(title, bestRoute);
        
        String result = Json.mapper().writeValueAsString(recommendedRoutes);
        bestRoute = null;
        
        return result;
    }

}
