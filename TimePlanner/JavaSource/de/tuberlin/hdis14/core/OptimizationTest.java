package de.tuberlin.hdis14.core;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import de.tuberlin.hdis14.cinema.Cinema;
import de.tuberlin.hdis14.core.OptimalCombination;
import de.tuberlin.hdis14.core.Optimization;
import de.tuberlin.hdis14.publictransport.CinemaRestaurantRoute;
import de.tuberlin.hdis14.publictransport.Route;
import de.tuberlin.hdis14.restaurant.Restaurant;

public class OptimizationTest {
	private Restaurant restaurant1;
	private Restaurant restaurant2;
	
	private Route route1;
	private Route route2;
	
	private Cinema cinema1;
	private Cinema cinema2;
	
	private CinemaRestaurantRoute cinemaRestaurantRoute1;
	private CinemaRestaurantRoute cinemaRestaurantRoute2;

	private List <CinemaRestaurantRoute> cinemaRestaurantRoutes;
		
	Optimization optimization;
	
	OptimalCombination optimalCombination;
	
	@Before
	public  void setUp() throws Exception {
		restaurant1 = new Restaurant();
        restaurant1.setName("DaVinci");
        restaurant1.setPhone("0176473844");
        restaurant1.setRating("miserable");
        restaurant1.setRestaurantAddress("Berliner Straﬂe 112");
        
        restaurant2 = new Restaurant();
        restaurant2.setName("HiHo");
        restaurant2.setPhone("666666666");
        restaurant2.setRating("very good");
        restaurant2.setRestaurantAddress("ThePlatz 12");

        route1 = new Route(2147483647,16129,8,2, "Straﬂe des 17. Juni 135", "Berliner Straﬂe 13" );
        route2 = new Route(2147483285, 16061, 15, 5, "Alexanderstraﬂe 1", "Heerstraﬂe 35" );
        
        cinema1 = new Cinema("Berliner Straﬂe 13", "19:00", "HalluGalle", route1 );
        cinema2 = new Cinema("Heerstraﬂe 35", "21:15", "All is Lost", route2 );
        
        cinemaRestaurantRoute1 = new CinemaRestaurantRoute(cinema1);
        Map<Restaurant, Route> restaurantRoute1 = new HashMap<Restaurant, Route>();
        restaurantRoute1.put(restaurant1, route1);
        Map<Restaurant, Route> restaurantRoute2 = new HashMap<Restaurant, Route>();
        restaurantRoute1.put(restaurant2, route2);
        cinemaRestaurantRoute1.setRestaurantRouteList(restaurantRoute1);
        cinemaRestaurantRoute2 = new CinemaRestaurantRoute(cinema2);
        cinemaRestaurantRoute2.setRestaurantRouteList(restaurantRoute2);
        
        optimization = new Optimization();
        cinemaRestaurantRoutes = new ArrayList<CinemaRestaurantRoute>();
        cinemaRestaurantRoutes. add(cinemaRestaurantRoute1);
        cinemaRestaurantRoutes.add(cinemaRestaurantRoute2);
        
        optimalCombination = new OptimalCombination(cinema1, restaurant1, 45, 8);
	}
	
	@Test
    public void testRestaurant ()
	{
		assertEquals("DaVinci", restaurant1.getName());
		assertEquals("0176473844", restaurant1.getPhone());
		assertEquals("miserable", restaurant1.getRating());
		assertEquals("Berliner Straﬂe 112", restaurant1.getRestaurantAddress());
    }
	
	@Test
	public void testRoute ()
	{
		assertEquals(2147483647, route1.getArrival_time());
		assertEquals(16129, route1.getDeparture_time());
		assertEquals(8, route1.getDuration());
		assertEquals(2, route1.getDistance());
		assertEquals("Straﬂe des 17. Juni 135", route1.getStart_address());
		assertEquals("Berliner Straﬂe 13", route1.getEnd_address());
	}
	
	@Test
	public void testOptimalCombination()
	{
		assertEquals(cinema1 , optimalCombination.getCinema());
		assertEquals(restaurant1 , optimalCombination.getRestaurant());
		assertEquals(45 + 8 , optimalCombination.getWeight());
		
	}
	
	@Test
	public void testOptimization()
	{
		optimization.getOptimalCombination(cinemaRestaurantRoutes);
	}
	
	public static void main(String args[]) throws Exception {
		OptimizationTest test = new OptimizationTest();
		test.setUp();
//		test.testRestaurant();
		test.testRoute();
		test.testOptimalCombination();
//		test.testOptimization();
	}
}

