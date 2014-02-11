package de.tuberlin.hdis14.core;


public class FakeUserCriteria {
	private String time;
	private int maxDistance;
	
	private static FakeUserCriteria instance = null;
	
	public static FakeUserCriteria getInstance() 
	{
		if(instance == null) {
			instance = new FakeUserCriteria();
	    }
	    return instance;
	}
	
	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	
	public int getMaxDistance() {
		return maxDistance;
	}

	public void setMaxDistance(int maxDistance) {
		this.maxDistance = maxDistance;
	}
}
