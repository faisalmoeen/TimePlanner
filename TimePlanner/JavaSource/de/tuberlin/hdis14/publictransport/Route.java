package de.tuberlin.hdis14.publictransport;


public class Route {
	
	//UNIX format
	private int arrival_time;   
	private int departure_time; 
	private int duration; //in sec
	private int distance; //in meters
	private String start_address;
	private String end_address;
	
	
	public Route(){}
	
	public Route(int arrival_time, int departure_time, int duration,
			int distance, String start_address, String end_address) {
		
		this.arrival_time = arrival_time;
		this.departure_time = departure_time;
		this.duration = duration;
		this.distance = distance;
		this.start_address = start_address;
		this.end_address = end_address;
	}
	public int getArrival_time() {
		return arrival_time;
	}
	public void setArrival_time(int i) {
		this.arrival_time = i;
	}
	public int getDeparture_time() {
		return departure_time;
	}
	public void setDeparture_time(int i) {
		this.departure_time = i;
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

