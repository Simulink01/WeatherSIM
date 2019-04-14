package com.cba.weather.model.enums;

public enum WeatherCondition {
	
	RAIN("Rain"),SUNNY("Sunny"),SNOW("Snow");

	private String displayName;


	WeatherCondition(String displayName) {

		this.setDisplayName(displayName);
		
	}


	public String getDisplayName() {
		return displayName;
	}


	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

}
