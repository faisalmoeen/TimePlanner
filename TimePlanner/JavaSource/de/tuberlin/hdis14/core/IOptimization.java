package de.tuberlin.hdis14.core;

import java.util.List;
import java.util.Map;

import de.tuberlin.hdis14.cinema.Cinema;
import de.tuberlin.hdis14.restaurant.Restaurant;

public interface IOptimization {

	/**
	 * 
	 * @param cinemaRestList All valid combinations of cinema and restaurant
	 * @return Map of 3 optimal combinations of cinema and restaurant 
	 */
	public static Map<Cinema, Restaurant> getOptimalCombination(List<CinemaRestaurant> cinemaRestList);
}
