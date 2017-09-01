package com.yulong.jiangyu.geekweather.net;


import android.content.Context;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.yulong.jiangyu.geekweather.R;
import com.yulong.jiangyu.geekweather.constant.Constant;
import com.yulong.jiangyu.geekweather.entity.DateEntity;
import com.yulong.jiangyu.geekweather.impl.DateImpl;
import com.yulong.jiangyu.geekweather.listener.HttpCallbackListener;
import com.yulong.jiangyu.geekweather.utils.LogUtil;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * author RichardJay
 * email jiangfengfn12@163.com
 * created 2017/3/7 16:07
 * version v1.0
 * modified 2017/3/7
 * note 网络请求工具类
 **/

public class RequestData {
    //日志TAG
    private static final String TAG = "RequestData";

    private static OkHttpClient mOkHttpClient;
    private static DateImpl mDateImpl;
    private static Retrofit dateRetrofit;

    /**
     * 请求天气数据
     *
     * @param context
     * @param weatherRequest       天气请求码
     * @param isWeatherCode        是否使用天气代码
     * @param httpCallbackListener http返回成功的回调接口
     */
    public static void requestWeatherData(Context context, final String weatherRequest, final boolean isWeatherCode,
                                          final HttpCallbackListener httpCallbackListener) {
        final String address_weather;
        if (isWeatherCode) {// 用城市编码请求天气数据
            address_weather = context.getString(R.string.weather_city_key_base_url, weatherRequest);
        } else { // 用城市名请求天气数据
            address_weather = context.getString(R.string.weather_city_base_url, weatherRequest);
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (mOkHttpClient == null)
                    mOkHttpClient = new OkHttpClient();
                //设置连接属性
                mOkHttpClient.setReadTimeout(5 * 1000, TimeUnit.MILLISECONDS);
                mOkHttpClient.setConnectTimeout(5 * 1000, TimeUnit.MILLISECONDS);
                mOkHttpClient.setWriteTimeout(5 * 1000, TimeUnit.MILLISECONDS);
                //发起请求
                Request request = new Request.Builder().url(address_weather).build();
                try {
                    Response response = mOkHttpClient.newCall(request).execute();
                    String result = response.body().string();
                    if (httpCallbackListener != null)
                        // TODO: 2017/3/8  不能直接使用response.body().string()传递参数
                        //httpCallbackListener.onFinished(response.body().string());
                        httpCallbackListener.onFinished(result);
                } catch (IOException e) {
                    e.printStackTrace();
                    if (httpCallbackListener != null)
                        httpCallbackListener.onError(e);
                }
            }
        }).start();
    }

    /**
     * 请求日历数据
     *
     * @param dateStr 日期 2017-1-1
     */
    public static void requestDateData(String dateStr, final HttpCallbackListener httpCallbackListener) {
        //聚合数据万年历网络请求
        dateRetrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl
                (Constant.dateBaseUrl).build();
        mDateImpl = dateRetrofit.create(DateImpl.class);
        //转换日期
//        DateFormat dateFormatSource = new SimpleDateFormat(getString(R.string.data_source));
//        DateFormat dateFormatTarget = new SimpleDateFormat(getString(R.string.data_target));
//        Date date = null;
//        try {
//            date = dateFormatSource.parse(dateStr);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
        mDateImpl.getDate(dateStr, Constant.dateKey).enqueue(new Callback<DateEntity>() {
            @Override
            public void onResponse(Call<DateEntity> call, retrofit2.Response<DateEntity> response) {
                DateEntity dateInfo = response.body();
                if (Constant.errorCode == dateInfo.getError_code()) {//检查返回的结果
                    httpCallbackListener.onFinished(dateInfo);
                }
            }

            @Override
            public void onFailure(Call<DateEntity> call, Throwable t) {
                httpCallbackListener.onError(t);
                LogUtil.e(TAG, "获取日历数据失败！");
            }
        });
    }
}
