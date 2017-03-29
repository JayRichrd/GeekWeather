package com.yulong.jiangyu.geekweather.listener;

import com.yulong.jiangyu.geekweather.bean.DateInfo;

/**
 * ic_author RichardJay
 * email jiangfengfn12@163.com
 * created 2017/3/17 19:38
 * version v1.0
 * modified 2017/3/17
 * note 日历数据返回的接口
 **/

public interface DateCallbackListener {

    /**
     * 返回成功
     *
     * @param dateInfo 日历实体
     */
    void onFinish(DateInfo dateInfo);

    /**
     * 返回失败
     *
     * @param t 错误
     */
    void onError(Throwable t);
}
