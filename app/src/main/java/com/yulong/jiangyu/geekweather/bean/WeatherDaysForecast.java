package com.yulong.jiangyu.geekweather.bean;

import java.io.Serializable;

/**
 * author RichardJay
 * email jiangfengfn12@163.com
 * created 2017/3/7 13:48
 * version v1.0
 * modified 2017/3/7
 * note 多天天气预报实例
 **/

public class WeatherDaysForecast implements Serializable {
    private static final long serialVersionUID = -888062980292384724L;
    //日期
    private String mDate;
    //高温
    private String mHighTemperature;
    //低温
    private String mLowTemperature;
    //白天天气类型
    private String mTypeDay;
    //夜晚天气类型
    private String mTypeNight;
    //白天风向
    private String mWindDirectionDay;
    //夜晚风向
    private String getmWindDirectionNight;
    //白天风力
    private String mWindPpwerDay;
    //夜晚风力
    private String getmWindPpwerNight;

    public String getmDate() {
        return mDate;
    }

    public void setmDate(String mDate) {
        this.mDate = mDate;
    }

    public String getmHighTemperature() {
        return mHighTemperature;
    }

    public void setmHighTemperature(String mHighTemperature) {
        this.mHighTemperature = mHighTemperature;
    }

    public String getmLowTemperature() {
        return mLowTemperature;
    }

    public void setmLowTemperature(String mLowTemperature) {
        this.mLowTemperature = mLowTemperature;
    }

    public String getmTypeDay() {
        return mTypeDay;
    }

    public void setmTypeDay(String mTypeDay) {
        this.mTypeDay = mTypeDay;
    }

    public String getmTypeNight() {
        return mTypeNight;
    }

    public void setmTypeNight(String mTypeNight) {
        this.mTypeNight = mTypeNight;
    }

    public String getmWindDirectionDay() {
        return mWindDirectionDay;
    }

    public void setmWindDirectionDay(String mWindDirectionDay) {
        this.mWindDirectionDay = mWindDirectionDay;
    }

    public String getGetmWindDirectionNight() {
        return getmWindDirectionNight;
    }

    public void setGetmWindDirectionNight(String getmWindDirectionNight) {
        this.getmWindDirectionNight = getmWindDirectionNight;
    }

    public String getmWindPpwerDay() {
        return mWindPpwerDay;
    }

    public void setmWindPpwerDay(String mWindPpwerDay) {
        this.mWindPpwerDay = mWindPpwerDay;
    }

    public String getGetmWindPpwerNight() {
        return getmWindPpwerNight;
    }

    public void setGetmWindPpwerNight(String getmWindPpwerNight) {
        this.getmWindPpwerNight = getmWindPpwerNight;
    }
}
