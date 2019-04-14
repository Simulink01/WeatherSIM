package com.cba.weather.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.EnumUtils;

import com.cba.weather.common.WeatherUtils;
import com.cba.weather.common.constants.WeatherSimulatorConstants;
import com.cba.weather.model.LocationWeather;
import com.cba.weather.model.enums.Stations;
import com.cba.weather.model.enums.WeatherCondition;
import com.cba.weather.model.enums.WeatherParameter;

/**
 * @author milanie
 *
 */
/**
 * 
 * This class is the service class for weather related services
 *
 */
public class WeatherCalculatorServiceImpl implements WeatherCalculatorService {

	private WeatherCondition weatherCondition;
	private LocationWeather locationWeather;

	/*
	 * Get history data for the station for the given date and the all the days
	 * in the same week for all the previous years
	 * 
	 * @param: Station name and Date
	 * 
	 * @return: history data summary in array list for each weather parameter .
	 * Weather Parameters are mentioned in WeatherParameter enum *
	 * Sample data: [TEMPERATURE{21,21,15,16,19,22,19},PRESSURE{998,999,1000,1001,1006,1001}
	 */

	@Override
	public Map<WeatherParameter, ArrayList<String>> getHistoryData(
			String station, Date date) {

		String fileName = WeatherUtils.constructHistoricalFileURL(station);
		String findDate = WeatherUtils.dateToString(date,
				WeatherSimulatorConstants.DEFAULT_DATE_FORMAT);
		if(fileName==null || findDate== null)
			return null;

		return WeatherUtils.retreiveCSVData(fileName,
				WeatherUtils.stripYear(findDate));
	}

	/*
	 * Get Weather details for the given station and date
	 * 
	 * @param Station, Date
	 * 
	 * @return LocationWeather
	 */
	@Override
	public LocationWeather getWeatherDetails(String station, Date date) {

		Map<WeatherParameter, ArrayList<String>> historyData = getHistoryData(
				station, date);

		Map<WeatherParameter, String> predictData = new HashMap<WeatherParameter, String>();
		// predict weather parameter based on mean algorithm with history data
		// as input
		historyData
				.forEach((parameter, summaryData) -> {
					if (WeatherSimulatorConstants.ALGORITHM1.equals(parameter
							.getAlgorithm())) {
						DecimalFormat format = new DecimalFormat("##.#");
						predictData.put(parameter, String.valueOf(format
								.format(WeatherUtils
										.calculateMeanValue(summaryData))));
					}
				});
		// get weather condition based on other predicted weather parameter
		WeatherCondition condition = getWeatherCondition(predictData);
		predictData.put(WeatherParameter.CONDITION, condition.getDisplayName());
		// put final predicted data in LocationWeather
		locationWeather = constructLocationDetails(predictData, station, date);
		System.out.println(locationWeather);
		return locationWeather;
	}

	/*
	 * Construct location details into LocationWeather
	 * 
	 * @param predicted weather data, station date
	 * 
	 * @return LocationWeather
	 */
	private LocationWeather constructLocationDetails(
			Map<WeatherParameter, String> predictData, String station, Date date) {
		locationWeather = new LocationWeather();
		locationWeather.setLocationName(station);
		Optional<Stations> stations = EnumUtils.getEnumList(Stations.class)
				.stream().filter(s -> (s.name().equalsIgnoreCase(station)))
				.findFirst();
		if (stations.isPresent()) {
			Stations currentStation = stations.get();
			locationWeather.setLocationName(station);
			locationWeather.setElevation(currentStation.getElevation());
			locationWeather.setLatitude(currentStation.getLatitude());
			locationWeather.setLongitude(currentStation.getLongitude());
			locationWeather.setHumidity(predictData
					.get(WeatherParameter.HUMIDITY));
			locationWeather.setPressure(predictData
					.get(WeatherParameter.PRESSURE));
			String temperature = predictData.get(WeatherParameter.TEMPERATURE);
			locationWeather.setTemperature(WeatherUtils
					.isNegativeValue(temperature) ? temperature : "+"
					+ temperature);
			locationWeather.setConditions(predictData
					.get(WeatherParameter.CONDITION));
			String zonedDateTime = WeatherUtils.getISO8601StringForDate(date,
					currentStation.getTimezone());
			locationWeather.setLocalTime(zonedDateTime);
		}
		return locationWeather;
	}

	/*
	 * Get weather condtions based on predicted weather parameters
	 */
	public WeatherCondition getWeatherCondition(
			Map<WeatherParameter, String> weatherParameterValue) {
		weatherCondition = null;

		if (WeatherUtils.isNegativeValue(weatherParameterValue
				.get(WeatherParameter.TEMPERATURE))) {
			weatherCondition = WeatherCondition.SNOW;
		} else if (WeatherUtils.isHighHumidity(weatherParameterValue
				.get(WeatherParameter.HUMIDITY))) {
			weatherCondition = WeatherCondition.RAIN;
		}

		else if (WeatherUtils.isHighPressure(weatherParameterValue
				.get(WeatherParameter.PRESSURE))) {
			weatherCondition = WeatherCondition.SUNNY;
		} else if (WeatherUtils.isLowPressure(weatherParameterValue
				.get(WeatherParameter.PRESSURE))) {
			weatherCondition = WeatherCondition.RAIN;
		}

		return weatherCondition;
	}

}
