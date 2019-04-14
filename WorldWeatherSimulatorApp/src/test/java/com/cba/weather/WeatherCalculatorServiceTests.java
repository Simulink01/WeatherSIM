package com.cba.weather;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import com.cba.weather.model.LocationWeather;
import com.cba.weather.model.enums.Stations;
import com.cba.weather.model.enums.WeatherParameter;
import com.cba.weather.service.WeatherCalculatorService;
import com.cba.weather.service.WeatherCalculatorServiceImpl;



@SuppressWarnings("deprecation")
public class WeatherCalculatorServiceTests {
	 WeatherCalculatorService weatherCaluclatorService = new WeatherCalculatorServiceImpl();
		
	@Test
	public void getHistorDataWithLocationAndDateTest(){		
		String location = "London";
		Date date = new Date();		
		Map<WeatherParameter, ArrayList<String>> weatherMap =weatherCaluclatorService.getHistoryData(location, date);
		System.out.println(weatherMap);
		Assert.assertTrue(weatherMap!=null);
	}
	@Test
	public void getHistorDataWithIncorrectLocationTest(){		
		String location = "Xasasa";
		Date date = new Date();		
		Map<WeatherParameter, ArrayList<String>> weatherMap =weatherCaluclatorService.getHistoryData(location, date);
		Assert.assertFalse(weatherMap!=null);
	}
	
	@Test
	public void getWeatherDetailsTest(){		
		List<Stations> stations = EnumUtils.getEnumList(Stations.class);
		List<LocationWeather> locationWeatherList = new ArrayList<LocationWeather>();
		stations.forEach(station -> {
			
			LocationWeather  locationWeather =	weatherCaluclatorService.getWeatherDetails(StringUtils.capitalize(station.name()), new Date());
			locationWeatherList.add(locationWeather);
		});
		Assert.assertEquals(stations.size(), locationWeatherList.size());
	}

}
