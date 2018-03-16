package com.star.coolweather.gson;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Weather {

    @SerializedName("status")
    private String mStatus;

    @SerializedName("basic")
    private Basic mBasic;

    @SerializedName("aqi")
    private AQI mAQI;

    @SerializedName("now")
    private Now mNow;

    @SerializedName("suggestion")
    private Suggestion mSuggestion;

    @SerializedName("daily_forecast")
    private List<Forecast> mForecastList;
}
