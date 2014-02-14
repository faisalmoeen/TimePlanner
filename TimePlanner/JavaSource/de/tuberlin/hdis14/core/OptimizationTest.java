package de.tuberlin.hdis14.core;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Before;
import org.junit.Test;

import de.tuberlin.hdis14.cinema.Cinema;
import de.tuberlin.hdis14.publictransport.CinemaRestaurantRoute;
import de.tuberlin.hdis14.publictransport.Route;
import de.tuberlin.hdis14.restaurant.Restaurant;

public class OptimizationTest {
	private Restaurant restaurant1;
	private Restaurant restaurant2;
	private Restaurant restaurant3;
	private Restaurant restaurant4;
	private Restaurant restaurant5;
	private Restaurant restaurant6;
	private Restaurant restaurant7;
	private Restaurant restaurant8;
	private Restaurant restaurant9;
	private Restaurant restaurant10;
	private Restaurant restaurant11;
	private Restaurant restaurant12;
	
	private Route route1;
	private Route route2;
	private Route route3;
	private Route route4;
	private Route route5;
	private Route route6;
	private Route route7;
	private Route route8;
	private Route route9;
	private Route route10;
	private Route route11;
	private Route route12;
	
	private Cinema cinema1;
	private Cinema cinema2;
	private Cinema cinema3;
	private Cinema cinema4;
	
	private CinemaRestaurantRoute cinemaRestaurantRoute1;
	private CinemaRestaurantRoute cinemaRestaurantRoute2;
	private CinemaRestaurantRoute cinemaRestaurantRoute3;
	private CinemaRestaurantRoute cinemaRestaurantRoute4;

	private List <CinemaRestaurantRoute> cinemaRestaurantRoutes;
		
	Optimization optimization;

	OptimalCombination optimalCombination;
	
	FakeUserCriteria userCriteria;
	
	@Before
	public  void setUp() throws Exception {
		restaurant1 = new Restaurant();
        restaurant1.setName("Rest1");
        restaurant1.setPhone("0176473844");
        restaurant1.setRating("miserable");
        restaurant1.setRestaurantAddress("Berliner Stra�e 112");
        
        restaurant2 = new Restaurant();
        restaurant2.setName("Rest2");
        restaurant2.setPhone("666666666");
        restaurant2.setRating("very good");
        restaurant2.setRestaurantAddress("ThePlatz 12");
        
        restaurant3 = new Restaurant();
        restaurant3.setName("Rest3");
        restaurant3.setPhone("666666666");
        restaurant3.setRating("very good");
        restaurant3.setRestaurantAddress("ThePlatz 12");
        
        restaurant4 = new Restaurant();
        restaurant4.setName("Rest4");
        restaurant4.setPhone("666666666");
        restaurant4.setRating("very good");
        restaurant4.setRestaurantAddress("ThePlatz 12");
        
        restaurant5 = new Restaurant();
        restaurant5.setName("Rest5");
        restaurant5.setPhone("666666666");
        restaurant5.setRating("very good");
        restaurant5.setRestaurantAddress("ThePlatz 12");
        
        restaurant6 = new Restaurant();
        restaurant6.setName("Rest6");
        restaurant6.setPhone("666666666");
        restaurant6.setRating("very good");
        restaurant6.setRestaurantAddress("ThePlatz 12");
        
        restaurant7 = new Restaurant();
        restaurant7.setName("Rest7");
        restaurant7.setPhone("666666666");
        restaurant7.setRating("very good");
        restaurant7.setRestaurantAddress("ThePlatz 12");
        
        restaurant8 = new Restaurant();
        restaurant8.setName("Rest8");
        restaurant8.setPhone("666666666");
        restaurant8.setRating("very good");
        restaurant8.setRestaurantAddress("ThePlatz 12");
        
        restaurant9 = new Restaurant();
        restaurant9.setName("Rest9");
        restaurant9.setPhone("666666666");
        restaurant9.setRating("very good");
        restaurant9.setRestaurantAddress("ThePlatz 12");
        
        restaurant10 = new Restaurant();
        restaurant10.setName("Rest10");
        restaurant10.setPhone("666666666");
        restaurant10.setRating("very good");
        restaurant10.setRestaurantAddress("ThePlatz 12");
        
        restaurant11 = new Restaurant();
        restaurant11.setName("Rest11");
        restaurant11.setPhone("666666666");
        restaurant11.setRating("very good");
        restaurant11.setRestaurantAddress("ThePlatz 12");
        
        restaurant12 = new Restaurant();
        restaurant12.setName("Rest12");
        restaurant12.setPhone("666666666");
        restaurant12.setRating("very good");
        restaurant12.setRestaurantAddress("ThePlatz 12");

        route1 = new Route(2147483647,16129,8,1, "Route 1", "Berliner Stra�e 13" );
        route2 = new Route(2147483285, 16061, 15, 8, "Route 2", "Heerstra�e 35" );
        route3 = new Route(2147483285, 16061, 15, 6, "Route 3", "Heerstra�e 35" );
        route4 = new Route(2147483285, 16061, 15, 4, "Route 4", "Heerstra�e 35" );
        route5 = new Route(2147483285, 16061, 15, 2, "Route 5", "Heerstra�e 35" );
        route6 = new Route(2147483285, 16061, 15, 6, "Route 6", "Heerstra�e 35" );
        route7 = new Route(2147483285, 16061, 15, 7, "Route 7", "Heerstra�e 35" );
        route8 = new Route(2147483285, 16061, 15, 8, "Route 8", "Heerstra�e 35" );
        route9 = new Route(2147483285, 16061, 15, 9, "Route 9", "Heerstra�e 35" );
        route10 = new Route(2147483285, 16061, 15, 10, "Route 10", "Heerstra�e 35" );
        route11= new Route(2147483285, 16061, 15, 11, "Route 11", "Heerstra�e 35" );
        route12 = new Route(2147483285, 16061, 15, 12, "Route 12", "Heerstra�e 35" );
        
        String[] startTimes1 = {"19:00","20:00"}; 
        String[] startTimes2 = {"20:15","22:35"}; 
        
        int[] movieDuration1 = {120,155}; //String[] endTimes1 = {"21:00","22:00"};  
        int[] movieDurations2 = {90,137}; //String[] endTimes2 = {"22:15","00:35"}; 
        
        cinema1 = new Cinema("Kino1Zoo Palast", startTimes1 , "Berliner Stra�e 13", movieDuration1); //("Berliner Stra�e 13", "19:00", "HalluGalle", route1 );
        cinema2 = new Cinema("Kino2Cubix Alexanderplatz", startTimes2, "Heerstra�e 35", movieDurations2);//"Heerstra�e 35", "21:15", "All is Lost", route2 );
        cinema3 = new Cinema("Kino3Cubix Alexanderplatz", startTimes1, "Heerstra�e 35", movieDurations2);//"Heerstra�e 35", "21:15", "All is Lost", route2 );
        cinema4 = new Cinema("Kino3Cubix Alexanderplatz", startTimes2, "Heerstra�e 35", movieDurations2);//"Heerstra�e 35", "21:15", "All is Lost", route2 );
        
        Map<Restaurant, Route> restaurantRoute1 = new HashMap<Restaurant, Route>();
        restaurantRoute1.put(restaurant1, route1);
        restaurantRoute1.put(restaurant2, route2);
        restaurantRoute1.put(restaurant3, route3);
        restaurantRoute1.put(restaurant4, route4);
        Map<Restaurant, Route> restaurantRoute2 = new HashMap<Restaurant, Route>();
        restaurantRoute2.put(restaurant5, route5);
        restaurantRoute2.put(restaurant6, route6);
        Map<Restaurant, Route> restaurantRoute3 = new HashMap<Restaurant, Route>();
        restaurantRoute2.put(restaurant7, route7);
        restaurantRoute2.put(restaurant8, route8);
        Map<Restaurant, Route> restaurantRoute4 = new HashMap<Restaurant, Route>();
        restaurantRoute2.put(restaurant9, route9);
        restaurantRoute2.put(restaurant10, route10);
        
        cinemaRestaurantRoute1 = new CinemaRestaurantRoute(cinema1);
        List<Restaurant> foo = new ArrayList<Restaurant>();
        foo.add(restaurant1);
        foo.add(restaurant2);
        foo.add(restaurant3);
        foo.add(restaurant4);
        cinemaRestaurantRoute1.setRestaurantList(foo);
        List<Route> root = new ArrayList<Route>();
        root.add(route1);
        root.add(route2);
        root.add(route3);
        root.add(route4);
        cinemaRestaurantRoute1.setRouteList(root);
               
//        cinemaRestaurantRoute2 = new CinemaRestaurantRoute(cinema2);
//        cinemaRestaurantRoute2.setRestaurantRouteList(restaurantRoute2);
//        
//        cinemaRestaurantRoute3 = new CinemaRestaurantRoute(cinema3);
//        cinemaRestaurantRoute3.setRestaurantRouteList(restaurantRoute3);
//        
//        cinemaRestaurantRoute4 = new CinemaRestaurantRoute(cinema4);
//        cinemaRestaurantRoute4.setRestaurantRouteList(restaurantRoute4);
        
        cinemaRestaurantRoutes = new ArrayList<CinemaRestaurantRoute>();
        cinemaRestaurantRoutes. add(cinemaRestaurantRoute1);
//        cinemaRestaurantRoutes.add(cinemaRestaurantRoute2);
//        cinemaRestaurantRoutes.add(cinemaRestaurantRoute3);
//        cinemaRestaurantRoutes.add(cinemaRestaurantRoute4);
        
        optimization = new Optimization();
        optimalCombination = new OptimalCombination(cinema1, restaurant1, 45, 8);
        
//        userCriteria = FakeUserCriteria.getInstance();
//        userCriteria.setTime("20:00");
//        userCriteria.setMaxDistance(500);
	}
	
	@Test
	public void testRestaurant ()
	{
		assertEquals("DaVinci", restaurant1.getName());
		assertEquals("0176473844", restaurant1.getPhone());
		assertEquals("miserable", restaurant1.getRating());
		assertEquals("Berliner Stra�e 112", restaurant1.getRestaurantAddress());
	}
	
	@Test
    public void testCinema ()
	{
		assertEquals("Zoo Palast", cinema1.getTheaterName());
		assertEquals("Berliner Stra�e 13", cinema1.getAddress());
//		assertArrayEquals(new String[]{"19:00", "20:00"}, cinema1.getScreeningTime());
		
		assertEquals("19:00", cinema1.getScreeningTime()[0]);
		assertEquals("20:00", cinema1.getScreeningTime()[1]);
		
//		assertEquals(new int[]{120, 155}, cinema1.getMovieEndTime());
		assertEquals(120, cinema1.getMovieEndTime()[0]);
		assertEquals(155, cinema1.getMovieEndTime()[1]);

		
		assertEquals("Cubix Alexanderplatz", cinema2.getTheaterName());
		assertEquals("Heerstra�e 35", cinema2.getAddress());
		
//		assertArrayEquals(new String[]{"20:15", "22:35"}, cinema2.getScreeningTime());
		assertEquals("20:15", cinema2.getScreeningTime()[0]);
		assertEquals("22:35", cinema2.getScreeningTime()[1]);
		
//		assertEquals(new int[]{90, 137}, cinema2.getMovieEndTime());
		assertEquals(90, cinema2.getMovieEndTime()[0]);
		assertEquals(137, cinema2.getMovieEndTime()[1]);
    }
	
	@Test
	public void testRoute ()
	{
		assertEquals(2147483647, route1.getArrival_time());
		assertEquals(16129, route1.getDeparture_time());
		assertEquals(8, route1.getDuration());
		assertEquals(2, route1.getDistance());
		assertEquals("Stra�e des 17. Juni 135", route1.getStart_address());
		assertEquals("Berliner Stra�e 13", route1.getEnd_address());
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
//		long startTime = 1392136150296L; // 11.02.2014 17:29
		
		Iterator<Entry<Cinema, Restaurant>> instance = optimization.getOptimalCombination(cinemaRestaurantRoutes, 10). entrySet().iterator();
//		for(Entry<Cinema, Restaurant> instance: optimization.getOptimalCombination(cinemaRestaurantRoutes). entrySet().iterator())
		while(instance.hasNext())
		{
//			Cinema cinemaTest = instance.;
//			assertEquals(cinema1, cinemaTest);
//			Restaurant restaurantTest = instance.getValue();
//			assertEquals(restaurant1, restaurantTest);
			instance.toString();
			Map<Cinema, Restaurant> expected = new HashMap<Cinema, Restaurant>();
			expected.put(cinema1, restaurant1);
//			assertEquals(expected, instance);
		}
	}
	
	public static void main(String args[]) throws Exception {
		OptimizationTest test = new OptimizationTest();
		test.setUp();
//		test.testRestaurant();
//		test.testCinema();
//		test.testRoute();
//		test.testOptimalCombination();
		test.testOptimization();
	}
}

