package com.yulong.jiangyu.geekweather.constant;

/**
 * Created by jiangyu on 2017/1/25.
 */

public class Constant {
    //和风天气
    //public static final String heWeatherKey = "92db8eb848534ae09f35951c6628a87a";
    //public static final String heWeatherBaseUrl = "https://free-api.heweather.com/v5/";
    //聚合数据天气预报
    //public static final String juHeWeatherKey = "4ea58de8a7573377cec0046f5e2469d5";
    //public static final String juHeWeatherBaseUrl = "http://op.juhe.cn/";
    //中华万年历天气
    //public static final String weatherUrl = "http://wthrcdn.etouch.cn/WeatherApi?city=";
    //天气类型
    //public static final int HEWEATHER = 1;
    //public static final int JUHEWEATHER = 2;
    //public static final int WEATHER = 3;
    //聚合数据万年历
    public static final String dateKey = "f8e4dc7c7920b3d9e8c163af1cc2fb3f";
    public static final String dateBaseUrl = "http://v.juhe.cn/";
    public static final String statusOK = "ok";
    public static final int errorCode = 0;
    //数据版本号
    public static final int TABLE_VERSION = 1;
    //查询的ID
    //public static final Integer ID = 1;


    // share preference
    public static final String WEATHER_SHARE_PREFERENCE = "share_preference_weather";
    // default city
    // SQLite db
    public static final String CITY_MANAGE_DB = "city_manage.db";
    public static final String TABLE_NAME1 = "life_index.db";

    public static final String CITY_INFO_DB = "city_info.db";


    public static final String SHARED_PREFERENCE = "SharedPreference";
    public static final String DEFAULT_CITY = "DefaultCity";

    public static final String CHOSE_CITY = "ChoseCity";
    // 白天到夜晚的时间分界点
    public static final int DAY_2_NIGHT = 18;

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

}
