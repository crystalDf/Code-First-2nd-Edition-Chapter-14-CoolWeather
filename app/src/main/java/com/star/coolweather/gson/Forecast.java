package com.star.coolweather.gson;


import com.google.gson.annotations.SerializedName;

public class Forecast {

    @SerializedName("date")
    private String mDate;

    @SerializedName("tmp")
    private Temperature mTemprature;

    @SerializedName("cond")
    private More mMore;

    private class Temperature {

        @SerializedName("max")
        private String mMax;

        @SerializedName("min")
        private String mMin;
    }

    private class More {

        @SerializedName("txt_d")
        private String mInfo;
    }
}
