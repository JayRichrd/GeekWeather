package com.yulong.jiangyu.geekweather.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by jiangyu on 2017/2/3.
 */

//数据库表
@DatabaseTable(tableName = "tb_weather_ui_info")
public class WeatherUIInfo {
    @DatabaseField(generatedId = true)
    private static final int id = 1;

    /**
     * 空气质量
     * air_brf : 很差
     * air_txt : 气象条件不利于空气污染物稀释、扩散和清除，请尽量避免在室外长时间活动。
     */
    //@DatabaseField
    //private String air_brf;
    //@DatabaseField
    //private String air_txt;

    /**
     * 舒适度
     * brf : 较舒适
     * txt : 白天虽然天气晴好，但早晚会感觉偏凉，午后舒适、宜人。
     */
    @DatabaseField
    private String comf_brf;
    @DatabaseField
    private String comf_txt;

    /**
     * 紫外线
     * uv_brf : 弱
     * uv_txt : 紫外线强度较弱，建议出门前涂擦SPF在12-15之间、PA+的防晒护肤品。
     */
    @DatabaseField
    private String uv_brf;
    @DatabaseField
    private String uv_txt;

    /**
     * 洗车指数
     * cw_brf : 较适宜
     * txt : 较适宜洗车，未来一天无雨，风力较小，擦洗一新的汽车至少能保持一天。
     */
    @DatabaseField
    private String cw_brf;
    @DatabaseField
    private String cw_txt;

    /**
     * 穿衣指数
     * drsg_brf : 较冷
     * drsg_txt : 建议着厚外套加毛衣等服装。年老体弱者宜着大衣、呢外套加羊毛衫。
     */
    @DatabaseField
    private String drsg_brf;
    @DatabaseField
    private String drsg_txt;

    /**
     * 感冒指数
     * flu_brf : 较易发
     * flu_txt : 昼夜温差较大，较易发生感冒，请适当增减衣服。体质较弱的朋友请注意防护。
     */
    @DatabaseField
    private String flu_brf;
    @DatabaseField
    private String flu_txt;

    /**
     * 运动指数
     * sport_brf : 较不宜
     * sport_txt : 天气较好，但考虑天气寒冷，推荐您进行各种室内运动，若在户外运动请注意保暖并做好准备活动。
     */
    @DatabaseField
    private String sport_brf;
    @DatabaseField
    private String sport_txt;

    /**
     * 旅游指数
     * trav_brf : 适宜
     * trav_txt : 天气较好，气温稍低，会感觉稍微有点凉，不过也是个好天气哦。适宜旅游，可不要错过机会呦！
     */
    //@DatabaseField
    //private String trav_brf;
    //@DatabaseField
    //private String trav_txt;

    //空气湿度
    @DatabaseField
    private String humidity;
    //风向
    @DatabaseField
    private String windDir;
    //风力
    @DatabaseField
    private String windSc;
    //PM2.5
    @DatabaseField
    private String pm25;
    //当前天气
    @DatabaseField
    private String weather;
    //当前温度
    @DatabaseField
    private String temperature;
    //城市
    @DatabaseField
    private String city;
    //更新时间
    @DatabaseField
    private String time;
    //日期
    @DatabaseField
    private String date;

    public WeatherUIInfo() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getPm25() {
        return pm25;
    }

    public void setPm25(String pm25) {
        this.pm25 = pm25;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getWindDir() {
        return windDir;
    }

    public void setWindDir(String windDir) {
        this.windDir = windDir;
    }

    public String getWindSc() {
        return windSc;
    }

    public void setWindSc(String windSc) {
        this.windSc = windSc;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getId() {
        return id;
    }

//    public void setId(int id) {
//        this.id = id;
//    }

//    public String getAir_brf() {
//        return air_brf;
//    }

//    public void setAir_brf(String air_brf) {
//        this.air_brf = air_brf;
//    }

//    public String getAir_txt() {
//        return air_txt;
//    }

//    public void setAir_txt(String air_txt) {
//        this.air_txt = air_txt;
//    }

    public String getComf_brf() {
        return comf_brf;
    }

    public void setComf_brf(String comf_brf) {
        this.comf_brf = comf_brf;
    }

    public String getComf_txt() {
        return comf_txt;
    }

    public void setComf_txt(String comf_txt) {
        this.comf_txt = comf_txt;
    }

    public String getCw_brf() {
        return cw_brf;
    }

    public void setCw_brf(String cw_brf) {
        this.cw_brf = cw_brf;
    }

    public String getCw_txt() {
        return cw_txt;
    }

    public void setCw_txt(String cw_txt) {
        this.cw_txt = cw_txt;
    }

    public String getDrsg_brf() {
        return drsg_brf;
    }

    public void setDrsg_brf(String drsg_brf) {
        this.drsg_brf = drsg_brf;
    }

    public String getDrsg_txt() {
        return drsg_txt;
    }

    public void setDrsg_txt(String drsg_txt) {
        this.drsg_txt = drsg_txt;
    }

    public String getFlu_brf() {
        return flu_brf;
    }

    public void setFlu_brf(String flu_brf) {
        this.flu_brf = flu_brf;
    }

    public String getFlu_txt() {
        return flu_txt;
    }

    public void setFlu_txt(String flu_txt) {
        this.flu_txt = flu_txt;
    }

    public String getSport_brf() {
        return sport_brf;
    }

    public void setSport_brf(String sport_brf) {
        this.sport_brf = sport_brf;
    }

    public String getSport_txt() {
        return sport_txt;
    }

    public void setSport_txt(String sport_txt) {
        this.sport_txt = sport_txt;
    }

//    public String getTrav_brf() {
//        return trav_brf;
//    }

//    public void setTrav_brf(String trav_brf) {
//        this.trav_brf = trav_brf;
//    }

//    public String getTrav_txt() {
//        return trav_txt;
//    }

//    public void setTrav_txt(String trav_txt) {
//        this.trav_txt = trav_txt;
//    }

    public String getUv_brf() {
        return uv_brf;
    }

    public void setUv_brf(String uv_brf) {
        this.uv_brf = uv_brf;
    }

    public String getUv_txt() {
        return uv_txt;
    }

    public void setUv_txt(String uv_txt) {
        this.uv_txt = uv_txt;
    }
}
