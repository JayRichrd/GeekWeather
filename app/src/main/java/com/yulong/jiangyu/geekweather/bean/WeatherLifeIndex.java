package com.yulong.jiangyu.geekweather.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * author RichardJay
 * email jiangfengfn12@163.com
 * created 2017/3/7 13:58
 * version v1.0
 * modified 2017/3/7
 * note 生活指数实例
 **/
@DatabaseTable(tableName = "weather_life_index")
public class WeatherLifeIndex implements Serializable {
    private static final long serialVersionUID = -6000595252788417505L;
    @DatabaseField(generatedId = true)
    private int id;
    //指数名
    @DatabaseField
    private String mIndexName;
    //指数建议
    @DatabaseField
    private String mIndexSuggestion;
    //指数详情
    @DatabaseField
    private String mIndexDetail;

    public WeatherLifeIndex() {
    }

    public String getmIndexName() {
        return mIndexName;
    }

    public void setmIndexName(String mIndexName) {
        this.mIndexName = mIndexName;
    }

    public String getmIndexSuggestion() {
        return mIndexSuggestion;
    }

    public void setmIndexSuggestion(String mIndexSuggestion) {
        this.mIndexSuggestion = mIndexSuggestion;
    }

    public String getmIndexDetail() {
        return mIndexDetail;
    }

    public void setmIndexDetail(String mIndexDetail) {
        this.mIndexDetail = mIndexDetail;
    }
}
