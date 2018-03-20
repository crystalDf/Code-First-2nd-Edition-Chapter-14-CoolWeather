package com.star.coolweather;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.star.coolweather.gson.Weather;
import com.star.coolweather.util.Utility;

public class WeatherActivity extends AppCompatActivity {

    private ScrollView mWeatherLayout;
    private TextView mTitleCity;
    private TextView mTitleUpdateTime;
    private TextView mDegree;
    private TextView mWeatherInfo;

    private LinearLayout mForecastLayout;

    private TextView mAQI;
    private TextView mPM25;
    private TextView mComfort;
    private TextView mCarWash;
    private TextView mSport;

    private SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        mWeatherLayout = findViewById(R.id.weather_layout);
        mTitleCity = findViewById(R.id.title_city);
        mTitleUpdateTime = findViewById(R.id.title_update_time);
        mDegree = findViewById(R.id.degree_text);
        mWeatherInfo = findViewById(R.id.weather_info_text);
        mForecastLayout = findViewById(R.id.forecast_layout);
        mAQI = findViewById(R.id.aqi_text_value);
        mPM25 = findViewById(R.id.pm25_text_value);
        mComfort = findViewById(R.id.comfort_text);
        mCarWash = findViewById(R.id.car_wash_text);
        mSport = findViewById(R.id.sport_text);

        mSharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(this);

        String weatherString = mSharedPreferences.getString("weather", null);

        if (!TextUtils.isEmpty(weatherString)) {

            Weather weather = Utility.handleWeatherResponse(weatherString);
            
            showWeatherInfo(weather);
        } else {

            String weatherId = getIntent().getStringExtra("weather_id");

            mWeatherLayout.setVisibility(View.INVISIBLE);

            requestWeather(weatherId);
        }
    }

    private void showWeatherInfo(Weather weather) {
    }

    private void requestWeather(final String weatherId) {
    }
}
