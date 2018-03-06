package com.star.coolweather.util;


import android.text.TextUtils;

import com.star.coolweather.db.City;
import com.star.coolweather.db.County;
import com.star.coolweather.db.Province;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class Utility {

    public static boolean handleProvincesResponse(String response) {

        if (!TextUtils.isEmpty(response)) {

            try {

                JSONArray allProvinces = new JSONArray(response);

                for (int i = 0; i < allProvinces.length(); i++) {

                    JSONObject provinceObject = allProvinces.getJSONObject(i);

                    Province province = new Province();
                    province.setProvinceId(provinceObject.getInt("id"));
                    province.setProvinceName(provinceObject.getString("name"));
                    province.save();
                }

                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    public static boolean handleCitiesResponse(String response, int provinceId) {

        if (!TextUtils.isEmpty(response)) {

            try {

                JSONArray allCities = new JSONArray(response);

                for (int i = 0; i < allCities.length(); i++) {

                    JSONObject cityObject = allCities.getJSONObject(i);

                    City city = new City();
                    city.setCityId(cityObject.getInt("id"));
                    city.setCityName(cityObject.getString("name"));
                    city.setProvinceId(provinceId);
                    city.save();
                }

                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    public static boolean handleCountiesResponse(String response, int cityId) {

        if (!TextUtils.isEmpty(response)) {

            try {

                JSONArray allCounties = new JSONArray(response);

                for (int i = 0; i < allCounties.length(); i++) {

                    JSONObject countyObject = allCounties.getJSONObject(i);

                    County county = new County();
                    county.setCountyName(countyObject.getString("name"));
                    county.setCityId(cityId);
                    county.setWeatherId(countyObject.getString("weather_id"));
                    county.save();
                }

                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return false;
    }
}
