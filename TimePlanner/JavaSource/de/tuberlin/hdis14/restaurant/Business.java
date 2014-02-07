package de.tuberlin.hdis14.restaurant;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 
 * @author JANANI
 *
 */
public class Business implements Serializable{
	


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String is_claimed;
	String rating;
	String mobile_url;
	String rating_img_url;
	String review_count;
	String snippet_image_url;
	String rating_img_url_small;
	String url;
	String snippet_text;
	String image_url;
	String rating_img_url_large;
	String id;
	String is_closed;
	String display_phone;
	public String getDisplay_phone() {
		return display_phone;
	}
	public void setDisplay_phone(String display_phone) {
		this.display_phone = display_phone;
	}
	public String getIs_claimed() {
		return is_claimed;
	}
	public void setIs_claimed(String is_claimed) {
		this.is_claimed = is_claimed;
	}
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
	public String getMobile_url() {
		return mobile_url;
	}
	public void setMobile_url(String mobile_url) {
		this.mobile_url = mobile_url;
	}
	public String getRating_img_url() {
		return rating_img_url;
	}
	public void setRating_img_url(String rating_img_url) {
		this.rating_img_url = rating_img_url;
	}
	public String getReview_count() {
		return review_count;
	}
	public void setReview_count(String review_count) {
		this.review_count = review_count;
	}
	public String getSnippet_image_url() {
		return snippet_image_url;
	}
	public void setSnippet_image_url(String snippet_image_url) {
		this.snippet_image_url = snippet_image_url;
	}
	public String getRating_img_url_small() {
		return rating_img_url_small;
	}
	public void setRating_img_url_small(String rating_img_url_small) {
		this.rating_img_url_small = rating_img_url_small;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getSnippet_text() {
		return snippet_text;
	}
	public void setSnippet_text(String snippet_text) {
		this.snippet_text = snippet_text;
	}
	public String getImage_url() {
		return image_url;
	}
	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}
	public String getRating_img_url_large() {
		return rating_img_url_large;
	}
	public void setRating_img_url_large(String rating_img_url_large) {
		this.rating_img_url_large = rating_img_url_large;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIs_closed() {
		return is_closed;
	}
	public void setIs_closed(String is_closed) {
		this.is_closed = is_closed;
	}
	public ArrayList<ArrayList<String>> getCategories() {
		return categories;
	}
	public void setCategories(ArrayList<ArrayList<String>> categories) {
		this.categories = categories;
	}
	ArrayList<ArrayList<String>> categories;
	
	private String name;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public RestaurantAddress getLocation() {
		return location;
	}
	public void setLocation(RestaurantAddress location) {
		this.location = location;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	private RestaurantAddress location;
	private String phone;



}
