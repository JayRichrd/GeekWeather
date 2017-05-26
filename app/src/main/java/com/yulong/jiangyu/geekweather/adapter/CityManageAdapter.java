package com.yulong.jiangyu.geekweather.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.j256.ormlite.dao.Dao;
import com.yulong.jiangyu.geekweather.R;
import com.yulong.jiangyu.geekweather.bean.CityManage;
import com.yulong.jiangyu.geekweather.constant.Constant;
import com.yulong.jiangyu.geekweather.dao.CityManageDBHelper;
import com.yulong.jiangyu.geekweather.impl.CityManageDBListener;
import com.yulong.jiangyu.geekweather.util.WeatherInfoUtil;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * author RichardJay
 * email jiangfengfn12@163.com
 * created 2017/5/4 20:36
 * version v1.0
 * modified 2017/5/4
 * note Adapter of CityManage instance
 **/

public class CityManageAdapter extends ArrayAdapter<CityManage> {
    private Context mContext;
    private List<CityManage> mCityList;
    private String mDefaultCity;
    private boolean mIsVisibleForDeleteBtn = false;
    private CityManageDBHelper mCityManageDBHelper;
    private Dao<CityManage, Integer> mCityManageDBDao;
    private CityManageDBListener mCityManageDBListener;

    /**
     * Constructor
     *
     * @param context  The current context.
     * @param resource The resource ID for a layout file containing a TextView to use when
     *                 instantiating views.
     * @param cityList The objects to represent in the ListView.
     */
    public CityManageAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<CityManage> cityList) {
        super(context, resource, cityList);
        mContext = context;
        mCityList = cityList;

        // get default city
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(Constant.WEATHER_SHARE_PREFERENCE,
                Context.MODE_PRIVATE);
        mDefaultCity = sharedPreferences.getString(Constant.DEFAULT_CITY, "");
    }

    /**
     * set visibility of delete button
     *
     * @param visible visibility of delete button
     */
    public void setVisibleForDeleteBtn(boolean visible) {
        mIsVisibleForDeleteBtn = visible;
    }

    /**
     * set listener
     *
     * @param cityManageDBListener
     */
    public void setCityManageDBListener(CityManageDBListener cityManageDBListener) {
        mCityManageDBListener = cityManageDBListener;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final CityManage cityManage = getItem(position);
        final String cityName = cityManage.getCityName();
        ViewHolder viewHolder;
        if (null == convertView) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_city_manage, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //city list is empty
        if (1 == mCityList.size()) {
            mIsVisibleForDeleteBtn = false;
            viewHolder.llCityWeather.setVisibility(View.GONE);
            viewHolder.ivAddCity.setVisibility(View.VISIBLE);
            viewHolder.ivDeleteCity.setVisibility(View.GONE);
            mCityManageDBListener.onDBDataChanged();
        } else {
            if (position != mCityList.size() - 1) {// not the last add city item
                viewHolder.llCityWeather.setVisibility(View.VISIBLE);
                viewHolder.ivAddCity.setVisibility(View.GONE);
                //set the delete btn
                if (mIsVisibleForDeleteBtn) {// delete btn is visible
                    viewHolder.ivDeleteCity.setVisibility(View.VISIBLE);
                    viewHolder.ivDeleteCity.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (cityName != null && mDefaultCity.equals(cityName))//do not delete default city
                                return;

                            if (mCityManageDBHelper == null)
                                mCityManageDBHelper = CityManageDBHelper.getInstance(mContext);
                            mCityManageDBDao = mCityManageDBHelper.getCityManageDBDao();
                            // delete CityManage obj from db
                            try {
                                mCityManageDBDao.delete(cityManage);
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }

                            //delete CityManage obj from List
                            mCityList.remove(cityManage);
                            notifyDataSetChanged();
                        }
                    });
                } else {
                    viewHolder.ivDeleteCity.setVisibility(View.GONE);
                }

                //fill the weather layout
                if (cityManage.getCityName() != null) {
                    // set city name
                    viewHolder.tvCityName.setText(cityManage.getCityName());
                    // set weather icon depending on the type of weather
                    int weatherTypeId;
                    //hour of current time
                    int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
                    if (hour >= 0 && hour <= 6) {//the morning
                        weatherTypeId = WeatherInfoUtil.getWeatherImageId(cityManage.getWeatherTypeDay(), false);
                    } else if (hour > 6 && hour <= 18) {// daytime
                        weatherTypeId = WeatherInfoUtil.getWeatherImageId(cityManage.getWeatherTypeDay(), true);
                    } else {// night
                        weatherTypeId = WeatherInfoUtil.getWeatherImageId(cityManage.getWeatherTypeNight(), false);
                    }
                    viewHolder.ivWeatherType.setImageResource(weatherTypeId);
                    //set top temperature and low temperature
                    viewHolder.tvTopTemp.setText(cityManage.getTempTop());
                    viewHolder.tvLowTemp.setText(cityManage.getTempLow());
                    //set weather type
                    viewHolder.tvWeatherType.setText(cityManage.getWeatherType());
                }

                //whether if it is default city
                if (cityName != null && mDefaultCity.equals(cityName)) {
                    viewHolder.tvDefault.setText(R.string.my_default);
                } else {
                    viewHolder.tvDefault.setText(R.string.set_default);
                }

                //set click listener for default city
                viewHolder.tvDefault.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreferences sharedPreferences = mContext.getSharedPreferences(Constant
                                .WEATHER_SHARE_PREFERENCE, Context.MODE_PRIVATE);
                        // check if set default city repeatedly
                        if (cityName != null && sharedPreferences.getString(Constant.DEFAULT_CITY, "").equals(cityName))
                            return;

                        String weatherCode;
                        if (cityManage.getLocationCity() == null) {
                            weatherCode = cityManage.getWeatherCode();
                        } else {
                            weatherCode = mContext.getString(R.string.auto_location);
                        }

                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(Constant.DEFAULT_CITY, cityName);
                        editor.putString(Constant.DEFAULT_CITY_WEATHER_CODE, weatherCode);
                        editor.apply();
                        //set default city and notify change
                        mDefaultCity = cityName;
                        notifyDataSetChanged();
                    }
                });

            } else {// the last add city item
                viewHolder.llCityWeather.setVisibility(View.GONE);
                viewHolder.ivAddCity.setVisibility(View.VISIBLE);
                viewHolder.ivDeleteCity.setVisibility(View.GONE);
            }

        }

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.tv_city_name)
        TextView tvCityName;
        @BindView(R.id.iv_weather_type)
        ImageView ivWeatherType;
        @BindView(R.id.tv_top_temp)
        TextView tvTopTemp;
        @BindView(R.id.tv_low_temp)
        TextView tvLowTemp;
        @BindView(R.id.tv_weather_type)
        TextView tvWeatherType;
        @BindView(R.id.tv_default)
        TextView tvDefault;
        @BindView(R.id.ll_city_weather)
        LinearLayout llCityWeather;
        @BindView(R.id.iv_add_city)
        ImageView ivAddCity;
        @BindView(R.id.iv_delete_city)
        ImageView ivDeleteCity;
        @BindView(R.id.fl_item)
        FrameLayout flItem;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
