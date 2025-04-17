package io.meli.melimaps.model;

import java.util.ArrayList;
import java.util.List;

public class Route {

	List<Vertex> optimalRoute = new ArrayList<>();
	Vertex previous = new Vertex();
	Vertex current = new Vertex();
	Vertex next = new Vertex();
	List<Vertex> notVisited = new ArrayList<>();

	public List<String> possibleRoutes = new ArrayList<>();

	public List<Vertex> findBestPossibleRoute(Graph g, Vertex origin, Vertex destination) {
		g.getVertices().forEach(vertex -> {
			notVisited.add(vertex);
		});

		// origin will be the first to start counting distances, so the distance towards himself is 0 and there is no previous vertex;
		previous = null;
		current = origin;
		current.setWeight(0);
		current.setPrevious(previous);

		while(!notVisited.isEmpty()) {
			current.getPaths().forEach(road -> {
				next = road.getDestination();
				if (!next.alreadyVisited()) {
					if (next.getWeight() > current.getWeight() + road.getDistance()) {
						next.setWeight(current.getWeight() + road.getDistance());
						next.setPrevious(current);
						previous = current;
						current = next;
						next = null;

						if (current.getName().equalsIgnoreCase(destination.getName())) {
							// got to destination -> come back adding every previous vertex went through to reach the destination to the path, until we get back to origin
							optimalRoute.clear();
							while(!current.getName().equalsIgnoreCase(origin.getName())) {
								optimalRoute.add(current);
								current = current.getPrevious();
							}// -> still did not reach destination, loop again infinetely until reaching
						} 
					}// -> next point weight is either infinity (Integer.max) or had a worse path than the current one
				}// -> vertex already visited
			});// still inside while loop but has already gone through all paths
			current.visit();
			notVisited.remove(current);

		}

		return optimalRoute;
	}
}