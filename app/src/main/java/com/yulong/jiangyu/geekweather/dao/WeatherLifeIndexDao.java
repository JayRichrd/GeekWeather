package com.yulong.jiangyu.geekweather.dao;
/*
 *   author RichardJay
 *    email jiangfengfn12@163.com
 *  created 2017/6/21 20:41
 *  version v1.0
 * modified 2017/6/21
 *     note 生活指数的数据库操作
 */

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.yulong.jiangyu.geekweather.bean.WeatherLifeIndex;

import java.sql.SQLException;
import java.util.List;

public class WeatherLifeIndexDao {
    WeatherLifeIndexDBHelper helper;
    Dao<WeatherLifeIndex, Integer> mDao;
    private Context mContext;

    public WeatherLifeIndexDao(Context context) {
        this.mContext = context;
        helper = WeatherLifeIndexDBHelper.getInstance(context);
        try {
            mDao = helper.getDao(WeatherLifeIndex.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 向数据库中插入数据
     *
     * @param weatherLifeIndexes 将被插入数据库中的对象
     * @return 如果成功则返回true，否则返回false
     */
    public boolean insert(List<WeatherLifeIndex> weatherLifeIndexes) {
        boolean result = false;
        try {
            // 表格中本来就有数据，先删除数据
            if (mDao.countOf() >= 1)
                mDao.deleteBuilder().delete();
            // 插入数据
            if (1 == mDao.create(weatherLifeIndexes))
                result = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 根据生活指数名查询记录
     *
     * @param indexName 生活指数名
     * @return
     */
    public List<WeatherLifeIndex> query(String indexName) {
        List<WeatherLifeIndex> weatherLifeIndexList = null;
        try {
            QueryBuilder queryBuilder = mDao.queryBuilder();
            queryBuilder.where().eq("mIndexName", indexName);
            weatherLifeIndexList = queryBuilder.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return weatherLifeIndexList;
    }
}
