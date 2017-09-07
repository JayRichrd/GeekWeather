package com.yulong.jiangyu.geekweather.utils;

import android.content.Context;
import android.util.Xml;

import com.yulong.jiangyu.geekweather.R;
import com.yulong.jiangyu.geekweather.entity.LifeIndexEntity;
import com.yulong.jiangyu.geekweather.entity.WeatherForecastDaysEntity;
import com.yulong.jiangyu.geekweather.entity.WthrcdnWeatherEntity;
import com.yulong.jiangyu.geekweather.interfaces.IDataRequest;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * author RichardJay
 * email jiangfengfn12@163.com
 * created 2017/3/8 10:17
 * version v1.0
 * modified 2017/3/8
 * note 工具类
 **/

public class Utils {
    private static final String TAG = "Utils";
    // 类的后缀
    private static final String SUFFIX_WEATHER_REQUEST_NET = "WeatherRequestNet";
    private static final String SUFFIX_DATE_REQUEST_NET = "DateRequestNet";
    // 包名后缀
    private static final String SUFFIX_PACKAGE_NAME = ".net";

    /**
     * @param inputStream 输入流
     * @return 解析好的天气信息
     */
    public static WthrcdnWeatherEntity handleWthrcdnWeatherEntity(InputStream inputStream) {
        //初始化
        WthrcdnWeatherEntity weatherInfo = new WthrcdnWeatherEntity();
        WeatherForecastDaysEntity weatherDaysForecast = null;
        List<WeatherForecastDaysEntity> weatherDaysForecasts = new ArrayList<>();
        LifeIndexEntity weatherLifeIndex = null;
        List<LifeIndexEntity> weatherLifeIndices = new ArrayList<>();
        //是否是多天天气预报
        boolean isDaysForecast = false;
        //是否是白天
        boolean isDay = false;
        try {//解析XML文件输入流
            XmlPullParser parser = Xml.newPullParser();
            //设置解析器属性
            parser.setInput(inputStream, "UTF-8");
            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        switch (parser.getName()) {
                            case "city"://城市
                                weatherInfo.setmCity(parser.nextText());
                                break;
                            case "updatetime"://更新时间
                                weatherInfo.setmUpdateTime(parser.nextText());
                                break;
                            case "wendu"://当前温度
                                weatherInfo.setmTemperature(parser.nextText());
                                break;
                            case "fengli"://风力
                                if (!isDaysForecast)
                                    weatherInfo.setmWindPower(parser.nextText());
                                else if (weatherDaysForecast != null) {
                                    if (isDay)
                                        weatherDaysForecast.setmWindPowerDay(parser.nextText());
                                    else
                                        weatherDaysForecast.setmWindPowerNight(parser.nextText());
                                }
                                break;
                            case "shidu"://湿度
                                weatherInfo.setmHumidity(parser.nextText());
                                break;
                            case "fengxiang"://风向
                                if (!isDaysForecast)
                                    weatherInfo.setmWindDirection(parser.nextText());
                                else if (weatherDaysForecast != null) {
                                    if (isDay)
                                        weatherDaysForecast.setmWindDirectionDay(parser.nextText());
                                    else
                                        weatherDaysForecast.setmWindDirectionNight(parser.nextText());
                                }
                                break;
                            case "sunrise_1"://日出时间
                                weatherInfo.setmSunrise(parser.nextText());
                                break;
                            case "sunset_1"://日落时间
                                weatherInfo.setmSunset(parser.nextText());
                                break;
                            case "aqi"://空气质量指标
                                weatherInfo.setmAQI(parser.nextText());
                                break;
                            case "quality"://空气质量
                                weatherInfo.setmAQ(parser.nextText());
                                break;
                            case "alarmType"://警报类型
                                weatherInfo.setmAlarmType(parser.nextText());
                                break;
                            case "alarmDegree"://警报等级
                                weatherInfo.setmAlarmDegree(parser.nextText());
                                break;
                            case "alarmText"://警报内容
                                weatherInfo.setmAlarmText(parser.nextText());
                                break;
                            case "alarm_details"://警报详情
                                weatherInfo.setmAlarmDetail(parser.nextText());
                                break;
                            case "time"://警报时间
                                weatherInfo.setmAlarmTime(parser.nextText());
                                break;
                            case "forecast"://多天天气预报
                                isDaysForecast = true;
                                break;
                            case "yesterday"://昨日天气
                                isDaysForecast = true;
                                weatherDaysForecast = new WeatherForecastDaysEntity();
                                break;
                            case "date_1"://日期
                                if (weatherDaysForecast != null)
                                    weatherDaysForecast.setmDate(parser.nextText());
                                break;
                            case "high_1"://高温
                                if (weatherDaysForecast != null)
                                    weatherDaysForecast.setmHighTemperature(parser.nextText());
                                break;
                            case "low_1"://低温
                                if (weatherDaysForecast != null)
                                    weatherDaysForecast.setmLowTemperature(parser.nextText());
                                break;
                            case "day_1"://白天
                                isDay = true;
                                break;
                            case "night_1"://晚上
                                isDay = false;
                                break;
                            case "type_1"://天气类型
                                if (weatherDaysForecast != null) {
                                    if (isDay)
                                        weatherDaysForecast.setmTypeDay(parser.nextText());
                                    else
                                        weatherDaysForecast.setmTypeNight(parser.nextText());
                                }
                                break;
                            case "fx_1"://风向
                                if (weatherDaysForecast != null) {
                                    if (isDay)
                                        weatherDaysForecast.setmWindDirectionDay(parser.nextText());
                                    else
                                        weatherDaysForecast.setmWindDirectionNight(parser.nextText());
                                }
                                break;
                            case "fl_1"://风力
                                if (weatherDaysForecast != null) {
                                    if (isDay)
                                        weatherDaysForecast.setmWindPowerDay(parser.nextText());
                                    else
                                        weatherDaysForecast.setmWindPowerNight(parser.nextText());
                                }
                                break;
                            case "weather"://多天天气预报
                                weatherDaysForecast = new WeatherForecastDaysEntity();
                                break;
                            case "date"://日期
                                if (weatherDaysForecast != null)
                                    weatherDaysForecast.setmDate(parser.nextText());
                                break;
                            case "high"://高温
                                if (weatherDaysForecast != null)
                                    weatherDaysForecast.setmHighTemperature(parser.nextText());
                                break;
                            case "low"://低温
                                if (weatherDaysForecast != null)
                                    weatherDaysForecast.setmLowTemperature(parser.nextText());
                                break;
                            case "day"://白天
                                isDay = true;
                                break;
                            case "night"://晚上
                                isDay = false;
                                break;
                            case "type"://类型
                                if (weatherDaysForecast != null)
                                    if (isDay)
                                        weatherDaysForecast.setmTypeDay(parser.nextText());
                                    else
                                        weatherDaysForecast.setmTypeNight(parser.nextText());
                                break;
                            case "zhishu"://生活指数
                                weatherLifeIndex = new LifeIndexEntity();
                                break;
                            case "name"://指数名
                                if (weatherLifeIndex != null)
                                    weatherLifeIndex.setmIndexName(parser.nextText());
                                break;
                            case "value"://指数内容
                                if (weatherLifeIndex != null)
                                    weatherLifeIndex.setmIndexSuggestion(parser.nextText());
                                break;
                            case "detail"://指数详情
                                if (weatherLifeIndex != null)
                                    weatherLifeIndex.setmIndexDetail(parser.nextText());
                                break;
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        switch (parser.getName()) {
                            case "yesterday"://昨天天气
                            case "weather"://多天天气
                                weatherDaysForecasts.add(weatherDaysForecast);
                                weatherDaysForecast = null;
                                break;
                            case "zhishu"://生活指数
                                weatherLifeIndices.add(weatherLifeIndex);
                                weatherLifeIndex = null;
                                break;
                        }
                        break;
                }
                eventType = parser.next();
            }
        } catch (XmlPullParserException | IOException e) {
            LogUtil.e(TAG, e.getMessage());
            e.printStackTrace();
        }
        weatherInfo.setmWeatherDaysForecasts(weatherDaysForecasts);
        weatherInfo.setmWeatherLifeIndies(weatherLifeIndices);
        return weatherInfo;
    }

    /**
     * 返回天气图片id
     *
     * @param type  天气类型
     * @param isDay 是否为白天
     * @return 天气图片id
     */
    public static int getWeatherImageId(String type, boolean isDay) {
        if (type == null) {
            return R.drawable.ic_weather_no;
        }
        int imgId;
        switch (type) {
            case "晴":
                if (isDay) {
                    imgId = R.drawable.ic_weather_sunny_day;
                } else {
                    imgId = R.drawable.ic_weather_sunny_night;
                }
                break;
            case "多云":
                if (isDay) {
                    imgId = R.drawable.ic_weather_cloudy_day;
                } else {
                    imgId = R.drawable.ic_weather_cloudy_night;
                }
                break;
            case "阴":
                imgId = R.drawable.ic_weather_overcast;
                break;
            case "雷阵雨":
            case "雷阵雨伴有冰雹":
                imgId = R.drawable.ic_weather_thunder_shower;
                break;
            case "雨夹雪":
                imgId = R.drawable.ic_weather_sleet;
                break;
            case "冻雨":
                imgId = R.drawable.ic_weather_ice_rain;
                break;
            case "小雨":
            case "小到中雨":
            case "阵雨":
                imgId = R.drawable.ic_weather_light_rain_or_shower;
                break;
            case "中雨":
            case "中到大雨":
                imgId = R.drawable.ic_weather_moderate_rain;
                break;
            case "大雨":
            case "大到暴雨":
                imgId = R.drawable.ic_weather_heavy_rain;
                break;
            case "暴雨":
            case "大暴雨":
            case "特大暴雨":
            case "暴雨到大暴雨":
            case "大暴雨到特大暴雨":
                imgId = R.drawable.ic_weather_storm;
                break;
            case "阵雪":
            case "小雪":
            case "小到中雪":
                imgId = R.drawable.ic_weather_light_snow;
                break;
            case "中雪":
            case "中到大雪":
                imgId = R.drawable.ic_weather_moderate_snow;
                break;
            case "大雪":
            case "大到暴雪":
                imgId = R.drawable.ic_weather_heavy_snow;
                break;
            case "暴雪":
                imgId = R.drawable.ic_weather_snowstrom;
                break;
            case "雾":
                imgId = R.drawable.ic_weather_foggy;
                break;
            case "霾":
                imgId = R.drawable.ic_weather_haze;
                break;
            case "沙尘暴":
                imgId = R.drawable.ic_weather_duststorm;
                break;
            case "强沙尘暴":
                imgId = R.drawable.ic_weather_sandstorm;
                break;
            case "浮尘":
            case "扬沙":
                imgId = R.drawable.ic_weather_sand_or_dust;
                break;
            default:
                if (type.contains("尘") || type.contains("沙")) {
                    imgId = R.drawable.ic_weather_sand_or_dust;
                } else if (type.contains("雾") || type.contains("霾")) {
                    imgId = R.drawable.ic_weather_foggy;
                } else if (type.contains("雨")) {
                    imgId = R.drawable.ic_weather_ice_rain;
                } else if (type.contains("雪") || type.contains("冰雹")) {
                    imgId = R.drawable.ic_weather_moderate_snow;
                } else {
                    imgId = R.drawable.ic_weather_no;
                }
                break;
        }
        return imgId;
    }

    /**
     * 返回空气质量图片id
     *
     * @param quality 空气质量
     * @return 空气质量图片id
     */
    public static int getQualityImageId(String quality) {
        int imgId;
        switch (quality) {
            case "优":
                imgId = R.drawable.ic_quality_nice;
                break;
            case "良":
                imgId = R.drawable.ic_quality_good;
                break;
            case "轻度污染":
                imgId = R.drawable.ic_quality_little;
                break;
            case "中度污染":
                imgId = R.drawable.ic_quality_medium;
                break;
            case "重度污染":
                imgId = R.drawable.ic_quality_serious;
                break;
            case "严重污染":
                imgId = R.drawable.ic_quality_terrible;
                break;
            default:
                imgId = R.drawable.ic_quality_nice;
                break;
        }
        return imgId;
    }

    /**
     * 获取温度
     *
     * @param tempStr 温度字符串 高温 23℃
     * @return 返回温度值
     */
    public static int parseTemperature(String tempStr) {
        int temperature = 0;
        if (tempStr.contains(" ")) {
            //根据空格将字符串拆分
            String[] subStr = tempStr.split(" ");
            if (2 == subStr.length) {
                //根据℃拆字符串
                String[] subSubStr = subStr[1].split("℃");
                if (subSubStr != null)
                    temperature = Integer.parseInt(subSubStr[0]);
            }
        }
        return temperature;
    }

    /**
     * 重组字符串，当超过3个字符时，在第3个字符后面加上换行符
     *
     * @param source 字符源
     * @return 处理过后的字符串
     */
    public static String recombineString(String source) {
        if (source.length() >= 3)
            return source.substring(0, 3) + "\n" + source.substring(3);
        else
            return source;
    }

    /**
     * 根据时间格式获取系统当前日期
     *
     * @param formatStr 时间格式
     * @return 系统当前日期
     */
    public static String getCurrentDate(String formatStr) {
        String currentDate;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatStr, java.util.Locale.getDefault());
        currentDate = simpleDateFormat.format(System.currentTimeMillis());
        return currentDate;
    }

    /**
     * 解析日期的字符串
     *
     * @param dateStr 字符串 18日星期六
     * @return 返回日期和星期的数组
     */
    public static String[] parseDate(String dateStr) {
        return dateStr.split("日");
    }

//    public static void refreshWeatherData(final Context context, final String weatherRequest, final boolean
//            isWeatherCode, final RefreshCallbackListener refreshCallbackListener) {
//
//        String address_weather;
//        if (isWeatherCode) {// 用城市编码请求天气数据
//            address_weather = context.getString(R.string.weather_city_key_base_url, weatherRequest);
//        } else { // 用城市名请求天气数据
//            address_weather = context.getString(R.string.weather_city_base_url, weatherRequest);
//        }
//
//        WebUtil.requestData(address_weather, new HttpCallbackListener() {
//            @Override
//            public void onFinished(Object response) {
//                //获取天气数据
//                WeatherInfo weatherInfo = WeatherInfoUtil.handleWthrcdnWeatherEntity(new ByteArrayInputStream((
// (String)
// response)
//                        .getBytes()));
//                refreshCallbackListener.onRefreshed(weatherInfo);
//
//
//                if (isWeatherCode) {
//                    // 解析出天气数据后建立CityManage对象
//                    String cityName = weatherInfo.getmCity();
//                    String tempTop = weatherInfo.getmWeatherDaysForecasts().get(1).getmHighTemperature();
//                    String tempLow = weatherInfo.getmWeatherDaysForecasts().get(1).getmLowTemperature();
//                    String weatherTypeDay = weatherInfo.getmWeatherDaysForecasts().get(1).getmTypeDay();
//                    String weatherTypeNight = weatherInfo.getmWeatherDaysForecasts().get(1).getmTypeNight();
//                    CityManage cityManage = new CityManage(null, cityName, tempTop, tempLow, weatherTypeDay,
//                            weatherTypeNight, weatherRequest);
//
//                    // 将CityManage对象插入数据库
//                    CityMangeDao cityMangeDao = new CityMangeDao(context);
//                    refreshCallbackListener.onRefreshed(cityMangeDao.insert(cityManage));
//                } else {
//                    WeatherLifeIndexDao weatherLifeIndexDao = new WeatherLifeIndexDao(context);
//                    weatherLifeIndexDao.insert(weatherInfo.getmWeatherLifeIndies());
//                }
//            }
//
//            @Override
//            public void onError(Throwable t) {
//
//            }
//        });
//    }

    /**
     * 利用反射生成对应的类
     *
     * @param context 上下文
     * @return 返回生成的类
     */
    public static IDataRequest createWeatherRequestNet(Context context) {
        // 包名
        String pkName = context.getPackageName();
        // 类名
        String className = context.getString(R.string.config_weather) + SUFFIX_WEATHER_REQUEST_NET;
        // 类的全限定名
        String fullPath = pkName + SUFFIX_PACKAGE_NAME + "." + className;
        // 下面将利用反射来生成类
        IDataRequest weatherRequestNet = null;
        try {
            Class<?> clazz = Class.forName(fullPath);
            weatherRequestNet = (IDataRequest) clazz.newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return weatherRequestNet;
    }

    /**
     * 利用反射生成对应的类
     *
     * @param context 上下文
     * @return 返回生成的类
     */
    public static IDataRequest createDateRequestNet(Context context) {
        // 包名
        String pkName = context.getPackageName();
        // 类名
        String className = context.getString(R.string.config_date) + SUFFIX_DATE_REQUEST_NET;
        // 类的全限定名
        String fullPath = pkName + SUFFIX_PACKAGE_NAME + "." + className;
        // 下面将利用反射来生成类
        IDataRequest dateRequestNet = null;
        try {
            Class<?> clazz = Class.forName(fullPath);
            dateRequestNet = (IDataRequest) clazz.newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return dateRequestNet;
    }


}
