package com.cba.weather;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;

import junit.framework.Assert;

import org.junit.Test;

import com.cba.weather.common.WeatherUtils;
import com.cba.weather.model.enums.Stations;

@SuppressWarnings("deprecation")
public class WeatherUtilsTests {

	@Test
	public void getWeeksToDateTest(){
		
		LocalDate date = WeatherUtils.addWeekToDate("11/30/2014");
		Assert.assertTrue(date!=null);
	}
	@Test
	public void prevWeeksToDateTest(){
		
		LocalDate date = WeatherUtils.prevWeekToDate("11/30/2014");
		Assert.assertTrue(date!=null);
	}
	@Test
	public void stringToLocaleTest(){
		
		LocalDate date = 	WeatherUtils.stringToLocaleDate("11/30/2014");
		Assert.assertTrue(date!=null);
	}
	@Test
	public void isDateWithYearNotInFortNightOfTest(){
		
	Assert.assertFalse(WeatherUtils.isDateInFortNightOf("12/12/2014","11/30/2014",false));
	}
	@Test
	public void isDateWithYearInFortNightOfTest(){
		
	Assert.assertTrue(WeatherUtils.isDateInFortNightOf("12/05/2014","11/30/2014",false));
	}
	@Test
	public void isDateWithoutYearInFortNightOfTest(){
		
	Assert.assertTrue(WeatherUtils.isDateInFortNightOf("12/1/","11/27/",true));
	}
	
	@Test
	public void stripYearTest(){
		
	Assert.assertTrue("5/13/".equals(WeatherUtils.stripYear("5/13/2015")));
	}
	
	@Test
	public void getISO8601StringForDateTest(){
		String dateTime =WeatherUtils.getISO8601StringForDate(new Date(), Stations.JOHANNESBURG.getTimezone());
		Assert.assertTrue(dateTime!=null);
	}
	
	@Test
	public void getHistoricalFileURLTest (){
		Assert.assertTrue(WeatherUtils.constructHistoricalFileURL("Melbourne")!=null);
	}
	
	@Test
	public void getHistoricalFileURLInvalidTest (){
		Assert.assertFalse(WeatherUtils.constructHistoricalFileURL("XAAAA")!=null);
	}
	
	@Test
	public void isDateInNextWeekOfTest(){
		Assert.assertTrue(WeatherUtils.isDateInWeekOf("12/8/", "12/4/", true));
	}
	
	@Test
	public void isDateNotInNextWeekOfTest(){
		Assert.assertTrue(WeatherUtils.isDateInWeekOf("12/8/", "12/4/", true));
	}
	
	@Test
	public void calculateMeanValueTest(){
	
		Assert.assertEquals(3.5, WeatherUtils.calculateMeanValue(Arrays.asList("2","3","4","5")), 0);
		
	}
}
