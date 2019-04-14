/**
 * 
 */
package com.cba.weather.model;

import java.io.Serializable;

import com.cba.weather.common.constants.WeatherSimulatorConstants;


public class LocationWeather implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String locationName;
	public String getLocationName() {
		return locationName;
	}


	public void setLocationName(String locationName) {
		this.locationName = locationName;
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


	public String getLocalTime() {
		return localTime;
	}


	public void setLocalTime(String localTime) {
		this.localTime = localTime;
	}


	public String getConditions() {
		return conditions;
	}


	public void setConditions(String conditions) {
		this.conditions = conditions;
	}


	public String getTemperature() {
		return temperature;
	}


	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}


	public String getPressure() {
		return pressure;
	}


	public void setPressure(String pressure) {
		this.pressure = pressure;
	}


	public String getHumidity() {
		return humidity;
	}


	public void setHumidity(String humidity) {
		this.humidity = humidity;
	}


	private double latitude;
	private double longitude;
	private double elevation;
	private String localTime;
	private String conditions;
	private String temperature;
	private String pressure;
	private String humidity;

	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(locationName);
		builder.append(WeatherSimulatorConstants.PIPE_SEPARATOR);
		builder.append(latitude);
		builder.append(WeatherSimulatorConstants.COMMA_SEPERATOR);
		builder.append(longitude);
		builder.append(WeatherSimulatorConstants.COMMA_SEPERATOR);
		builder.append(elevation);
		builder.append(WeatherSimulatorConstants.PIPE_SEPARATOR);
		builder.append(localTime);
		builder.append(WeatherSimulatorConstants.PIPE_SEPARATOR);
		builder.append(conditions);
		builder.append(WeatherSimulatorConstants.PIPE_SEPARATOR);
		builder.append(temperature);
		builder.append(WeatherSimulatorConstants.PIPE_SEPARATOR);
		builder.append(pressure);
		builder.append(WeatherSimulatorConstants.PIPE_SEPARATOR);
		builder.append(humidity);
		return builder.toString();
	}
}
