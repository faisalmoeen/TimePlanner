/**
 * 
 */
package de.tuberlin.hdis14.restaurant;

import java.util.List;
import java.util.Map;

import de.tuberlin.hdis14.cinema.Cinema;

/**
 * @author JANANI
 *
 */
public interface IRestaurant {
	
	public Map<Cinema, Restaurant> fromFaisal(String startLocation, String startTime, List<Cinema> cinemaList,String cuisine, String type, int maxDistance);
}