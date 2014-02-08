/**
 * 
 */
package de.tuberlin.hdis14.restaurant;

import java.util.List;

import de.tuberlin.hdis14.cinema.Cinema;

/**
 * @author JANANI
 *
 */
public interface IRestaurant {
	
	public List<Restaurant> getRestaurants(Cinema cinema, String cuisine, String type,
			int radius);
}
