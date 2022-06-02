package com.example.weatherforecast;

import static com.example.weatherforecast.Utils.NetworkUtils.generateURL;
import static com.example.weatherforecast.Utils.NetworkUtils.getResponseFromURL;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private EditText searchField;
    private ImageButton searchImageButton;
    private TextView result;
    private LinearLayout searchBar;


    private boolean proverka;
    private String lonCity = null;
    private String latCity = null;
    private String city = null;
    private static final String EXCEPTION = "City not found. Try again!";

    class WeatherQueryTask extends AsyncTask<URL, Void, String> {

        @Override
        protected String doInBackground(URL... urls) {
            String response = null;
            try {
                response = getResponseFromURL(urls[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }

        protected void onPostExecute(String response) {

            try {

                if (response == null) {
                    proverka = false;
                } else {

                    JSONObject jsonResponse = new JSONObject(response);
                    city = jsonResponse.getString("name");

                    JSONObject jsonObjectCOORD = jsonResponse.getJSONObject("coord");

                    lonCity = jsonObjectCOORD.getString("lon");
                    latCity = jsonObjectCOORD.getString("lat");

                    proverka = true;
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (proverka == false) {
                result.setText(EXCEPTION);
            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);

        searchField = findViewById(R.id.et_search_city);
        searchBar = findViewById(R.id.search_bar);
        searchBar.setBackgroundResource(R.drawable.rounded_corners);
        searchImageButton = findViewById(R.id.ib_search);

        result = findViewById(R.id.tv_result1);

        View.OnClickListener buttonSearch = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                URL generatedURL = generateURL(searchField.getText().toString());
                new WeatherQueryTask().execute(generatedURL);

                if (proverka) {
                    Intent intent = new Intent(MainActivity.this, CityWeatherActivity.class);
                    intent.putExtra("lon", lonCity);
                    intent.putExtra("lat", latCity);
                    intent.putExtra("city", city);
                    startActivity(intent);
                    proverka = false;
                }

            }
        };
        searchImageButton.setOnClickListener(buttonSearch);

    }
}