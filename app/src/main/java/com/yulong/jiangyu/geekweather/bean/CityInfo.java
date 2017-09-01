package com.yulong.jiangyu.geekweather.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/*
 *   author RichardJay
 *    email jiangfengfn12@163.com
 *  created 2017/6/20 14:43
 *  version v1.0
 * modified 2017/6/20
 *     note xxx
 */

@DatabaseTable(tableName = "city_info_test")
public class CityInfo implements Serializable {

    private static final long serialVersionUID = 3698199440205767863L;

    @DatabaseField(generatedId = true)
    private int id;

    // 城市名
    @DatabaseField
    private String city;

    //天气代码
    @DatabaseField
    private String weatherCode;

    // 城市名的拼音
    @DatabaseField
    private String cityPinYin;

    // 城市名的英文缩写
    @DatabaseField
    private String cityEn;

    /**
     * 0 没有匹配结果
     * 1 头部匹配
     * 2 尾部匹配
     */
    @DatabaseField
    private int matchType = 0; // 查询搜索时被匹配的类型

    public CityInfo() {
    }

    public CityInfo(String city, String weatherCode, String cityPinYin, String cityEn,
                    int matchType) {
        this.city = city;
        this.weatherCode = weatherCode;
        this.cityPinYin = cityPinYin;
        this.cityEn = cityEn;
        this.matchType = matchType;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getWeatherCode() {
        return weatherCode;
    }

    public void setWeatherCode(String weatherCode) {
        this.weatherCode = weatherCode;
    }

    public String getCityPinYin() {
        return cityPinYin;
    }

    public void setCityPinYin(String cityPinYin) {
        this.cityPinYin = cityPinYin;
    }

    public String getCityEn() {
        return cityEn;
    }

    public void setCityEn(String cityEn) {
        this.cityEn = cityEn;
    }

    public int getMatchType() {
        return matchType;
    }

    public void setMatchType(int matchType) {
        this.matchType = matchType;
    }
}
