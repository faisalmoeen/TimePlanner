package de.tuberlin.hdis14.publictransport;

import de.tuberlin.hdis14.cinema.*;
import de.tuberlin.hdis14.restaurant.*;

import java.util.List;

public interface IPublicTransport {

	public List<Cinema> getFilteredCinemas(String startAddress,List<Cinema> cinemas, String departureTime);
	
	public List<CinemaRestaurantRoute> getRoutesCinemaRestaurant(List<Cinema> cinemas,List<Restaurant> restaurants);

}
