package com.yulong.jiangyu.geekweather.listener;

import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;

/**
 * author RichardJay
 * email jiangfengfn12@163.com
 * created 2017/9/4 15:50
 * version v1.0
 * modified 2017/9/4
 * note 百度地图定位监听接口
 */

public class BaiDuLocationListener implements BDLocationListener {
    private static final String TAG = "BaiDuLocationListener";
    private String locationCity;
    private LocationClient mLocationClient;

    public BaiDuLocationListener(LocationClient mLocationClient) {
        this.mLocationClient = mLocationClient;
    }

    @Override
    public void onReceiveLocation(BDLocation bdLocation) {
        Log.d(TAG, "===onReceiveLocation()===");

    }
}
