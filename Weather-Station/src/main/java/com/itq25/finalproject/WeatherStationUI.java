/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.itq25.finalproject;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.TextField;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
    * This class is used to create the UI for the Weather Station application.
    *
    * It displays weather information of the current location of the user.<br>
        * For this, it uses the CurrentLocation class to find the current location of the user.<br>
    * It also allows the user to change the location.
    
    @author itq25/Sree Ram
    @version 1.0
    */

class WeatherStationUI extends JFrame{
    
    // User location information automatically extracted from user IP address
    private String currentCity; // = currentLocation.city;
    private String currentCountry; // = currentLocation.country;
    private float currentLatitude; // = currentLocation.latitude;
    private float currentLongitude; // = currentLocation.longitude;

    // Differnt Location selected by user
    private String userSelectedCity;
    private String userSelectedCountry;
    private float userSelectedLatitude;
    private float userSelectedLongitude;
    private final Image backgroundImage;

    private String currentTemperature;
    private String currentWeather;
    private String currentWindSpeed;
    private String currentWindDirection;
    private String roomTemperatureValue;
    private String roomHumidityValue;


    List<Double> temperatureToday;
    List<Double> windSpeedToday;
    List<Double> precipitationToday;

    List<Double> temperature_3days;
    List<Double> windSpeed_3days;
    List<Double> precipitation_3days;

    // Display Elements
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    TextField dateTimeTextField;
    TextField dateTimeValueField;
    
    TextField locationTextField;
    TextField locationValueField;
    
    TextField currentWeatherTextField;

    TextField outsideTemperatureTextField;
    TextField outsideTemperatureValueField;

    TextField outsideWeatherTextField;
    TextField outsideWeatherValueField;

    TextField windTextField;
    TextField windValueField;

    TextField windDirectionTextField;
    TextField windDirectionValueField;

    ForecastGraphPanel panelToday;
    ForecastGraphPanel panel3Days;
    
    private TextField userSelectedCityField;

    // Image for current weather condition
    private String currentWeatherPicFullPath;
    JLabel weatherImageJLabel = new JLabel();
    //private String currentWeatherPicFullPath Name; // = apiCaller.getcurrentWeatherPicFullPath Name();
    ImageIcon imageIconCurrentWeather = new ImageIcon(); //(currentWeatherPicFullPath);
    //imageIconCurrentWeather.setImage(imageIconCurrentWeather.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH));
     // to shown waether inage based on current 'weather code'
    /*
    private String currentWeatherPicFullPath;
    JLabel weatherImageJLabel = new JLabel();
    ImageIcon imageIconCurrentWeather = new ImageIcon(currentWeatherPicFullPath);
    weatherImageJLabel.setIcon(imageIconCurrentWeather);
     */

    // background image paths
    String imagePath = "/Users/sreeram/Documents/SreeRam/ITQ-Final Project/ITQ25WeatherStation-1/weather-station/src/main/java/com/itq25/finalproject/images/sunnyday.jpg";
    

    public WeatherStationUI() throws IOException, InterruptedException{

        // Weather parameter values type
        final String[] UnitType = {"Metric"}; // (metric or imperial): Metric, by default
        //final String[] tempType = {"Celsius"}; // (Celsius or Fahrenheit): Celsius, by default
        //final String[] windSpeedUnit = {"km/h"}; // (km/h or mph): km/h, by default

        JFrame frame = new JFrame("Weather Station                          @linkedin.com/in/srgreddy/");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int frameWidth = 1150;
        int frameHeight = 950;
        frame.setMinimumSize(new Dimension(frameWidth, 600));
        frame.setMaximumSize(new Dimension(frameWidth, frameHeight));
        frame.setPreferredSize(new Dimension(frameWidth, 620));
        frame.setLocationRelativeTo(null);
        //frame.setResizable(true);
        //frame.setVisible(true);
        //frame.setLayout(null);
        frame.getContentPane().setLayout(new BorderLayout());

        JPanel contentPanel = new JPanel(null); // Using null layout to preserve setBounds()
        contentPanel.setPreferredSize(new Dimension(1200, 1200)); // Make this large enough for scrolling

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);


        frame.setTitle("Weather Station                          @linkedin.com/in/srgreddy/");
        // Load the background image
        this.backgroundImage = new ImageIcon(imagePath).getImage();

        // Find 'current location' of the user by initiating the CurrentLocation class
        // Create a new instance of the CurrentLocation class
        CurrentLocation currentLocation = new CurrentLocation();
        
        // Use the instance to access the city and country
        this.currentCity = currentLocation.city;
        this.currentCountry = currentLocation.country;
        this.currentLatitude = currentLocation.latitude;
        this.currentLongitude = currentLocation.longitude;

        // Differnt Location selected by user
        //String userSelectedCity;
        //String userSelectedLatitude;
        //String userSelectedLongitude;

        // Call Weather API with above location (currentLatitude, currentLongitude) information
        APICaller apiCallerDefault = new APICaller();
        apiCallerDefault.getWeatherData(currentLatitude, currentLongitude, UnitType[0]);
        // Use the instance to access the weather data
        currentTemperature = apiCallerDefault.getCurrentTemperature();
        currentWeather = apiCallerDefault.getCurrentWeather();
        currentWindSpeed = apiCallerDefault.getCurrentWindSpeed();
        currentWindDirection = apiCallerDefault.getCurrentWindDirection();
        temperatureToday = apiCallerDefault.getTemperatureToday();
        windSpeedToday = apiCallerDefault.getWindSpeedToday();
        precipitationToday = apiCallerDefault.getPrecipitationToday();
        temperature_3days = apiCallerDefault.getTemperature_3days();
        windSpeed_3days = apiCallerDefault.getWindSpeed_3days();
        precipitation_3days = apiCallerDefault.getPrecipitation_3days();
        currentWeatherPicFullPath = apiCallerDefault.getCodeToPicture();


        System.out.println("");
        System.out.println(" ########## print at apiCallerDefault ############");
        System.out.println("currentTemperature: " + currentTemperature);
        System.out.println("currentWeather: " + currentWeather);
        System.out.println("temperatureToday: " + temperatureToday);
        System.out.println("currentWindSpeed: " + currentWindSpeed);
        System.out.println("currentWindDirection: " + currentWindDirection);
        System.out.println("#####################################################");
        System.out.println("");

        //String currentHumidity = apiCaller.getCurrentHumidity();

        // Add components to the frame here

        ///Add 'Refresh' button'
        JButton refreshButton = new JButton();
        refreshButton.setBounds(955, 5, 130, 30);
        refreshButton.setText("Refresh Data");
        refreshButton.setForeground(Color.BLUE); // Changes the text color
        refreshButton.setOpaque(true);
        refreshButton.setContentAreaFilled(true); 
        refreshButton.setBorderPainted(true);
        contentPanel.add(refreshButton);
      
        //refreshButton.addActionListener(e -> refreshUI());


       
        // Add ActionListener to the 'refreshButton' Button
        refreshButton.addActionListener(e -> {
            // Refresh the data
            try {
                APICaller apiCallerRefresh = new APICaller();

                apiCallerRefresh.getWeatherData(currentLatitude, currentLongitude, UnitType[0]);
                // Use the instance to access the weather data

                // Update the values with the new data

                currentTemperature = apiCallerRefresh.getCurrentTemperature();
                currentWeather = apiCallerRefresh.getCurrentWeather();
                currentWeatherPicFullPath = apiCallerDefault.getCodeToPicture();
                currentWindSpeed = apiCallerRefresh.getCurrentWindSpeed();
                currentWindDirection = apiCallerRefresh.getCurrentWindDirection();
                temperatureToday = apiCallerRefresh.getTemperatureToday();
                windSpeedToday = apiCallerRefresh.getWindSpeedToday();
                precipitationToday = apiCallerRefresh.getPrecipitationToday();
                temperature_3days = apiCallerRefresh.getTemperature_3days();
                windSpeed_3days = apiCallerRefresh.getWindSpeed_3days();
                precipitation_3days = apiCallerRefresh.getPrecipitation_3days();

                // Update the UI with the new data
                dateTimeValueField.setText(java.time.LocalDateTime.now().format(formatter));
                outsideTemperatureValueField.setText(currentTemperature);
                outsideWeatherValueField.setText(currentWeather);
                windValueField.setText(currentWindSpeed);
                windDirectionValueField.setText(currentWindDirection);
                weatherImageJLabel.setIcon(new ImageIcon(new ImageIcon(currentWeatherPicFullPath).getImage().getScaledInstance(100  , 100, Image.SCALE_SMOOTH))); // Update the current weather image
            
                // Update the current weather image
                

                // Remove old panels from the content panel
                contentPanel.remove(panelToday);
                contentPanel.remove(panel3Days);

                // Create new panels with updated data
                panelToday = new ForecastGraphPanel(temperatureToday, windSpeedToday, precipitationToday, 1, UnitType[0]);
                panelToday.setBounds(50, 220, 1050, 300);
                panelToday.setBackground(Color.LIGHT_GRAY);

                panel3Days = new ForecastGraphPanel(temperature_3days, windSpeed_3days, precipitation_3days, 3, UnitType[0]);
                panel3Days.setBounds(50, 585, 1050, 300);
                panel3Days.setBackground(Color.LIGHT_GRAY);

                // Add updated panels back to content panel
                contentPanel.add(panelToday);
                contentPanel.add(panel3Days);

                // Refresh layout
                contentPanel.revalidate();
                contentPanel.repaint();
                System.out.println("Data refreshed successfully!");
                System.out.println("tomperatureToday: " + temperatureToday);
                System.out.println("windSpeedToday: " + windSpeedToday);
                System.out.println("precipitationToday: " + precipitationToday);
            } catch (IOException | InterruptedException ex) {
                ex.printStackTrace();
            }
        });





        //Add Date and Time
        dateTimeTextField = new TextField();
        dateTimeTextField.setBounds(50, 50, 85, 20);
        dateTimeTextField.setEditable(false);
        dateTimeTextField.setText("Date/Time: ");
        contentPanel.add(dateTimeTextField);

        dateTimeValueField = new TextField();
        dateTimeValueField.setBounds(145, 50, 145, 20);
        dateTimeValueField.setEditable(false);
        dateTimeValueField.setText(java.time.LocalDateTime.now().format(formatter));
        contentPanel.add(dateTimeValueField);

        // Add 'current location' field
        locationTextField = new TextField();
        locationTextField.setBounds(50, 80, 85, 20);
        locationTextField.setEditable(false);
        locationTextField.setText("Location:");
        contentPanel.add(locationTextField);

        // Add 'current location' information 
        locationValueField = new TextField();
        locationValueField.setBounds(145, 80, 200, 20);
        locationValueField.setEditable(false);
        locationValueField.setText(currentCity + ", " + currentCountry);
        contentPanel.add(locationValueField);

        // Add current weather image
        //currentWeatherPicFullPath  = new ImageIcon(currentWeatherPicFullPath Name);
        currentWeatherPicFullPath = apiCallerDefault.getCodeToPicture();
        
        // Add weather image to the JLabel
        weatherImageJLabel.setIcon(new ImageIcon(new ImageIcon(currentWeatherPicFullPath).getImage().getScaledInstance(100  , 100, Image.SCALE_SMOOTH)));
        weatherImageJLabel.setBounds(650, 50, 100, 100);
        contentPanel.add(weatherImageJLabel);


        // Add 'current Weather' field
        currentWeatherTextField = new TextField();
        currentWeatherTextField.setBounds(800, 50, 150, 20);
        currentWeatherTextField.setEditable(false);
        currentWeatherTextField.setText("Current Weather:");
        //currentWeatherTextField.setHorizontalAlignment(SwingConstants.CENTER);
        contentPanel.add(currentWeatherTextField);

        ///Add measurement unit system (Metric/Imperial )option
        JButton measurementSystemButton = new JButton();
        measurementSystemButton.setBounds(955, 45, 150, 30);
        measurementSystemButton.setText("Imperial Unit?");
        //measurementSystemButton.setBackground(Color.YELLOW);
        measurementSystemButton.setForeground(Color.BLUE); // Changes the text color
        measurementSystemButton.setOpaque(true);
        measurementSystemButton.setContentAreaFilled(true); 
        measurementSystemButton.setBorderPainted(true);
        contentPanel.add(measurementSystemButton);

        // Add ActionListener to the 'measurementSystemButton' Button
        measurementSystemButton.addActionListener(e -> {
            APICaller apiCallerUnitChange = new APICaller();
            // Switch between Metric and Imperial units
            if (UnitType[0].equals("Metric")) {
                UnitType[0] = "Imperial";
                //tempType[0] = "Fahrenheit";
                //windSpeedUnit[0] = "mph";
                measurementSystemButton.setText("Metric Unit?");
            } else {
                UnitType[0] = "Metric";
                //tempType[0] = "Celsius";
                //windSpeedUnit[0] = "km/h";
                measurementSystemButton.setText("Imperial Unit?");
            }
            // call the API again with the new unit type
            try {

                // Call all APIs 
                apiCallerUnitChange.getWeatherData(currentLatitude, currentLongitude, UnitType[0]);

                // Use the instance to access the weather data

                // Update the values with the new data
                currentTemperature = apiCallerUnitChange.getCurrentTemperature();
                currentWeather = apiCallerUnitChange.getCurrentWeather();
                currentWindSpeed = apiCallerUnitChange.getCurrentWindSpeed();
                currentWindDirection = apiCallerUnitChange.getCurrentWindDirection();
                temperatureToday = apiCallerUnitChange.getTemperatureToday();
                windSpeedToday = apiCallerUnitChange.getWindSpeedToday();
                precipitationToday = apiCallerUnitChange.getPrecipitationToday();
                temperature_3days = apiCallerUnitChange.getTemperature_3days();
                windSpeed_3days = apiCallerUnitChange.getWindSpeed_3days();
                precipitation_3days = apiCallerUnitChange.getPrecipitation_3days();

                // Update the UI with the new data
                outsideTemperatureValueField.setText(currentTemperature);
                outsideWeatherValueField.setText(currentWeather);
                windValueField.setText(currentWindSpeed);
                windDirectionValueField.setText(currentWindDirection);
       
                // Remove old panels from the content panel
                contentPanel.remove(panelToday);
                contentPanel.remove(panel3Days);

                // Create new panels with updated data
                panelToday = new ForecastGraphPanel(temperatureToday, windSpeedToday, precipitationToday, 1, UnitType[0]);
                panelToday.setBounds(50, 220, 1050, 300);
                panelToday.setBackground(Color.LIGHT_GRAY);

                panel3Days = new ForecastGraphPanel(temperature_3days, windSpeed_3days, precipitation_3days , 3, UnitType[0]);
                panel3Days.setBounds(50, 585, 1050, 300);
                panel3Days.setBackground(Color.LIGHT_GRAY);

                // Add updated panels back to content panel
                contentPanel.add(panelToday);
                contentPanel.add(panel3Days);

                // Refresh layout
                contentPanel.revalidate();
                contentPanel.repaint();




            } catch (IOException | InterruptedException ex) {
                ex.printStackTrace();
            }
            System.out.println("");
            System.out.println(" ########## print at apiCallerUnitChange ############");
            System.out.println("currentTemperature: " + currentTemperature);
            System.out.println("currentWeather: " + currentWeather);
            System.out.println("temperatureToday: " + temperatureToday);
            System.out.println("windSpeedToday: " + windSpeedToday);
            System.out.println("precipitationToday: " + precipitationToday);
            System.out.println("currentWindSpeed: " + currentWindSpeed);
            System.out.println("currentWindDirection: " + currentWindDirection);
            System.out.println("#####################################################");
            System.out.println("");
            frame.revalidate(); // Updates layout if components changed
            frame.repaint();    // Redraws the entire frame

        });

        // Add 'current weather' panel
        JPanel currentWeatherPanel = new JPanel();
        currentWeatherPanel.setBounds(800, 75, 300, 140);
        currentWeatherPanel.setBackground(Color.LIGHT_GRAY);
        currentWeatherPanel.setLayout(new GridLayout(4, 2, 5 ,5));
        contentPanel.add(currentWeatherPanel);
 
        // Add 'Outside Temperature' Text
        outsideTemperatureTextField = new TextField();
        outsideTemperatureTextField.setBounds(800, 300, 100, 30);
        outsideTemperatureTextField.setEditable(false);
        outsideTemperatureTextField.setText("Outside Temperature");
        currentWeatherPanel.add(outsideTemperatureTextField);
        // Add 'Outside temperature' information
        outsideTemperatureValueField = new TextField();
        outsideTemperatureValueField.setBounds(800, 100, 100, 30);
        outsideTemperatureValueField.setEditable(false);
        outsideTemperatureValueField.setText(currentTemperature);
        currentWeatherPanel.add(outsideTemperatureValueField);
        // Add 'Outside weather' Text
        outsideWeatherTextField = new TextField();
        outsideWeatherTextField.setBounds(800, 350, 100, 30);
        outsideWeatherTextField.setEditable(false);
        outsideWeatherTextField.setText("Outside Weather");
        currentWeatherPanel.add(outsideWeatherTextField);
        // Add 'Outside weather' information
        outsideWeatherValueField = new TextField();
        outsideWeatherValueField.setBounds(800, 150, 100, 30);
        outsideWeatherValueField.setEditable(false);
        outsideWeatherValueField.setText(currentWeather);
        currentWeatherPanel.add(outsideWeatherValueField);

        // Add 'Outside Wind' Text
        windTextField = new TextField();
        windTextField.setBounds(800, 350, 100, 30);
        windTextField.setEditable(false);
        windTextField.setText("Wind Speed");
        currentWeatherPanel.add(windTextField);
        // Add 'wind value' information
        windValueField = new TextField();
        windValueField.setBounds(800, 150, 100, 30);
        windValueField.setEditable(false);
        windValueField.setText(currentWindSpeed);
        currentWeatherPanel.add(windValueField);

        // Add 'Outside Wind Direction' Text
        windDirectionTextField = new TextField();
        windDirectionTextField.setBounds(800, 350, 100, 30);
        windDirectionTextField.setEditable(false);
        windDirectionTextField.setText("Wind Direction");
        currentWeatherPanel.add(windDirectionTextField);
        // Add 'wind value' information
        windDirectionValueField = new TextField();
        windDirectionValueField.setBounds(800, 150, 100, 30);
        windDirectionValueField.setEditable(false);
        windDirectionValueField.setText(currentWindDirection);
        currentWeatherPanel.add(windDirectionValueField);

        // TextField for 'Today's forecast'
        TextField forecastToday = new TextField();
        forecastToday.setBounds(50, 195, 250, 20);
        forecastToday.setEditable(false);
        forecastToday.setText("Today's Temperature & Wind Forecast");
        contentPanel.add(forecastToday);
        
        // Add the custom panel to the frame, to plot today's temperature forecast
        panelToday = new ForecastGraphPanel(temperatureToday, windSpeedToday, precipitationToday, 1, UnitType[0]);
        panelToday.setBounds(50, 220, 1050, 300);
        panelToday.setBackground(Color.LIGHT_GRAY);
        contentPanel.add(panelToday);
        frame.setVisible(true);

        // TextField for 'next 3 days Temperature forecast'
        TextField forecast3Days = new TextField();
        forecast3Days.setBounds(50, 550, 200, 30);
        forecast3Days.setEditable(false);
        forecast3Days.setText("3 Days Temperature Forecast");
        contentPanel.add(forecast3Days);

        // Add the custom panel to the frame, to plot 3-days forecast
        panel3Days = new ForecastGraphPanel(temperature_3days, windSpeed_3days, precipitation_3days, 3, UnitType[0]);
        panel3Days.setBounds(50, 585, 1050, 300);
        panel3Days.setBackground(Color.LIGHT_GRAY);
        contentPanel.add(panel3Days);
        frame.setVisible(true);

        // Add Button to allow user to change location
        JButton changeLocationButton = new JButton();
        changeLocationButton.setBounds(350, 75, 160, 30);
        changeLocationButton.setText("Change Location?");
        //changeLocationButton.setBackground(Color.YELLOW);
        changeLocationButton.setForeground(Color.BLUE); // Changes the text color
        changeLocationButton.setOpaque(true);
        changeLocationButton.setContentAreaFilled(true);
        

        contentPanel.add(changeLocationButton);

        // Add ActionListener to the 'changeLocationButton' Button
        changeLocationButton.addActionListener(e -> {
                // Call the CityFinder class to ask user to enter a city name
                CityFinder cityFinder = new CityFinder(frame);
                
                // Collect back the user selected city name, country, latitude and longitude
                this.currentCity = cityFinder.cityName + ", " + cityFinder.country;
                this.locationValueField.setText(currentCity);
                this.userSelectedCity = cityFinder.cityName;
                this.userSelectedCountry = cityFinder.country;
                this.userSelectedLatitude = cityFinder.latitude;
                this.userSelectedLongitude = cityFinder.longitude;
                this.currentCity = userSelectedCity; 
                this.currentCountry = userSelectedCountry;
                this.currentLatitude = userSelectedLatitude; 
                this.currentLongitude = userSelectedLongitude; 
                System.out.println("At Seat Button AL: userSelectedCity: " + this.userSelectedCity);
                System.out.println("At Seat Button AL: userSelectedLatitude: " + this.userSelectedLatitude);
                System.out.println("At Seat Button AL: userSelectedLongitude: " + this.userSelectedLongitude);
                // Call the API with the new city coordinates
                APICaller apiCallerWithSelectedCity = new APICaller();

                try {
                    System.out.println("Calling API 'apiCallerWithSelectedCity' with coordinates: " + userSelectedLatitude + ", " + userSelectedLongitude);
                    apiCallerWithSelectedCity.getWeatherData(userSelectedLatitude, userSelectedLongitude, UnitType[0]);
                    // Use the instance to access the weather data
    
                    // Update the values with the new data
                    currentTemperature = apiCallerWithSelectedCity.getCurrentTemperature();
                    
                    currentWeather = apiCallerWithSelectedCity.getCurrentWeather();
                    currentWindSpeed = apiCallerWithSelectedCity.getCurrentWindSpeed();
                    currentWindDirection = apiCallerWithSelectedCity.getCurrentWindDirection();
                    temperatureToday = apiCallerWithSelectedCity.getTemperatureToday();
                    windSpeedToday = apiCallerWithSelectedCity.getWindSpeedToday();
                    precipitationToday = apiCallerWithSelectedCity.getPrecipitationToday();
                    temperature_3days = apiCallerWithSelectedCity.getTemperature_3days();
                    windSpeed_3days = apiCallerWithSelectedCity.getWindSpeed_3days();
                    precipitation_3days = apiCallerWithSelectedCity.getPrecipitation_3days();
                    currentWeatherPicFullPath = apiCallerWithSelectedCity.getCodeToPicture();
    
                    // Update the UI with the new data
                    //locationValueField.setText(userSelectedCity);
                    locationValueField.setText(userSelectedCity + ", " + userSelectedCountry);
                    outsideTemperatureValueField.setText(currentTemperature);
                    outsideWeatherValueField.setText(currentWeather);
                    windValueField.setText(currentWindSpeed);
                    windDirectionValueField.setText(currentWindDirection);
                    weatherImageJLabel.setIcon(imageIconCurrentWeather);
                    weatherImageJLabel.setIcon(new ImageIcon(new ImageIcon(currentWeatherPicFullPath).getImage().getScaledInstance(100  , 100, Image.SCALE_SMOOTH)));
           
                    // Remove old panels from the content panel
                    contentPanel.remove(panelToday);
                    contentPanel.remove(panel3Days);
    
                    // Create new panels with updated data
                    panelToday = new ForecastGraphPanel(temperatureToday, windSpeedToday, precipitationToday, 1, UnitType[0]);
                    panelToday.setBounds(50, 220, 1050, 300);
                    panelToday.setBackground(Color.LIGHT_GRAY);
    
                    panel3Days = new ForecastGraphPanel(temperature_3days, windSpeed_3days, precipitation_3days , 3, UnitType[0]);
                    panel3Days.setBounds(50, 585, 1050, 300);
                    panel3Days.setBackground(Color.LIGHT_GRAY);
    
                    // Add updated panels back to content panel
                    contentPanel.add(panelToday);
                    contentPanel.add(panel3Days);
    
                    // Refresh layout
                    contentPanel.revalidate();
                    contentPanel.repaint();

                } catch (IOException | InterruptedException ex) {
                    ex.printStackTrace();
                }
                
                // You can use the cityFinder instance to get the latitude and longitude

                // For now, just print it to the console
                //System.out.println("Searching for: " + cityName);
                // Close the cityFinderFrame after searching
                //cityFinderFrame.dispose();
            //});

        });

        frame.pack();  // Adjusts frame to fit scrollPane
        frame.setVisible(true);

        frame.revalidate(); // Updates layout if components changed
            frame.repaint();    // Redraws the entire frame
            System.out.println("At endpoin: userSelectedCity: " + this.userSelectedCity);
            System.out.println("At endpoin: userSelectedLatitude: " + this.userSelectedLatitude);
            System.out.println("At endpoin: userSelectedLongitude: " + this.userSelectedLongitude);
        }
    

    }
    


