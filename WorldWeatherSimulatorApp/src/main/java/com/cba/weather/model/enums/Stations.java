package com.cba.weather.model.enums;

public enum Stations {

	MELBOURNE("Melbourne",-37.865799, 145.102829,31,"Australia/Melbourne"),
	ADELAIDE("Adelaide",-29.01091, 142.568008,50,"Australia/Adelaide"),
	SYDNEY("Sydney",-33.856281,151.020966,19,"Australia/Sydney"),
	BRISBANE("Brisbane",-27.470125, 153.021072,28,"Australia/Brisbane"),
	PARIS("Paris",2.3412, 48.85693,35,"Europe/Paris"),
	LONDON("London",51.507702,-0.12797,35,"Europe/London"),
	TOKYO("Tokyo",35.670479,139.740921,40,"Asia/Tokyo"),
	CHICAGO("Chicago",41.884151,-87.632408,181,"America/Chicago"),
	DELHI("Delhi",28.643999,77.091003,216,"Asia/Calcutta"),
	DUBAI("Dubai",25.26944,55.308651,16,"Asia/Dubai"),
	JOHANNESBURG("Johannesburg",-26.204941,28.04003,1753,"Africa/Johannesburg"),
	TORONTO("Toronto",43.761539, -79.411079,177,"America/Toronto");
	
	private String displayName;
	private double latitude;
	private double longitude;
	private double elevation;
	private String timezone;

	Stations(String displayName,double latitude, double longitude,double elevation,String timezone) {
		this.setDisplayName(displayName);
		this.setLatitude(latitude);
		this.setLongitude(longitude);
		this.setElevation(elevation);
		this.setTimezone(timezone);

	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getElevation() {
		return elevation;
	}

	public void setElevation(double elevation) {
		this.elevation = elevation;
	}

	public String getTimezone() {
		return timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

}
