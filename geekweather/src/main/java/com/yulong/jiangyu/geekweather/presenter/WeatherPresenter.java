package com.yulong.jiangyu.geekweather.presenter;

import android.content.Context;
import android.util.Log;

import com.yulong.jiangyu.geekweather.entity.WthrcdnWeatherEntity;
import com.yulong.jiangyu.geekweather.interfaces.IDataRequest;
import com.yulong.jiangyu.geekweather.interfaces.IView;
import com.yulong.jiangyu.geekweather.interfaces.IWeatherPresenter;
import com.yulong.jiangyu.geekweather.listener.IHttpCallbackListener;
import com.yulong.jiangyu.geekweather.utils.Utils;

/**
 * author RichardJay
 * email jiangfengfn12@163.com
 * created 2017/9/4 15:50
 * version v1.0
 * modified 2017/9/4
 * note 请求天气的Presenter
 */

public class WeatherPresenter implements IWeatherPresenter, IHttpCallbackListener {
    private static final String TAG = "WeatherPresenter";

    private IView mView;
    private Context mContext;

    public WeatherPresenter(IView mView, Context mContext) {
        this.mView = mView;
        this.mContext = mContext;
    }

    /**
     * 请求天气数据
     *
     * @param request       请求码
     * @param isWeatherCode 是否是城市码
     */
    @Override
    public void requestWeather(String request, boolean isWeatherCode) {
        IDataRequest weatherRequest = Utils.createWeatherRequestNet(mContext);
        if (request != null)
            weatherRequest.requestData(mContext, request, isWeatherCode, this);
    }

    /**
     * 加载完成
     *
     * @param response http返回的数据
     */
    @Override
    public void onFinished(Object response) {
        mView.updateWeather((WthrcdnWeatherEntity) response);
    }

    /**
     * 加载失败
     *
     * @param t 错误信息
     */
    @Override
    public void onError(Throwable t) {
        Log.e(TAG, t.getMessage());
    }
}
