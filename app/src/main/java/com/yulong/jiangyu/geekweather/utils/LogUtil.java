package com.yulong.jiangyu.geekweather.utils;

import android.util.Log;

/**
 * author RichardJay
 * email jiangfengfn12@163.com
 * created 2017/3/8 18:48
 * version v1.0
 * modified 2017/3/8
 * note 打印日志的工具类
 **/

public class LogUtil {
    /**
     * 输出错误日志
     *
     * @param tag 日志的tag
     * @param msg 日志信息
     */
    public static void e(String tag, String msg) {
        Log.e(tag, msg);
    }

    /**
     * 输出debug日志
     *
     * @param tag 日志的tag
     * @param msg 日志信息
     */
    public static void d(String tag, String msg) {
        Log.d(tag, msg);
    }

    /**
     * 输出info日志
     *
     * @param tag 日志的tag
     * @param msg 日志信息
     */
    public static void i(String tag, String msg) {
        Log.i(tag, msg);
    }
}
