package com.yulong.jiangyu.geekweather.dao;
/**
 * author RichardJay
 * email jiangfengfn12@163.com
 * created 2017/6/21 14:44
 * version v1.0
 * modified 2017/6/21
 * note 城市管理的数据库操作
 */

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;
import com.yulong.jiangyu.geekweather.entity.CityManageEntity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CityMangeEntityDao {
    private Dao<CityManageEntity, Integer> mDao;

    public CityMangeEntityDao(Context mContext) {
        CityManageEntityDBHelper helper = CityManageEntityDBHelper.getInstance(mContext);
        try {
            mDao = helper.getDao(CityManageEntity.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 向数据库中插入数据
     *
     * @param cityManage 将被插入数据库中的对象
     * @return 如果成功则返回true，否则返回false
     */
    public boolean insert(CityManageEntity cityManage) {
        boolean result = false;
        try {
            if (1 == mDao.create(cityManage))
                result = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 查询全部数据
     *
     * @return 返回数据库表中的全部数据
     */
    public List<CityManageEntity> queryAll() {
        ArrayList<CityManageEntity> cityManageList = null;
        try {
            cityManageList = (ArrayList<CityManageEntity>) mDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cityManageList;
    }

    /**
     * 查询城市是否已经存在
     *
     * @param cityName 城市名
     * @return
     */
    public boolean query(String cityName) {
        boolean result = false;
        try {
            QueryBuilder queryBuilder = mDao.queryBuilder();
            queryBuilder.where().eq("cityName", cityName);
            if (!queryBuilder.query().isEmpty())
                result = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 查询城市是否已经存在
     *
     * @param weatherCode 城市的天气代号
     * @return
     */
    public boolean query(int weatherCode) {
        boolean result = false;
        try {
            QueryBuilder queryBuilder = mDao.queryBuilder();
            queryBuilder.where().eq("weatherCode", weatherCode + "");
            if (!queryBuilder.query().isEmpty())
                result = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 根据城市名删除记录
     *
     * @param cityName 城市名
     * @return
     */
    public boolean delete(String cityName) {
        boolean result = false;
        try {
            DeleteBuilder deleteBuilder = mDao.deleteBuilder();
            deleteBuilder.where().eq("cityName", cityName);
            if (deleteBuilder.delete() >= 1)
                result = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 先删除再重新插入
     *
     * @param cityManage
     * @return
     */
    public boolean update(CityManageEntity cityManage) {
        boolean result = false;
        try {
            DeleteBuilder deleteBuilder = mDao.deleteBuilder();
            deleteBuilder.where().eq("cityName", cityManage.getCityName());
            if (deleteBuilder.delete() >= 1) {
                result = insert(cityManage);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
