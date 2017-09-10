package com.yulong.jiangyu.geekweather.presenter;

import android.content.Context;
import android.util.Log;

import com.yulong.jiangyu.geekweather.entity.JuHeDateEntity;
import com.yulong.jiangyu.geekweather.listener.IHttpCallbackListener;
import com.yulong.jiangyu.geekweather.utils.Utils;
import com.yulong.jiangyu.geekweather.view.IView;

/**
 * author RichardJay
 * email jiangfengfn12@163.com
 * created 2017/9/4 15:50
 * version v1.0
 * modified 2017/9/4
 * note 请求日历的Presenter
 */

public class DatePresenter implements IHttpCallbackListener, IPresenter {
    private static final String TAG = "WeatherPresenter";

    private IView mView;
    private Context mContext;

    public DatePresenter(IView mView, Context mContext) {
        this.mView = mView;
        this.mContext = mContext;
    }

    /**
     * 加载完成
     *
     * @param response http返回的数据
     */
    @Override
    public void onFinished(Object response) {
        mView.updateDate((JuHeDateEntity) response);
    }

    /**
     * 加载失败
     *
     * @param t 错误信息
     */
    @Override
    public void onError(Throwable t) {
        if (t != null)
            Log.e(TAG, t.getMessage());
    }

    /**
     * 请求数据
     *
     * @param request       请求码
     * @param isWeatherCode 是否是城市码
     * @param isRefresh     是否在刷新
     */
    @Override
    public void requestData(String request, boolean isWeatherCode, boolean isRefresh) {
        if (request != null)
            Utils.createDateRequestNet(mContext).requestData(mContext, request, isWeatherCode, isRefresh, this);
    }
}
