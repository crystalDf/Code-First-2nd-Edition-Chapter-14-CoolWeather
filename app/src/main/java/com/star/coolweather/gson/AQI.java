package com.star.coolweather.gson;


import com.google.gson.annotations.SerializedName;

public class AQI {

    @SerializedName("city")
    private AQICity mCity;

    private class AQICity {

        @SerializedName("aqi")
        private String mAqi;

        @SerializedName("pm25")
        private String mPm25;
    }
}
