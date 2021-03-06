package com.yulong.jiangyu.geekweather.impl;

import com.yulong.jiangyu.geekweather.bean.DateInfo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by jiangyu on 2017/1/26.
 * 聚合数据万年历
 */

public interface DateImpl {
    @GET("calendar/day?")
    Call<DateInfo> getDate(@Query("date") String date, @Query("key") String key);
}
