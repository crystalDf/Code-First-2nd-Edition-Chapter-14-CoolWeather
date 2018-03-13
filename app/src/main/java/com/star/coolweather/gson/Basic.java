package com.star.coolweather.gson;


import com.google.gson.annotations.SerializedName;

public class Basic {

    @SerializedName("city")
    private String mCityName;

    @SerializedName("id")
    private String mWeatherId;

    @SerializedName("update")
    private Update mUpdate;

    private class Update {

        @SerializedName("loc")
        private String mUpdateTime;
    }
}
