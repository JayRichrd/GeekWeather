package com.yulong.jiangyu.geekweather.dao;
/**
 * author RichardJay
 * email jiangfengfn12@163.com
 * created 2017/6/20 11:37
 * version v1.0
 * modified 2017/6/20
 * note 操作城市实体数据库
 */

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.yulong.jiangyu.geekweather.R;
import com.yulong.jiangyu.geekweather.entity.CityEntity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CityEntityDao {
    private final Context mContext;
    private final CityEntityDBHelper helper;
    private Dao<CityEntity, Integer> cityEntityDao;

    /**
     * 有参构造函数
     *
     * @param context
     */
    public CityEntityDao(Context context) {
        this.mContext = context;
        helper = CityEntityDBHelper.getInstance(mContext);
        try {
            cityEntityDao = helper.getDao(CityEntity.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建数据库中的表
     */
    public void createTable() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 从资源文件中加载数据
                String[] cities = mContext.getResources().getStringArray(R.array.city_china);
                String[] weatherCodes = mContext.getResources().getStringArray(R.array.city_china_weather_code);
                String[] citiesFullPinYin = mContext.getResources().getStringArray(R.array.city_china_pinyin);
                String[] citiesFullEN = mContext.getResources().getStringArray(R.array.city_china_en);

                List<CityEntity> cityEntityList = new ArrayList<>();
                for (int i = 0, length = cities.length; i < length; i++) {
                    CityEntity cityEntity = new CityEntity(cities[i], weatherCodes[i], citiesFullPinYin[i],
                            citiesFullEN[i], 0);
                    cityEntityList.add(cityEntity);
                }

                try {
                    // 创建数据库表
                    cityEntityDao.create(cityEntityList);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public List<CityEntity> queryCity(String search, boolean fuzzy) {

        ArrayList<CityEntity> cityEntityList = null;
        ArrayList<CityEntity> cityEntityList1;
        QueryBuilder queryBuilder = cityEntityDao.queryBuilder();

        try {
            String beginSqlStr = search + "%";
            queryBuilder.where().like("city", beginSqlStr).or().like("cityPinYin", beginSqlStr).or().like("cityEn",
                    beginSqlStr);
            cityEntityList = (ArrayList<CityEntity>) queryBuilder.query();
            if (cityEntityList != null) {// 头部匹配
                for (CityEntity cityInfo : cityEntityList) {
                    cityInfo.setMatchType(1);
                }
            }

            if (fuzzy) {
                String endSqlStr = "%" + search;
                queryBuilder.where().like("city", endSqlStr).or().like("cityPinYin", endSqlStr).or().like("cityEn",
                        endSqlStr);
                cityEntityList1 = (ArrayList<CityEntity>) queryBuilder.query();
                if (cityEntityList1 != null) { // 尾部匹配
                    for (CityEntity cityEntity : cityEntityList1) {
                        cityEntity.setMatchType(2);
                    }
                }
                // 将两部分的查找结果组合
                assert cityEntityList != null;
                cityEntityList.addAll(cityEntityList1 != null ? cityEntityList1 : null);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cityEntityList;
    }

    /**
     * 获取数据库中记录的数量
     *
     * @return 返回数据库记录数
     */
    public long queryCount() {
        long count = 0;
        if (helper.isOpen()) {
            try {
                count = cityEntityDao.queryBuilder().countOf();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return count;
    }

}
