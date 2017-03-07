package com.yulong.jiangyu.geekweather.bean;

import java.io.Serializable;

/**
 * author RichardJay
 * email jiangfengfn12@163.com
 * created 2017/3/7 13:58
 * version v1.0
 * modified 2017/3/7
 * note 生活指数实例
 **/

public class WeatherLifeIndex implements Serializable {
    private static final long serialVersionUID = -6000595252788417505L;
    //指数名
    private String mIndexName;
    //指数建议
    private String mIndexSuggestion;
    //指数详情
    private String mIndexDetail;

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
