package com.yulong.jiangyu.geekweather.dao;
/*
 *   author RichardJay
 *    email jiangfengfn12@163.com
 *  created 2017/6/20 11:37
 *  version v1.0
 * modified 2017/6/20
 *     note xxx
 */

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.yulong.jiangyu.geekweather.R;
import com.yulong.jiangyu.geekweather.bean.CityInfo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CityInfoDao {
    private Context mContext;
    private Dao<CityInfo, Integer> cityInfoDao;
    private CityInfoDBHelper helper;

    /**
     * 有参构造函数
     *
     * @param context
     */
    public CityInfoDao(Context context) {
        this.mContext = context;
        helper = CityInfoDBHelper.getInstance(mContext);
        try {
            cityInfoDao = helper.getDao(CityInfo.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建数据库中的表
     */
    public void creatTable() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 从资源文件中加载数据
                String[] cities = mContext.getResources().getStringArray(R.array.city_china);
                String[] weatherCodes = mContext.getResources().getStringArray(R.array.city_china_weather_code);
                String[] citiesFullPinYin = mContext.getResources().getStringArray(R.array.city_china_pinyin);
                String[] citiesFullEN = mContext.getResources().getStringArray(R.array.city_china_en);

//                List<CityInfo> cityInfoList = new ArrayList<>();
                List<CityInfo> cityInfoList = new ArrayList<>();
                for (int i = 0, length = cities.length; i < length; i++) {
                    // 解析城市名和省份名
//                    String[] cityFullName = cities[i].split("-");
//                    String cityName = cityFullName[0].trim();
//                    String province = cityFullName[1].trim();

                    // 解析省份名和城市名的拼音
//                    String citiesPinYin[] = citiesFullPinYin[i].split("-");
//                    String provincePinYin = citiesPinYin[0].trim();
//                    String cityPinYin = citiesPinYin[1].trim();

                    // 解析省份名和城市名的英语缩写
//                    String[] cityFullEn = citiesFullEN[i].split("-");
//                    String provinceEn = cityFullEn[0].trim();
//                    String cityEn = cityFullEn[1].trim();

                    // 解析天气代码
//                    String weatherCode = weatherCodes[i].trim();

//                    CityInfo cityInfo = new CityInfo(cityName, province, weatherCode, cityPinYin, provincePinYin,
//                            cityEn, provinceEn);
                    CityInfo cityInfo = new CityInfo(cities[i], weatherCodes[i], citiesFullPinYin[i],
                            citiesFullEN[i], 0);
                    cityInfoList.add(cityInfo);
//                    cityInfoList.add(cityInfo);
                }

                try {
                    // 创建数据库表
                    cityInfoDao.create(cityInfoList);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public List<CityInfo> queryCity(String search, boolean fuzzy) {

        ArrayList<CityInfo> cityInfoList = null;
        ArrayList<CityInfo> CityInfoList1;
        QueryBuilder queryBuilder = cityInfoDao.queryBuilder();
        // 打开数据库
//        helper.getReadableDatabase();

        try {
            String beginSqlStr = new StringBuilder(search).append("%").toString();
            queryBuilder.where().like("city", beginSqlStr).or().like("cityPinYin", beginSqlStr).or().like("cityEn",
                    beginSqlStr);
            cityInfoList = (ArrayList<CityInfo>) queryBuilder.query();
            if (cityInfoList != null) {// 头部匹配
                for (CityInfo cityInfo : cityInfoList) {
                    cityInfo.setMatchType(1);
                }
            }

            if (fuzzy) {
                String endSqlStr = new StringBuilder("%").append(search).toString();
                queryBuilder.where().like("city", endSqlStr).or().like("cityPinYin", endSqlStr).or().like("cityEn",
                        endSqlStr);
                CityInfoList1 = (ArrayList<CityInfo>) queryBuilder.query();
                if (CityInfoList1 != null) { // 尾部匹配
                    for (CityInfo cityInfo : CityInfoList1) {
                        cityInfo.setMatchType(2);
                    }
                }
                // 将两部分的查找结果组合
                cityInfoList.addAll(CityInfoList1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

//        helper.close();
        return cityInfoList;
    }


    public long queryCount() {
        long count = 0;
        if (helper.isOpen()) {
            try {
                count = cityInfoDao.queryBuilder().countOf();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return count;
    }

    /**
     * 使用完后关闭数据库
     */
//    public void close() {
//        if (helper.isOpen())
//            helper.close();
//    }

}
