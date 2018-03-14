package com.star.coolweather.gson;


import com.google.gson.annotations.SerializedName;

public class Now {

    @SerializedName("tmp")
    private String mTemprature;

    @SerializedName("cond")
    private More mMore;

    private class More {

        @SerializedName("info")
        private String mInfo;
    }
}
