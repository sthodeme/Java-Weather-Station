package com.itq25.finalproject;

// Removed java.awt.List import to avoid ambiguity
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * APICaller class is responsible for making API calls to the weather service
 * and retrieving weather data based on latitude and longitude.
 */

public class APICaller {

    
    private String currentTemperature;
    private int currentWeatherCode;
    private String currentWeatherPicName;
    private String currentWeather;
    private String currentWindSpeed;
    private String currentWindDirection;
    private List<Double> temperatureToday;
    private List<Double> windSpeedToday;
    private List<Double> precipitationToday;
    private List<Double> temperature_3days;
    private List<Double> windSpeed_3days;
    private List<Double> precipitation_3days;
    
    //private String currentHumidity;

    public APICaller() {
        // Constructor
    }

    public void getWeatherData(float latitude, float longitude, String unitType ) throws IOException, InterruptedException {

        float lat = latitude;
        float lon = longitude;
        System.out.println("Latitude in APICaller: " + lat);
        System.out.println("Longitude in APICaller: " + lon);
        String tempUnit;
        String windUnit;
        String precipitationUnit;
        if (unitType.equals("Imperial")) {
            tempUnit = "temperature_unit=fahrenheit";
            windUnit = "wind_speed_unit=mph";
            precipitationUnit = "precipitation_unit=inch";
        } else {
            tempUnit = "";
            windUnit = "";
            precipitationUnit = "";
        }

        // https://api.open-meteo.com/v1/forecast?latitude=52.52&longitude=13.41
        //&current=temperature_2m,wind_speed_10m
        //&hourly=temperature_2m,wind_speed_10m
        //&forecast_days=1
        //&temperature_unit=fahrenheit&wind_speed_unit=mph
        String basicURL = "https://api.open-meteo.com/v1/forecast?latitude=" + lat + "&longitude=" + lon;
    
        String URLEndPoint_1day = "&current=temperature_2m,weather_code,wind_speed_10m,wind_direction_10m&hourly=temperature_2m,weather_code,wind_speed_10m,precipitation&forecast_days=1"; //&"+tempUnit+"&"+windUnit;
        String URLEndPoint_3days = "&hourly=temperature_2m,wind_speed_10m,precipitation&forecast_days=3";
        //String URLEndPoint3 = "&latitude=50.9333&longitude=6.95&hourly=temperature_2m,rain&forecast_days=1";
        String completeURL_1day = basicURL + URLEndPoint_1day; // + URLEndPoint2 + URLEndPoint3;
        String completeURL_3days = basicURL + URLEndPoint_3days;
        if (unitType.equals("Imperial")) {
            completeURL_1day = completeURL_1day + "&temperature_unit=fahrenheit&wind_speed_unit=mph&precipitation_unit=inch";
            completeURL_3days = basicURL + URLEndPoint_3days + "&temperature_unit=fahrenheit&wind_speed_unit=mph&precipitation_unit=inch";
        } 

        System.out.println("Complete URL: " + completeURL_1day);

        // Call the weather API for 'current' and '1-day forecast' data
        HttpURLConnection connection_1day = (HttpURLConnection) new URL(completeURL_1day).openConnection();
            connection_1day.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection_1day.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            // Parse JSON response
            JSONObject json = new JSONObject(response.toString());

            // Extract current temperature
            BigDecimal tempValue = json.getJSONObject("current").getBigDecimal("temperature_2m");
            float temperature = tempValue.floatValue();  // Convert BigDecimal to float
            if (unitType.equals("Imperial")) {
                currentTemperature = String.valueOf(temperature) + "°F";
            } else {
                currentTemperature = String.valueOf(temperature) + "°C";
            }
            

            // Extract current wind speed
            BigDecimal windSpeedValue = json.getJSONObject("current").getBigDecimal("wind_speed_10m");
            float windSpeed = windSpeedValue.floatValue();  // Convert BigDecimal to float
            if (unitType.equals("Imperial")) {
                currentWindSpeed = String.valueOf(windSpeed) + " miles/h";
            } else {
                currentWindSpeed = String.valueOf(windSpeed) + " km/h";
            }

            // Extract current wind direction
            BigDecimal windDirectionValue = json.getJSONObject("current").getBigDecimal("wind_direction_10m");
            float windDirectionDeg = windDirectionValue.floatValue();  // Convert BigDecimal to float
            currentWindDirection = WindDirectionDegToText(windDirectionDeg);

            // Extract current weather code
            currentWeatherCode = (int) json.getJSONObject("current").get("weather_code");
            // Call the WeatherCodeToText method to get the weather description, as per WMO Weather interpretation codes (WW)
            currentWeather = WeatherCodeToText(currentWeatherCode);
            // Call the CodeToDayPicture method to get the weather picture name
            currentWeatherPicName = CodeToDayPicture(currentWeatherCode);
            
            // Extract JSONArray of today's temperature forecast
            JSONArray temperatureTodayTemp = json.getJSONObject("hourly").getJSONArray("temperature_2m");
            // Convert JSONArray to List<Double>
            temperatureToday = IntStream.range(0, temperatureTodayTemp.length())
                .mapToObj(temperatureTodayTemp::getDouble)
                .collect(Collectors.toList());

            // Extract JSONArray of today's wind speed forecast
            JSONArray windSpeedTodayTemp = json.getJSONObject("hourly").getJSONArray("wind_speed_10m");
            // Convert JSONArray to List<Double>
            windSpeedToday = IntStream.range(0, windSpeedTodayTemp.length())
                .mapToObj(windSpeedTodayTemp::getDouble)
                .collect(Collectors.toList());

            // Extract JSONArray of today's precipitation forecast
            JSONArray precipitationTodayTemp = json.getJSONObject("hourly").getJSONArray("precipitation");
            // Convert JONArray to List<Double>
            precipitationToday = IntStream.range(0, precipitationTodayTemp.length())
                .mapToObj(precipitationTodayTemp::getDouble)
                .collect(Collectors.toList());

            // // ######################## End of API call for '1-day forecast' data ##########################################

            // Call the weather API for '3-day forecast' data
            HttpURLConnection connection_3days = (HttpURLConnection) new URL(completeURL_3days).openConnection();
            connection_3days.setRequestMethod("GET");
            BufferedReader reader_3days = new BufferedReader(new InputStreamReader(connection_3days.getInputStream()));
            StringBuilder response_3days = new StringBuilder();
            String line_3days;
            while ((line_3days = reader_3days.readLine()) != null) {
                response_3days.append(line_3days);
            }
            reader_3days.close();
            // Parse JSON response
            JSONObject json_3days = new JSONObject(response_3days.toString());
            // Extract JSONArray of 3-days temperature forecast
            JSONArray temperatureToday_3days = json_3days.getJSONObject("hourly").getJSONArray("temperature_2m");
            // Convert JSONArray to List<Double>
            temperature_3days = IntStream.range(0, temperatureToday_3days.length())
                .mapToObj(temperatureToday_3days::getDouble)
                .collect(Collectors.toList());
            // Extract JSONArray of 3-days wind speed forecast
            JSONArray windSpeedToday_3days = json_3days.getJSONObject("hourly").getJSONArray("wind_speed_10m");
            // Convert JSONArray to List<Double>
            windSpeed_3days = IntStream.range(0, windSpeedToday_3days.length())
                .mapToObj(windSpeedToday_3days::getDouble)
                .collect(Collectors.toList());

            // Extract JSONArray of 3-days precipitation forecast
            JSONArray precipitationToday_3days = json_3days.getJSONObject("hourly").getJSONArray("precipitation");
            // Convert JSONArray to List<Double>
            precipitation_3days = IntStream.range(0, precipitationToday_3days.length())
                .mapToObj(precipitationToday_3days::getDouble)
                .collect(Collectors.toList());
            
            // ######################## End of API call for '3-day forecast' data ##########################################
 
    }


    private String WindDirectionDegToText(float windDirectionDeg) {

        if (windDirectionDeg >= 0 && windDirectionDeg < 22.5) {
            return "N";
        } else if (windDirectionDeg >= 22.5 && windDirectionDeg < 67.5) {
            return "NE";
        } else if (windDirectionDeg >= 67.5 && windDirectionDeg < 112.5) {
            return "E";
        } else if (windDirectionDeg >= 112.5 && windDirectionDeg < 157.5) {
            return "SE";
        } else if (windDirectionDeg >= 157.5 && windDirectionDeg < 202.5) {
            return "S";
        } else if (windDirectionDeg >= 202.5 && windDirectionDeg < 247.5) {
            return "SW";
        } else if (windDirectionDeg >= 247.5 && windDirectionDeg < 292.5) {
            return "W";
        } else if (windDirectionDeg >= 292.5 && windDirectionDeg < 337.5) {
            return "NW";
        } else {
            return "N"; // Default case
        }
    }

    private String WeatherCodeToText(int currentWeatherCode) {
        switch (currentWeatherCode) {
            case 0:
                return "Clear sky";
            case 1:
                return "Mainly clear";
            case 2:
                return "Partly cloudy";
            case 3:
                return "Overcast";
            case 45:
                return "Fog";
            case 48:
                return "Fog"; //Depositing rime fog";
            case 51:
                return "Drizzle"; //: Light intensity";
            case 53:
                return "Drizzle"; //: Moderate intensity";
            case 55:
                return "Drizzle"; //: Dense intensity";
            case 56:
                return "Freezing Drizzle"; //: Light intensity";
            case 57:
                return "Freezing Drizzle"; //: Dense intensity";
            case 61:
                return "Rain"; //: Slight intensity";
            case 63:
                return "Rain"; //: Moderate intensity";
            case 65:
                return "Rain"; //" Heavy intensity";
            case 66:
                return "Freezing Rain"; //: Light intensity";
            case 67:
                return "Freezing Rain"; //: Heavy intensity";
            case 71:
                return "Snow fall"; //: Slight intensity";
            case 73:
                return "Snow fall"; //: Moderate intensity";
            case 75:
                return "Snow fall"; //: Heavy intensity";
            case 77:
                return "Snow grains";
            case 80:
                return "Rain showers"; //: Slight intensity";
            case 81:
                return "Rain showers"; //: Moderate intensity";
            case 82:
                return "Rain showers"; //: Violent intensity";
            case 85:
                return "Snow showers"; //: Slight intensity";
            case 86:
                return "Snow showers"; //: Heavy intensity";
            case 95:
                return "Thunderstorm"; //: Slight or moderate";
            case 96:
                return "Thunderstorm"; // with slight hail";
            case 99:
                return "Thunderstorm"; // with heavy hail";
            default:
                throw new AssertionError();
        }
    }

    public static String CodeToDayPicture(int currentWeatherCode) {
        // Weather Pictures directory path
        String path = "/Users/sreeram/Documents/SreeRam/ITQ-Final Project/Self Executable Code/Java-Weather-Station/Weather-Station/Resources/Pictures/";
        switch (currentWeatherCode) {
            case 0:
                return path + "Sunny.png"; // Clear sky
            case 1:
                return path + "Sunny.png"; // "Mainly clear";
            case 2:
                return path + "PartlyCloudy.png"; // partly cloudy
            case 3:
                return path + "Cloudy.png"; // Overcast
            case 45:
                return path + "Foggy.png"; // foggy 
            case 48:
                return path + "Foggy.png"; // "Depositing rime fog"
            case 51:
                return path + "Rainy.png"; // "Drizzle: Light intensity";
            case 53:
                return path + "Rainy.png"; // "Drizzle: Moderate intensity";
            case 55:
                return path + "Rainy.png"; // "Drizzle: Dense intensity";
            case 56:
                return path + "FreezingDrizzle.png"; // "Freezing Drizzle: Light intensity"
            case 57:
                return path + "FreezingDrizzle.png"; // "Freezing Drizzle: Dense intensity"
            case 61:
                return path + "Rainy.png"; // Rain: Slight intensity
            case 63:
                return path + "Rainy.png"; // Rain: moderate intensity
            case 65:
                return path + "Rainy.png"; // Rain: heavy intensity
            case 66:
                return path + "Rainy.png"; //"Freezing Rain: Light intensity"
            case 67:
                return path + "Rainy.png"; // "Freezing Rain: Heavy intensity"
            case 71:
                return path + "SnowFall.png"; // "Snow fall: Slight intensity"
            case 73:
                return path + "SnowFall.png"; // "Snow fall: Moderate intensity"
            case 75:
                return path + "SnowFall.png"; // "Snow fall: Heavy intensity"
            case 77:
                return path + "SnowFall.png"; // "Snow grains"
            case 80:
                return path + "Rainy.png"; // "Rain showers: Slight intensity"
            case 81:
                return path + "Rainy.png"; // "Rain showers: Moderate intensity"
            case 82:
                return path + "Rainy.png"; // "Rain showers: Violent intensity"
            case 85:
                return path + "SnowFall.png"; //  "Snow showers: Slight intensity"
            case 86:
                return path + "SnowFall.png"; // "Snow showers: Heavy intensity"
            case 95:
                return path + "ThunderStorm.png"; // Thunderstorm: Slight or moderate
            case 96:
                return path + "ThunderStorm.png"; // Thunderstorm with slight and heavy hail
            case 99:
                return path + "ThunderStorm.png"; // Thunderstorm with slight and heavy hail
            default:
                throw new AssertionError();
            }
        }

    public String getCurrentTemperature() {
        return currentTemperature;
    }
    public String getCurrentWeather() {
        return currentWeather;
    }

    public List<Double> getTemperatureToday() {
        return temperatureToday; 
    }

    public String getCurrentWindSpeed() {
        return currentWindSpeed;
    }

    public String getCurrentWindDirection() {
        return currentWindDirection;
    }

    public List<Double> getWindSpeedToday() {
        return windSpeedToday;
    }
    public List<Double> getPrecipitationToday() {
        return precipitationToday;
    }
    

    public List<Double> getTemperature_3days() {
        return temperature_3days;
    }

    public List<Double> getWindSpeed_3days() {
        return windSpeed_3days;
    }
    public List<Double> getPrecipitation_3days() {
        return precipitation_3days;
    }

    public String getCodeToPicture() {
        return currentWeatherPicName;
    }


    /* 
    public String getCurrentHumidity() {
        return currentHumidity;
    }
    */

}
