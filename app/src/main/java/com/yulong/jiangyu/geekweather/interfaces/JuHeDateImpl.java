package com.yulong.jiangyu.geekweather.interfaces;

import com.yulong.jiangyu.geekweather.entity.JuHeDateEntity;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by jiangyu on 2017/1/26.
 * 聚合数据万年历
 */

public interface JuHeDateImpl {
    @GET("calendar/day?")
    Call<JuHeDateEntity> getDate(@Query("date") String date, @Query("key") String key);
}
