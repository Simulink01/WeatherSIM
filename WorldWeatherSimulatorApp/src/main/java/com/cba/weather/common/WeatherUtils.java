package com.cba.weather.common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import com.cba.weather.common.constants.WeatherSimulatorConstants;
import com.cba.weather.model.enums.WeatherParameter;
import com.opencsv.CSVReader;

public class WeatherUtils {
	private static String[] csvReaderLine;

	
	/**
	 * Method to covert fahrenheit to celcius
	 * @param temp in fahrenheit
	 * @return temp in celcius
	 */
	public static int fahrenheitToCelcius(int temp) {
		temp = ((temp - 32) * 5) / 9;
		return temp;
	}

	/**
	 * @param date
	 * @param format
	 * @return Formated string date
	 */
	public static String dateToString(Date date, String format) {

		DateFormat df = new SimpleDateFormat(format);
		return df.format(date);
	}

	/**Method to construct the historical file URL
	 * @param station
	 * @return Historical Data file URL
	 */
	public static String constructHistoricalFileURL(String station) {
		File file = null;
		
		try {
			
			
			URL res =WeatherUtils.class
					.getClassLoader()
					.getResource(
							WeatherSimulatorConstants.HISTORY_DATA_PATH + "/"
									+ station + WeatherSimulatorConstants.EXTENSION);
			// for file in generated jar 
			 if (res.toString().startsWith("jar:")) {
			        try {
			            InputStream input = WeatherUtils.class
								.getClassLoader()
								.getResourceAsStream(
										WeatherSimulatorConstants.HISTORY_DATA_PATH + "/"
												+ station + WeatherSimulatorConstants.EXTENSION);
			            file = File.createTempFile(station, ".csv");
			            OutputStream out = new FileOutputStream(file);
			            int read;
			            byte[] bytes = new byte[1024];

			            while ((read = input.read(bytes)) != -1) {
			                out.write(bytes, 0, read);
			            }
			            out.close();
			            file.deleteOnExit();
			            
			        } catch (IOException ex) {
			            ex.printStackTrace();
			        }
			    } else {
			       // for file in java class path
			        file = new File(res.getFile());
			        
			    }

			    if (file != null && !file.exists()) {
			        throw new RuntimeException("Error: File " + file + " not found!");
			    }
			
		} catch (Exception e) {
			System.out.println("File not found for station "+station );
			
		}
return file.getPath().replace("%20", " "); //fileURL;
	}

	/**
	 * @param csvFile
	 * @param findKey date
	 * @return data summary for the  weeks belonging to the date in history data
	 */
	@SuppressWarnings("resource")
	public static Map<WeatherParameter, ArrayList<String>> retreiveCSVData(
			String csvFile, String findKey) {
		Map<WeatherParameter, ArrayList<String>> columnSummary = WeatherUtils
				.constructWeatherSummaryMap();
		CSVReader reader = null;
		try {
			reader = new CSVReader(new FileReader(csvFile));
			reader.readNext();
			while ((csvReaderLine = reader.readNext()) != null) {
				String rowDate = csvReaderLine[0];
				if (WeatherUtils.isDateInWeekOf(
						WeatherUtils.stripYear(rowDate), findKey, true)) {
					columnSummary
							.forEach((parameter, colArray) -> {
								String colValue = csvReaderLine[parameter
										.getCsvIndex()];
								colArray.add(colValue);
							});
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return columnSummary;
	}

	/**
	 * @return empty map
	 */
	public static Map<WeatherParameter, ArrayList<String>> constructWeatherSummaryMap() {

		Map<WeatherParameter, ArrayList<String>> columnSummary = new HashMap<WeatherParameter, ArrayList<String>>();
		EnumUtils.getEnumList(WeatherParameter.class).forEach(param -> {
			columnSummary.put(param, new ArrayList<String>());
		});
		return columnSummary;
	}

	/**Method to calculate mean 
	 * @param values
	 * @return mean value
	 */
	public static double calculateMeanValue(List<String> values) {

		double sum = 0;
		for (int i = 0; i < values.size(); i++) {
			sum += Integer.parseInt(StringUtils.isNotBlank(values.get(i))?values.get(i):"0"  );
		}

		return sum / values.size();
	}

	/**
	 * @param values
	 * @return returns element with maximum occurence
	 */
	public static String getMaxFrequencyElement(List<String> values) {
		Map<String, Long> map = values.stream().collect(
				Collectors.groupingBy(w -> w, Collectors.counting()));

		String result = map.entrySet().stream()
				.sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
				.limit(1).collect(Collectors.toList()).get(0).getKey();
		return result;

	}

	/**
	 * @param value
	 * @return negative value
	 */
	public static boolean isNegativeValue(String value) {
		return value.startsWith("-");
	}

	/**
	 * @param value
	 * @return boolean for: is high humid(value)
	 */
	public static boolean isHighHumidity(String value) {
		return (Double.parseDouble(value) > 60);
	}
	/**
	 * @param value
	 * @return boolean for: is high pressure(value)
	 */
	public static boolean isHighPressure(String value) {
		return (Double.parseDouble(value) >= 998);
	}
	/**
	 * @param value
	 * @return boolean for: is low humid(value)
	 */
	public static boolean isLowPressure(String value) {
		return (Double.parseDouble(value) < 998);
	}

	/**
	 * @param date
	 * @return add a week to date
	 */
	public static LocalDate addWeekToDate(String date) {
		LocalDate today = stringToLocaleDate(date);

		// add 1 week to the current date
		LocalDate nextWeek = today.plus(1, ChronoUnit.WEEKS);

		return nextWeek;
	}

	/**
	 * @param date
	 * @return previous week date
	 */
	public static LocalDate prevWeekToDate(String date) {
		LocalDate today = stringToLocaleDate(date);

		LocalDate nextWeek = today.minus(1, ChronoUnit.WEEKS);

		return nextWeek;
	}

	/**
	 * @param  String date
	 * @return Locale date
	 */
	public static LocalDate stringToLocaleDate(String date) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");

		LocalDate localDate = LocalDate.parse(date, formatter);

		return localDate;
	}

	/**
	 * @param checkDate
	 * @param startDate
	 * @param endDate
	 * @return boolean to check if date is in between start date and end date
	 */
	public static boolean isDateBetweenTheDates(LocalDate checkDate,
			LocalDate startDate, LocalDate endDate) {

		return (checkDate.isAfter(startDate) || (checkDate.isEqual(startDate)))
				&& (checkDate.isBefore(endDate) || (checkDate.isEqual(endDate)));
	}

	/**
	 * @param checkDate
	 * @param fortNightDate
	 * @param isyearStripped
	 * @return boolean to check if date is in 2weeks of fornightDate
	 */
	public static boolean isDateInFortNightOf(String checkDate,
			String fortNightDate, boolean isyearStripped) {

		if (isyearStripped) {
			checkDate = checkDate + "2015";// pass dummy year
			fortNightDate = fortNightDate + "2015"; // pass dummy year
		}

		LocalDate startDate = prevWeekToDate(fortNightDate);
		LocalDate endDate = addWeekToDate(fortNightDate);
		return isDateBetweenTheDates(stringToLocaleDate(checkDate), startDate,
				endDate);
	}

	/**
	 * @param checkDate
	 * @param weekDate
	 * @param isyearStripped
	 * @return boolen to check if date in the week of weekDate
	 */
	public static boolean isDateInWeekOf(String checkDate, String weekDate,
			boolean isyearStripped) {

		if (isyearStripped) {
			checkDate = checkDate + "2015"; // pass dummy year
			weekDate = weekDate + "2015"; // pass dummy year
		}

		LocalDate startDate = stringToLocaleDate(weekDate);
		LocalDate endDate = addWeekToDate(weekDate);
		return isDateBetweenTheDates(stringToLocaleDate(checkDate), startDate,
				endDate);
	}

	/**
	 * @param date
	 * @return String after stripping year from date
	 */
	public static String stripYear(String date) {
		int index = date.lastIndexOf("/");
		String strippedDate = date.substring(0, index + 1);

		return strippedDate;

	}

	/**
	 * @param date
	 * @param timeZone
	 * @return ISO8601String
	 */
	public static String getISO8601StringForDate(Date date, String timeZone) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		String currentDate = sdf.format(date);
		if (isTodaysDate(date)) {
			LocalDateTime ldt = LocalDateTime.parse(sdf.format(new Date()),
					DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'"));
			ZonedDateTime localTime = ldt.atZone(ZoneId.systemDefault());
			ZonedDateTime zonedTime = localTime.withZoneSameInstant(ZoneId
					.of(timeZone));
			DateTimeFormatter format = DateTimeFormatter
					.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
			
			currentDate = format.format(zonedTime);
		}
		
		return currentDate; 
	}

	public static boolean isTodaysDate(Date date) {
		return DateUtils.isSameDay(date, new Date());
	}
}
