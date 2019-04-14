package com.cba.weather.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import com.cba.weather.model.LocationWeather;
import com.cba.weather.model.enums.WeatherParameter;

public interface WeatherCalculatorService {
	
	public Map<WeatherParameter, ArrayList<String>> getHistoryData(String station, Date date);
	public LocationWeather getWeatherDetails(String station, Date date);
	//public List<LocationWeather> getAllStationsWeatherDetails();
	

}
