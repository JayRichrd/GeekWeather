package com.yulong.jiangyu.geekweather.view;

import com.yulong.jiangyu.geekweather.interfaces.IDataEntity;

/**
 * author RichardJay
 * email jiangfengfn12@163.com
 * created 2017/9/4 15:50
 * version v1.0
 * modified 2017/9/4
 * note MVP架构中处于V层的视图的接口
 */

public interface IView {
    /**
     * 更新天气
     *
     * @param dataEntity 天气数据
     */
    void updateWeather(IDataEntity dataEntity);

    /**
     * 更新日历日期
     *
     * @param dataEntity 日历日期数据
     */
    void updateDate(IDataEntity dataEntity);
}
