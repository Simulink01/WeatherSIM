package com.cba.weather.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;

import com.cba.weather.model.LocationWeather;
import com.cba.weather.model.enums.Stations;
import com.cba.weather.service.WeatherCalculatorService;
import com.cba.weather.service.WeatherCalculatorServiceImpl;
import com.cba.weather.view.LocationView;

/**
 * @author milanie
 *
 */

/**
 * 
 * This class is the controller class that routes the service request from the screen
 *
 */

public class WeatherController {

	public WeatherCalculatorService weatherCalculatorService = new WeatherCalculatorServiceImpl();

	/**
	 * method to retrieve all weather details for the stations
	 * 
	 * @param locationViews
	 * @return LocationWeather in a list
	 */
	public List<LocationWeather> retreiveAllStationsWeatherDetails(
			List<LocationView> locationViews) {

		List<LocationWeather> locationsWeather = new ArrayList<LocationWeather>();
		// check if input is default 
		if (isDefaultInput(locationViews)) {
			System.out.println("processing request...");
			// retrieves station list from Stations Enum
			EnumUtils
					.getEnumList(Stations.class)
					.stream()
					.forEach(
							station -> {
								// retrieves station weather details for default date, which is the current date
								locationsWeather.add(weatherCalculatorService
										.getWeatherDetails(station.getDisplayName(),
												new Date()));

							});
		}
		// if report is for specific input date
		else {
			locationViews.forEach(loc -> {
				Date date = formatToDefaultDate(loc);
				locationsWeather.add(weatherCalculatorService
						.getWeatherDetails(loc.getLocationName(), date));
			});
		}
		return locationsWeather;

	}

	/**
	 * check weather there is any input station and dates
	 * 
	 * @param locationViews
	 * @return boolean
	 */
	private boolean isDefaultInput(List<LocationView> locationViews) {

		return (locationViews == null || locationViews.isEmpty());
	}

	/**
	 * format date to default date format
	 * 
	 * @param loc
	 * @return date
	 */
	private static Date formatToDefaultDate(LocationView loc) {
		Date date = null;
		try {
			date = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy").parse(loc
					.getReportDate().toString());
			DateFormat destDf = new SimpleDateFormat("MM/dd/yyyy");
			String formattedDateStr = destDf.format(date);
			return destDf.parse(formattedDateStr);
		} catch (Exception e) {
			e.printStackTrace();
			date = new Date();
		}
		return date;
	}

}
