package de.tuberlin.hdis14.publictransport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.tuberlin.hdis14.cinema.*;
import de.tuberlin.hdis14.restaurant.*;

public class CinemaRestaurantRoute {
	
	private Cinema cinema;
    List<Restaurant> restaurantList;
    List<Route> routeList;
    
   
	public CinemaRestaurantRoute(Cinema cinema) {

		this.cinema = cinema;
		this.restaurantList = new ArrayList<Restaurant>();
		this.routeList = new ArrayList<Route>();
	}

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

	public List<Route> getRouteList() {
		return routeList;
	}

	public void setRouteList(List<Route> routeList) {
		this.routeList = routeList;
	}


	

 
	
	

}

