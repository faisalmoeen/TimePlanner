package de.tuberlin.hdis14.core;

import java.util.List;

public interface BestScreeningOfCinema {

	/**
	 * 
	 * @param routes List of all routes to cinemas
	 * @return List of routes to cinemas we can reach
	 */
	public List<RouteToCinema> getValidScreenings(List<RouteToCinema> routes);
	
	
	
}
