package de.tuberlin.hdis14.restaurant;



import java.io.Serializable;
import java.util.ArrayList;
/**
 * 
 * @author JANANI
 *
 */
public class RestaurantAddress implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String city;
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}

	public String getPostal_code() {
		return postal_code;
	}
	public void setPostal_code(String postal_code) {
		this.postal_code = postal_code;
	}
	private ArrayList<String> address;
	private String postal_code;
	
	ArrayList<String> display_address;
	ArrayList<String> neighborhoods;
public ArrayList<String> getAddress() {
		return address;
	}
	public void setAddress(ArrayList<String> address) {
		this.address = address;
	}
	public ArrayList<String> getDisplay_address() {
		return display_address;
	}
	public void setDisplay_address(ArrayList<String> display_address) {
		this.display_address = display_address;
	}
	public ArrayList<String> getNeighborhoods() {
		return neighborhoods;
	}
	public void setNeighborhoods(ArrayList<String> neighborhoods) {
		this.neighborhoods = neighborhoods;
	}
	public String getState_code() {
		return state_code;
	}
	public void setState_code(String state_code) {
		this.state_code = state_code;
	}
	public String getCountry_code() {
		return country_code;
	}
	public void setCountry_code(String country_code) {
		this.country_code = country_code;
	}
String state_code;
String country_code;

}
