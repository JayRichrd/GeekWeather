package com.yulong.jiangyu.geekweather.impl;

import com.yulong.jiangyu.geekweather.bean.JuHeWeatherInfo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by jiangyu on 2017/2/3.
 * 聚合数据天气预报
 */

public interface JuHeWeatherImpl {
    @GET("onebox/weather/query?")
    Call<JuHeWeatherInfo> getWeather(@Query("cityname") String cityname, @Query("key") String key);
}
