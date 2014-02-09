package de.tuberlin.hdis14.cinema;

import java.io.Serializable;
import java.util.List;

import de.tuberlin.hdis14.publictransport.*;
import de.tuberlin.hdis14.restaurant.*;

public class Cinema implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String address;
	private String screeningTime;
	private String theaterName;
	private Route route; //route from start location to the cinema
	private List<Restaurant> optimalRestaurants;
	private int movieEndTime;
	
	//...............................
	
	public Cinema(String address,String screeningTime, String theaterName) {

		this.address = address;
		this.screeningTime = screeningTime;
		this.theaterName = theaterName;
	}
	//...............................
	
	
	public Cinema(String address, String screeningTime, String theaterName,Route route) {

		this.address = address;
		this.screeningTime = screeningTime;
		this.theaterName = theaterName;
		this.route = route;
	}
		
	public int getMovieEndTime() {
		return movieEndTime;
	}


	public void setMovieEndTime(int movieEndTime) {
		this.movieEndTime = movieEndTime;
	}


	public List<Restaurant> getOptimalRestaurants() {
		return optimalRestaurants;
	}


	public void setOptimalRestaurants(List<Restaurant> optimalRestaurants) {
		this.optimalRestaurants = optimalRestaurants;
	}


	public Route getRoute() {
		return route;
	}
	public void setRoute(Route route) {
		this.route = route;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getScreeningTime() {
		return screeningTime;
	}
	public void setScreeningTime(String screeningTime) {
		this.screeningTime = screeningTime;
	}
	public String getTheaterName() {
		return theaterName;
	}
	public void setTheaterName(String theaterName) {
		this.theaterName = theaterName;
	}

	

}
