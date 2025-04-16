package io.meli.melimaps.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Route {

	static List<Vertex> optimalRoute = new ArrayList<>();
	static Vertex vertex = new Vertex();
	static Vertex current = new Vertex();
	static Vertex child = new Vertex();
	static List<Vertex> notVisited = new ArrayList<>();
	public static List<Vertex> dijkstra(Graph graph, Vertex origin,
			Vertex destination) {

		optimalRoute.add(origin);
        List<Vertex> allVerticesOnMap = graph.getVertices();
        allVerticesOnMap.stream().map(v -> {
            if (v.getName().equalsIgnoreCase(origin.getName())) {
                v.setDistance(0);
            } 
            notVisited.add(v);
            return v;
        }).collect(Collectors.toList());

		while (!notVisited.isEmpty()) {
			current = notVisited.get(0);
			current.getPaths().forEach(path -> {
				child = path.getDestination();
				if (!child.alreadyVisited()) {
					if (child.getWeight() > (current.getWeight() + path.getDistance())) {
						child.setDistance(current.getWeight()
								+ path.getDistance());
						child.setParent(current);
						if (child == destination) {
							optimalRoute.clear();
							vertex = child;
							optimalRoute.add(child);
							while (vertex.getParent() != null) {
								optimalRoute.add(vertex.getParent());
								vertex = vertex.getParent();
							}
						}
					}
				}  
			});
			current.visit();
			notVisited.remove(current);
            
            optimalRoute = optimalRoute.stream().sorted(Comparator.comparingInt(v -> v.getWeight())).collect(Collectors.toList());
		}

		return optimalRoute;
	}
}