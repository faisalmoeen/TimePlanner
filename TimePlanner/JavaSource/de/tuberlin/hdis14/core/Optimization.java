package de.tuberlin.hdis14.core;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.faces.context.FacesContext;

import de.tuberlin.hdis14.cinema.Cinema;
import de.tuberlin.hdis14.publictransport.CinemaRestaurantRoute;
import de.tuberlin.hdis14.publictransport.Route;
import de.tuberlin.hdis14.restaurant.Restaurant;

public class Optimization implements IOptimization {
	//private fields
	private List<OptimalCombination> optimalCombinationsTemp;
	private List<OptimalCombination> optimalCombinations;
	
	//Optimization Parameters
	private int weightTripDuration = 1;
	private int factorWalkingDistance = 20;
	private int maxOptimalCombinations = 3;
	
	//TODO: Exceptions im Fail Fall
	@Override
	public Map<Cinema, Restaurant> getOptimalCombination(List<CinemaRestaurantRoute> cinemaRestList, int maxDistance) {
		System.out.println("Entering Optimization");
		System.out.println("Got " + cinemaRestList.size() + " combinations");
		
		optimalCombinationsTemp = new ArrayList<OptimalCombination>();
		getOptimalCombinationTemp(cinemaRestList);
		
		optimalCombinations = new ArrayList<OptimalCombination>();
		extractOptima();
		
		Map<Cinema, Restaurant> threeOptimcalCombinations = createReturnValue();
		
		if(threeOptimcalCombinations.size() > maxOptimalCombinations)
		{
			System.out.println("threeOptimcalCombinations.size = " +  threeOptimcalCombinations.size() + "THIS SHOULD NOT HAPPEN");
		}
		
		return threeOptimcalCombinations;
	}

	private Map<Cinema, Restaurant> createReturnValue() {
		Map<Cinema, Restaurant> threeOptimcalCombinations = new HashMap<Cinema, Restaurant>();
		System.out.println("Found " + optimalCombinations.size() + " optimal combinations");
		for(OptimalCombination combination : optimalCombinations)
		{
			threeOptimcalCombinations.put(combination.getCinema(), combination.getRestaurant());
			System.out.println("----- Cinema "+ combination.getCinema().getTheaterName());
			System.out.println("Restaurant " + combination.getRestaurant().getName());
			System.out.println("WeightedDurationOfTrip " + combination.getWeight() + " -----");
		}
		return threeOptimcalCombinations;
	}

	private void extractOptima() {
		for(OptimalCombination combinationTemp : optimalCombinationsTemp)
		{
			boolean isMoreOptimal = false;
			for(OptimalCombination combination : optimalCombinations)
			{
				if (combinationTemp.getWeight() < combination.getWeight()) {
					isMoreOptimal = true;
					//TODO break???
				}
			}
			if (isMoreOptimal || optimalCombinations.size() < maxOptimalCombinations)
			{
				optimalCombinations.add(combinationTemp);
				if(optimalCombinations.size() > maxOptimalCombinations)
				{
					OptimalCombination foo = null;
					long highestWeight=Integer.MIN_VALUE;
					for(OptimalCombination combination : optimalCombinations)
					{
						if(highestWeight < combination.getWeight())
						{
							highestWeight = combination.getWeight();
							foo = combination;
						}
					}
					optimalCombinations.remove(foo);
				}
			}
		}
	}

	private void getOptimalCombinationTemp(
			List<CinemaRestaurantRoute> cinemaRestList) {
		for(CinemaRestaurantRoute cinemaInstance: cinemaRestList)
		{
			String departureTime = (String) FacesContext.getCurrentInstance().getAttributes().get("departureTime"); //"20:00";
			int maxDistance = Integer.parseInt("500"); 
//			String blb = 		(String) FacesContext.getCurrentInstance().getAttributes().get("maxDistance");

			Calendar cal = Calendar.getInstance();

            String tm[]= departureTime.split(":");

			cal.set(Calendar.HOUR, Integer.parseInt(tm[0]));
			cal.set(Calendar.MINUTE, Integer.parseInt(tm[1]));
			cal.set(Calendar.SECOND, 0);
			long startTimeFromUser = cal.getTimeInMillis();
			
			tm = cinemaInstance.getCinema().getScreeningTime()[0].split(":");
			cal.set(Calendar.HOUR, Integer.parseInt(tm[0]));
			cal.set(Calendar.MINUTE, Integer.parseInt(tm[1]));
			cal.set(Calendar.SECOND, 0);
			
			long startTimeFromCinema = cal.getTimeInMillis(); 
						
			long durationOfTrip = startTimeFromCinema - startTimeFromUser;
			long weightedDurationOfTrip = durationOfTrip * weightTripDuration;
			long weightedWalkingDistance;
			
//			System.out.println("Got " + cinemaInstance.getRestaurantList().size() + " combinations");
			
//			Iterator<Entry<Restaurant, Route>> iterator = cinemaInstance.getRouteList().entrySet().iterator();
//			while(iterator.hasNext())
			
			for(int i=0;i<cinemaInstance.getRouteList().size();i++)
			{
//				Entry<Restaurant, Route>restaurantRouteInstance = iterator.next();
				int temp = (cinemaInstance.getRouteList().get(i)).getDistance();// / maxDistance;
				
				
				weightedWalkingDistance = temp * factorWalkingDistance;
				
				optimalCombinationsTemp.add(new OptimalCombination(
						cinemaInstance.getCinema(),
						(Restaurant)cinemaInstance.getRestaurantList().get(i),
						weightedDurationOfTrip, 
						weightedWalkingDistance));
				System.out.println("added to optimal combination:");
				System.out.println("Cinema" + cinemaInstance.getCinema().getTheaterName());
				System.out.println("Restaurant " + cinemaInstance.getRestaurantList().get(i).getName());
				System.out.println("WeightedDurationOfTrip " + weightedDurationOfTrip);
				System.out.println("WeightedWalkingDistance " + weightedWalkingDistance);
			}
			
			
			
			
//			for(Entry<Restaurant, Route> restaurantRouteInstance : cinemaInstance.getRestaurantRouteList().entrySet())
//			{
//				int temp = restaurantRouteInstance.getValue().getDistance(); // / userCriteria.getMaxDistance();
//				weightedWalkingDistance = temp * factorWalkingDistance;
//				
//				optimalCombinationsTemp.add(new OptimalCombination(
//						cinemaInstance.getCinema(),
//						(Restaurant)restaurantRouteInstance.getKey(),
//						weightedDurationOfTrip, 
//						weightedWalkingDistance));
//				System.out.println("added to optimal combination:");
//				System.out.println("Cinema" + cinemaInstance.getCinema().getTheaterName());
//				System.out.println("Restaurant" + restaurantRouteInstance.getKey().getName());
//			}
		}
		System.out.println("Added " + optimalCombinationsTemp.size() + " to optimalCombinationsTemp\n");
	}

}











