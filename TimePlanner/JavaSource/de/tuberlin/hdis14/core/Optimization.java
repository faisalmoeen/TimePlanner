package de.tuberlin.hdis14.core;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import de.tuberlin.hdis14.cinema.Cinema;
import de.tuberlin.hdis14.publictransport.CinemaRestaurantRoute;
import de.tuberlin.hdis14.publictransport.PublicTransport;
import de.tuberlin.hdis14.publictransport.Route;
import de.tuberlin.hdis14.restaurant.Restaurant;
import de.tuberlin.hdis14.ui.CalendarBean;
import de.tuberlin.hdis14.ui.UserCriteria;

public class Optimization implements IOptimization {
	int weightTripDuration = 1;
	int factorWalkingDistance = 20;
	int maxOptimalCombinations = 3;
	
	//TODO: Exceptions im Fail Fall
	@Override
	public Map<Cinema, Restaurant> getOptimalCombination(long userStartTime, List<CinemaRestaurantRoute> cinemaRestList) {
		
//		UserCriteria userCriteria = new UserCriteria();
		FakeUserCriteria userCriteria = FakeUserCriteria.getInstance();
		List<OptimalCombination> optimalCombinationsTemp = new ArrayList<OptimalCombination>();
		
		for(CinemaRestaurantRoute cinemaInstance: cinemaRestList)
		{
			//TODO: ist Screening time Start time vom Film
			
			Calendar cal = Calendar.getInstance(); 

//            String tm[]=       userCriteria.getTime().split(":");
			    
//			cal.set(Calendar.HOUR, Integer.parseInt(tm[0])); 
//			cal.set(Calendar.MINUTE, Integer.parseInt(tm[1])); 
//			cal.set(Calendar.SECOND, 0);
//			long startTimeFromUser = cal.getTimeInMillis();
			
			String tm[] = cinemaInstance.getCinema().getScreeningTime()[0].split(":");
			cal.set(Calendar.HOUR, Integer.parseInt(tm[0])); 
			cal.set(Calendar.MINUTE, Integer.parseInt(tm[1])); 
			cal.set(Calendar.SECOND, 0); 
			long startTimeFromCinema = cal.getTimeInMillis(); 
			
//			long durationOfTrip = cinemaInstance.getCinema().getScreeningTime()[0] - userCriteria.getTime();
//			long durationOfTrip = startTimeFromCinema - startTimeFromUser;
			long durationOfTrip = startTimeFromCinema - userStartTime;
			long weightedDurationOfTrip = durationOfTrip * weightTripDuration;
			
			long weightedWalkingDistance;
			int bar = Integer.MAX_VALUE;
			Restaurant restaurantTemp = new Restaurant();
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
		
		List<OptimalCombination> optimalCombinations = new ArrayList<OptimalCombination>();
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
		
		Map<Cinema, Restaurant> threeOptimcalCombinations = new HashMap<Cinema, Restaurant>();
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











