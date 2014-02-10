package de.tuberlin.hdis14.core;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import de.tuberlin.hdis14.cinema.Cinema;
import de.tuberlin.hdis14.publictransport.CinemaRestaurantRoute;
import de.tuberlin.hdis14.publictransport.Route;
import de.tuberlin.hdis14.restaurant.Restaurant;
import de.tuberlin.hdis14.ui.CalendarBean;
import de.tuberlin.hdis14.ui.UserCriteria;

public class Optimization implements IOptimization {

//	@SuppressWarnings("deprecation")
//	Date bufferBeforeCinema = new Date(0, 0, 0, 0, 15, 0);
	
	int weightTripDuration = 1;
	int factorWalkingDistance = 20;
	int maxOptimalCombinations = 3;
	
	//TODO: Exceptions im Fail Fall
	@Override
	public Map<Cinema, Restaurant> getOptimalCombination(List<CinemaRestaurantRoute> cinemaRestList) {
		
		UserCriteria userCriteria = new UserCriteria();
		List<OptimalCombination> optimalCombinationsTemp;
		
		for(CinemaRestaurantRoute cinemaInstance: cinemaRestList)
		{
			//TODO: ist Screening time Start time vom Film
			int durationOfTrip = cinemaInstance.getCinema().getScreeningTime() - userCriteria.getTime();
			int weightedDurationOfTrip = durationOfTrip * weightTripDuration;
			
			int weightedWalkingDistance;
			int bar = Integer.MAX_VALUE;
			Restaurant restaurantTemp;
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
						restaurantTemp,
						weightedDurationOfTrip, 
						weightedWalkingDistance));
			}
		}
		
		List<OptimalCombination> optimalCombinations;
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
			
			if (isMoreOptimal || optimalCombinations.size() < maxOptimalCombinations)
			{
				optimalCombinations.add(combinationTemp);
				if(optimalCombinations.size() > maxOptimalCombinations)
				{
					OptimalCombination foo;
					int highestWeight=Integer.MIN_VALUE;
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
		
		Map<Cinema, Restaurant> threeOptimcalCombinations;
		for(OptimalCombination combination : optimalCombinations)
		{
			threeOptimcalCombinations.put(combination.getCinema(), combination.getRestaurant());
			
		}
		
		if(threeOptimcalCombinations.size() > maxOptimalCombinations)
		{
			System.out.println("threeOptimcalCombinations.size = " +  threeOptimcalCombinations.size() + "THIS SHOULD NOT HAPPEN");
		}
		
		return threeOptimcalCombinations;
	}

}











