package com.itq25.finalproject;

import java.awt.FlowLayout;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class CityFinder {

    static String cityName;
    static String country;
    static float latitude;
    static float longitude;

    static JsonObject[] cityObjects;
/* 
    public static void main(String[] args) {
        SwingUtilities.invokeLater(CityFinder::createGUI);
    }
*/
    // Constructor to initialize the GUI
    public CityFinder(JFrame parentFrame) {
        createGUI(parentFrame);
        //SwingUtilities.invokeLater(CityFinder::createGUI);
    }
    private static void createGUI(JFrame parentFrame) {
        // Create a modal dialog
        JDialog dialog = new JDialog(parentFrame, "City Finder", true);
        dialog.setSize(400, 300);
        dialog.setLayout(new FlowLayout());

        JTextField cityInputField = new JTextField(20);
        JButton searchButton = new JButton("Search");
        JLabel resultLabel = new JLabel("Enter city name and click search.                                     ");
        dialog.add(resultLabel);
        dialog.add(new JLabel("Place:"));
        dialog.add(cityInputField);
        dialog.add(searchButton);
        

        searchButton.addActionListener(e -> {
            String input = cityInputField.getText().trim();
            if (!input.isEmpty()) {
                searchCity(input, dialog);
            } else {
                resultLabel.setText("Please enter a city name.");
            }
        });

        dialog.setLocationRelativeTo(parentFrame);
        dialog.setVisible(true); // blocks until dialog is closed
    }

     private static void searchCity(String inputCity, JDialog dialog) {
        try {
            String encodedCity = URLEncoder.encode(inputCity, "UTF-8");
            String apiUrl = "https://geocoding-api.open-meteo.com/v1/search?name=" + encodedCity + "&count=10&language=en&format=json";

            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            if (conn.getResponseCode() == 200) {
                JsonObject response = JsonParser.parseReader(new InputStreamReader(conn.getInputStream())).getAsJsonObject();
                JsonArray results = response.getAsJsonArray("results");

                if (results == null || results.size() == 0) {
                    JOptionPane.showMessageDialog(dialog, "No cities found.");
                    return;
                }

                cityObjects = new JsonObject[results.size()];
                String[] cityNames = new String[results.size()];

                for (int i = 0; i < results.size(); i++) {
                    JsonObject city = results.get(i).getAsJsonObject();
                    cityObjects[i] = city;
                    cityNames[i] = city.get("name").getAsString() + ", " + city.get("country").getAsString();
                }

                showCitySelectionDialog(cityNames, dialog);

            } else {
                JOptionPane.showMessageDialog(dialog, "API failed: " + conn.getResponseCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(dialog, "Error: " + e.getMessage());
        }
    }

    private static void showCitySelectionDialog(String[] cityNames, JDialog parentDialog) {
        JList<String> cityList = new JList<>(cityNames);
        JScrollPane scrollPane = new JScrollPane(cityList);

        int option = JOptionPane.showConfirmDialog(parentDialog, scrollPane, "Select a city",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (option == JOptionPane.OK_OPTION && cityList.getSelectedIndex() >= 0) {
            JsonObject selected = cityObjects[cityList.getSelectedIndex()];
            cityName = selected.get("name").getAsString();
            country = selected.get("country").getAsString();
            latitude = selected.get("latitude").getAsFloat();
            longitude = selected.get("longitude").getAsFloat();
            parentDialog.dispose(); // close modal dialog
        }
    }
}

/* 

    // Getters for the city details
    public static String getCityName() {
        return cityName;
    }
    public static String getCountry() {
        return country;
    }
    public static float getLatitude() {
        return latitude;
    }
    public static float getLongitude() {
        return longitude;
    }
*/
