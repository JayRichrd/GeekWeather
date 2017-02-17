package com.yulong.jiangyu.geekweather.impl;

import com.yulong.jiangyu.geekweather.bean.HeWeatherInfo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by jiangyu on 2017/1/25.
 * 和风天气
 */

public interface HeWeatherImpl {
    @GET("weather?")
    Call<HeWeatherInfo> getWeather(@Query("city") String city, @Query("key") String key);
}
