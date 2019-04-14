package com.cba.weather.model.enums;

import com.cba.weather.common.constants.WeatherSimulatorConstants;

/**@author milanie
 * WeatherParameter has displayname, its csv column index in history data csv, its unit and the algorithm to calculate the final weather parameter
 */
public enum WeatherParameter {

	TEMPERATURE("Temperature",2,"C",WeatherSimulatorConstants.ALGORITHM1),
	HUMIDITY("Humidity",8,"%",WeatherSimulatorConstants.ALGORITHM1),
	PRESSURE("Pressure",11,"hpa",WeatherSimulatorConstants.ALGORITHM1),	
	CONDITION("Condition",21,"",WeatherSimulatorConstants.ALGORITHM2);
	
	private String displayName;
	private int csvIndex;
	private String unit;
	private String algorithm;

	WeatherParameter(String displayName, Integer csvIndex,String unit,String algorithm) {

		this.setDisplayName(displayName);
		this.setCsvIndex(csvIndex);
		this.setUnit(unit);
		this.setAlgorithm(algorithm);
	}

	public String getAlgorithm() {
		return algorithm;
	}

	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public int getCsvIndex() {
		return csvIndex;
	}

	public void setCsvIndex(int csvIndex) {
		this.csvIndex = csvIndex;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	
	
	
}
