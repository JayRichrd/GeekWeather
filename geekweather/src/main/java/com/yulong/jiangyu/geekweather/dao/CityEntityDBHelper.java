package com.yulong.jiangyu.geekweather.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.yulong.jiangyu.geekweather.constant.Constant;
import com.yulong.jiangyu.geekweather.entity.CityEntity;

import java.sql.SQLException;

/**
 * author RichardJay
 * email jiangfengfn12@163.com
 * created 2017/6/20 10:42
 * version v1.0
 * modified 2017/6/20
 * note
 */

public class CityEntityDBHelper extends OrmLiteSqliteOpenHelper {

    private static CityEntityDBHelper instance = null;

    private CityEntityDBHelper(Context context) {
        super(context, Constant.CITY_INFO_DB, null, Constant.TABLE_VERSION);
    }

    /**
     * 单例模式获取类实例
     *
     * @param context
     * @return
     */
    public static synchronized CityEntityDBHelper getInstance(Context context) {
        if (null == instance) {
            synchronized (CityEntityDBHelper.class) {
                instance = new CityEntityDBHelper(context);
            }
        }
        return instance;
    }

    /**
     * 创建数据库中的表
     *
     * @param database
     * @param connectionSource
     */
    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTableIfNotExists(connectionSource, CityEntity.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新数据库
     *
     * @param database
     * @param connectionSource
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, CityEntity.class, true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}