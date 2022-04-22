package com.example.weatherforecast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText searchField;
    private ImageButton searchImageButton;
    private TextView result;

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
                result.setText("Кнопка была нажата раз!");
            }
        };

        searchImageButton.setOnClickListener(buttonSearch);

    }
}