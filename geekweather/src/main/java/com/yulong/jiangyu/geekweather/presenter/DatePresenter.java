package com.yulong.jiangyu.geekweather.presenter;

import android.content.Context;
import android.util.Log;

import com.yulong.jiangyu.geekweather.entity.JuHeDateEntity;
import com.yulong.jiangyu.geekweather.interfaces.IDataRequest;
import com.yulong.jiangyu.geekweather.interfaces.IDatePresenter;
import com.yulong.jiangyu.geekweather.interfaces.IView;
import com.yulong.jiangyu.geekweather.listener.IHttpCallbackListener;
import com.yulong.jiangyu.geekweather.utils.Utils;

/**
 * author RichardJay
 * email jiangfengfn12@163.com
 * created 2017/9/4 15:50
 * version v1.0
 * modified 2017/9/4
 * note 请求日历的Presenter
 */

public class DatePresenter implements IHttpCallbackListener, IDatePresenter {
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
        Log.e(TAG, t.getMessage());
    }

    /**
     * 请求日历数据
     *
     * @param request       请求码
     * @param isWeatherCode 是否是城市码
     */
    @Override
    public void requestDate(String request, boolean isWeatherCode) {
        IDataRequest dateRequest = Utils.createDateRequestNet(mContext);
        if (request != null)
            dateRequest.requestData(mContext, request, isWeatherCode, this);
    }
}
