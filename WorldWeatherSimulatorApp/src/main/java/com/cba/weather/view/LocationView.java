package com.cba.weather.view;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


@SuppressWarnings("restriction")
public   class LocationView {
	
	
	private final StringProperty locationName;

	private final DoubleProperty latitude;
	private final DoubleProperty longitude;
	private final DoubleProperty elevation;
	private final StringProperty localTime;
	private final StringProperty conditions;
	private final StringProperty temperature;
	private final StringProperty pressure;
	private final StringProperty humidity;
	private final StringProperty position;
	private final ObjectProperty<Date> reportDate;
	
	 LocationView(String lName, Double lat, Double longit,Double elev,String time,String cond,String temp, String press,String humid,Date repDate) {
	       
		 
		 this.locationName = new SimpleStringProperty(lName);
		 this.latitude = new SimpleDoubleProperty(lat);
		 this.longitude = new SimpleDoubleProperty(longit);
		 this.elevation = new SimpleDoubleProperty(elev);
		 this.localTime = new SimpleStringProperty(time);
		 this.conditions = new SimpleStringProperty(cond);
		 this.temperature = new SimpleStringProperty(temp);
		 this.pressure = new SimpleStringProperty(press);
		 this.humidity = new SimpleStringProperty(humid);
		 this.position = new SimpleStringProperty(lat.toString()+","
				 +longit.toString()+","+elev.toString());
		 this.reportDate = new SimpleObjectProperty<Date>(repDate);
	    }

	 public String getLocationName() {
         return locationName.get();
     }

     public void setLocationName(String lName) {
    	 locationName.set(lName);
     }

     public Double getLatitude() {
         return latitude.get();
     }

     public void setLatitude(Double lat) {
    	 latitude.set(lat);
     }

     public Double getLongitude() {
         return longitude.get();
     }

     public void setLongitude(Double longit) {
    	 longitude.set(longit);
     }
     public Double getElevation(){
    	 return elevation.get();
     }
 
     public void setElevation(Double elev) {
    	 elevation.set(elev);
     }
     
     public String getLocalTime() {
         return localTime.get();
     }

     public void setLocalTime(String time) {
    	 localTime.set(time);
     }
     public String getConditions() {
         return conditions.get();
     }

     public void setConditions(String cond) {
    	 conditions.set(cond);
     }
     public String getTemperature() {
         return temperature.get();
     }

     public void setTemperature(String temp) {
    	 temperature.set(temp);
     }
     public String getPressure() {
         return pressure.get();
     }

     public void setPressure(String press) {
    	 pressure.set(press);
     }
     public String getHumidity() {
         return humidity.get();
     }

     public void setHumidity(String humid) {
    	 humidity.set(humid);
     }
     public String getPosition() {
         return position.get();
     }

	public Date getReportDate() {
		return reportDate.get();
	}

	 public void setReportDate(Date repDate) {
		 reportDate.set(repDate);
     }
	 public String getDateAsString() {
		 DateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
	     
	        String strDate = (null == reportDate || null == reportDate.get())
	                ? "" : formatter.format(reportDate.get());
	        
	        return strDate;
	    }
}
