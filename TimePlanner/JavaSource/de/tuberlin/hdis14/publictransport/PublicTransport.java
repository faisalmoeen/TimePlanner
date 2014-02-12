package de.tuberlin.hdis14.publictransport;

import de.tuberlin.hdis14.cinema.*;
import de.tuberlin.hdis14.core.IOptimization;
import de.tuberlin.hdis14.core.Optimization;
import de.tuberlin.hdis14.restaurant.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Date;
import java.util.Map;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.nio.*;
import java.nio.charset.Charset;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PublicTransport implements IPublicTransport {

	private static final String DIRECTIONS_API_BASE = "https://maps.googleapis.com/maps/api/directions";
	private static final String OUT_JSON = "/json";
	private static final String DISTANCE_MATRIX_API_BASE = "https://maps.googleapis.com/maps/api/distancematrix";

	private static PublicTransport instance = null;
	private long departure;

	public static PublicTransport getInstance() {
		if (instance == null) {
			instance = new PublicTransport();
		}
		return instance;
	}

	@Override
	public List<Cinema> callJelena1(String startAddress, String departureTime, List<Cinema> cinemas) {

		HttpURLConnection conn = null;
		StringBuilder DistanceMatrixResults = new StringBuilder();
		URL url;
		long screeningTime;
		// optimum list of cinemas
		List<Cinema> optimumCinemas = new ArrayList<Cinema>();
		Route route = null;

		try {

			for (int j = 0; j < cinemas.size(); j++) {
				// format the json request
				// append one destination per time in the http request
				StringBuilder sb = new StringBuilder(DISTANCE_MATRIX_API_BASE);
				sb.append(OUT_JSON);
				String startAdd = URLEncoder.encode(startAddress,"UTF-8");
				sb.append("?origins=" + startAdd + "&destinations=");
				String cinemaAddress = URLEncoder.encode(cinemas.get(j).getAddress(),"UTF-8");
				sb.append(cinemaAddress);
				sb.append("&sensor=false");
				sb.append("&mode=transit");

			
				url = new URL(sb.toString());				
				System.out.println("URL: " + url.toString());
				conn = (HttpURLConnection) url.openConnection();
				InputStreamReader in = new InputStreamReader(conn.getInputStream());

				int read;
				char[] buff = new char[1024];
				while ((read = in.read(buff)) != -1) {
					DistanceMatrixResults.append(buff, 0, read);
				}
				
				JSONObject rootObject = new JSONObject(DistanceMatrixResults.toString());
				// Get all JSONArray rows
				JSONArray rows = rootObject.getJSONArray("rows");
				//System.out.println("row element\t"+rows.toString());

				// departure time: 18:50. COnvert to Unix timestamp for the
				// requests
				List<String> tempScreens = new ArrayList<String>();
				List<Integer> endTimes = new ArrayList<Integer>();
				departure = strDateToUnixTimestamp(departureTime);

				// Loop over each each row
				for (int i = 0; i < rows.length(); i++) {
					// Get row object
				//	System.out.println("row length\t"+rows.length());
					//System.out.println("counter\t"+i);
					JSONObject row = rows.getJSONObject(i);
					// Get all elements for each row as an array
					JSONArray elements = row.getJSONArray("elements");
					//System.out.println("element element\t"+elements.toString());
					// Iterate each element in the elements array
					for (int i1 = 0; i1 < elements.length(); i1++) {
						// Get the element object
						JSONObject element = elements.getJSONObject(i1);
						// Get duration sub object
						JSONObject duration = element.getJSONObject("duration");
						// Get distance sub object
						System.out.println("duration element\t"+duration.toString());
						JSONObject distance = element.getJSONObject("distance");
						System.out.println("distance element\t"+distance.toString());

						System.out.println("how many screening times are there\t"+cinemas.get(j).getScreeningTime().length);
						// for each screening time check if is is possible to be
						// there in time
						for (int k = 0; k < cinemas.get(j).getScreeningTime().length; k++) {
							screeningTime = strDateToUnixTimestamp(cinemas.get(j).getScreeningTime()[k]);
							//System.out.println("***********check which screening times user can catch");
							if (duration.getInt("value") <= screeningTime - departure) {
								// include this screening time to the result
								//System.out.println("get screening times string\t"+ cinemas.get(j).getScreeningTime()[k]);
								tempScreens.add(cinemas.get(j).getScreeningTime()[k]);
								//System.out.println("user catches these screenings\t"+tempScreens.toString());
								// include this screening's time endtime to the
								// result
								Integer temp = cinemas.get(j).getMovieEndTime()[k];
								endTimes.add(cinemas.get(j).getMovieEndTime()[k]);
								//System.out.println("end times for the screenings\t"+ endTimes+ "\t"+ cinemas.get(j).getMovieEndTime()[k]);

							}
							//System.out.println("***********end of for*******");
						}

						// users can catch at least one screening
						if (tempScreens.size() > 0) {
							// since there are screenings times, endtimes have
							// to be copied too
							int[] tempEndArray = new int[endTimes.size()];
							String[] tempScreenArray = new String[tempScreens.size()];

							for (int l = 0; l < endTimes.size(); l++) {
								tempEndArray[l] = endTimes.get(l);
								tempScreenArray[l] = tempScreens.get(l);
							}
							// here we need a constructor fom the Cinema Class
							optimumCinemas.add(new Cinema(cinemas.get(j).getTheaterName(), tempScreenArray, cinemas
									.get(j).getAddress(), tempEndArray));
						}
					}
				}
			}

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}

		return optimumCinemas;
	}


	// core group will call this method to calculate optimal restaurants for
	// each cinema based on route details (duration,...)
	@Override
	public Map<Cinema, Restaurant> callJelena2(List<CinemaRestaurant> cinRest, int maxDistance) {

		Map<Cinema, Restaurant> results = new HashMap<Cinema, Restaurant>();
		
				List<CinemaRestaurantRoute> allCinemasRestaurantsRoutes = new ArrayList<CinemaRestaurantRoute>();

		for (int i = 0; i < cinRest.size(); i++) {
			Cinema cinema = (cinRest.get(i)).getCinema();
			CinemaRestaurantRoute crr = new CinemaRestaurantRoute(cinema);
			List<Restaurant> restaurants = (cinRest.get(i)).getRestaurantList();
			
			for (int j = 0; j < restaurants.size(); j++) {
				Route route = calculateRoute(cinema.getAddress(),
						(restaurants.get(j)).getRestaurantAddress(), "walking");

				crr.getRestaurantRouteList().put(restaurants.get(j), route);

			}

			allCinemasRestaurantsRoutes.add(crr);
		}
		IOptimization optimization = new Optimization();
		
		results = optimization.getOptimalCombination(allCinemasRestaurantsRoutes, maxDistance);

		
		
		return results;
	}

	private Route calculateRoute(String startAddress, String endAddress,String mode) {

		Route route = null;

		HttpURLConnection conn = null;
		StringBuilder jsonResults = new StringBuilder();

		String startAd;
		String endAd = endAddress;
		String travelMode = mode;

		try {
			startAd = URLEncoder.encode(startAddress,"UTF-8");
			endAd = URLEncoder.encode(endAddress,"UTF-8");
			StringBuilder sb = new StringBuilder(DIRECTIONS_API_BASE);
			sb.append(OUT_JSON);
			sb.append("?origin=" + startAd);
			sb.append("&destination=" + endAd);
			sb.append("&sensor=false");
			sb.append("&mode=" + travelMode);

			URL url = new URL(sb.toString());
			conn = (HttpURLConnection) url.openConnection();
			System.out.println("second url"+url.toString());
			InputStreamReader in = new InputStreamReader(conn.getInputStream());

			int read;
			char[] buff = new char[1024];
			while ((read = in.read(buff)) != -1) {
				jsonResults.append(buff, 0, read);
			}

		} catch (MalformedURLException e) {
			// Log.e(LOG_TAG, "Error processing Places API URL", e);
			// return resultList;
		} catch (IOException e) {
			// Log.e(LOG_TAG, "Error connecting to Places API", e);
			// return resultList;
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}

		try {

			// Create a JSON object hierarchy from the results
			JSONObject jsonObj = new JSONObject(jsonResults.toString());
			JSONArray routesJsonArray = jsonObj.getJSONArray("routes");

			// Extract the Place descriptions from the results

			// Loop over each each route
			for (int i = 0; i < routesJsonArray.length(); i++) {
				route = new Route();
				// Get route object
				JSONObject routeJsonObj = routesJsonArray.getJSONObject(i);
				// Get all legs elements for each row as an array
				JSONArray legsJsonArray = routeJsonObj.getJSONArray("legs");

				// Iterate each "leg" object in the elements array
				for (int j = 0; j < legsJsonArray.length(); j++) {
					// Get the leg Json object
					JSONObject legObject = legsJsonArray.getJSONObject(j);

					//JSONObject arrival_time = legObject.getJSONObject("arrival_time");
					//JSONObject departure_time = legObject.getJSONObject("departure_time");
					JSONObject distance = legObject.getJSONObject("distance");
					JSONObject duration = legObject.getJSONObject("duration");

					// set parameters of each route


					route.setDuration(duration.getInt("value"));

					route.setDistance(distance.getInt("value"));

					route.setStart_address(legObject.getString("start_address"));

					route.setEnd_address(legObject.getString("end_address"));

				}

			}

		} catch (JSONException e) {
			System.out.println("LOG_TAG, Error processing JSON results"
					+ e.toString());
		}

		return route;
	}
/*
	private Route calculateCinemaRoute(String startAddress, String endAddress,
			String mode, long departureTime) {

		Route route = null;

		HttpURLConnection conn = null;
		StringBuilder jsonResults = new StringBuilder();

		String startAd = startAddress;
		String endAd = endAddress;
		long depTime = departureTime;
		String travelMode = mode;

		try {
			StringBuilder sb = new StringBuilder(DIRECTIONS_API_BASE);
			sb.append(OUT_JSON);
			sb.append("?origin=" + startAd);
			sb.append("&destination=" + endAd);
			sb.append("&sensor=false");
			sb.append("&departure_time=" + depTime);
			sb.append("&mode=" + travelMode);

			URL url = new URL(sb.toString());
			conn = (HttpURLConnection) url.openConnection();
			InputStreamReader in = new InputStreamReader(conn.getInputStream());

			int read;
			char[] buff = new char[1024];
			while ((read = in.read(buff)) != -1) {
				jsonResults.append(buff, 0, read);
			}

		} catch (MalformedURLException e) {
			// Log.e(LOG_TAG, "Error processing Places API URL", e);
			// return resultList;
		} catch (IOException e) {
			// Log.e(LOG_TAG, "Error connecting to Places API", e);
			// return resultList;
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}

		try {

			// Create a JSON object hierarchy from the results
			JSONObject jsonObj = new JSONObject(jsonResults.toString());
			JSONArray routesJsonArray = jsonObj.getJSONArray("routes");

			// Loop over each each route
			for (int i = 0; i < routesJsonArray.length(); i++) {
				route = new Route();
				// Get route object
				JSONObject routeJsonObj = routesJsonArray.getJSONObject(i);
				// Get all legs elements for each row as an array
				JSONArray legsJsonArray = routeJsonObj.getJSONArray("legs");

				// Iterate each "leg" object in the elements array
				for (int j = 0; j < legsJsonArray.length(); j++) {
					// Get the leg Json object
					JSONObject legObject = legsJsonArray.getJSONObject(j);

					JSONObject arrival_time = legObject
							.getJSONObject("arrival_time");
					JSONObject departure_time = legObject
							.getJSONObject("departure_time");
					JSONObject distance = legObject.getJSONObject("distance");
					JSONObject duration = legObject.getJSONObject("duration");

					// set parameters of each route
					route.setArrival_time(arrival_time.getInt("value"));

					route.setDeparture_time(departure_time.getInt("value"));

					route.setDuration(duration.getInt("value"));

					route.setDistance(distance.getInt("value"));

					route.setStart_address(legObject.getString("start_address"));

					route.setEnd_address(legObject.getString("end_address"));

				}

			}

		} catch (JSONException e) {
			System.out.println("LOG_TAG, Error processing JSON results"
					+ e.toString());
		}

		return route;
	}
*/
	// convert date (departure time) to unix timestamp
	
	
	private long strDateToUnixTimestamp(String departureTime) {
		long unixtime = 0;
		// get current date, convert it to string and append the departure time
		// to that
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy");
		String today = sdf.format(new Date());
		today = today.concat(" " + departureTime + ":00");

		DateFormat sdf2 = new SimpleDateFormat("dd-MM-yy HH:mm:ss");
		Date date;
		try {
			date = sdf2.parse(today);
			unixtime = date.getTime() / 1000;

		} catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return unixtime;
	}

	/*for testing...............................

	public static void main(String[] args) {

		List<Cinema> rts = new ArrayList<Cinema>();
		String startAddre = "Marchstrasse 3,Berlin,DE";

		List<Cinema> endLoc = new ArrayList<Cinema>();
		String[] t = {  "20:00", "22:00" };
		
		//1392115363 -->11.02.2014, 11:42:43
		//1392201763 --> 12.02.2014, 10:42:43
		//1392234163--> 12.02.2014 ,19:42:43
		//1392241363 --> 21.02.2014, 21:42:43
		//all 
		int[] temp2 = { 1392234163, 1392241363 };
		Cinema c = new Cinema("Sony Center", t,"Friedrichstrasse 3,Berlin,DE", temp2);
		endLoc.add(c);
		/*String[] t2 = { "17:00", "20:00" };
		Cinema c1 = new Cinema("Palast", t2,"Mollwitzstrasse 3,Berlin,DE", temp2);
		endLoc.add(c1);
		String[] t1 = { "22:00", "23:00" };
		Cinema c2 = new Cinema("Fay", t1,"Friedrichstrasse 120,Berlin,DE", temp2);
		endLoc.add(c2);

		PublicTransport pt = new PublicTransport();

		//rts = pt.callJelena1(startAddre, "18:30", endLoc);

		for (int i = 0; i < rts.size(); i++) {

			System.out.println("Optimized cinemas.........\t" +i+"\n");
			System.out.println("********************");
			System.out.println("name" + (rts.get(i)).getTheaterName());
			System.out.println("address"  + (rts.get(i)).getAddress());
			System.out.println("address" + (rts.get(i)).getScreeningTime());
			
		}
		
		Restaurant r= new Restaurant("Friedrichstrasse 120,Berlin,DE");

		Restaurant rr= new Restaurant("Friedrichstrasse 150,Berlin,DE");
		
		 List<Restaurant> restaurantList= new ArrayList<Restaurant>();
		 restaurantList.add(r);
		 restaurantList.add(rr);
		 
		 CinemaRestaurant cr= new CinemaRestaurant(c, restaurantList);
		 
		 List<CinemaRestaurant> listcinrest= new ArrayList<CinemaRestaurant>();
		 listcinrest.add(cr);
		 
		 Map<Cinema, Restaurant> cinrest= pt.callJelena2(listcinrest, 10);	
		 
		 for (Map.Entry entry : cinrest.entrySet()) {
			 
			   Cinema ccc= (Cinema)entry.getKey();
			   Restaurant rrr= (Restaurant)entry.getValue();
			   
			    System.out.println(ccc.getAddress() + ", " + rrr.getRestaurantAddress());
		 }

	}
	
	*/

	// ..........................................
}
