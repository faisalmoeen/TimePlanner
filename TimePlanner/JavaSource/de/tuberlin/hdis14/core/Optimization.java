package de.tuberlin.hdis14.core;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import de.tuberlin.hdis14.cinema.Cinema;
import de.tuberlin.hdis14.publictransport.CinemaRestaurantRoute;
import de.tuberlin.hdis14.publictransport.Route;
import de.tuberlin.hdis14.restaurant.Restaurant;
import de.tuberlin.hdis14.ui.UserCriteria;

public class Optimization implements IOptimization {
	//private fields
//	private UserCriteria userCriteria;
	private FakeUserCriteria userCriteria;
	private List<OptimalCombination> optimalCombinationsTemp;
	private List<OptimalCombination> optimalCombinations;
	
	//Optimization Parameters
	private int weightTripDuration = 1;
	private int factorWalkingDistance = 20;
	private int maxOptimalCombinations = 3;
	
	//TODO: Exceptions im Fail Fall
	@Override
	public Map<Cinema, Restaurant> getOptimalCombination(List<CinemaRestaurantRoute> cinemaRestList) {
//		userCriteria = new UserCriteria();
		userCriteria = FakeUserCriteria.getInstance();
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
		for(OptimalCombination combination : optimalCombinations)
		{
			threeOptimcalCombinations.put(combination.getCinema(), combination.getRestaurant());
			
		}
		return threeOptimcalCombinations;
	}

	private void extractOptima() {
		for(OptimalCombination combinationTemp : optimalCombinationsTemp)
		{
			boolean isMoreOptimal = false;
			//TODO was passiert wenn optimalCombinations leer ist?
			for(OptimalCombination combination : optimalCombinations)
			{
				if (combinationTemp.getWeight() < combination.getWeight()) {
					isMoreOptimal = true;
					//TODO break???
				}
			}
			
			if(!optimalCombinations.contains(combinationTemp.getCinema()))
			{
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
			}else{
				System.out.println("it works");
				//TODO: remove identical cinema that has the longer walking distance 
			}
		}
	}

	private void getOptimalCombinationTemp(
			List<CinemaRestaurantRoute> cinemaRestList) {
		for(CinemaRestaurantRoute cinemaInstance: cinemaRestList)
		{
			//TODO: ist Screening time Start time vom Film
			
			Calendar cal = Calendar.getInstance(); 
			userCriteria.setTime("20:30");
			userCriteria.setMaxDistance(50);
			
            String tm[]=  userCriteria.getTime().split(":");
			    
			cal.set(Calendar.HOUR, Integer.parseInt(tm[0])); 
			cal.set(Calendar.MINUTE, Integer.parseInt(tm[1])); 
			cal.set(Calendar.SECOND, 0);
			long startTimeFromUser = cal.getTimeInMillis();
			
			tm = cinemaInstance.getCinema().getScreeningTime()[0].split(":");
			cal.set(Calendar.HOUR, Integer.parseInt(tm[0])); 
			cal.set(Calendar.MINUTE, Integer.parseInt(tm[1])); 
			cal.set(Calendar.SECOND, 0); 
			long startTimeFromCinema = cal.getTimeInMillis(); 
			
//			long durationOfTrip = cinemaInstance.getCinema().getScreeningTime()[0] - userCriteria.getTime();
			long durationOfTrip = startTimeFromCinema - startTimeFromUser;
			long weightedDurationOfTrip = durationOfTrip * weightTripDuration;
			
			long weightedWalkingDistance;
			int bar = Integer.MAX_VALUE;
			//Restaurant restaurantTemp = new Restaurant("sth...............................");
			for(Entry<Restaurant, Route> restaurantRouteInstance : cinemaInstance.getRestaurantRouteList().entrySet())
			{
				int temp = restaurantRouteInstance.getValue().getDistance() / userCriteria.getMaxDistance();
				weightedWalkingDistance = temp * factorWalkingDistance;
				
//				if(temp < bar)
//				{
//					bar = temp;
//					restaurantTemp = restaurantRouteInstance.getKey();
//					weightedWalkingDistance = temp * factorWalkingDistance;
//				}
//			}
				
				optimalCombinationsTemp.add(new OptimalCombination(
						cinemaInstance.getCinema(),
						(Restaurant)restaurantRouteInstance.getKey(),
						weightedDurationOfTrip, 
						weightedWalkingDistance));
			}
		}
	}

}











