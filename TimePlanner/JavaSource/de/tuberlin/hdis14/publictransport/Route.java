package de.tuberlin.hdis14.publictransport;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;


public class Route {
	
	//UNIX format
	private long arrival_time;   
	private long departure_time; 
	private int duration; //in sec
	private int distance; //in meters
	private String start_address;
	private String end_address;
	
	
	public Route(){}
	
	public Route(int arrival_time, long departure_time, int duration,
			int distance, String start_address, String end_address) {
		
		this.arrival_time = arrival_time;
		this.departure_time = departure_time;
		this.duration = duration;
		this.distance = distance;
		this.start_address = start_address;
		this.end_address = end_address;
	}
	public long getArrival_time() {
		return arrival_time;
	}
	public void setArrival_time(long i) {
		this.arrival_time = i;
	}
	public long getDeparture_time() {
		return departure_time;
	}
	public void setDeparture_time(long i) {
		this.departure_time = i; 
	}
	
	public String getArrivalTimeFormatted(){ 
			
		Date date = new Date(arrival_time*1000L); // *1000 is to convert seconds to milliseconds
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z"); // the format of your date
		String formattedDate = sdf.format(date);
		System.out.println(formattedDate);
		
		return formattedDate;
		
	}
	public String getDepartureTimeFormatted(){
		
		Date date = new Date(departure_time*1000L); // *1000 is to convert seconds to milliseconds
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z"); // the format of your date
		String formattedDate = sdf.format(date);
		System.out.println(formattedDate);
		
		return formattedDate;
	}
	
	public int getDuration() {
		return duration;
	}
	public void setDuration(int i) {
		this.duration = i;
	}
	public int getDistance() {
		return distance;
	}
	public void setDistance(int distance) {
		this.distance = distance;
	}
	public String getStart_address() {
		return start_address;
	}
	public void setStart_address(String start_address) {
		this.start_address = start_address;
	}
	public String getEnd_address() {
		return end_address;
	}
	public void setEnd_address(String end_address) {
		this.end_address = end_address;
	}
	
	

}

