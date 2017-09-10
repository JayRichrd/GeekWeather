package com.yulong.jiangyu.geekweather.interfaces;

import android.content.Context;

import com.yulong.jiangyu.geekweather.listener.IHttpCallbackListener;

/**
 * author RichardJay
 * email jiangfengfn12@163.com
 * created 2017/9/4 11:25
 * version v1.0
 * modified 2017/9/4
 * note 网络请求数据的接口,MVP架构中处于M层的Module的接口
 */

public interface IDataRequest {

    /**
     * 请求数据的接口
     *
     * @param context              上下文
     * @param RequestCode          请求码
     * @param isCityCode           是否使用城市代码请求数据
     * @param httpCallbackListener http回调接口
     */
    void requestData(Context context, String RequestCode, boolean isCityCode, final IHttpCallbackListener
            httpCallbackListener);

    /**
     * 请求数据的接口
     *
     * @param context              上下文
     * @param RequestCode          请求码
     * @param httpCallbackListener http回调接口
     */
//    void requestData(Context context, String RequestCode, final IHttpCallbackListener
//            httpCallbackListener);

    /**
     * 将不同种类的天气实体数据转换成基础的天气实体数据
     *
     * @param dataEntity 待转换的不同种类的天气实体数据
     * @return 基础的天气实体数据
     */
    IDataEntity trans2Base(IDataEntity dataEntity);
}
