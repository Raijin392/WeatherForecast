package com.example.weatherforecast;

import static com.example.weatherforecast.Utils.NetworkUtils.generateURL;
import static com.example.weatherforecast.Utils.NetworkUtils.getResponseFromURL;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private EditText searchField;
    private ImageButton searchImageButton;
    private TextView result;

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
            result.setText(response);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);

        searchField = findViewById(R.id.et_search_city);
        searchImageButton = findViewById(R.id.ib_search);
        result = findViewById(R.id.tv_result);

        View.OnClickListener buttonSearch = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                URL generatedURL = generateURL(searchField.getText().toString());
                new WeatherQueryTask().execute(generatedURL);

            }
        };

        searchImageButton.setOnClickListener(buttonSearch);

    }
}