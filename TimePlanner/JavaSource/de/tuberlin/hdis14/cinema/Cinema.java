package de.tuberlin.hdis14.cinema;


import java.io.Serializable;
import java.util.List;

import de.tuberlin.hdis14.publictransport.Route;
import de.tuberlin.hdis14.restaurant.Restaurant;

public class Cinema implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String address;
	private String[] screeningTimes;
	private String theaterName;

	private int[] movieEndTime;
	
	public Cinema(String name, String[] times, String address, int[] endtimes)
	{
		this.screeningTimes = times;
		this.theaterName = name;
		this.address = address;
		this.movieEndTime=endtimes;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int[] getMovieEndTime() {
		return  movieEndTime;
	}
	public void setMovieEndTime(int[]  movieEndTime) {
		this. movieEndTime =  movieEndTime;
	}
	public String[] getScreeningTime() {
		return screeningTimes;
	}
	public void setScreeningTime(String[] screeningTime) {
		this.screeningTimes = screeningTime;
	}
	public String getTheaterName() {
		return theaterName;
	}
	public void setTheaterName(String theaterName) {
		this.theaterName = theaterName;
	}
	
	

}
