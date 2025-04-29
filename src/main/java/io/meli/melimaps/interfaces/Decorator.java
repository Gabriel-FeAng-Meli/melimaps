package io.meli.melimaps.interfaces;

import java.util.ArrayList;
import java.util.List;

import io.meli.melimaps.decorator.BaseDecorator;
import io.meli.melimaps.enums.EnumDecoration;
import io.meli.melimaps.enums.EnumTransport;
import io.meli.melimaps.model.Route;
import io.meli.melimaps.model.Vertex;

public interface Decorator extends OptimizationInterface {

    public static List<Route> calculateRoutesForEachPreference(EnumTransport transport, List<EnumDecoration> preferences, Vertex origin, Vertex destination, Graph map) {

        List<Route> routes = new ArrayList<>();

        preferences.forEach(preference -> {
            BaseDecorator decorator = preference.chooseDecorator(transport);

            if (decorator != null) {
                
                routes.add(decorator.calculateBestRoute(origin, destination, map));
            }
        });

        return routes;

    }


}
