package de.tuberlin.hdis14.core;

import java.util.List;

public interface Optimization {

	/**
	 * 
	 * @param cinResCombo All valid combinations of cinema and restaurant
	 * @return List of 3 optimal combinations of cinema and restaurant 
	 */
	public List<OptimalSolution> getOptimalCombination(List<CinemaRestaurantCombination> cinResCombo);
}
