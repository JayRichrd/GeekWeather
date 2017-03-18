package com.yulong.jiangyu.geekweather.util;

import com.yulong.jiangyu.geekweather.constant.Constant;

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

    /**
     * 解析日期的字符串
     *
     * @param dateStr 字符串 18日星期六
     * @return 日期和星期的数组
     */
    public static String[] parseDate(String dateStr) {
        String[] result = dateStr.split(Constant.SPLIT_DATE_REGEX);
        return result;
    }
}
