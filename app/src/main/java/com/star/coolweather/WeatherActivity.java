package com.star.coolweather;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.star.coolweather.gson.Forecast;
import com.star.coolweather.gson.Weather;
import com.star.coolweather.util.HttpUtil;
import com.star.coolweather.util.Utility;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class WeatherActivity extends AppCompatActivity {

    public static final String WEATHER_ID = "weather_id";
    public static final String WEATHER = "weather";

    private static final String BING_PIC = "bing_pic";
    private static final String BING_PIC_URL = "http://guolin.tech/api/bing_pic";

    private static final String WEATHER_URL = "http://guolin.tech/api/weather";
    private static final String QUERY_CITY_ID = "?cityid=";
    private static final String QUERY_KEY = "&key=";
    private static final String KEY = "d2a8fc30ff4a437b9b8df86c69747f88";
    private static final String OK = "ok";

    private static final String TEMPERATURE_UNIT = "°C";
    private static final String COMFORT_LABEL = "舒适度：";
    private static final String CAR_WASH_LABEL = "洗车指数：";
    private static final String SPORT_LABEL = "运动建议：";

    private ImageView mBingPicImg;

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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                            View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        setContentView(R.layout.activity_weather);

        mBingPicImg = findViewById(R.id.bing_pic_img);

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

        String weatherString = mSharedPreferences.getString(WEATHER, null);

        if (!TextUtils.isEmpty(weatherString)) {

            Weather weather = Utility.handleWeatherResponse(weatherString);
            
            showWeatherInfo(weather);
        } else {

            String weatherId = getIntent().getStringExtra(WEATHER_ID);

            mWeatherLayout.setVisibility(View.INVISIBLE);

            requestWeather(weatherId);
        }

        String bingPic = mSharedPreferences.getString(BING_PIC, null);

        if (bingPic != null) {

            Glide.with(this).load(bingPic).into(mBingPicImg);
        } else {

            loadBingPic();
        }
    }

    private void requestWeather(final String weatherId) {

        String weatherUrl = WEATHER_URL + QUERY_CITY_ID + weatherId +
                QUERY_KEY + KEY;

        HttpUtil.sendOkHttpRequest(weatherUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                e.printStackTrace();

                runOnUiThread(() -> {

                    Toast.makeText(WeatherActivity.this,
                            "获取天气信息失败", Toast.LENGTH_LONG).show();
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                final String responseText = response.body().string();
                final Weather weather = Utility.handleWeatherResponse(responseText);

                runOnUiThread(() -> {

                    if (weather != null && OK.equals(weather.getStatus())) {

                        SharedPreferences.Editor editor =
                                PreferenceManager.getDefaultSharedPreferences(
                                        WeatherActivity.this).edit();
                        editor.putString(WEATHER, responseText);
                        editor.apply();
                        showWeatherInfo(weather);
                    } else {

                        Toast.makeText(WeatherActivity.this,
                                "获取天气信息失败", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        loadBingPic();
    }

    private void showWeatherInfo(Weather weather) {

        String cityName = weather.getBasic().getCityName();
        String updateTime = weather.getBasic().getUpdate().getUpdateTime()
                .split(" ")[1];
        String degree = weather.getNow().getTemperature() + TEMPERATURE_UNIT;
        String weatherInfo = weather.getNow().getMore().getInfo();

        mTitleCity.setText(cityName);
        mTitleUpdateTime.setText(updateTime);
        mDegree.setText(degree);
        mWeatherInfo.setText(weatherInfo);

        mForecastLayout.removeAllViews();

        for (Forecast forecast : weather.getForecastList()) {

            View view = LayoutInflater.from(this).inflate(
                    R.layout.forecast_item, mForecastLayout, false);

            TextView dateText = view.findViewById(R.id.date_text);
            TextView infoText = view.findViewById(R.id.info_text);
            TextView maxText = view.findViewById(R.id.max_text);
            TextView minText = view.findViewById(R.id.min_text);
            dateText.setText(forecast.getDate());
            infoText.setText(forecast.getMore().getInfo());
            maxText.setText(forecast.getTemprature().getMax());
            minText.setText(forecast.getTemprature().getMin());

            mForecastLayout.addView(view);
        }

        if (weather.getAQI() != null) {

            mAQI.setText(weather.getAQI().getCity().getAQI());
            mPM25.setText(weather.getAQI().getCity().getPM25());
        }

        String comfort = COMFORT_LABEL +
                weather.getSuggestion().getComfort().getInfo();
        String carWash = CAR_WASH_LABEL +
                weather.getSuggestion().getCarWash().getInfo();
        String sport = SPORT_LABEL +
                weather.getSuggestion().getSport().getInfo();

        mComfort.setText(comfort);
        mCarWash.setText(carWash);
        mSport.setText(sport);

        mWeatherLayout.setVisibility(View.VISIBLE);
    }

    private void loadBingPic() {

        String requestBingPic = BING_PIC_URL;

        HttpUtil.sendOkHttpRequest(requestBingPic, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                final String bingPic = response.body().string();

                runOnUiThread(() -> {

                    SharedPreferences.Editor editor =
                            PreferenceManager.getDefaultSharedPreferences(
                                    WeatherActivity.this).edit();
                    editor.putString(BING_PIC, bingPic);
                    editor.apply();

                    Glide.with(WeatherActivity.this).load(bingPic).into(mBingPicImg);
                });
            }
        });
    }
}
