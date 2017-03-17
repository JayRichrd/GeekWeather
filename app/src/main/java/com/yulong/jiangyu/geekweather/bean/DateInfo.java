package com.yulong.jiangyu.geekweather.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by jiangyu on 2017/1/26.
 */

public class DateInfo implements Serializable {
    private static final long serialVersionUID = -642688239048492271L;
    /**
     * reason : Success
     * result : {"data":{"avoid":"破土.动土.安葬.","animalsYear":"猴","weekday":"星期四","suit":"祭祀.解除.教牛马.会亲友.余事勿取.",
     * "lunarYear":"丙申年","lunar":"腊月廿九","year-month":"2017-1","date":"2017-1-26"}}
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
         * data : {"avoid":"破土.动土.安葬.","animalsYear":"猴","weekday":"星期四","suit":"祭祀.解除.教牛马.会亲友.余事勿取.",
         * "lunarYear":"丙申年","lunar":"腊月廿九","year-month":"2017-1","date":"2017-1-26"}
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
             * avoid : 破土.动土.安葬.
             * animalsYear : 猴
             * weekday : 星期四
             * suit : 祭祀.解除.教牛马.会亲友.余事勿取.
             * lunarYear : 丙申年
             * lunar : 腊月廿九
             * year-month : 2017-1
             * date : 2017-1-26
             */

            private String avoid;
            private String animalsYear;
            private String weekday;
            private String suit;
            private String lunarYear;
            private String lunar;
            @SerializedName("year-month")
            private String yearmonth;
            private String date;

            public String getAvoid() {
                return avoid;
            }

            public void setAvoid(String avoid) {
                this.avoid = avoid;
            }

            public String getAnimalsYear() {
                return animalsYear;
            }

            public void setAnimalsYear(String animalsYear) {
                this.animalsYear = animalsYear;
            }

            public String getWeekday() {
                return weekday;
            }

            public void setWeekday(String weekday) {
                this.weekday = weekday;
            }

            public String getSuit() {
                return suit;
            }

            public void setSuit(String suit) {
                this.suit = suit;
            }

            public String getLunarYear() {
                return lunarYear;
            }

            public void setLunarYear(String lunarYear) {
                this.lunarYear = lunarYear;
            }

            public String getLunar() {
                return lunar;
            }

            public void setLunar(String lunar) {
                this.lunar = lunar;
            }

            public String getYearmonth() {
                return yearmonth;
            }

            public void setYearmonth(String yearmonth) {
                this.yearmonth = yearmonth;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }
        }
    }
}
