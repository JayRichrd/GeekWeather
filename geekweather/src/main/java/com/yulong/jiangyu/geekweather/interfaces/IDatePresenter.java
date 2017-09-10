package com.yulong.jiangyu.geekweather.interfaces;

/**
 * author RichardJay
 * email jiangfengfn12@163.com
 * created 2017/9/4 15:50
 * version v1.0
 * modified 2017/9/4
 * note MVP架构中处于P层的Presenter的接口
 */

public interface IDatePresenter {
    /**
     * 请求日历数据
     *
     * @param request       请求码
     * @param isWeatherCode 是否是城市码
     */
    void requestDate(String request, boolean isWeatherCode);
}
