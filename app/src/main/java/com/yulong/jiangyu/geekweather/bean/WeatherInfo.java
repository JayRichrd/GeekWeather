package com.yulong.jiangyu.geekweather.bean;

import java.io.Serializable;
import java.util.List;

/**
 * author RichardJay
 * email jiangfengfn12@163.com
 * created 2017/3/7 13:37
 * version v1.0
 * modified 2017/3/7
 * note 天气实例
 **/

public class WeatherInfo implements Serializable {
    private static final long serialVersionUID = -1817778761471469646L;
    //城市
    private String mCity;
    //更新时间
    private String mUpdateTime;
    //温度
    private String mTemperature;
    //湿度
    private String mHumidity;
    //风力
    private String mWindPower;
    //风向
    private String mWindDirection;
    //日出
    private String mSunrise;
    //日落
    private String mSunset;
    //空气质量指数
    private String mAQI;
    //空气质量
    private String mAQ;
    //警报类型
    private String mAlarmType;
    //警报等级
    private String mAlarmDegree;
    //警报正文
    private String mAlarmText;
    //警报时间
    private String mAlarmTime;
    //警报详情
    private String mAlarmDetail;
    //多天天气预报
    private List<WeatherDaysForecast> mWeatherDaysForecasts;
    //生活指数
    private List<WeatherLifeIndex> mWeatherLifeIndies;

    public String getmCity() {
        return mCity;
    }

    public void setmCity(String mCity) {
        this.mCity = mCity;
    }

    public String getmUpdateTime() {
        return mUpdateTime;
    }

    public void setmUpdateTime(String mUpdateTime) {
        this.mUpdateTime = mUpdateTime;
    }

    public String getmTemperature() {
        return mTemperature;
    }

    public void setmTemperature(String mTemperature) {
        this.mTemperature = mTemperature;
    }

    public String getmHumidity() {
        return mHumidity;
    }

    public void setmHumidity(String mHumidity) {
        this.mHumidity = mHumidity;
    }

    public String getmWindPower() {
        return mWindPower;
    }

    public void setmWindPower(String mWindPower) {
        this.mWindPower = mWindPower;
    }

    public String getmWindDirection() {
        return mWindDirection;
    }

    public void setmWindDirection(String mWindDirection) {
        this.mWindDirection = mWindDirection;
    }

    public String getmSunrise() {
        return mSunrise;
    }

    public void setmSunrise(String mSunrise) {
        this.mSunrise = mSunrise;
    }

    public String getmSunset() {
        return mSunset;
    }

    public void setmSunset(String mSunset) {
        this.mSunset = mSunset;
    }

    public String getmAQI() {
        return mAQI;
    }

    public void setmAQI(String mAQI) {
        this.mAQI = mAQI;
    }

    public String getmAQ() {
        return mAQ;
    }

    public void setmAQ(String mAQ) {
        this.mAQ = mAQ;
    }

    public String getmAlarmType() {
        return mAlarmType;
    }

    public void setmAlarmType(String mAlarmType) {
        this.mAlarmType = mAlarmType;
    }

    public String getmAlarmDegree() {
        return mAlarmDegree;
    }

    public void setmAlarmDegree(String mAlarmDegree) {
        this.mAlarmDegree = mAlarmDegree;
    }

    public String getmAlarmText() {
        return mAlarmText;
    }

    public void setmAlarmText(String mAlarmText) {
        this.mAlarmText = mAlarmText;
    }

    public String getmAlarmTime() {
        return mAlarmTime;
    }

    public void setmAlarmTime(String mAlarmTime) {
        this.mAlarmTime = mAlarmTime;
    }

    public String getmAlarmDetail() {
        return mAlarmDetail;
    }

    public void setmAlarmDetail(String mAlarmDetail) {
        this.mAlarmDetail = mAlarmDetail;
    }

    public List<WeatherDaysForecast> getmWeatherDaysForecasts() {
        return mWeatherDaysForecasts;
    }

    public void setmWeatherDaysForecasts(List<WeatherDaysForecast> mWeatherDaysForecasts) {
        this.mWeatherDaysForecasts = mWeatherDaysForecasts;
    }

    public List<WeatherLifeIndex> getmWeatherLifeIndies() {
        return mWeatherLifeIndies;
    }

    public void setmWeatherLifeIndies(List<WeatherLifeIndex> mWeatherLifeIndies) {
        this.mWeatherLifeIndies = mWeatherLifeIndies;
    }
}
