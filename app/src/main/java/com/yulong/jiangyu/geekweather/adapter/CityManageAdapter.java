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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yulong.jiangyu.geekweather.R;
import com.yulong.jiangyu.geekweather.bean.CityManage;
import com.yulong.jiangyu.geekweather.constant.Constant;
import com.yulong.jiangyu.geekweather.dao.CityMangeDao;
import com.yulong.jiangyu.geekweather.utils.Utils;

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
 * note
 **/

public class CityManageAdapter extends ArrayAdapter<CityManage> {
    SharedPreferences mSharedPreferences;
    private Context mContext;
    // 城市列表
    private List<CityManage> mCityList;
    // 默认城市
    private String mDefaultCity;
    // 删除按钮的可见性
    private boolean mIsVisibleForDeleteBtn = false;
    // 数据库操作
    private CityMangeDao mCityManageDao;

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
        mCityManageDao = new CityMangeDao(mContext);

        // 获取默认城市
        mSharedPreferences = mContext.getSharedPreferences(Constant.WEATHER_SHARE_PREFERENCE,
                Context.MODE_PRIVATE);
        mDefaultCity = mSharedPreferences.getString(Constant.DEFAULT_CITY, "");
    }

    /**
     * 设置删除按钮的可见性
     *
     * @param visible visibility of delete button
     */
    public void setVisibleForDeleteBtn(boolean visible) {
        mIsVisibleForDeleteBtn = visible;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final CityManage cityManage = getItem(position);
        final String cityName = cityManage.getCityName();
        ViewHolder viewHolder;

        // 获取布局
        if (null == convertView) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_city_manage_adapter, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (1 == mCityList.size()) { // 城市列表为空
            mIsVisibleForDeleteBtn = false;
            /**
             * llCityWeather的可见性必须设置为INVISIBLE
             * 不能设置为GONE
             * 否则最后一项——添加城市，显示的高度会自适应地变矮，而与前面的各个Item高度不一致
             */
            viewHolder.llCityWeather.setVisibility(View.INVISIBLE);

            viewHolder.ivAddCity.setVisibility(View.VISIBLE);
            viewHolder.ivDeleteCity.setVisibility(View.GONE);
        } else { // 城市列表非空
            if (position != mCityList.size() - 1) {// 非最后一项——添加城市
                viewHolder.llCityWeather.setVisibility(View.VISIBLE);
                viewHolder.ivAddCity.setVisibility(View.GONE);

                // 根据删除图标的可见性进行设置
                if (mIsVisibleForDeleteBtn) {
                    viewHolder.ivDeleteCity.setVisibility(View.VISIBLE);
                    viewHolder.ivDeleteCity.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (cityName.equals(mDefaultCity)) {
                                Toast.makeText(mContext, "暂时无法删除默认城市", Toast.LENGTH_SHORT).show();
                            } else {
                                // 删除并刷新
                                mCityList.remove(cityManage);
                                if (mCityManageDao.delete(cityName))
                                    notifyDataSetChanged();
                            }
                        }
                    });
                } else {
                    viewHolder.ivDeleteCity.setVisibility(View.GONE);
                }

                // 设置城市天气信息
                if (cityManage.getCityName() != null) {
                    // 城市名
                    viewHolder.tvCityName.setText(cityManage.getCityName());
                    // 根据当前时间获取天气类型图标并设置
                    int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
                    int weatherTypeId;
                    if (hour >= 0 && hour <= 6) { // 上午
                        weatherTypeId = Utils.getWeatherImageId(cityManage.getWeatherTypeDay(), false);
                    } else if (hour > 6 && hour <= 18) { // 白昼
                        weatherTypeId = Utils.getWeatherImageId(cityManage.getWeatherTypeDay(), true);
                    } else {// 黑夜
                        weatherTypeId = Utils.getWeatherImageId(cityManage.getWeatherTypeNight(), false);
                    }
                    viewHolder.ivWeatherType.setImageResource(weatherTypeId);
                    // 设置高温和低温
                    viewHolder.tvTopTemp.setText(cityManage.getTempTop());
                    viewHolder.tvLowTemp.setText(cityManage.getTempLow());

                    // 设置天气类型
                    if (hour > 6 && hour <= 18) { // 白天
                        viewHolder.tvWeatherType.setText(cityManage.getWeatherTypeDay());
                    } else { // 黑夜
                        viewHolder.tvWeatherType.setText(cityManage.getWeatherTypeNight());
                    }
                }

                // 设置默认城市
                if (cityName != null && mDefaultCity.equals(cityName)) {
                    viewHolder.tvDefault.setText(R.string.my_default);
                    viewHolder.tvDefault.setBackground(mContext.getDrawable(R.drawable.bg_gv_city_manage_default));
                } else {
                    viewHolder.tvDefault.setText(R.string.set_default);
                    viewHolder.tvDefault.setBackground(mContext.getDrawable(R.drawable.bg_gv_city_manage_set_default));
                }

                // 点击设置默认城市
                viewHolder.tvDefault.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (cityName.equals(mDefaultCity)) {
                            Toast.makeText(mContext, "已经设置为默认城市，无需重复设置", Toast.LENGTH_SHORT).show();
                        } else {
                            SharedPreferences.Editor editor = mSharedPreferences.edit();
                            editor.putString(Constant.DEFAULT_CITY, cityName);
                            editor.apply();
                            mDefaultCity = cityName;
                            notifyDataSetChanged();
                        }
                    }
                });

            } else { // 最后一项——添加城市
                /**
                 * llCityWeather的可见性必须设置为INVISIBLE
                 * 不能设置为GONE
                 * 否则最后一项——添加城市，显示的高度会自适应地变矮，而与前面的各个Item高度不一致
                 */
                viewHolder.llCityWeather.setVisibility(View.INVISIBLE);
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
        ViewGroup flItem;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
