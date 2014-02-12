package de.tuberlin.hdis14.restaurant;


import java.io.Serializable;
import java.util.HashMap;



/**
 * 
 * @author JANANI
 *
 */
public class Restaurant extends HashMap<String, String> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String name;
	
	
	public String getName() {
		return name;
	}
	private String rating;
	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getRestaurantAddress() {
		return restaurantAddress;
	}

	public void setRestaurantAddress(String restaurantAddress) {
		this.restaurantAddress = restaurantAddress;
	}
	private String restaurantAddress;
	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	private String phone;



	public Restaurant(String address){
		super();
		restaurantAddress=address;
	}
	
	public Restaurant()
	{
		super();
	}
}


