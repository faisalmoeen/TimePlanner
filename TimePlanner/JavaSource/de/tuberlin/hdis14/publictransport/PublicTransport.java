package de.tuberlin.hdis14.publictransport;

import de.tuberlin.hdis14.cinema.*;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

  


public class PublicTransport implements IPublicTransport {
	
    private static final String DIRECTIONS_API_BASE = "https://maps.googleapis.com/maps/api/directions";
    private static final String DISTANCE_MATRIX_API_BASE = "http://maps.googleapis.com/maps/api/distancematrix";
    private static final String OUT_JSON = "/json";
    // KEY!
    //private static final String API_KEY = "YOUR KEY";

	
	@Override
	public List<Route> getRoutesCinemaRestaurant(List<Cinema> cinemas,List<Restaurant> restaurants) {
		/*List<Route> routes=null;
		 
		String startAd= (cinemas.get(0)).getStartAdr();
		String endAd= (cinemas.get(0)).getEndAdr();
		String mode="walking";
		int startTime=(cinemas.get(0)).getTime();
		
		routes = calculateRoute(startAd,endAd,mode,startTime);
		        
		return routes;*/
		return null;
	}



	@Override
	public List<Cinema> getFilteredCinemas(String startAddress,List<Cinema> cinemas, String departureTime) {
		
		
		 HttpURLConnection conn = null;
		 StringBuilder DistanceMatrixResults = new StringBuilder();
		 URL url;
		 long screeningTime;
 		 long departure;
		 //optimum list of cinemas
         List<Cinema> optimumCinemas = new ArrayList<Cinema>();
         
		 try {
			 

	         for (int j=0; j< cinemas.size(); j++)
	         {
				 //format the json request
		         //append one destination per time in the http request
	        	 StringBuilder sb = new StringBuilder(DISTANCE_MATRIX_API_BASE);
		         sb.append(OUT_JSON);
		         sb.append("?origins=" + startAddress +"&destinations=");
	        	 sb.append(cinemas.get(j).getAddress());
		         sb.append("&sensor=false");
		         sb.append("&mode=transit");
		         
		         url = new URL(sb.toString());
		         System.out.println("URL: "+ url.toString());
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

				 //departure time: 18:50. COnvert to Unix timestamp for the requests
				 String delims = "[ ]+";
				 String[] tokens = cinemas.get(j).getScreeningTime().split(delims);
				 String tempScreens="";
				 departure = strDateToUnixTimestamp(departureTime);
                 
		      // Loop over each each row
	             for(int i=0; i < rows.length(); i++) { 
	            	// Get row object
	                JSONObject row = rows.getJSONObject(i); 
	                // Get all elements for each row as an array
	                JSONArray elements = row.getJSONArray("elements"); 
	                
	             // Iterate each element in the elements array
	                for(int i1=0; i1 < elements.length(); i1++) { 
	                	// Get the element object
	                    JSONObject element =  elements.getJSONObject(i1); 
	                    // Get duration sub object
	                    JSONObject duration = element.getJSONObject("duration"); 
	                    // Get distance sub object
	                    JSONObject distance = element.getJSONObject("distance");                     
	                	
	                    //for each screening time check if is is possible to be there in time
	                    for (int k=0; k<tokens.length; k++)
	                    {
	                    	screeningTime= strDateToUnixTimestamp(tokens[k]);
	                    	
	                    	if (duration.getInt("value") <= screeningTime - departure )
		                    {
	                    		tempScreens= tempScreens.concat(tokens[k]);
		                    }
	                    }
	                    //users can catch at least one screening
	                    if (!tempScreens.equals(""))
	                    {	                    	
	                    	//here we need a constructor fom the Cinema Class
	                    	optimumCinemas.add(new Cinema(cinemas.get(j).getTheaterName(),cinemas.get(j).getAddress(),tempScreens));
	                    }  
	                    
	                }
	            }
	         }
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch  (IOException e) {
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
		
	
	private List<Route> calculateRoute(String startAddress,String endAddress,String mode,int departureTime) {
			
			List<Route> routes=null;
	
			 HttpURLConnection conn = null;
			 StringBuilder jsonResults = new StringBuilder();
	
			String startAd= startAddress;
			String endAd= endAddress;
	        int depTime= departureTime;
	        String travelMode=mode;
	        
			
			        try {
			            StringBuilder sb = new StringBuilder(DIRECTIONS_API_BASE);
			            sb.append(OUT_JSON);
			            sb.append("?origin=" + startAd);
			            sb.append("&destination=" + endAd);
			            sb.append("&sensor=false");
						sb.append("&departure_time="+depTime);
			            sb.append("&mode="+travelMode);
	
	
			            URL url = new URL(sb.toString());
			            conn = (HttpURLConnection) url.openConnection();
			            InputStreamReader in = new InputStreamReader(conn.getInputStream());
	
			            int read;
			            char[] buff = new char[1024];
			            while ((read = in.read(buff)) != -1) {
			                jsonResults.append(buff, 0, read);
			            }
			            
			        } catch (MalformedURLException e) {
			            //Log.e(LOG_TAG, "Error processing Places API URL", e);
			            //return resultList;
			        } catch (IOException e) {
			            //Log.e(LOG_TAG, "Error connecting to Places API", e);
			            //return resultList;
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
			            routes= new ArrayList<Route>(routesJsonArray.length());
			            
			            // Loop over each each route
			            for(int i=0; i < routesJsonArray.length(); i++) {
			            	Route route = new Route();
			            	// Get route object
			                JSONObject routeJsonObj = routesJsonArray.getJSONObject(i); 
			             // Get all legs elements for each row as an array
			                JSONArray legsJsonArray = routeJsonObj.getJSONArray("legs"); 
	
			             // Iterate each "leg" object in the elements array
			                for(int j=0; j < legsJsonArray.length(); j++) { 
			                	// Get the leg Json object
			                    JSONObject legObject =  legsJsonArray.getJSONObject(j); 
			                    
			                    JSONObject arrival_time = legObject.getJSONObject("arrival_time"); 
			                    JSONObject departure_time = legObject.getJSONObject("departure_time"); 
			                    JSONObject distance = legObject.getJSONObject("distance"); 
			                    JSONObject duration = legObject.getJSONObject("duration"); 
		                
			                    //set parameters of each route
				                route.setArrival_time(arrival_time.getInt("value"));
				                
				                route.setDeparture_time(departure_time.getInt("value"));
				                
				                route.setDuration(duration.getInt("value"));
				                
				                route.setDistance(distance.getInt("value"));
				                
				                route.setStart_address(legObject.getString("start_address"));
				                
				                route.setEnd_address(legObject.getString("end_address"));
	
				                //add route to the Routes List
				                routes.add(route);
			                }
			            
			                
			            }        
			                            
			        } catch (JSONException e) {
			        		System.out.println("LOG_TAG, Error processing JSON results"+ e.toString());
			        }
			        
			return routes;
		}
	
	
	

	//convert date (departure time) to unix timestamp
		private  long strDateToUnixTimestamp(String departureTime)
		{
			 long unixtime=0;
			//get current date, convert it to string and append the departure time to that
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy");
			String today = sdf.format(new Date()); 
			today = today.concat(" "+departureTime+":00");
			
			DateFormat sdf2 = new SimpleDateFormat("dd-MM-yy HH:mm:ss");
			Date date;
			try {
				date = sdf2.parse(today);
		        unixtime = date.getTime()/1000 ;
		      
			} catch (java.text.ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	        return unixtime;
		}
		
}


