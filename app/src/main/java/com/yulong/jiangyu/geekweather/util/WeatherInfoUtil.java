package com.yulong.jiangyu.geekweather.util;

import android.util.Xml;

import com.yulong.jiangyu.geekweather.bean.WeatherDaysForecast;
import com.yulong.jiangyu.geekweather.bean.WeatherInfo;
import com.yulong.jiangyu.geekweather.bean.WeatherLifeIndex;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * author RichardJay
 * email jiangfengfn12@163.com
 * created 2017/3/8 10:17
 * version v1.0
 * modified 2017/3/8
 * note 操作天气数据的工具类
 **/

public class WeatherInfoUtil {
    /**
     * @param inputStream 输入流
     * @return 解析好的天气信息
     */
    public static WeatherInfo handleWeatherInfo(InputStream inputStream) {
        //初始化
        WeatherInfo weatherInfo = new WeatherInfo();
        WeatherDaysForecast weatherDaysForecast = null;
        List<WeatherDaysForecast> weatherDaysForecasts = new ArrayList<>();
        WeatherLifeIndex weatherLifeIndex = null;
        List<WeatherLifeIndex> weatherLifeIndices = new ArrayList<>();
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
                                        weatherDaysForecast.setmWindDirectionDay(parser.nextText());
                                    else
                                        weatherDaysForecast.setmWindDirectionNight(parser.nextText());
                                }
                                break;
                            case "weather"://多天天气预报
                                weatherDaysForecast = new WeatherDaysForecast();
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
                                weatherLifeIndex = new WeatherLifeIndex();
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
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        weatherInfo.setmWeatherDaysForecasts(weatherDaysForecasts);
        weatherInfo.setmWeatherLifeIndies(weatherLifeIndices);
        return weatherInfo;
    }
}
