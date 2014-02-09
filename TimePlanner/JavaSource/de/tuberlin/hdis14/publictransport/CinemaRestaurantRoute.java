package de.tuberlin.hdis14.publictransport;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.tuberlin.hdis14.cinema.*;
import de.tuberlin.hdis14.restaurant.*;

public class CinemaRestaurantRoute {
	
	private Cinema cinema;

    Map<Restaurant,Route> restaurantRouteList;
    
   
	public CinemaRestaurantRoute(Cinema cinema) {

		this.cinema = cinema;
		this.restaurantRouteList = new HashMap<Restaurant, Route>();
	}

	public Cinema getCinema() {
		return cinema;
	}

	public void setCinema(Cinema cinema) {
		this.cinema = cinema;
	}

	public Map<Restaurant, Route> getRestaurantRouteList() {
		return restaurantRouteList;
	}

	public void setRestaurantRouteList(Map<Restaurant, Route> restaurantRouteList) {
		this.restaurantRouteList = restaurantRouteList;
	}
	
	

 
	
	

}

