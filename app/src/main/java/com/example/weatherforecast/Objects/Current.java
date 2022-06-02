package com.example.weatherforecast.Objects;

public class Current {
    private String sunrise;
    private String sunset;
    private String temp;
    private String humidity;
    private String wind;

    private String description;
    private String icon;

    public Current(String sunrise, String sunset, String temp, String humidity, String wind, String description, String icon) {
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.temp = temp;
        this.humidity = humidity;
        this.wind = wind;
        this.description = description;
        this.icon = icon;
    }

    public String getSunrise() {
        return sunrise;
    }

    public String getSunset() {
        return sunset;
    }

    public String getTemp() {
        return temp;
    }

    public String getHumidity() {
        return humidity;
    }

    public String getWind() {
        return wind;
    }

    public String getDescription() {
        return description;
    }

    public String getIcon() {
        return icon;
    }

    @Override
    public String toString() {
        return "Current{" +
                "sunrise='" + sunrise + '\'' +
                ", sunset='" + sunset + '\'' +
                ", temp='" + temp + '\'' +
                ", humidity='" + humidity + '\'' +
                ", wind='" + wind + '\'' +
                ", description='" + description + '\'' +
                ", icon='" + icon + '\'' +
                '}';
    }
}
