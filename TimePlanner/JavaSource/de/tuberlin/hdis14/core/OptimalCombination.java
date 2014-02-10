package de.tuberlin.hdis14.core;

import java.util.Map;

import de.tuberlin.hdis14.cinema.Cinema;
import de.tuberlin.hdis14.publictransport.CinemaRestaurantRoute;
import de.tuberlin.hdis14.restaurant.Restaurant;

public class OptimalCombination  {
	int weight = Integer.MAX_VALUE;
	Cinema cinema;
	Restaurant restaurant;
	
	public OptimalCombination(Cinema cinema ,Restaurant restaurant , int weightedTripDuration, int factorWalkingDistance) {
		this.cinema = cinema;
		this.restaurant =  restaurant;
		this.weight = weightedTripDuration + factorWalkingDistance;
	}
}
