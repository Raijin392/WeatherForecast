package com.example.weatherforecast.Objects;

public class Hourly {
    private String time;
    private String icon;
    private String temp;

    public Hourly(String time, String icon, String temp) {
        this.time = time;
        this.icon = icon;
        this.temp = temp;
    }

    public String getTime() {
        return time;
    }

    public String getIcon() {
        return icon;
    }

    public String getTemp() {
        return temp;
    }
}
