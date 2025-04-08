package com.itq25.finalproject;

public class WeatherParameters {
    private float temperature;
    private float rain;
    private String skyCondition;
    private float roomtemperature;
    private float roomHumidity;

    public WeatherParameters(float temperature, float rain, String skyCondition, float humidity, float roomtemperature, float roomHumidity) {
        this.temperature = temperature;
        this.rain = rain;
        this.skyCondition = skyCondition;
        this.roomtemperature = roomtemperature;
        this.roomHumidity = roomHumidity;
    }
    public float getTemperature() {
        return temperature;
    }
    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }
    public float getRain() {
        return rain;
    }
    public void setRain(float rain) {
        this.rain = rain;
    }
    public String getSkyCondition() {
        return skyCondition;
    }
    public void setSkyCondition(String skyCondition) {
        this.skyCondition = skyCondition;
    }
    public float getRoomtemperature() {
        return roomtemperature;
    }
    public void setRoomtemperature(float roomtemperature) {
        this.roomtemperature = roomtemperature;
    }
    public float getRoomHumidity() {
        return roomHumidity;
    }
    public void setRoomHumidity(float roomHumidity) {
        this.roomHumidity = roomHumidity;
    }
    @Override
    public String toString() {
        return "WeatherParameters{" +
                "temperature=" + temperature +
                ", rain=" + rain +
                ", skyCondition='" + skyCondition + '\'' +
                ", roomtemperature=" + roomtemperature +
                ", roomHumidity=" + roomHumidity +
                '}';
    }

}
