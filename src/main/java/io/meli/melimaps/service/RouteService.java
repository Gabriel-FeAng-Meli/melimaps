package io.meli.melimaps.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.meli.melimaps.model.Graph;
import io.meli.melimaps.model.Route;
import io.meli.melimaps.model.Vertex;

@Service
public class RouteService {

    @Autowired
    Graph map;

    // @Autowired
    // UserService userService;

    public List<Vertex> generateOptimalRouteForUser(Integer userId, String originName, String destinationName) {
        // User user = userService.getUserById(userId);
        // transportStategyFactory picks preferences and instantiates the right one;

        Route r = new Route();

        var optimalRoute = r.findBestPossibleRoute(map, map.findPlaceByName(originName), map.findPlaceByName(destinationName));

        return optimalRoute;
    }

    
}
