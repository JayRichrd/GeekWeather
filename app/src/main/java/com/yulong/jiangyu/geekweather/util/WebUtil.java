package com.yulong.jiangyu.geekweather.util;


import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.yulong.jiangyu.geekweather.bean.DateInfo;
import com.yulong.jiangyu.geekweather.constant.Constant;
import com.yulong.jiangyu.geekweather.impl.DateImpl;
import com.yulong.jiangyu.geekweather.listener.DateCallbackListener;
import com.yulong.jiangyu.geekweather.listener.HttpCallbackListener;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.github.mikephil.charting.charts.Chart.LOG_TAG;

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
    private static DateImpl mDateImpl;
    private static Retrofit dateRetrofit;

    /**
     * 请求天气数据
     *
     * @param addressWeather 获取天气数据的url地址
     */
    public static void requestWeather(final String addressWeather, final HttpCallbackListener httpCallbackListener) {
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
                    if (httpCallbackListener != null)
                        // TODO: 2017/3/8  不能直接使用response.body().string()传递参数
                        //httpCallbackListener.onFinish(response.body().string());
                        httpCallbackListener.onFinish(result);
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
    public static void requesDate(String dateStr, final DateCallbackListener dateCallbackListener) {
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
        mDateImpl.getDate(dateStr, Constant.dateKey).enqueue(new Callback<DateInfo>() {
            @Override
            public void onResponse(Call<DateInfo> call, retrofit2.Response<DateInfo> response) {
                DateInfo dateInfo = response.body();
                if (Constant.errorCode == dateInfo.getError_code()) {//检查返回的结果
                    dateCallbackListener.onFinish(dateInfo);
//                    DateInfo.ResultBean.DataBean dataBean = dateInfo.getResult().getData();
                }
            }

            @Override
            public void onFailure(Call<DateInfo> call, Throwable t) {
                dateCallbackListener.onError(t);
                LogUtil.e(LOG_TAG, "获取日历数据失败！");
            }
        });
    }
}
