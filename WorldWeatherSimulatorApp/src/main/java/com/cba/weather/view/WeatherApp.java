package com.cba.weather.view;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import com.cba.weather.common.constants.WeatherSimulatorConstants;
import com.cba.weather.controller.WeatherController;
import com.cba.weather.model.LocationWeather;

/**
 * @author milanie
 *
 */

/* 
 * This class launches the Weather Simulator App. 
 * This class has the UI components which includes 
 * 	*** a header Local Data with date and time, 
 *  *** a table with weather details of stations,
 *  *** a text area with delimited weather report data	 * 	 
 */

@SuppressWarnings({ "unchecked", "rawtypes" ,"restriction"})

public class WeatherApp extends Application {
	static String locationData = "";

	public BorderPane localDataHeader;

	final String cssDefault = WeatherSimulatorConstants.DEFAULT_CSS;
	public String currentTime;

	public Button generateDataButton;
	public Button changeDateButton;
	public Button submitButton;

	
	public List<LocationWeather> locationDetails;
	public ObservableList<LocationView> locsModel = FXCollections
			.observableArrayList(new ArrayList<LocationView>());

	public HBox tableActionBox;
	public TableView<LocationView> topCities;
	

	public TextArea weatherData;
	WeatherController weatherController = new WeatherController();

	public WeatherApp() {
		ZonedDateTime now = ZonedDateTime.now();
		currentTime = now.format(DateTimeFormatter.RFC_1123_DATE_TIME);
	}
	
	public static void main(String[] args) {
		launch(args);
	}

	
	/* (non-Javadoc)
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 * Method to launch the App screen
	 */
	@Override
	public void start(Stage primaryStage) {

		localDataHeader = constructLocalDataHeader();
		
		/***Calling controller to retrieve all stations weather details using WeatherCalculator Service*/		
		
		locationDetails = weatherController.retreiveAllStationsWeatherDetails(null);
		
		/****************************************************************/	

		topCities = constructTable(loadLocationsModel(locationDetails));

		tableActionBox = constructTableActionHBox();

		weatherData = constructWeatherDataArea();

		final VBox vbox = new VBox();
		vbox.getChildren().addAll(localDataHeader, tableActionBox, topCities,
				weatherData);
		vbox.setStyle(cssDefault);
		Group root = new Group();
		root.getChildren().add(vbox);
		Scene scene = new Scene(root, 700, 700);
		scene.getStylesheets().add("/darkmantheme.css");
		primaryStage.setScene(scene);

		primaryStage.setTitle(WeatherSimulatorConstants.WEATHER_SIMULATOR);
		primaryStage.show();
	}

	/**Method to construct Local Data Header
	 * 
	 * @return BorderPane
	 */
	private BorderPane constructLocalDataHeader() {
		localDataHeader = new BorderPane();
		HBox localeBox = addLocaleDataHBox();
		localDataHeader.setTop(localeBox);
		return localDataHeader;
	}
/**
 * Method to construct table for displayig station weather details
 * @param locationModel
 * @return TableView
 */
	private TableView<LocationView> constructTable(
			ObservableList<LocationView> locationModel) {
		topCities = new TableView<LocationView>();
		topCities.setEditable(true);

		TableColumn locationCol = new TableColumn("Location");
		TableColumn dateReaderCol = new TableColumn("Report Date (dd/MM/yyyy)");
		TableColumn positionCol = new TableColumn("Position");
		TableColumn localTimeCol = new TableColumn("Local Time");
		TableColumn conditionsCol = new TableColumn("Conditions");
		TableColumn temperatureCol = new TableColumn("Temperature");
		TableColumn pressureCol = new TableColumn("Pressure");
		TableColumn humidity = new TableColumn("Humidity");
		topCities.getColumns().addAll(locationCol, dateReaderCol, positionCol,
				localTimeCol, conditionsCol, temperatureCol, pressureCol,
				humidity);
		topCities.setPrefHeight(350);
		topCities.setPrefWidth(750);

		locationCol
				.setCellValueFactory(new PropertyValueFactory<LocationView, String>(
						"locationName"));

		dateReaderCol.setEditable(true);
		dateReaderCol.setPrefWidth(75);
		dateReaderCol.setMinWidth(200);

		dateReaderCol
				.setCellValueFactory(new PropertyValueFactory<LocationView, Date>(
						"reportDate"));
		dateReaderCol.setCellFactory(new Callback<TableColumn, TableCell>() {
			@Override
			public TableCell call(TableColumn p) {
				DatePickerCell datePick = new DatePickerCell(locationModel);
				return datePick;
			}
		});

		positionCol
				.setCellValueFactory(new PropertyValueFactory<LocationView, String>(
						"position"));
		localTimeCol
				.setCellValueFactory(new PropertyValueFactory<LocationView, String>(
						"localTime"));

		conditionsCol
				.setCellValueFactory(new PropertyValueFactory<LocationView, String>(
						"conditions"));
		temperatureCol
				.setCellValueFactory(new PropertyValueFactory<LocationView, String>(
						"temperature"));
		pressureCol
				.setCellValueFactory(new PropertyValueFactory<LocationView, String>(
						"pressure"));
		humidity.setCellValueFactory(new PropertyValueFactory<LocationView, String>(
				"humidity"));

		dateReaderCol.setVisible(false);
		topCities.setItems(locationModel);
		return topCities;
	}

	/**
	 * Method to construct TableAction Hbox
	 * @return HBox
	 */
	private HBox constructTableActionHBox() {
		HBox hbox = new HBox();
		hbox.setStyle(cssDefault);
		hbox.getChildren().addAll(generateDataButton(),
				changeReportDateButton(), submitButton());
		return hbox;
	}
/**
 * Method to construct local data hbox
 * @return HBox
 */
	private HBox addLocaleDataHBox() {
		HBox hbox = new HBox();
		hbox.setPadding(new Insets(15, 12, 15, 12));
		hbox.setSpacing(10);

		Label time = new Label(currentTime);

		hbox.getChildren().addAll(time);

		hbox.requestFocus();
		hbox.setStyle(cssDefault);
		return hbox;
	}

	/**
	 * @return Button
	 */
	private Button submitButton() {
		submitButton = new Button("Submit");
		submitButton.setVisible(false);
		submitButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {

				List<LocationView> locationViews = topCities.getItems()
						.parallelStream().collect(Collectors.toList());

				locationDetails = weatherController
						.retreiveAllStationsWeatherDetails(locationViews);

				locsModel = loadLocationsModel(locationDetails);
				topCities.setItems(locsModel);

				toggleToResultMode();
			}

		});
		return submitButton;
	}

	/**
	 * @return Button
	 */
	private Button changeReportDateButton() {
		changeDateButton = new Button("Change Report Date");
		changeDateButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				toggleToSearchMode();
			}

		});
		return changeDateButton;
	}

	/**
	 * @return Button
	 */
	private Button generateDataButton() {
		generateDataButton = new Button("Hide Text Data");

		generateDataButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				toggleShowHide();
			}

		});
		return generateDataButton;
	}

	/**construct WeatherDataArea
	 * @return TextArea
	 */
	private TextArea constructWeatherDataArea() {
		weatherData = new TextArea();
		weatherData.setEditable(false);
		weatherData.setPrefRowCount(10);
		weatherData.setPrefColumnCount(100);
		weatherData.setWrapText(true);
		weatherData.setPrefWidth(200);
		weatherData.setPrefHeight(300);
		setWeatherTextData() ;
		return weatherData;
	}

	/**Method to load location details to table
	 * @param locationDetails
	 * @return tableview list
	 */
	private ObservableList<LocationView> loadLocationsModel(
			List<LocationWeather> locationDetails) {
		locsModel.clear();
		List<LocationView> locViewList = new ArrayList<LocationView>();
		locationDetails.forEach(loc -> {
			LocationView locView = new LocationView(loc.getLocationName(), loc
					.getLatitude(), loc.getLongitude(), loc.getElevation(), loc
					.getLocalTime().replaceAll("[T|Z]", " "), loc.getConditions(), loc.getTemperature(),
					loc.getPressure(), loc.getHumidity(), new Date());
			
			locViewList.add(locView);
		});
		locsModel.addAll(locViewList);
		return locsModel;
	}

	/**
	 * Method to toggle screen to result mode
	 */
	private void toggleToResultMode() {
		topCities.getColumns().forEach(col -> {
			col.setVisible(true);
		});
		topCities.getColumns().get(1).setVisible(false);
		weatherData.setVisible(true);
		setWeatherTextData();
		generateDataButton.setVisible(true);
		generateDataButton.setText("Hide Text Data");
		submitButton.setVisible(false);
	}

	/**
	 * Method to toggle screen to search mode
	 */
	private void toggleToSearchMode() {
		submitButton.setVisible(true);
		generateDataButton.setVisible(false);
		weatherData.setVisible(false);
		topCities.getColumns().forEach(col -> {
			col.setVisible(false);
		});
		topCities.getColumns().get(0).setVisible(true);
		topCities.getColumns().get(1).setVisible(true);
	}

	/**
	 * Method to hide and show text data area
	 */
	private void toggleShowHide() {
		if ("Hide Text Data".equals(generateDataButton.getText())) {
			weatherData.setVisible(false);
			generateDataButton.setText("Generate Text Data");
		} else {

			setWeatherTextData();
		}
	}

	/**
	 * Generate weather text data
	 */
	private void setWeatherTextData() {
		locationData ="";
		locationDetails.forEach(data -> {

			locationData = locationData + data + "\n";
		});
		generateDataButton.setText("Hide Text Data");
		weatherData.setText(locationData);
		weatherData.setVisible(true);
	}

}