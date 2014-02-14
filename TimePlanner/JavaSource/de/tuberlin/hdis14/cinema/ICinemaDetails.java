package de.tuberlin.hdis14.cinema;

import java.util.List;

import com.jaunt.NodeNotFound;
import com.jaunt.ResponseException;

public interface ICinemaDetails {

	public  List<Cinema> getCinemaDetails(String near, String movie)
			throws ResponseException, NodeNotFound;

	public List<String> getMovieList(String string) throws ResponseException, NodeNotFound;

	public List<String> removeDuplicates(List<String> movies);

}