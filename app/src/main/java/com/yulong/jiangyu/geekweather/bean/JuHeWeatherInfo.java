package com.yulong.jiangyu.geekweather.bean;

import java.util.List;

/**
 * Created by jiangyu on 2017/2/3.
 * 聚合数据天气
 */

public class JuHeWeatherInfo {

    /**
     * reason : successed!
     * result : {"data":{"pubdate":"2017-02-03","pubtime":"11:00:00","realtime":{"city_code":"101280601",
     * "city_name":"深圳","date":"2017-02-03","time":"16:00:00","week":5,"moon":"正月初七","dataUptime":1486110966,
     * "weather":{"temperature":"20","humidity":"57","info":"阴","img":"2"},"wind":{"direct":"东风","power":"1级",
     * "offset":null,"windspeed":null}},"life":{"date":"2017-2-3","info":{"chuanyi":["较舒适",
     * "建议着薄外套、开衫牛仔衫裤等服装。年老体弱者应适当添加衣物，宜着夹克衫、薄毛衣等。"],"ganmao":["较易发","天气较凉，较易发生感冒，请适当增加衣服。体质较弱的朋友尤其应该注意防护。"],
     * "kongtiao":["较少开启","您将感到很舒适，一般不需要开启空调。"],"xiche":["较适宜","较适宜洗车，未来一天无雨，风力较小，擦洗一新的汽车至少能保持一天。"],"yundong":["较适宜",
     * "阴天，较适宜进行各种户内外运动。"],"ziwaixian":["最弱","属弱紫外线辐射天气，无需特别防护。若长期在户外，建议涂擦SPF在8-12之间的防晒护肤品。"]}},
     * "weather":[{"date":"2017-02-03","info":{"day":["2","阴","20","","微风","07:01"],"night":["2","阴","16","","微风",
     * "18:13"]},"week":"五","nongli":"正月初七"},{"date":"2017-02-04","info":{"dawn":["2","阴","16","无持续风向","微风","18:13"],
     * "day":["7","小雨","18","","微风","07:01"],"night":["7","小雨","15","","微风","18:13"]},"week":"六","nongli":"正月初八"},
     * {"date":"2017-02-05","info":{"dawn":["7","小雨","15","无持续风向","微风","18:13"],"day":["2","阴","22","","微风","07:00"],
     * "night":["2","阴","16","","微风","18:14"]},"week":"日","nongli":"正月初九"},{"date":"2017-02-06","info":{"dawn":["2",
     * "阴","16","无持续风向","微风","18:14"],"day":["1","多云","21","","微风","07:00"],"night":["1","多云","14","","微风","18:15"]},
     * "week":"一","nongli":"正月初十 "},{"date":"2017-02-07","info":{"dawn":["1","多云","14","无持续风向","微风","18:15"],
     * "day":["1","多云","21","","微风","06:59"],"night":["1","多云","16","","微风","18:15"]},"week":"二","nongli":"正月十一"}],
     * "f3h":{"temperature":[{"jg":"20170203170000","jb":"20"},{"jg":"20170203200000","jb":"17"},
     * {"jg":"20170203230000","jb":"16"},{"jg":"20170204020000","jb":"16"},{"jg":"20170204050000","jb":"16"},
     * {"jg":"20170204080000","jb":"16"},{"jg":"20170204110000","jb":"17"},{"jg":"20170204140000","jb":"17"},
     * {"jg":"20170204170000","jb":"16"}],"precipitation":[{"jg":"20170203170000","jf":"0"},{"jg":"20170203200000",
     * "jf":"0"},{"jg":"20170203230000","jf":"0"},{"jg":"20170204020000","jf":"0"},{"jg":"20170204050000","jf":"0"},
     * {"jg":"20170204080000","jf":"0"},{"jg":"20170204110000","jf":"0"},{"jg":"20170204140000","jf":"0"},
     * {"jg":"20170204170000","jf":"0"}]},"pm25":{"key":"Shenzhen","show_desc":0,"pm25":{"curPm":"42","pm25":"28",
     * "pm10":"40","level":1,"quality":"优","des":"空气很好，可以外出活动"},"dateTime":"2017年02月03日16时","cityName":"深圳"},
     * "jingqu":"","jingqutq":"","date":"","isForeign":"0"}}
     * error_code : 0
     */

    private String reason;
    private ResultBean result;
    private int error_code;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public static class ResultBean {
        /**
         * data : {"pubdate":"2017-02-03","pubtime":"11:00:00","realtime":{"city_code":"101280601","city_name":"深圳",
         * "date":"2017-02-03","time":"16:00:00","week":5,"moon":"正月初七","dataUptime":1486110966,
         * "weather":{"temperature":"20","humidity":"57","info":"阴","img":"2"},"wind":{"direct":"东风","power":"1级",
         * "offset":null,"windspeed":null}},"life":{"date":"2017-2-3","info":{"chuanyi":["较舒适",
         * "建议着薄外套、开衫牛仔衫裤等服装。年老体弱者应适当添加衣物，宜着夹克衫、薄毛衣等。"],"ganmao":["较易发","天气较凉，较易发生感冒，请适当增加衣服。体质较弱的朋友尤其应该注意防护。"],
         * "kongtiao":["较少开启","您将感到很舒适，一般不需要开启空调。"],"xiche":["较适宜","较适宜洗车，未来一天无雨，风力较小，擦洗一新的汽车至少能保持一天。"],
         * "yundong":["较适宜","阴天，较适宜进行各种户内外运动。"],"ziwaixian":["最弱","属弱紫外线辐射天气，无需特别防护。若长期在户外，建议涂擦SPF在8-12之间的防晒护肤品。"]}},
         * "weather":[{"date":"2017-02-03","info":{"day":["2","阴","20","","微风","07:01"],"night":["2","阴","16","",
         * "微风","18:13"]},"week":"五","nongli":"正月初七"},{"date":"2017-02-04","info":{"dawn":["2","阴","16","无持续风向","微风",
         * "18:13"],"day":["7","小雨","18","","微风","07:01"],"night":["7","小雨","15","","微风","18:13"]},"week":"六",
         * "nongli":"正月初八"},{"date":"2017-02-05","info":{"dawn":["7","小雨","15","无持续风向","微风","18:13"],"day":["2","阴",
         * "22","","微风","07:00"],"night":["2","阴","16","","微风","18:14"]},"week":"日","nongli":"正月初九"},
         * {"date":"2017-02-06","info":{"dawn":["2","阴","16","无持续风向","微风","18:14"],"day":["1","多云","21","","微风",
         * "07:00"],"night":["1","多云","14","","微风","18:15"]},"week":"一","nongli":"正月初十 "},{"date":"2017-02-07",
         * "info":{"dawn":["1","多云","14","无持续风向","微风","18:15"],"day":["1","多云","21","","微风","06:59"],"night":["1",
         * "多云","16","","微风","18:15"]},"week":"二","nongli":"正月十一"}],"f3h":{"temperature":[{"jg":"20170203170000",
         * "jb":"20"},{"jg":"20170203200000","jb":"17"},{"jg":"20170203230000","jb":"16"},{"jg":"20170204020000",
         * "jb":"16"},{"jg":"20170204050000","jb":"16"},{"jg":"20170204080000","jb":"16"},{"jg":"20170204110000",
         * "jb":"17"},{"jg":"20170204140000","jb":"17"},{"jg":"20170204170000","jb":"16"}],
         * "precipitation":[{"jg":"20170203170000","jf":"0"},{"jg":"20170203200000","jf":"0"},{"jg":"20170203230000",
         * "jf":"0"},{"jg":"20170204020000","jf":"0"},{"jg":"20170204050000","jf":"0"},{"jg":"20170204080000",
         * "jf":"0"},{"jg":"20170204110000","jf":"0"},{"jg":"20170204140000","jf":"0"},{"jg":"20170204170000",
         * "jf":"0"}]},"pm25":{"key":"Shenzhen","show_desc":0,"pm25":{"curPm":"42","pm25":"28","pm10":"40","level":1,
         * "quality":"优","des":"空气很好，可以外出活动"},"dateTime":"2017年02月03日16时","cityName":"深圳"},"jingqu":"","jingqutq":"",
         * "date":"","isForeign":"0"}
         */

        private DataBean data;

        public DataBean getData() {
            return data;
        }

        public void setData(DataBean data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * pubdate : 2017-02-03
             * pubtime : 11:00:00
             * realtime : {"city_code":"101280601","city_name":"深圳","date":"2017-02-03","time":"16:00:00","week":5,
             * "moon":"正月初七","dataUptime":1486110966,"weather":{"temperature":"20","humidity":"57","info":"阴",
             * "img":"2"},"wind":{"direct":"东风","power":"1级","offset":null,"windspeed":null}}
             * life : {"date":"2017-2-3","info":{"chuanyi":["较舒适","建议着薄外套、开衫牛仔衫裤等服装。年老体弱者应适当添加衣物，宜着夹克衫、薄毛衣等。"],
             * "ganmao":["较易发","天气较凉，较易发生感冒，请适当增加衣服。体质较弱的朋友尤其应该注意防护。"],"kongtiao":["较少开启","您将感到很舒适，一般不需要开启空调。"],
             * "xiche":["较适宜","较适宜洗车，未来一天无雨，风力较小，擦洗一新的汽车至少能保持一天。"],"yundong":["较适宜","阴天，较适宜进行各种户内外运动。"],
             * "ziwaixian":["最弱","属弱紫外线辐射天气，无需特别防护。若长期在户外，建议涂擦SPF在8-12之间的防晒护肤品。"]}}
             * weather : [{"date":"2017-02-03","info":{"day":["2","阴","20","","微风","07:01"],"night":["2","阴","16","",
             * "微风","18:13"]},"week":"五","nongli":"正月初七"},{"date":"2017-02-04","info":{"dawn":["2","阴","16","无持续风向",
             * "微风","18:13"],"day":["7","小雨","18","","微风","07:01"],"night":["7","小雨","15","","微风","18:13"]},
             * "week":"六","nongli":"正月初八"},{"date":"2017-02-05","info":{"dawn":["7","小雨","15","无持续风向","微风","18:13"],
             * "day":["2","阴","22","","微风","07:00"],"night":["2","阴","16","","微风","18:14"]},"week":"日",
             * "nongli":"正月初九"},{"date":"2017-02-06","info":{"dawn":["2","阴","16","无持续风向","微风","18:14"],"day":["1",
             * "多云","21","","微风","07:00"],"night":["1","多云","14","","微风","18:15"]},"week":"一","nongli":"正月初十 "},
             * {"date":"2017-02-07","info":{"dawn":["1","多云","14","无持续风向","微风","18:15"],"day":["1","多云","21","","微风",
             * "06:59"],"night":["1","多云","16","","微风","18:15"]},"week":"二","nongli":"正月十一"}]
             * f3h : {"temperature":[{"jg":"20170203170000","jb":"20"},{"jg":"20170203200000","jb":"17"},
             * {"jg":"20170203230000","jb":"16"},{"jg":"20170204020000","jb":"16"},{"jg":"20170204050000","jb":"16"},
             * {"jg":"20170204080000","jb":"16"},{"jg":"20170204110000","jb":"17"},{"jg":"20170204140000","jb":"17"},
             * {"jg":"20170204170000","jb":"16"}],"precipitation":[{"jg":"20170203170000","jf":"0"},
             * {"jg":"20170203200000","jf":"0"},{"jg":"20170203230000","jf":"0"},{"jg":"20170204020000","jf":"0"},
             * {"jg":"20170204050000","jf":"0"},{"jg":"20170204080000","jf":"0"},{"jg":"20170204110000","jf":"0"},
             * {"jg":"20170204140000","jf":"0"},{"jg":"20170204170000","jf":"0"}]}
             * pm25 : {"key":"Shenzhen","show_desc":0,"pm25":{"curPm":"42","pm25":"28","pm10":"40","level":1,
             * "quality":"优","des":"空气很好，可以外出活动"},"dateTime":"2017年02月03日16时","cityName":"深圳"}
             * jingqu :
             * jingqutq :
             * date :
             * isForeign : 0
             */

            private String pubdate;
            private String pubtime;
            private RealtimeBean realtime;
            private LifeBean life;
            private F3hBean f3h;
            private Pm25BeanX pm25;
            private String jingqu;
            private String jingqutq;
            private String date;
            private String isForeign;
            private List<WeatherBeanX> weather;

            public String getPubdate() {
                return pubdate;
            }

            public void setPubdate(String pubdate) {
                this.pubdate = pubdate;
            }

            public String getPubtime() {
                return pubtime;
            }

            public void setPubtime(String pubtime) {
                this.pubtime = pubtime;
            }

            public RealtimeBean getRealtime() {
                return realtime;
            }

            public void setRealtime(RealtimeBean realtime) {
                this.realtime = realtime;
            }

            public LifeBean getLife() {
                return life;
            }

            public void setLife(LifeBean life) {
                this.life = life;
            }

            public F3hBean getF3h() {
                return f3h;
            }

            public void setF3h(F3hBean f3h) {
                this.f3h = f3h;
            }

            public Pm25BeanX getPm25() {
                return pm25;
            }

            public void setPm25(Pm25BeanX pm25) {
                this.pm25 = pm25;
            }

            public String getJingqu() {
                return jingqu;
            }

            public void setJingqu(String jingqu) {
                this.jingqu = jingqu;
            }

            public String getJingqutq() {
                return jingqutq;
            }

            public void setJingqutq(String jingqutq) {
                this.jingqutq = jingqutq;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getIsForeign() {
                return isForeign;
            }

            public void setIsForeign(String isForeign) {
                this.isForeign = isForeign;
            }

            public List<WeatherBeanX> getWeather() {
                return weather;
            }

            public void setWeather(List<WeatherBeanX> weather) {
                this.weather = weather;
            }

            public static class RealtimeBean {
                /**
                 * city_code : 101280601
                 * city_name : 深圳
                 * date : 2017-02-03
                 * time : 16:00:00
                 * week : 5
                 * moon : 正月初七
                 * dataUptime : 1486110966
                 * weather : {"temperature":"20","humidity":"57","info":"阴","img":"2"}
                 * wind : {"direct":"东风","power":"1级","offset":null,"windspeed":null}
                 */

                private String city_code;
                private String city_name;
                private String date;
                private String time;
                private int week;
                private String moon;
                private int dataUptime;
                private WeatherBean weather;
                private WindBean wind;

                public String getCity_code() {
                    return city_code;
                }

                public void setCity_code(String city_code) {
                    this.city_code = city_code;
                }

                public String getCity_name() {
                    return city_name;
                }

                public void setCity_name(String city_name) {
                    this.city_name = city_name;
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

                public int getWeek() {
                    return week;
                }

                public void setWeek(int week) {
                    this.week = week;
                }

                public String getMoon() {
                    return moon;
                }

                public void setMoon(String moon) {
                    this.moon = moon;
                }

                public int getDataUptime() {
                    return dataUptime;
                }

                public void setDataUptime(int dataUptime) {
                    this.dataUptime = dataUptime;
                }

                public WeatherBean getWeather() {
                    return weather;
                }

                public void setWeather(WeatherBean weather) {
                    this.weather = weather;
                }

                public WindBean getWind() {
                    return wind;
                }

                public void setWind(WindBean wind) {
                    this.wind = wind;
                }

                public static class WeatherBean {
                    /**
                     * temperature : 20
                     * humidity : 57
                     * info : 阴
                     * img : 2
                     */

                    private String temperature;
                    private String humidity;
                    private String info;
                    private String img;

                    public String getTemperature() {
                        return temperature;
                    }

                    public void setTemperature(String temperature) {
                        this.temperature = temperature;
                    }

                    public String getHumidity() {
                        return humidity;
                    }

                    public void setHumidity(String humidity) {
                        this.humidity = humidity;
                    }

                    public String getInfo() {
                        return info;
                    }

                    public void setInfo(String info) {
                        this.info = info;
                    }

                    public String getImg() {
                        return img;
                    }

                    public void setImg(String img) {
                        this.img = img;
                    }
                }

                public static class WindBean {
                    /**
                     * direct : 东风
                     * power : 1级
                     * offset : null
                     * windspeed : null
                     */

                    private String direct;
                    private String power;
                    private Object offset;
                    private Object windspeed;

                    public String getDirect() {
                        return direct;
                    }

                    public void setDirect(String direct) {
                        this.direct = direct;
                    }

                    public String getPower() {
                        return power;
                    }

                    public void setPower(String power) {
                        this.power = power;
                    }

                    public Object getOffset() {
                        return offset;
                    }

                    public void setOffset(Object offset) {
                        this.offset = offset;
                    }

                    public Object getWindspeed() {
                        return windspeed;
                    }

                    public void setWindspeed(Object windspeed) {
                        this.windspeed = windspeed;
                    }
                }
            }

            public static class LifeBean {
                /**
                 * date : 2017-2-3
                 * info : {"chuanyi":["较舒适","建议着薄外套、开衫牛仔衫裤等服装。年老体弱者应适当添加衣物，宜着夹克衫、薄毛衣等。"],"ganmao":["较易发",
                 * "天气较凉，较易发生感冒，请适当增加衣服。体质较弱的朋友尤其应该注意防护。"],"kongtiao":["较少开启","您将感到很舒适，一般不需要开启空调。"],"xiche":["较适宜",
                 * "较适宜洗车，未来一天无雨，风力较小，擦洗一新的汽车至少能保持一天。"],"yundong":["较适宜","阴天，较适宜进行各种户内外运动。"],"ziwaixian":["最弱",
                 * "属弱紫外线辐射天气，无需特别防护。若长期在户外，建议涂擦SPF在8-12之间的防晒护肤品。"]}
                 */

                private String date;
                private InfoBean info;

                public String getDate() {
                    return date;
                }

                public void setDate(String date) {
                    this.date = date;
                }

                public InfoBean getInfo() {
                    return info;
                }

                public void setInfo(InfoBean info) {
                    this.info = info;
                }

                public static class InfoBean {
                    private List<String> chuanyi;
                    private List<String> ganmao;
                    private List<String> kongtiao;
                    private List<String> xiche;
                    private List<String> yundong;
                    private List<String> ziwaixian;

                    public List<String> getChuanyi() {
                        return chuanyi;
                    }

                    public void setChuanyi(List<String> chuanyi) {
                        this.chuanyi = chuanyi;
                    }

                    public List<String> getGanmao() {
                        return ganmao;
                    }

                    public void setGanmao(List<String> ganmao) {
                        this.ganmao = ganmao;
                    }

                    public List<String> getKongtiao() {
                        return kongtiao;
                    }

                    public void setKongtiao(List<String> kongtiao) {
                        this.kongtiao = kongtiao;
                    }

                    public List<String> getXiche() {
                        return xiche;
                    }

                    public void setXiche(List<String> xiche) {
                        this.xiche = xiche;
                    }

                    public List<String> getYundong() {
                        return yundong;
                    }

                    public void setYundong(List<String> yundong) {
                        this.yundong = yundong;
                    }

                    public List<String> getZiwaixian() {
                        return ziwaixian;
                    }

                    public void setZiwaixian(List<String> ziwaixian) {
                        this.ziwaixian = ziwaixian;
                    }
                }
            }

            public static class F3hBean {
                private List<TemperatureBean> temperature;
                private List<PrecipitationBean> precipitation;

                public List<TemperatureBean> getTemperature() {
                    return temperature;
                }

                public void setTemperature(List<TemperatureBean> temperature) {
                    this.temperature = temperature;
                }

                public List<PrecipitationBean> getPrecipitation() {
                    return precipitation;
                }

                public void setPrecipitation(List<PrecipitationBean> precipitation) {
                    this.precipitation = precipitation;
                }

                public static class TemperatureBean {
                    /**
                     * jg : 20170203170000
                     * jb : 20
                     */

                    private String jg;
                    private String jb;

                    public String getJg() {
                        return jg;
                    }

                    public void setJg(String jg) {
                        this.jg = jg;
                    }

                    public String getJb() {
                        return jb;
                    }

                    public void setJb(String jb) {
                        this.jb = jb;
                    }
                }

                public static class PrecipitationBean {
                    /**
                     * jg : 20170203170000
                     * jf : 0
                     */

                    private String jg;
                    private String jf;

                    public String getJg() {
                        return jg;
                    }

                    public void setJg(String jg) {
                        this.jg = jg;
                    }

                    public String getJf() {
                        return jf;
                    }

                    public void setJf(String jf) {
                        this.jf = jf;
                    }
                }
            }

            public static class Pm25BeanX {
                /**
                 * key : Shenzhen
                 * show_desc : 0
                 * pm25 : {"curPm":"42","pm25":"28","pm10":"40","level":1,"quality":"优","des":"空气很好，可以外出活动"}
                 * dateTime : 2017年02月03日16时
                 * cityName : 深圳
                 */

                private String key;
                private int show_desc;
                private Pm25Bean pm25;
                private String dateTime;
                private String cityName;

                public String getKey() {
                    return key;
                }

                public void setKey(String key) {
                    this.key = key;
                }

                public int getShow_desc() {
                    return show_desc;
                }

                public void setShow_desc(int show_desc) {
                    this.show_desc = show_desc;
                }

                public Pm25Bean getPm25() {
                    return pm25;
                }

                public void setPm25(Pm25Bean pm25) {
                    this.pm25 = pm25;
                }

                public String getDateTime() {
                    return dateTime;
                }

                public void setDateTime(String dateTime) {
                    this.dateTime = dateTime;
                }

                public String getCityName() {
                    return cityName;
                }

                public void setCityName(String cityName) {
                    this.cityName = cityName;
                }

                public static class Pm25Bean {
                    /**
                     * curPm : 42
                     * pm25 : 28
                     * pm10 : 40
                     * level : 1
                     * quality : 优
                     * des : 空气很好，可以外出活动
                     */

                    private String curPm;
                    private String pm25;
                    private String pm10;
                    private int level;
                    private String quality;
                    private String des;

                    public String getCurPm() {
                        return curPm;
                    }

                    public void setCurPm(String curPm) {
                        this.curPm = curPm;
                    }

                    public String getPm25() {
                        return pm25;
                    }

                    public void setPm25(String pm25) {
                        this.pm25 = pm25;
                    }

                    public String getPm10() {
                        return pm10;
                    }

                    public void setPm10(String pm10) {
                        this.pm10 = pm10;
                    }

                    public int getLevel() {
                        return level;
                    }

                    public void setLevel(int level) {
                        this.level = level;
                    }

                    public String getQuality() {
                        return quality;
                    }

                    public void setQuality(String quality) {
                        this.quality = quality;
                    }

                    public String getDes() {
                        return des;
                    }

                    public void setDes(String des) {
                        this.des = des;
                    }
                }
            }

            public static class WeatherBeanX {
                /**
                 * date : 2017-02-03
                 * info : {"day":["2","阴","20","","微风","07:01"],"night":["2","阴","16","","微风","18:13"]}
                 * week : 五
                 * nongli : 正月初七
                 */

                private String date;
                private InfoBeanX info;
                private String week;
                private String nongli;

                public String getDate() {
                    return date;
                }

                public void setDate(String date) {
                    this.date = date;
                }

                public InfoBeanX getInfo() {
                    return info;
                }

                public void setInfo(InfoBeanX info) {
                    this.info = info;
                }

                public String getWeek() {
                    return week;
                }

                public void setWeek(String week) {
                    this.week = week;
                }

                public String getNongli() {
                    return nongli;
                }

                public void setNongli(String nongli) {
                    this.nongli = nongli;
                }

                public static class InfoBeanX {
                    private List<String> day;
                    private List<String> night;

                    public List<String> getDay() {
                        return day;
                    }

                    public void setDay(List<String> day) {
                        this.day = day;
                    }

                    public List<String> getNight() {
                        return night;
                    }

                    public void setNight(List<String> night) {
                        this.night = night;
                    }
                }
            }
        }
    }
}
