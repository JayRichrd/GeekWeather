package com.yulong.jiangyu.geekweather.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.yulong.jiangyu.geekweather.bean.WeatherUIInfo;
import com.yulong.jiangyu.geekweather.constant.Constant;

import java.sql.SQLException;

/**
 * Created by jiangyu on 2017/2/3.
 */

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    private static DatabaseHelper instance = null;
    private Dao<WeatherUIInfo, Integer> dao = null;

    //私有构造函数
    private DatabaseHelper(Context context) {
        super(context, Constant.TABLE_NAME, null, Constant.TABLE_VERSION);
    }

    //单例模式创建数据库的实例
    public static synchronized DatabaseHelper getInstance(Context context) {
        if (instance == null) {
            synchronized (DatabaseHelper.class) {
                if (instance == null)
                    instance = new DatabaseHelper(context);
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
            TableUtils.createTable(connectionSource, WeatherUIInfo.class);
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
        try {//先删除，再重新建立
            TableUtils.dropTable(connectionSource, WeatherUIInfo.class, true);
            onCreate(database, connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //操作数据库的类Dao
    public Dao<WeatherUIInfo, Integer> getDao() throws SQLException {
        if (dao == null)
            dao = getDao(WeatherUIInfo.class);
        return dao;
    }

    /**
     * Close any open connections.
     */
    @Override
    public void close() {
        super.close();
        dao = null;
    }
}
