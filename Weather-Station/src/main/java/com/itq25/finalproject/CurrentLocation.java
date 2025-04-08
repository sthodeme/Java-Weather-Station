package com.itq25.finalproject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

/**
 * This class is used to get the current location of the user.<br>
 * It uses an API to get the location details based on the user's IP address.<br>
 * The class provides methods to get the city, country, latitude, and longitude of the current location.<br>
 *
 * @author itq25/Sree Ram
 * @version 1.0
 */

public class CurrentLocation {

    static String city;
    static String country;
    static float latitude;
    static float longitude;


    public CurrentLocation() {

        getLocation();
        /*
        this.city = city;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
        */
    }


    private void getLocation() {
        try {
            // API to get location details
            String apiURL = "http://ip-api.com/json/";
            HttpURLConnection connection = (HttpURLConnection) new URL(apiURL).openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            } 
            reader.close();

            // Parse JSON response
            JSONObject json = new JSONObject(response.toString());
            //String city = json.getString("city");
            //String country = json.getString("country");

            // Update/Initiate class variables
            this.city = json.getString("city");
            this.country = json.getString("country");
            this.latitude = (float) json.getDouble("lat");
            this.longitude = (float) json.getDouble("lon");

            //return city + ", " + country;  // Example: "New York, United States"
        } catch (Exception e) {
            this.city = "city not found";
            this.country = "country not found";
            this.latitude = 0;
            this.longitude = 0;
        }
    }


}
