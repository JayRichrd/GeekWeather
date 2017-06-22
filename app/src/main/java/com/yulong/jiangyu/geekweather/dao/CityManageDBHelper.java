package com.yulong.jiangyu.geekweather.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.yulong.jiangyu.geekweather.bean.CityManage;
import com.yulong.jiangyu.geekweather.constant.Constant;

import java.sql.SQLException;

/**
 * author RichardJay
 * email jiangfengfn12@163.com
 * created 2017/5/10 15:42
 * version v1.0
 * modified 2017/5/10
 * note xxx
 **/

public class CityManageDBHelper extends OrmLiteSqliteOpenHelper {
    private static CityManageDBHelper instance = null;

    private CityManageDBHelper(Context context) {
        super(context, Constant.CITY_MANAGE_DB, null, Constant.TABLE_VERSION);
    }

    /**
     * return the instance in singleton pattern
     *
     * @param context
     * @return instance initialized
     */
    public static synchronized CityManageDBHelper getInstance(Context context) {
        if (null == instance) {
            synchronized (CityManageDBHelper.class) {
                if (null == instance)
                    instance = new CityManageDBHelper(context);
            }
        }
        return instance;
    }

    /**
     * What to do when your database needs to be created. Usually this entails creating the tables and loading any
     * initial data.
     * <p>
     * <p>
     * <b>NOTE:</b> You should use the connectionSource argument that is passed into this method call or the one
     * returned by getConnectionSource(). If you use your own, a recursive call or other unexpected results may result.
     * </p>
     *
     * @param database         Database being created.
     * @param connectionSource
     */
    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTableIfNotExists(connectionSource, CityManage.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * What to do when your database needs to be updated. This could mean careful migration of old data to new data.
     * Maybe adding or deleting database columns, etc..
     * <p>
     * <p>
     * <b>NOTE:</b> You should use the connectionSource argument that is passed into this method call or the one
     * returned by getConnectionSource(). If you use your own, a recursive call or other unexpected results may result.
     * </p>
     *
     * @param database         Database being upgraded.
     * @param connectionSource To use get connections to the database to be updated.
     * @param oldVersion       The version of the current database so we can know what to do to the database.
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, CityManage.class, true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Close any open connections.
     */
    @Override
    public void close() {
        super.close();
    }
}
