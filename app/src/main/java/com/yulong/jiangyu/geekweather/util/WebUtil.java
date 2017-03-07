package com.yulong.jiangyu.geekweather.util;


import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * author RichardJay
 * email jiangfengfn12@163.com
 * created 2017/3/7 16:07
 * version v1.0
 * modified 2017/3/7
 * note 网络请求工具类
 **/

public class WebUtil {
    private static OkHttpClient mOkHttpClient;

    /**
     * 请求天气数据
     *
     * @param addressWeather 获取天气数据的url地址
     */
    public static void requestWeather(final String addressWeather) {
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
                Request request = new Request.Builder().url(addressWeather).build();
                try {
                    Response response = mOkHttpClient.newCall(request).execute();
                    String result = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
