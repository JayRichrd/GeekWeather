package com.yulong.jiangyu.geekweather.util;

import java.text.SimpleDateFormat;

/**
 * author RichardJay
 * email jiangfengfn12@163.com
 * created 2017/3/17 19:56
 * version v1.0
 * modified 2017/3/17
 * note 获取日期数据工具类
 **/

public class DateUtil {

    /**
     * 根据时间格式获取系统当前日期
     *
     * @param formatStr 时间格式
     * @return 系统当前日期
     */
    public static String getCurrentDate(String formatStr) {
        String currentDate;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatStr);
        currentDate = simpleDateFormat.format(System.currentTimeMillis());
        return currentDate;
    }
}
