package de.tuberlin.hdis14.ui;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import com.jaunt.NodeNotFound;
import com.jaunt.ResponseException;

import de.tuberlin.hdis14.cinema.Cinema;
import de.tuberlin.hdis14.cinema.CinemaDetails;
import de.tuberlin.hdis14.restaurant.IRestaurant;
import de.tuberlin.hdis14.restaurant.Restaurant;
import de.tuberlin.hdis14.restaurant.RestaurantImpl;

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
	private String type="10";
	private String cuisine="1";
	private int maxDistance;
	private Map<String,Object> movies;
	private String selectedMovie="Kill Your Darlings";
    private	String durationInfo;



	private String optionCinema1="Sony Center";
	private String optionCinema2="Toshiba Center";
	private String optionCinema3="Fujitsu Center";
	private String optionRestaurant1="Amrit";
	private String optionRestaurant2="Calcutta";
	private String optionRestaurant3="Heer";
	
	//prateek's variable
	List<Cinema> cinemaList;
	
	//State variable
	Map<Cinema, Restaurant> selectedMap;
	
	public void updateMoviesListener()
	{
		movies = new LinkedHashMap<String,Object>();
		movies.put("Kill Your Darlings", "Kill Your Darlings"); //label, value
		movies.put("12 Years a Slave", "12 Years a Slave");
		movies.put("The Wolf of Wall Street", "The Wolf of Wall Street");
		movies.put("All Is Lost", "All Is Lost");
		movies.put("Robocop", "Robocop");
		this.setSelectedMovie("Kill Your Darlings");
		System.out.println("update movies being called");
	//	System.out.println(this.date);
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

	public void callPrateek() throws NodeNotFound, ResponseException{
		CinemaDetails check=new CinemaDetails();


		this.cinemaList = check.getCinemaDetails("berlin", this.selectedMovie); 
		                             //(only city name, movieName)
		
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
	
	

	public String getOptionCinema1() {
		return optionCinema1;
	}
	public void setOptionCinema1(String optionCinema1) {
		this.optionCinema1 = optionCinema1;
	}
	public String getOptionCinema2() {
		return optionCinema2;
	}
	public void setOptionCinema2(String optionCinema2) {
		this.optionCinema2 = optionCinema2;
	}
	public String getOptionCinema3() {
		return optionCinema3;
	}
	public void setOptionCinema3(String optionCinema3) {
		this.optionCinema3 = optionCinema3;
	}
	public String getOptionRestaurant1() {
		return optionRestaurant1;
	}
	public void setOptionRestaurant1(String optionRestaurant1) {
		this.optionRestaurant1 = optionRestaurant1;
	}
	public String getOptionRestaurant2() {
		return optionRestaurant2;
	}
	public void setOptionRestaurant2(String optionRestaurant2) {
		this.optionRestaurant2 = optionRestaurant2;
	}
	public String getOptionRestaurant3() {
		return optionRestaurant3;
	}
	public void setOptionRestaurant3(String optionRestaurant3) {
		this.optionRestaurant3 = optionRestaurant3;
	}
	
	public void callJanani()
	{
		FacesContext.getCurrentInstance().getAttributes().put("maxDistance", getMaxDistance());
		FacesContext.getCurrentInstance().getAttributes().put("departureTime", getTime());
		System.out.println("Faces: " +FacesContext.getCurrentInstance().getAttributes().get("maxDistance"));
		System.out.println("Faces: "+FacesContext.getCurrentInstance().getAttributes().get("departureTime"));
		System.out.println("janani method called");
		System.out.println(this.cuisine);
		System.out.println(this.type);
		System.out.println(this.maxDistance);
		if(!validateInputs()){
			return;
		}
		String startLocation = this.houseNumber+","+this.streetAddress+","+this.zipCode;
		IRestaurant refRestaurant = RestaurantImpl.getInstance();
		try {
			callPrateek();
		} catch (NodeNotFound e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ResponseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Map<Cinema, Restaurant> mapReturned = refRestaurant.fromFaisal(startLocation, this.time, this.cinemaList, this.cuisine, this.type,this.maxDistance);
		this.selectedMap=mapReturned;
		
		
		 Iterator<Entry<Cinema, Restaurant>> it = mapReturned.entrySet().iterator();
		 int i=1;
	       Restaurant restaurant=null;
	        Cinema cinema = null;
	        Map.Entry<Cinema, Restaurant> pairs = null;
		    while (it.hasNext()) {
		       pairs = it.next();
		        System.out.println(pairs.getKey() + " = " + pairs.getValue());
		        cinema = pairs.getKey();
		        restaurant = pairs.getValue();
		        if(i==1){
		        	optionCinema1 = cinema.getTheaterName();
		        	optionRestaurant1 = restaurant.getName();
		        }else if(i==2){
		        	optionCinema2 =cinema.getTheaterName();
		        	optionRestaurant2 = restaurant.getName();
		        	
		        }else if(i==3){
		        	optionCinema3 =cinema.getTheaterName();
		        	optionRestaurant3 = restaurant.getName();
		        }
		        i++;
		        //it.remove(); 
		    }
	   
	}
	
	private boolean validateInputs()
	{
		System.out.println("------Error Check-----");
		System.out.println(this.getCuisine());//null
		System.out.println(this.getHouseNumber());
		if(this.getHouseNumber()==null){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("House No. Can't be empty"));
			return false;
		}
		if(this.getMaxDistance()==0){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Max distance can't be zero"));
			return false;
		}
		System.out.println(this.getMaxDistance());
		System.out.println(this.getPersons());
		if(this.getPersons()==0){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Person count can't be zero"));
			return false;
		}
		System.out.println(this.getSelectedMovie());//null
		if(this.getSelectedMovie()==null){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Please select a movie"));
			return false;
		}
		System.out.println(this.getStreetAddress());
		if(this.getStreetAddress()==null){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Please enter a street address"));
			return false;
		}
		System.out.println(this.getTime());//null
		if(this.getTime()==null || this.getTime().length()<5 || this.getTime().charAt(2)!=':'){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Invalid time format"));
			return false;
		}
		System.out.println(this.getType());//null
		if(this.getType()==null){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Please select a restaurant type"));
			return false;
		}
		System.out.println(this.getZipCode());
		if(this.getZipCode()==null){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Zipcode can't be null"));
			return false;
		}
		System.out.println(this.getDate());
		System.out.println("-----End of Error Check----");
		return true;
	}
	
}
