package com.yulong.jiangyu.geekweather.constant;

/**
 * Created by jiangyu on 2017/1/25.
 */

public class Constant {
    public static final int errorCode = 0x0;
    //数据版本号
    public static final int TABLE_VERSION = 1;

    // SQLite db
    public static final String CITY_MANAGE_DB = "city_manage.db";
    public static final String TABLE_NAME1 = "life_index.db";

    public static final String CITY_INFO_DB = "city_info.db";


    public static final String SHARED_PREFERENCE = "SharedPreference";
    public static final String DEFAULT_CITY = "DefaultCity";

    public static final String CHOSE_CITY = "ChoseCity";

    // 白天到夜晚的时间分界点
    public static final int DAY_TO_NIGHT = 18;

    //MainFragment
    public static final String MAIN_FRAGMENT_TAG = "MainFragmentTag";
    public static final int MAIN_FRAGMENT_REQUEST_CODE = 0x1;
    public static final int MAIN_FRAGMENT_UPDATE_WEATHER_UI = 0x2;
    public static final int MAIN_FRAGMENT_UPDATE_DATE_UI = 0x3;
    public static final String MAIN_FRAGMENT_WEATHER_ENTITY = "WeatherEntity";
    public static final String MAIN_FRAGMENT_DATE_ENTITY = "DateEntity";
    public static final String MAIN_FRAGMENT_LIFT_INDEX_EXERCISE = "晨练指数";
    public static final String MAIN_FRAGMENT_LIFT_INDEX_CLOTHE = "穿衣指数";
    public static final String MAIN_FRAGMENT_LIFT_INDEX_COMFORT = "舒适度";
    public static final String MAIN_FRAGMENT_LIFT_INDEX_INFLUENZA = "感冒指数";
    public static final String MAIN_FRAGMENT_LIFT_INDEX_WASH_CAR = "洗车指数";
    public static final String MAIN_FRAGMENT_LIFT_INDEX_ULTRAVIOLET = "紫外线强度";

    //CityManageActivity
    public static final int CITY_MANAGE_ACTIVITY_UPDATE_CITY_MANAGE = 0x4;
    public static final int CITY_MANAGE_ACTIVITY_REQUEST_CODE = 0x5;
    public static final int CITY_MANAGE_ACTIVITY_RESULT_CODE = 0x6;

    // AddCityActivity
    public static final int ADD_CITY_ACTIVITY_RESULT_CODE = 0x7;

    // WelcomeActivity
    public static final int WELCOME_ACTIVITY_START_MAIN_ACTIVITY = 0X8;

}
