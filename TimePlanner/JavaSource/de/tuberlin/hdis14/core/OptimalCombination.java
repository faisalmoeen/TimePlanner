package de.tuberlin.hdis14.core;

import java.util.Map;

import de.tuberlin.hdis14.cinema.Cinema;
import de.tuberlin.hdis14.publictransport.CinemaRestaurantRoute;
import de.tuberlin.hdis14.restaurant.Restaurant;

public class OptimalCombination  {
	private int weight = Integer.MAX_VALUE;
	private Cinema cinema;
	private Restaurant restaurant;
	
	public OptimalCombination(Cinema cinema ,Restaurant restaurant , int weightedTripDuration, int factorWalkingDistance) {
		this.setCinema(cinema);
		this.setRestaurant(restaurant);
		this.setWeight(weightedTripDuration + factorWalkingDistance);
	}

	public Cinema getCinema() {
		return cinema;
	}

	public void setCinema(Cinema cinema) {
		this.cinema = cinema;
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}
}
