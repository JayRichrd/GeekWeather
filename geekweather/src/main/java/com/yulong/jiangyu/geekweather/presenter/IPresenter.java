package com.yulong.jiangyu.geekweather.presenter;

/**
 * author RichardJay
 * email jiangfengfn12@163.com
 * created 2017/9/10 16:31
 * version v1.0
 * modified 2017/9/10
 * note xxx
 */

public interface IPresenter {
    /**
     * 请求数据
     *
     * @param request       请求码
     * @param isWeatherCode 是否是城市码
     * @param isRefresh     是否在刷新
     */
    void requestData(String request, boolean isWeatherCode, boolean isRefresh);
}
