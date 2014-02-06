package de.tuberlin.hdis14.ui;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.AjaxBehaviorEvent;

//@ManagedBean(name="userCriteria")
//@SessionScoped

public class UserCriteria {

	private String streetAddress="theodor heuss platz";
	private String houseNumber="5";
	private String zipCode="14052";
	private int persons=6;
	private Date date;
	private String time;
	private String name="Faisal";
	private String type;
	private String cuisine;
	private int maxDistance;
	private Map<String,Object> movies;
	private String selectedMovie;
	
	public void updateMoviesListener()
	{
		movies = new LinkedHashMap<String,Object>();
		movies.put("Cream Latte", "Cream Latte"); //label, value
		movies.put("Extreme Mocha", "Extreme Mocha");
		movies.put("Buena Vista", "Buena Vista");
		System.out.println("update movies being called");
		System.out.println(this.date);
	}
	public String getSelectedMovie() {
		return selectedMovie;
	}

	public void setSelectedMovie(String selectedMovie) {
		this.selectedMovie = selectedMovie;
	}

	public Map<String, Object> getMovies() {
		System.out.println("getMovies() being called");
		return movies;
	}

	public void setMovies(Map<String, Object> coffee2Value) {
		this.movies = coffee2Value;
	}

	static{
		
	}
	
	public UserCriteria() {
		System.out.println("I am alive");
	}

	public String getStreetAddress() {
		return streetAddress;
	}

	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	public String getHouseNumber() {
		return houseNumber;
	}

	public void setHouseNumber(String houseNumber) {
		this.houseNumber = houseNumber;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public int getPersons() {
		return persons;
	}

	public void setPersons(int persons) {
		this.persons = persons;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	public String getName() {
		return name + " Qaisar";
	}

	public void setName(String name) {
		this.name = name;
	}

	public String sayHello() {
		return "greeting";
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCuisine() {
		return cuisine;
	}

	public void setCuisine(String cuisine) {
		this.cuisine = cuisine;
	}

	public int getMaxDistance() {
		return maxDistance;
	}

	public void setMaxDistance(int maxDistance) {
		this.maxDistance = maxDistance;
	}

	public void callJanani()
	{
		System.out.println("janani method called");
		System.out.println(this.cuisine);
		System.out.println(this.type);
		System.out.println(this.maxDistance);
	}
	
	
}
