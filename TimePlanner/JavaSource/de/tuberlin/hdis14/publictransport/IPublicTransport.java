package de.tuberlin.hdis14.publictransport;

import de.tuberlin.hdis14.cinema.*;
import java.util.List;

public interface IPublicTransport {

	List<Cinema> getFilteredCinemas(String startAddress,List<Cinema> cinemas, String departureTime);
	
	List<Route> getRoutesCinemaRestaurant(List<Cinema> listCinemas,List<Restaurant> listRest);

}
