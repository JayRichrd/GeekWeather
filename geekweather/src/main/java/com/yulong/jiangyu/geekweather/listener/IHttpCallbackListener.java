package com.yulong.jiangyu.geekweather.listener;

/**
 * author RichardJay
 * email jiangfengfn12@163.com
 * created 2017/3/7 17:55
 * version v1.0
 * modified 2017/3/7
 * note Http访问回调接口
 **/

public interface IHttpCallbackListener {
    /**
     * 加载完成
     *
     * @param response http返回的数据
     */
    void onFinished(Object response);

    /**
     * 加载失败
     *
     * @param t 错误信息
     */
    void onError(Throwable t);
}
