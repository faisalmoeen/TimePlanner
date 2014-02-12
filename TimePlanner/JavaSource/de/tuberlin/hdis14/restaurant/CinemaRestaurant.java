package de.tuberlin.hdis14.restaurant;

import java.io.Serializable;
import java.util.List;

import de.tuberlin.hdis14.cinema.Cinema;

public class CinemaRestaurant implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Cinema cinema;
	List<Restaurant> restaurantList;
	public Cinema getCinema() {
		return cinema;
	}
	public void setCinema(Cinema cinema) {
		this.cinema = cinema;
	}
	public List<Restaurant> getRestaurantList() {
		return restaurantList;
	}
	public void setRestaurantList(List<Restaurant> restaurantList) {
		this.restaurantList = restaurantList;
	}
	public CinemaRestaurant(Cinema cinema, List<Restaurant> restaurantList) {
		
		this.cinema = cinema;
		this.restaurantList = restaurantList;
	}
	
	public CinemaRestaurant()
	{
		
	}

}
