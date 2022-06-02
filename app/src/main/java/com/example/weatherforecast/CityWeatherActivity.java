package com.example.weatherforecast;

import static com.example.weatherforecast.Utils.NetworkUtils.generateExtendedURL;
import static com.example.weatherforecast.Utils.NetworkUtils.getExtendedResponseFromURL;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.weatherforecast.Objects.Current;
import com.example.weatherforecast.Objects.Daily;
import com.example.weatherforecast.Objects.Hourly;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class CityWeatherActivity extends AppCompatActivity {

    private TextView toolbarCity;
    private TextView description;
    private ImageView currentIcon;
    private TextView currentTemp;
    private TextView windSpeed;
    private TextView humidity;
    private TextView sunrise;
    private TextView sunset;

    private Current current;

    private final HashMap<Integer, String> week = new HashMap<>();
    {
        week.put(1, "Вс");
        week.put(2, "Пн");
        week.put(3, "Вт");
        week.put(4, "Ср");
        week.put(5, "Чт");
        week.put(6, "Пт");
        week.put(7, "Сб");
    }

    private final HashMap<String, String> month = new HashMap<>();
    {
        month.put("01", "янв.");
        month.put("02", "фев.");
        month.put("03", "мар.");
        month.put("04", "апр.");
        month.put("05", "мая");
        month.put("06", "июн.");
        month.put("07", "июл.");
        month.put("08", "авг.");
        month.put("09", "сен.");
        month.put("10", "окт.");
        month.put("11", "ноя.");
        month.put("12", "дек.");
    }


    private ArrayList<Daily> dailyWeather = new ArrayList<>();

    private ArrayList<ImageView> dailyImageArray = new ArrayList<>();
    private final int[] dailyImageIDs = {R.id.DailyIcon1, R.id.DailyIcon2, R.id.DailyIcon3, R.id.DailyIcon4, R.id.DailyIcon5, R.id.DailyIcon6, R.id.DailyIcon7};

    private ArrayList<TextView> dailyDataArray = new ArrayList<>();
    private final int[] dailyDataIDs = {R.id.DailyData1, R.id.DailyData2, R.id.DailyData3, R.id.DailyData4, R.id.DailyData5, R.id.DailyData6, R.id.DailyData7};

    private ArrayList<TextView> dailyTempArray = new ArrayList<>();
    private final int[] dailyTempIDs = {R.id.DailyTemp1, R.id.DailyTemp2, R.id.DailyTemp3, R.id.DailyTemp4, R.id.DailyTemp5, R.id.DailyTemp6, R.id.DailyTemp7};


    private ArrayList<Hourly> hourlyWeather = new ArrayList<>();

    private ArrayList<TextView> hourlyTimeArray = new ArrayList<>();
    private final int[] hourlyTimeIDS = {R.id.HourlyTime1, R.id.HourlyTime2, R.id.HourlyTime3, R.id.HourlyTime4, R.id.HourlyTime5, R.id.HourlyTime6,
            R.id.HourlyTime7, R.id.HourlyTime8, R.id.HourlyTime9, R.id.HourlyTime10, R.id.HourlyTime11, R.id.HourlyTime12, R.id.HourlyTime13, R.id.HourlyTime14,
            R.id.HourlyTime15, R.id.HourlyTime16, R.id.HourlyTime17, R.id.HourlyTime18, R.id.HourlyTime19, R.id.HourlyTime20, R.id.HourlyTime21, R.id.HourlyTime22,
            R.id.HourlyTime23, R.id.HourlyTime24};

    private ArrayList<ImageView> hourlyImageArray = new ArrayList<>();
    private final int[] hourlyIconIDs = {R.id.HourlyIcon1, R.id.HourlyIcon2, R.id.HourlyIcon3, R.id.HourlyIcon4, R.id.HourlyIcon5, R.id.HourlyIcon6,
            R.id.HourlyIcon7, R.id.HourlyIcon8, R.id.HourlyIcon9, R.id.HourlyIcon10, R.id.HourlyIcon11, R.id.HourlyIcon12, R.id.HourlyIcon13, R.id.HourlyIcon14,
            R.id.HourlyIcon15, R.id.HourlyIcon16, R.id.HourlyIcon17, R.id.HourlyIcon18, R.id.HourlyIcon19, R.id.HourlyIcon20, R.id.HourlyIcon21, R.id.HourlyIcon22,
            R.id.HourlyIcon23, R.id.HourlyIcon24};

    private ArrayList<TextView> hourlyTempArray = new ArrayList<>();
    private final int[] hourlyTempIDs = {R.id.HourlyTemp1, R.id.HourlyTemp2, R.id.HourlyTemp3, R.id.HourlyTemp4, R.id.HourlyTemp5, R.id.HourlyTemp6,
            R.id.HourlyTemp7, R.id.HourlyTemp8, R.id.HourlyTemp9, R.id.HourlyTemp10, R.id.HourlyTemp11, R.id.HourlyTemp12, R.id.HourlyTemp13, R.id.HourlyTemp14,
            R.id.HourlyTemp15, R.id.HourlyTemp16, R.id.HourlyTemp17, R.id.HourlyTemp18, R.id.HourlyTemp19, R.id.HourlyTemp20, R.id.HourlyTemp21, R.id.HourlyTemp22,
            R.id.HourlyTemp23, R.id.HourlyTemp24};

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

            try {

                JSONObject jsonResponse = new JSONObject(response);
                JSONObject jsonCurrent = jsonResponse.getJSONObject("current");
                long timezoneOffset = jsonResponse.getInt("timezone_offset");
                String sunriseUnix = jsonCurrent.getString("sunrise");
                String sunsetUnix = jsonCurrent.getString("sunset");
                String temp = jsonCurrent.getString("temp");
                String humidity = jsonCurrent.getString("humidity");
                String windSpeed = jsonCurrent.getString("wind_speed");
                JSONArray weatherArray = jsonCurrent.getJSONArray("weather");
                JSONObject JsonDescription = weatherArray.getJSONObject(0);
                String description = JsonDescription.getString("description");
                JSONObject jsonIcon = weatherArray.getJSONObject(0);
                String icon = jsonIcon.getString("icon");
                icon = icon.charAt(icon.length()-1) + icon;

                Timestamp timestamp1 = new Timestamp((Long.parseLong(sunriseUnix) + timezoneOffset - 10800) * 1000);
                Timestamp timestamp2 = new Timestamp((Long.parseLong(sunsetUnix) + timezoneOffset - 10800) * 1000);

                String[] sunriseArray1 = timestamp1.toString().split(" ");
                String[] sunriseArray2 = timestamp2.toString().split(" ");

                String sunrise = sunriseArray1[1].substring(0, 5);
                if (sunrise.charAt(0) == '0') {
                    sunrise = sunrise.substring(1, 5);
                }

                String sunset = sunriseArray2[1].substring(0, 5);
                if (sunset.charAt(0) == '0') {
                    sunset = sunset.substring(1, 5);
                }

                current = new Current(sunrise, sunset, temp, humidity, windSpeed, description, icon);

                JSONArray dailyArray = jsonResponse.getJSONArray("daily");
                for (int i = 1; i < 8; i++) {
                    JSONObject day = dailyArray.getJSONObject(i);
                    String dailyData = day.getString("dt");

                    JSONObject dailyTemp = day.getJSONObject("temp");
                    String minTemp = dailyTemp.getString("min");
                    String maxTemp = dailyTemp.getString("max");

                    JSONArray dailyIconArray = day.getJSONArray("weather");
                    JSONObject JsonDailyIcon = dailyIconArray.getJSONObject(0);
                    String dailyIcon = JsonDailyIcon.getString("icon");

                    Timestamp dayTimestamp = new Timestamp((Long.parseLong(dailyData) + timezoneOffset - 10800) * 1000);
                    String[] dayData = dayTimestamp.toString().split(" ");
                    dayData = dayData[0].split("-");

                    String finalData;

                    if (i == 1) {
                        finalData = "Завтра, ";
                        if (dayData[2].charAt(0) == '0') {
                            finalData += dayData[2].charAt(1) + " " + month.get(dayData[1]);
                        } else {
                            finalData += dayData[2] + " " + month.get(dayData[1]);
                        }
                    } else {
                        Calendar c = Calendar.getInstance();
                        c.setTime(dayTimestamp);
                        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
                        finalData = week.get(dayOfWeek) +", ";

                        if (dayData[2].charAt(0) == '0') {
                            finalData += dayData[2].charAt(1) + " " + month.get(dayData[1]);
                        } else {
                            finalData += dayData[2] + " " + month.get(dayData[1]);
                        }

                    }

                    dailyIcon = dailyIcon.charAt(dailyIcon.length() - 1) + dailyIcon;
                    minTemp = String.format("%.0f", Double.parseDouble(minTemp)) + "°";
                    maxTemp = String.format("%.0f", Double.parseDouble(maxTemp)) + "° / ";
                    dailyWeather.add(new Daily(finalData, dailyIcon, minTemp, maxTemp));
                }

                JSONArray hourlyArray = jsonResponse.getJSONArray("hourly");
                for (int i = 0; i < 24; i++) {
                    JSONObject hour = hourlyArray.getJSONObject(i);
                    String hourlyTime = hour.getString("dt");
                    String hourlyTemp = hour.getString("temp");

                    JSONArray hourlyIconArray = hour.getJSONArray("weather");
                    JSONObject JsonHourlyIcon = hourlyIconArray.getJSONObject(0);
                    String hourlyIcon = JsonHourlyIcon.getString("icon");

                    Timestamp hourTimestamp = new Timestamp((Long.parseLong(hourlyTime) + timezoneOffset - 10800) * 1000);
                    String[] hourTime = hourTimestamp.toString().split(" ");
                    hourlyTime = hourTime[1].substring(0, 5);
                    if (hourlyTime.charAt(0) == '0') {
                        hourlyTime = hourlyTime.substring(1, 5);
                    }

                    hourlyTemp = String.format("%.0f", Double.parseDouble(hourlyTemp)) + "°";

                    hourlyIcon = hourlyIcon.charAt(hourlyIcon.length() - 1) + hourlyIcon;
                    hourlyWeather.add(new Hourly(hourlyTime, hourlyIcon, hourlyTemp));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            description.setText(current.getDescription());
            try {
                currentIcon.setImageResource(R.drawable.class.getField(current.getIcon()).getInt(getResources()));
            } catch (IllegalAccessException | NoSuchFieldException e) {
                e.printStackTrace();
            }
            currentTemp.setText(String.format("%.0f", Double.parseDouble(current.getTemp())) + "°");
            windSpeed.setText(String.format("%.0f", Double.parseDouble(current.getWind())) + " м/с");
            humidity.setText(current.getHumidity() + "%");
            sunrise.setText("Восход: " + current.getSunrise());
            sunset.setText("Закат:  " + current.getSunset());

            int i = 0;
            for (ImageView imageView : dailyImageArray) {
                try {
                    imageView.setImageResource(R.drawable.class.getField(dailyWeather.get(i).getIcon()).getInt(getResources()));
                } catch (IllegalAccessException | NoSuchFieldException e) {
                    e.printStackTrace();
                }
                i++;
            }

            int j = 0;
            for (TextView textView : dailyDataArray) {
                textView.setText(dailyWeather.get(j).getData());
                j++;
            }

            int k = 0;
            for (TextView textView : dailyTempArray) {
                textView.setText(dailyWeather.get(k).getMax() + dailyWeather.get(k).getMin());
                k++;
            }

            int l = 0;
            for (ImageView imageView : hourlyImageArray) {
                try {
                    imageView.setImageResource(R.drawable.class.getField(hourlyWeather.get(l).getIcon()).getInt(getResources()));
                } catch (IllegalAccessException | NoSuchFieldException e) {
                    e.printStackTrace();
                }
                l++;
            }

            int m = 0;
            for (TextView textView : hourlyTimeArray) {
                textView.setText(hourlyWeather.get(m).getTime());
                m++;
            }

            int n = 0;
            for (TextView textView : hourlyTempArray) {
                textView.setText(hourlyWeather.get(n).getTemp());
                n++;
            }

        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_weather);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);

        toolbarCity = findViewById(R.id.toolbarCity);

        description = findViewById(R.id.CurrentDescription);
        currentIcon = findViewById(R.id.CurrentIcon);
        currentTemp = findViewById(R.id.CurrentTemp);
        windSpeed = findViewById(R.id.CurrentWindSpeed);
        humidity = findViewById(R.id.CurrentHumidity);
        sunrise = findViewById(R.id.CurrentSunrise);
        sunset = findViewById(R.id.CurrentSunset);

        for (int i = 0; i < dailyImageIDs.length; i++) {
            dailyImageArray.add(findViewById(dailyImageIDs[i]));
        }

        for (int i = 0; i < dailyDataIDs.length; i++) {
            dailyDataArray.add(findViewById(dailyDataIDs[i]));
        }

        for (int i = 0; i < dailyTempIDs.length; i++) {
            dailyTempArray.add(findViewById(dailyTempIDs[i]));
        }

        for (int i = 0; i < hourlyIconIDs.length; i++) {
            hourlyImageArray.add(findViewById(hourlyIconIDs[i]));
        }

        for (int i = 0; i < hourlyTimeIDS.length; i++) {
            hourlyTimeArray.add(findViewById(hourlyTimeIDS[i]));
        }

        for (int i = 0; i < hourlyTempIDs.length; i++) {
            hourlyTempArray.add(findViewById(hourlyTempIDs[i]));
        }

        Bundle arguments = getIntent().getExtras();
        String lon = arguments.get("lon").toString();
        String lat = arguments.get("lat").toString();
        String city = arguments.get("city").toString();

        toolbarCity.setText(city);

        URL generatedURL = generateExtendedURL(lat, lon);
        new MainWeatherQueryTask().execute(generatedURL);


    }
}