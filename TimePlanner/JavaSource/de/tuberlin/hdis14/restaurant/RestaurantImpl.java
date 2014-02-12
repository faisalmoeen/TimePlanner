/**
 * 
 */
package de.tuberlin.hdis14.restaurant;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.scribe.builder.ServiceBuilder;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.tuberlin.hdis14.cinema.Cinema;
import de.tuberlin.hdis14.publictransport.PublicTransport;

/**
 * @author JANANI
 * 
 */
public class RestaurantImpl implements IRestaurant {

	OAuthService service;
	Token accessToken;
	
	public RestaurantImpl(){
		
	}

	/**
	 * Setup the Yelp API OAuth credentials.
	 * 
	 * OAuth credentials are available from the developer site, under Manage API
	 * access (version 2 API).
	 * 
	 * @param consumerKey
	 *            Consumer key
	 * @param consumerSecret
	 *            Consumer secret
	 * @param token
	 *            Token
	 * @param tokenSecret
	 *            Token secret
	 */
	public RestaurantImpl(String consumerKey, String consumerSecret,
			String token, String tokenSecret) {
		this.service = new ServiceBuilder().provider(YelpApi2.class)
				.apiKey(consumerKey).apiSecret(consumerSecret).build();
		this.accessToken = new Token(token, tokenSecret);
	}
	
	private static RestaurantImpl instance = null;
	
	 public static RestaurantImpl getInstance() {
	      if(instance == null) {
	         instance = new RestaurantImpl();
	      }
	      return instance;
	   }

	/**
	 * Search with term and location.
	 * 
	 * @param term
	 *            Search term
	 * @param latitude
	 *            Latitude
	 * @param longitude
	 *            Longitude
	 * @return JSON string response
	 */
	public String search(String type,String term, String radius, String location) {
		OAuthRequest request = new OAuthRequest(Verb.GET,
				"http://api.yelp.com/v2/search");

		String cuisineType="restaurants";
			if(type.equalsIgnoreCase("Bar")) {
				cuisineType ="bars";
			} else if(type.equalsIgnoreCase("Cafe")){
				cuisineType ="cafes";
			}
		    //request.addQuerystringParameter("term", term);
			String cuisine="german";
			if(term.equalsIgnoreCase("Chinese")){
				cuisine="chinese";
				
			}else if(term.equalsIgnoreCase("Japanese")){
				cuisine="japanese";
			}else if(term.equalsIgnoreCase("Italian")){
				cuisine="italian";
			}else if(term.equalsIgnoreCase("Indisch")){
				cuisine="indpak";
			}else if(term.equalsIgnoreCase("German")){
				cuisine="german";
			}else if(term.equalsIgnoreCase("French")){
				cuisine="french";
			}else if(term.equalsIgnoreCase("Cocktail")){
				cuisine="cocktail";
			}
			
			if(!radius.equals("")){
				 request.addQuerystringParameter("radius_filter", radius);
			}
		   if(cuisineType.equalsIgnoreCase("cafes")){
			   request.addQuerystringParameter("term", cuisineType);  
		   }else{
			   request.addQuerystringParameter("term", cuisine+"+"+cuisineType);
		   }
			
			request.addQuerystringParameter("location", location);
		
		System.out.println(request.getQueryStringParams());
		this.service.signRequest(this.accessToken, request);
		Response response = request.send();
		return response.getBody();
	}
	@Override
	public Map<Cinema, Restaurant> fromFaisal(String startLocation, String startTime, List<Cinema> cinemaList,String cuisine, String type, int maxDistance){
		
		
		List<CinemaRestaurant> listCinemaRest = new ArrayList<CinemaRestaurant>();
		List<Cinema> cinemaL = PublicTransport.getInstance().callJelena1(startLocation,startTime,cinemaList);
		List<Restaurant> restList =null;
		
		CinemaRestaurant cinemaRestList = new CinemaRestaurant();
		
		for(Cinema c : cinemaL){
			restList = getRestaurants(c, cuisine, type,maxDistance);
			cinemaRestList.setCinema(c);
			cinemaRestList.setRestaurantList(restList);
			listCinemaRest.add(cinemaRestList);
		}
		
		Map<Cinema, Restaurant> optmCinemaRest = PublicTransport.getInstance().callJelena2(listCinemaRest,maxDistance);
		
		return optmCinemaRest;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.tuberlin.hdis14.restaurant.IRestaurant#getRestaurants(de.tuberlin.
	 * hdis14.cinema.Cinema, java.lang.String, java.lang.String)
	 */
	public List<Restaurant> getRestaurants(Cinema cinema, String cuisine, String type,
			int radius) {

		// Update tokens here from Yelp developers site, Manage API access.
		String consumerKey = "mUNybDa3bIkZyWXJxhuQpg";
		String consumerSecret = "ENQgLkeGp7yzhOkPib4ciEVHL_I";
		String token = "hA3Ce4ao-7cq41a6j1D0kGDpg1C9j2v_";
		String tokenSecret = "1vJIWsFK_KFvz9DOI1LU9kkWK04";

		RestaurantImpl yelp = new RestaurantImpl(consumerKey, consumerSecret,
				token, tokenSecret);
		// String response = yelp.search("burritos", 30.361471, -87.164326);
		String inputJsonString = yelp.search(cuisine,type,String.valueOf(radius),
				cinema.getAddress());

		System.out.println(inputJsonString);

		String toReplace = inputJsonString
				.substring(
						inputJsonString.indexOf("region") - 1,
						(inputJsonString.indexOf(",",
								inputJsonString.indexOf("total")) + 2));
		System.out.println(toReplace);
		String newstring = inputJsonString.replace(toReplace, "");
		System.out.println(newstring);

		ObjectMapper mapper = new ObjectMapper();
		Businesses projs = null;
		try {
			projs = mapper.readValue(newstring, Businesses.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		ArrayList<Business> projects = projs.get("businesses");

		List<Restaurant> restaurantList = new ArrayList<Restaurant>();
		for (Business p : projects) {

			Restaurant restaurant = new Restaurant();
			
			restaurant.setName(p.getName());
			restaurant.setPhone(p.getDisplay_phone());
			
			ArrayList<String> addressList = p.getLocation()
					.getDisplay_address();
			System.out.println(addressList.size());
			String address = "";
			int count = addressList.size();
			for (String addr : addressList) {
				address = address + addr;
				count--;
				if (count != 0) {
					address = address + ",";
				}
			}

			restaurant.setRestaurantAddress(address);
			restaurantList.add(restaurant);

		}

		return restaurantList;
	}

}
