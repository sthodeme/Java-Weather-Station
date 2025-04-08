package com.itq25.finalproject;

import java.io.IOException;

/**
 * This is the start location of the Weather Station application.<br>
 * It creates an instance of the WeatherStationUI class, which is responsible for displaying the weather information.<br>
 *
 * @author itq25/Sree Ram
 * @version 1.0
 */
public class StartWS
{
    public static void main( String[] args ) throws IOException, InterruptedException 
    {
        WeatherStationUI weatherStationUI = new WeatherStationUI();
    }
}
