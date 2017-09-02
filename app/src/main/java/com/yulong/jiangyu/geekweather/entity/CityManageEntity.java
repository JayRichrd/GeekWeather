package com.yulong.jiangyu.geekweather.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * author RichardJay
 * email jiangfengfn12@163.com
 * created 2017/5/2 21:02
 * version v1.0
 * modified 2017/5/2
 * note 城市管理实例
 **/
@DatabaseTable(tableName = "city_manage")
public class CityManageEntity implements Serializable {

    private static final long serialVersionUID = 3721852296435167796L;
    //id
    @DatabaseField(generatedId = true)
    private int id;
    //城市定位
    @DatabaseField
    private String locationCity;

    /**
     * 城市名
     */
    @DatabaseField
    private String cityName;

    /**
     * 高温
     */
    @DatabaseField
    private String tempTop;

    /**
     * 低温
     */
    @DatabaseField
    private String tempLow;

    /**
     * 白天天气类型
     */
    @DatabaseField
    private String weatherTypeDay;

    /**
     * 夜间天气类型
     */
    @DatabaseField
    private String weatherTypeNight;

    /**
     * 天气代码
     */
    @DatabaseField
    private String weatherCode;

    public CityManageEntity() {
    }

    public CityManageEntity(String locationCity, String cityName, String tempTop, String tempLow, String weatherTypeDay,
                            String weatherTypeNight, String weatherCode) {
        this.locationCity = locationCity;
        this.cityName = cityName;
        this.tempTop = tempTop;
        this.tempLow = tempLow;
        this.weatherTypeDay = weatherTypeDay;
        this.weatherTypeNight = weatherTypeNight;
        this.weatherCode = weatherCode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocationCity() {
        return locationCity;
    }

    public void setLocationCity(String locationCity) {
        this.locationCity = locationCity;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getTempTop() {
        return tempTop;
    }

    public void setTempTop(String tempTop) {
        this.tempTop = tempTop;
    }

    public String getTempLow() {
        return tempLow;
    }

    public void setTempLow(String tempLow) {
        this.tempLow = tempLow;
    }

    public String getWeatherTypeDay() {
        return weatherTypeDay;
    }

    public void setWeatherTypeDay(String weatherTypeDay) {
        this.weatherTypeDay = weatherTypeDay;
    }

    public String getWeatherTypeNight() {
        return weatherTypeNight;
    }

    public void setWeatherTypeNight(String weatherTypeNight) {
        this.weatherTypeNight = weatherTypeNight;
    }

    public String getWeatherCode() {
        return weatherCode;
    }

    public void setWeatherCode(String weatherCode) {
        this.weatherCode = weatherCode;
    }
}
