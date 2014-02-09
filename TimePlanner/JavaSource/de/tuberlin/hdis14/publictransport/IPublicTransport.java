package de.tuberlin.hdis14.publictransport;

import de.tuberlin.hdis14.cinema.*;
import de.tuberlin.hdis14.restaurant.*;

import java.util.List;
import java.util.Map;

public interface IPublicTransport {

	public List<Cinema> callJelena1(String startAddress, String departureTime,List<Cinema> cinemas);
	
	public Map<List<Cinema>, List<Restaurant>> callJelena2(List<CinemaRestaurant> cinRest);

}
