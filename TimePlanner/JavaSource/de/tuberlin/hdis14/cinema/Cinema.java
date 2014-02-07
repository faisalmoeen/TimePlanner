package de.tuberlin.hdis14.cinema;

import java.io.Serializable;
import de.tuberlin.hdis14.publictransport.*;

public class Cinema implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String address;
	private String screeningTime;
	private String theaterName;
	private Route route; //route from start location to the cinema
	
	
	
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
