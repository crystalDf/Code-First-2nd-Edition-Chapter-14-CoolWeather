package com.star.coolweather.gson;


import com.google.gson.annotations.SerializedName;

public class Basic {

    @SerializedName("city")
    private String mCity;

    @SerializedName("id")
    private String mId;

    @SerializedName("update")
    private Update mUpdate;

    private class Update {

        @SerializedName("loc")
        private String mLoc;
    }
}
