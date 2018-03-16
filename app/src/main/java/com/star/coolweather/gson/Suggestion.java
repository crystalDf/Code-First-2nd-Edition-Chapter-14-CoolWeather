package com.star.coolweather.gson;


import com.google.gson.annotations.SerializedName;

public class Suggestion {

    @SerializedName("comf")
    private Comfort mComfort;

    @SerializedName("cw")
    private CarWash mCarWash;

    @SerializedName("sport")
    private Sport mSport;

    private class Comfort {

        @SerializedName("txt")
        private String mInfo;
    }

    private class CarWash {

        @SerializedName("txt")
        private String mInfo;
    }

    private class Sport {

        @SerializedName("txt")
        private String mInfo;
    }
}
