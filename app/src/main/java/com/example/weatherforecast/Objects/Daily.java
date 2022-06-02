package com.example.weatherforecast.Objects;

public class Daily {
    private String data;
    private String icon;
    private String min;
    private String max;

    public Daily(String data, String icon, String min, String max) {
        this.data = data;
        this.icon = icon;
        this.min = min;
        this.max = max;
    }

    public String getData() {
        return data;
    }

    public String getIcon() {
        return icon;
    }

    public String getMin() {
        return min;
    }

    public String getMax() {
        return max;
    }

    @Override
    public String toString() {
        return "Daily{" +
                "data='" + data + '\'' +
                ", icon='" + icon + '\'' +
                ", min='" + min + '\'' +
                ", max='" + max + '\'' +
                '}';
    }
}
