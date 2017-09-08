package com.yulong.jiangyu.geekweather.interfaces;

import com.yulong.jiangyu.geekweather.entity.JuHeDateEntity;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * author RichardJay
 * email jiangfengfn12@163.com
 * created 2017/9/4 11:25
 * version v1.0
 * modified 2017/9/4
 * note 请求聚合日历数据的接口
 */

public interface JuHeDateImpl {
    @GET("calendar/day?")
    Call<JuHeDateEntity> getDate(@Query("date") String date, @Query("key") String key);
}
