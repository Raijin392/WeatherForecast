package com.example.weatherforecast.Utils;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {

    // https://api.openweathermap.org/data/2.5/weather?q=Tokyo&appid=d9d714e6d9c6451ad942f01d3225876c&units=metric

    private static final String OPEN_WEATHER_MAP_BASE_URL = "https://api.openweathermap.org";
    private static final String DATA_WEATHER_GET = "/data/2.5/weather";
    private static final String PARAM_QUERY_CITY = "q";
    private static final String PARAM_KEY = "appid";
    private static final String KEY = "d9d714e6d9c6451ad942f01d3225876c";
    private static final String PARAM_UNITS = "units";
    private static final String UNITS = "metric";

    public static URL generateURL(String city) {
        Uri builtUri = Uri.parse(OPEN_WEATHER_MAP_BASE_URL + DATA_WEATHER_GET)
                .buildUpon()
                .appendQueryParameter(PARAM_QUERY_CITY, city)
                .appendQueryParameter(PARAM_KEY, KEY)
                .appendQueryParameter(PARAM_UNITS, UNITS)
                .build();
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static String getResponseFromURL(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        try {
            InputStream inputStream = urlConnection.getInputStream();

            Scanner scanner = new Scanner(inputStream);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else  {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }




}
