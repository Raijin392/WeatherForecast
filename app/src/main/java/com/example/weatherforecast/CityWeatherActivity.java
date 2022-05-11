package com.example.weatherforecast;

import static com.example.weatherforecast.Utils.NetworkUtils.generateExtendedURL;
import static com.example.weatherforecast.Utils.NetworkUtils.getExtendedResponseFromURL;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;

public class CityWeatherActivity extends AppCompatActivity {

    private TextView result;

    class MainWeatherQueryTask extends AsyncTask<URL, Void, String> {

        @Override
        protected String doInBackground(URL... urls) {
            String response = null;
            try {
                response = getExtendedResponseFromURL(urls[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }

        protected void onPostExecute(String response) {
            result.setText(response);
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_weather);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);

        result = findViewById(R.id.please);
        Bundle arguments = getIntent().getExtras();
        String lon = arguments.get("lon").toString();
        String lat = arguments.get("lat").toString();

        URL generatedURL = generateExtendedURL(lat, lon);
        new MainWeatherQueryTask().execute(generatedURL);

    }
}