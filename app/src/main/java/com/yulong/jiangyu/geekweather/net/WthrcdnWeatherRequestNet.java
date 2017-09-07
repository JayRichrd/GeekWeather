package com.yulong.jiangyu.geekweather.net;

import android.content.Context;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.yulong.jiangyu.geekweather.R;
import com.yulong.jiangyu.geekweather.entity.WthrcdnWeatherEntity;
import com.yulong.jiangyu.geekweather.interfaces.IDataEntity;
import com.yulong.jiangyu.geekweather.interfaces.IDataRequest;
import com.yulong.jiangyu.geekweather.listener.IHttpCallbackListener;
import com.yulong.jiangyu.geekweather.utils.Utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;


/**
 * author RichardJay
 * email jiangfengfn12@163.com
 * created 2017/9/4 11:53
 * version v1.0
 * modified 2017/9/4
 * note 调用万年历API请求天气数据
 */

public class WthrcdnWeatherRequestNet implements IDataRequest {
    //日志TAG
    private static final String TAG = "WthrcdnWeatherRequestNet";

    /**
     * 无参数的构造函数
     */
    public WthrcdnWeatherRequestNet() {
    }

    @Override
    public void requestData(final Context context, String RequestCode, boolean isCityCode, final
    IHttpCallbackListener httpCallbackListener) {
        final String urlWthrcdnWeatherRequest;

        if (isCityCode)
            // 用城市编码请求天气数据
            urlWthrcdnWeatherRequest = context.getString(R.string.config_wthrcdn_weather_request_base_url_city_key,
                    RequestCode);
        else
            // 用城市名请求天气数据
            urlWthrcdnWeatherRequest = context.getString(R.string.config_wthrcdn_weather_request_base_url_city,
                    RequestCode);

        new Thread(new Runnable() {
            @Override
            public void run() {
                // 使用okhttp来请求网络数据
                OkHttpClient okHttpClient = new OkHttpClient();

                //设置连接属性
                okHttpClient.setReadTimeout(5 * 1000, TimeUnit.MILLISECONDS);
                okHttpClient.setConnectTimeout(5 * 1000, TimeUnit.MILLISECONDS);
                okHttpClient.setWriteTimeout(5 * 1000, TimeUnit.MILLISECONDS);

                //发起请求
                Request request = new Request.Builder().url(urlWthrcdnWeatherRequest).build();

                try {
                    Response response = okHttpClient.newCall(request).execute();
                    if (response.body() == null)
                        throw new IOException("data is null!");

                    String result = response.body().string();
                    // 天气实体
                    WthrcdnWeatherEntity wthrcdnWeatherEntity;
                    IDataEntity baseWeatherEntity;

                    // 处理获取的xml数据得到天气实体类
                    wthrcdnWeatherEntity = Utils.handleWthrcdnWeatherEntity(new ByteArrayInputStream(
                            result.getBytes()));
                    // 将各种天气数据转换成基础的天气数据
                    baseWeatherEntity = trans2Base(wthrcdnWeatherEntity);

                    if (httpCallbackListener != null && baseWeatherEntity != null)// 返回基础的天气数据
                        httpCallbackListener.onFinished(baseWeatherEntity);
                } catch (IOException e) {
                    e.printStackTrace();
                    if (httpCallbackListener != null)
                        httpCallbackListener.onError(e);
                }
            }
        }).start();
    }

    @Override
    public IDataEntity trans2Base(IDataEntity dataEntity) {
        return dataEntity;
    }
}
