package de.tuberlin.hdis14.publictransport;

import de.tuberlin.hdis14.cinema.*;
import de.tuberlin.hdis14.restaurant.*;

public class CinemaRestaurantRoute {
	
	private Cinema cinema;
	private Restaurant restaurant;	
	private Route route;
	
	
	
	
	public CinemaRestaurantRoute(Cinema cinema, Restaurant restaurant,Route route) {
		this.cinema = cinema;
		this.restaurant = restaurant;
		this.route = route;
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
	public Route getRoute() {
		return route;
	}
	public void setRoute(Route route) {
		this.route = route;
	}
	
	
	

	
	

}

