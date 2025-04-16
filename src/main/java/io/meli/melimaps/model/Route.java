package io.meli.melimaps.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity
public class Route {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToMany(targetEntity = Vertex.class)
	private List<Vertex> route;
	
    
	List<Vertex> optimalRoute = new ArrayList<Vertex>();

	Vertex vertex = new Vertex();
	Vertex current = new Vertex();
	Vertex child = new Vertex();

	List<Vertex> notVisited = new ArrayList<Vertex>();

	public List<Vertex> dijkstra(Graph graph, Vertex origin,
			Vertex destination) {

		optimalRoute.add(origin);

        List<Vertex> allVerticesOnMap = graph.getVertices();

        allVerticesOnMap = allVerticesOnMap.stream().map(v -> {
            if (v.getName().equalsIgnoreCase(origin.getName())) {
                v.setDistance(0);
            } 
            notVisited.add(v);
            return v;
        }).collect(Collectors.toList());

		while (!this.notVisited.isEmpty()) {
			current = this.notVisited.get(0);
			current.getPaths().forEach(path -> {
				child = path.getDestination();
				if (!child.alreadyVisited()) {
					if (child.getWeight() > (current.getWeight() + path.getWeight())) {
						child.setDistance(current.getWeight()
								+ path.getWeight());
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
			this.notVisited.remove(current);
            
            optimalRoute = optimalRoute.stream().sorted(Comparator.comparingInt(v -> v.getWeight())).collect(Collectors.toList());
		}

		return optimalRoute;
	}
}