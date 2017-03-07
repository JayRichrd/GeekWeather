package com.yulong.jiangyu.geekweather.listener;

/**
 * author RichardJay
 * email jiangfengfn12@163.com
 * created 2017/3/7 17:55
 * version v1.0
 * modified 2017/3/7
 * note Http访问返回回调的接口
 **/

public interface HttpCallbackListener {
    /**
     * 加载完成
     *
     * @param response http返回的数据
     */
    void onFinish(String response);

    /**
     * 加载失败
     *
     * @param e 错误信息
     */
    void onError(Exception e);
}
