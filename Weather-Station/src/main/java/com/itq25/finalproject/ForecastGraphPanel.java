package com.itq25.finalproject;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.List;

import javax.swing.JPanel;

public class ForecastGraphPanel extends JPanel {
    // This class is a placeholder for the graph panel that will display today's forecast.
    // It extends JPanel to create a custom panel for the weather forecast graph.
    // The actual implementation of the graph will be added later.
    // Currently, it serves as a basic structure for future development.

    private final List<Double> TempForecastData;
    private final List<Double> WindForecastData;
    private final List<Double> precipForecastData;
    private final int numberOfDays;
    private final String unitType;
    private final int paddingLeft = 80;  // Increased left padding for labels
    private final int paddingRight = 50;
    private final int paddingBottom = 50;
    private final int paddingTop = 50;
    private final int pointSize = 6;
    public ForecastGraphPanel(List<Double> TempForecastData, List<Double> WindForecastData, List<Double> PrecipitationForecastData, int numberOfDays, String unit) {
        // Constructor for initializing the graph panel
        // Additional setup and customization can be done here in the future
        this.TempForecastData = TempForecastData;
        this.WindForecastData = WindForecastData;
        this.precipForecastData = PrecipitationForecastData;
        this.numberOfDays = numberOfDays;
        this.unitType = unit;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();
        int padding = 50;
        int graphWidth = width - (paddingLeft + paddingRight);
        int graphHeight = height - (paddingTop + paddingBottom);

        // Find min and max values for Temperature scaling
        double minTemperature = TempForecastData.stream().min(Double::compare).orElse(0.0) - 0.01;
        double maxTemperature = TempForecastData.stream().max(Double::compare).orElse(1.0) + 0.01;
        double tempRange = maxTemperature - minTemperature;

        // Find (min and) max values for Wind Speed scaling
        double minWindSpeed = 0; //WindForecastData.stream().min(Double::compare).orElse(0.0) - 0;
        double maxWindSpeed = WindForecastData.stream().max(Double::compare).orElse(1.0) + 0.01;
        double windRange = maxWindSpeed - minWindSpeed;

        // Find (min and) max values for precipitation scaling
        double minPrecipitation = 0; //PrecipForecastData.stream().min(Double::compare).orElse(0.0) - 0;
        double maxPrecipitation = precipForecastData.stream().max(Double::compare).orElse(1.0) + 0.01;
        double precipRange = maxPrecipitation - minPrecipitation;

        // Draw Y grid lines
        g2.setColor(Color.GRAY);
        for (int i = 0; i <= 5; i++) {
            int y = paddingTop + i * graphHeight / 5;
            g2.drawLine(paddingLeft, y, width - paddingRight, y);
        }
        
        // Draw X grid lines
        if (numberOfDays == 1) {
            g2.setColor(Color.GRAY);
            for (int i = 0; i < TempForecastData.size(); i++) {
                int x = paddingLeft + i * graphWidth / (TempForecastData.size() - 1);
                g2.drawLine(x, paddingTop, x, height - paddingBottom);
            }
        } else if (numberOfDays == 3) {
            g2.setColor(Color.GRAY);
            for (int i = 0; i <= numberOfDays; i++) {
                int x = paddingLeft + i * graphWidth / (numberOfDays - 0 );
                g2.drawLine(x, paddingTop, x, height - paddingBottom);
            }
        }

        /* 
        for (int i = 0; i < TempForecastData.size(); i++) {
            int x = paddingLeft + i * graphWidth / (TempForecastData.size() - 1);
            g2.drawLine(x, paddingTop, x, height - paddingBottom);
        }
        */
        

        // Draw axes
        g2.setColor(Color.BLACK);
        g2.drawLine(paddingLeft, height - paddingBottom, width - paddingRight, height - paddingBottom); // X-axis
        g2.drawLine(paddingLeft, paddingTop, paddingLeft, height - paddingBottom); // Y-axis


        // Plot points and lines
        int dataSize = 0; //dummy variable to avoid compile error
        if (numberOfDays == 1) {
            dataSize = TempForecastData.size();
        } else if (numberOfDays == 3) {
            dataSize = TempForecastData.size() - 1;
        }
        
        int pointSpacing = graphWidth / (dataSize - 1);
        

        // === Plot Temperature Data (Blue Line) ===
        g2.setColor(Color.BLUE);
        for (int i = 0; i < dataSize - 1; i++) {
            int x1 = paddingLeft + i * pointSpacing;
            int y1 = (int) (height - paddingBottom - ((TempForecastData.get(i) - minTemperature) / tempRange) * graphHeight);
            int x2 = paddingLeft + (i + 1) * pointSpacing;
            int y2 = (int) (height - paddingBottom - ((TempForecastData.get(i + 1) - minTemperature) / tempRange) * graphHeight);

            g2.drawLine(x1, y1, x2, y2);
            g2.fillOval(x1 - pointSize / 2, y1 - pointSize / 2, pointSize, pointSize);
        }

        // === Plot Wind Speed Data (Red Line) ===
        g2.setColor(Color.RED);
        
        for (int i = 0; i < dataSize - 1; i++) {
            int x1 = paddingLeft + i * pointSpacing;
            int y1 = (int) (height - paddingBottom - ((WindForecastData.get(i) - minWindSpeed) / windRange) * graphHeight);
            int x2 = paddingLeft + (i + 1) * pointSpacing;
            int y2 = (int) (height - paddingBottom - ((WindForecastData.get(i + 1) - minWindSpeed) / windRange) * graphHeight);

            g2.drawLine(x1, y1, x2, y2);
            g2.fillOval(x1 - pointSize / 2, y1 - pointSize / 2, pointSize, pointSize);
        }

        // === Plot Precipitation Data (Green Line) ===
        g2.setColor(Color.MAGENTA);
        for (int i = 0; i < dataSize - 1; i++) {
            int x1 = paddingLeft + i * pointSpacing;
            int y1 = (int) (height - paddingBottom - ((precipForecastData.get(i) - minPrecipitation) / precipRange) * graphHeight);
            int x2 = paddingLeft + (i + 1) * pointSpacing;
            int y2 = (int) (height - paddingBottom - ((precipForecastData.get(i + 1) - minPrecipitation) / precipRange) * graphHeight);

            g2.drawLine(x1, y1, x2, y2);
            g2.fillOval(x1 - pointSize / 2, y1 - pointSize / 2, pointSize, pointSize);
        }

        // Draw last point
        int lastX = padding + (dataSize - 1) * pointSpacing;
        int lastY = (int) (height - padding - ((TempForecastData.get(dataSize - 1) - minTemperature) / tempRange) * graphHeight);
        //g2.fillOval(lastX - 3, lastY - 3, 6, 6);

        /* 
        // Rotate text to align with Y-axis
        g2.rotate(-Math.PI / 2);  // Rotate 90 degrees counterclockwise
        g2.setColor(Color.BLACK);
        g2.drawString("Temperature / Wind Speed / Precipitation", -(height - 50) , 20);  // Adjust position
        g2.rotate(Math.PI / 2);  // Reset rotation
        */
        

        if (numberOfDays == 1) {
            // Label X-axis (hour scale) for 1-day forecast
            for (int i = 0; i < dataSize; i += 1) {
                g2.setColor(Color.BLACK);
                int x = paddingLeft + i * pointSpacing;
                g2.drawString(String.valueOf(i), x, height - padding + 15);
            }
            // Draw axis labels
            g2.setColor(Color.BLACK);
            g2.drawString("Time (Hours)", width / 2 - 30, height - 10);
        } else if (numberOfDays == 3) {
            // Label X-axis (day scale) for 3-day forecast
            for (int i = 0; i < numberOfDays; i += 1) {
                int x = (int) (paddingLeft + i * (graphWidth / (numberOfDays - 0)) + 0.5 * (graphWidth / (numberOfDays - 0)));
                String dayLabel = (i == 0) ? "Today" : (i == 1) ? "Tomorrow" : "Day " + (i + 1);
                g2.setColor(Color.BLACK);
                g2.drawString(dayLabel, x, height - padding + 15);
                //g2.drawString(String.valueOf(i), x, height - padding + 15);
            }
            // Draw axis labels
            //g2.setColor(Color.BLACK);
            //g2.drawString("Days", width / 2 - 30, height - 10);
        }

        // Draw Temperature legend
        g2.setColor(Color.BLUE);
        g2.fillRect(width - 140, 10, 10, 10);
        if (unitType.equals("Imperial")) {
            g2.drawString("Temperature (°F)", width - 125, 20);
        } else {
            g2.drawString("Temperature (°C)", width - 125, 20);
        }


        // Label Y-axis (Temperature scale)
        for (int i = 0; i <= 5; i++) {
            double value = minTemperature + i * (tempRange / 5);
            int y = height - padding - i * graphHeight / 5;
            g2.drawString(String.format("%.0f", value), 55, y + 5);
        }

        // Draw Wind Speed legend
        g2.setColor(Color.RED);
        g2.fillRect(width - 140, 25, 10, 10);
        if (unitType.equals("Imperial")) {
            g2.drawString("Wind Speed (mph)", width - 125, 35);
        } else {
            g2.drawString("Wind Speed (km/h)", width - 125, 35);
        }
       

        // Label Y-axis (WindSpeed scale)
        for (int i = 0; i <= 5; i++) {
            double value = minWindSpeed + i * (windRange / 5);
            int y = height - padding - i * graphHeight / 5;
            g2.drawString(String.format("%.0f", value), 35, y + 5);
        }

        // Draw Precipitation legend
        g2.setColor(Color.MAGENTA);
        g2.fillRect(width - 140, 40, 10, 10);
        if (unitType.equals("Imperial")) {
            g2.drawString("Precipitation (in)", width - 125, 50);
        } else {
            g2.drawString("Precipitation (mm)", width - 125, 50);
        }
        // Label Y-axis (Precipitation scale)  
        for (int i = 0; i <= 5; i++) {
            double value = minPrecipitation + i * (precipRange / 5);
            int y = height - padding - i * graphHeight / 5;
            g2.drawString(String.format("%.0f", value), 15, y + 5);
        }
    }

}
