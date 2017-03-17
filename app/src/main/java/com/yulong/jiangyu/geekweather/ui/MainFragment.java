package com.yulong.jiangyu.geekweather.ui;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.j256.ormlite.dao.Dao;
import com.yulong.jiangyu.chartview.ChartView;
import com.yulong.jiangyu.geekweather.R;
import com.yulong.jiangyu.geekweather.bean.DateInfo;
import com.yulong.jiangyu.geekweather.bean.WeatherDaysForecast;
import com.yulong.jiangyu.geekweather.bean.WeatherInfo;
import com.yulong.jiangyu.geekweather.bean.WeatherLifeIndex;
import com.yulong.jiangyu.geekweather.constant.Constant;
import com.yulong.jiangyu.geekweather.dao.WeatherInfoDatabaseHelper;
import com.yulong.jiangyu.geekweather.listener.DateCallbackListener;
import com.yulong.jiangyu.geekweather.listener.HttpCallbackListener;
import com.yulong.jiangyu.geekweather.util.DateUtil;
import com.yulong.jiangyu.geekweather.util.LogUtil;
import com.yulong.jiangyu.geekweather.util.WeatherInfoUtil;
import com.yulong.jiangyu.geekweather.util.WebUtil;

import java.io.ByteArrayInputStream;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.content.ContentValues.TAG;

public class MainFragment extends Fragment {
    //日志TAG
    private static final String LOG_TAG = "MainFragment";
    //加载控件
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ll_root_content)
    LinearLayout llRootContent;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.dl_main)
    DrawerLayout dlMain;
    //折现图
    @BindView(R.id.line_chart)
    ChartView lineChart;
    //城市
    @BindView(R.id.tv_city)
    TextView tvCity;
    //更新时间
    @BindView(R.id.tv_update_time)
    TextView tvUpdateTime;
    //风力
    @BindView(R.id.tv_wind)
    TextView tvWind;
    //湿度
    @BindView(R.id.tv_humidity)
    TextView tvHumidity;
    //空气质量
    @BindView(R.id.tv_aqi)
    TextView tvAqi;
    //温度
    @BindView(R.id.iv_number1)
    ImageView ivNumber1;
    @BindView(R.id.iv_number2)
    ImageView ivNumber2;
    @BindView(R.id.iv_number3)
    ImageView ivNumber3;
    //天气类型
    @BindView(R.id.tv_weather_type)
    TextView tvWeatherType;
    //日历
    @BindView(R.id.tv_date)
    TextView tvDate;
    //数据库操作
    Dao<WeatherLifeIndex, Integer> mDao = null;
    private Unbinder mUnbinder;
    //百度定位服务
    private LocationClient mLocationClient = null;
    private MyLocationListener myLocationListener = null;
    //定位提示框
    private ProgressDialog mProgressDialog = null;
    //当前城市
    private String mCity = "北京";
    //请求日历接口
    //private DateInfo mDateInfo = null;
    //数据库
    private WeatherInfoDatabaseHelper mWeatherInfoDatabaseHelper = null;
    private ActionBarDrawerToggle mActionBarDrawerToggle = null;
    //抽屉Drawer全部被拉出时的宽度
    private int mDrawerWidth = 0;
    //抽屉被拉出部分的宽度
    private float mScrollWidth = 0f;
    //更新UI的Handler
    private Handler mHandler = null;

    public MainFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mHandler = new Handler() {
            /**
             * Subclasses must implement this to receive messages.
             *
             * @param msg 处理的消息
             */
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case Constant.UPDATE_WEATHER_UI:
                        WeatherInfo weatherInfo = (WeatherInfo) msg.getData().getSerializable(Constant.WEATHER_INFO);
                        updateWeatherUI(weatherInfo);
                        break;
                    case Constant.UPDATE_DATE_UI:
                        DateInfo dateInfo = (DateInfo) msg.getData().getSerializable(Constant.DATE_INFO);
                        updateDateUI(dateInfo);
                        break;
                }
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        mUnbinder = ButterKnife.bind(this, view);

        //初始化控件
        initView();
        //开始位置服务
        startLocation();
        return view;
    }

    /**
     * Called when the fragment is no longer in use.  This is called
     * after {@link #onStop()} and before {@link #onDetach()}.
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

    /**
     * 初始化数据库操作实例
     */
    private void initDao() {
        try {
            if (mWeatherInfoDatabaseHelper == null) {
                mWeatherInfoDatabaseHelper = WeatherInfoDatabaseHelper.getInstance(getActivity()
                        .getApplicationContext());
                mDao = mWeatherInfoDatabaseHelper.getDaoWeatherLifeIndex();
            }
        } catch (SQLException e) {
            LogUtil.e(LOG_TAG, e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 初始化控件
     */
    private void initView() {
        toolbar.setTitleTextColor(Color.WHITE);
        //将默认标题设置为空，从而使用textview的内容
        toolbar.setTitle("");
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        //设置Drawerlayout的滑入滑出
        mActionBarDrawerToggle = new ActionBarDrawerToggle(getActivity(), dlMain, toolbar, R.string.drawer_open, R
                .string.drawer_close) {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                mDrawerWidth = navView.getMeasuredWidth();
                mScrollWidth = mDrawerWidth * slideOffset;
                //TODO 背景没有随着被挤压！
                //根据Drawer抽屉被拉出的宽度伸缩主布局内容
                llRootContent.setScrollX((int) (-1 * mScrollWidth));
            }
        };
        mActionBarDrawerToggle.syncState();
        dlMain.addDrawerListener(mActionBarDrawerToggle);
        //设置菜单menu项点击事件
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.app_info:
                        showDialog(getString(R.string.info_title), getString(R.string.app_info));
                        break;
                    case R.id.app_version:
                        Context context = getActivity().getApplicationContext();
                        try {
                            //获取应用的版本号
                            String version = context.getPackageManager().getPackageInfo(context.getPackageName(), 0)
                                    .versionName;
                            showDialog(getString(R.string.version_title), getString(R.string.app_version) + version);

                        } catch (PackageManager.NameNotFoundException e) {
                            Log.e(TAG, "***onNavigationItemSelected***:failed to get application version!");
                            e.printStackTrace();
                        }
                        break;
                    default:
                }
                return true;
            }

            /**
             * 根据id返回字符串内容
             *
             * @param resourceID id
             * @return 字符串内容
             */
            private String getString(int resourceID) {
                return getResources().getString(resourceID);
            }
        });

        lineChart.setTemperatureDay(new int[]{14, 15, 16, 17, 9, 9});
        lineChart.setTemperatureNight(new int[]{7, 5, 9, 10, 3, 2});
        lineChart.invalidate();

        //初始化定位进度对话框
        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setMessage(getString(R.string.location_tip));
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        //是否可以使用返回键取消
        mProgressDialog.setCancelable(false);

        mProgressDialog.show();
    }

    /**
     * 开始位置服务
     */
    private void startLocation() {
        LocationClientOption locationClientOption = new LocationClientOption();
        locationClientOption.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//设置定位模式
        locationClientOption.setCoorType("bd0911");//设置返回的坐标值类型，这里是返回百度坐标
        locationClientOption.setScanSpan(3 * 1000);//设置扫描时间间隔3秒
        locationClientOption.setIsNeedAddress(true);//是否需要返回地址
        locationClientOption.setOpenGps(true);//是否打开GPS
        locationClientOption.setLocationNotify(true);
        locationClientOption.setIsNeedLocationDescribe(true);
        locationClientOption.setIgnoreKillProcess(false);
        locationClientOption.SetIgnoreCacheException(false);
        locationClientOption.setEnableSimulateGps(false);
        if (mLocationClient == null)
            mLocationClient = new LocationClient(getActivity().getApplicationContext());
        mLocationClient.setLocOption(locationClientOption);
        if (myLocationListener == null)
            myLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(myLocationListener);
        mLocationClient.start();
        LogUtil.i(LOG_TAG, getString(R.string.start_location));
    }

    /**
     * 显示信息对话框
     *
     * @param title   标题
     * @param message 内容
     */
    private void showDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setNeutralButton(R.string.btn_confirm, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.show();
    }


    /**
     * 刷新获取天气数据
     */
    private void refreshData() {
        //请求天气数据
        String address_weather = getString(R.string.weather_base_url, mCity);
        WebUtil.requestWeather(address_weather, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                //获取天气数据
                WeatherInfo weatherInfo = WeatherInfoUtil.handleWeatherInfo(new ByteArrayInputStream(response
                        .getBytes()));
                try {
                    initDao();
                    //将生活数据存库
                    mDao.create(weatherInfo.getmWeatherLifeIndies());
                } catch (SQLException e) {
                    LogUtil.e(LOG_TAG, e.getMessage());
                    e.printStackTrace();
                }

                //发送Handler消息来更新UI界面
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constant.WEATHER_INFO, weatherInfo);
                Message msg = new Message();
                msg.setData(bundle);
                msg.what = Constant.UPDATE_WEATHER_UI;
                mHandler.sendMessage(msg);
            }

            @Override
            public void onError(Exception e) {
                LogUtil.e(LOG_TAG, e.getMessage());
            }
        });

        //请求日历数据
        String dateStr = DateUtil.getCurrentDate(getString(R.string.date_request));
        WebUtil.requesDate(dateStr, new DateCallbackListener() {
            @Override
            public void onFinish(DateInfo dateInfo) {
                //mDateInfo = dateInfo;
                //发送Handler消息来更新UI界面
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constant.DATE_INFO, dateInfo);
                Message msg = new Message();
                msg.setData(bundle);
                msg.what = Constant.UPDATE_DATE_UI;
                mHandler.sendMessage(msg);
            }

            @Override
            public void onError(Throwable t) {
                //LogUtil.e(LOG_TAG, getString(R.string.error_get_data));
                LogUtil.e(LOG_TAG, t.getMessage());
            }
        });
    }

    /**
     * 更新日期界面
     *
     * @param dateInfo 日期信息
     */
    private void updateDateUI(DateInfo dateInfo) {
        String dateStr = DateUtil.getCurrentDate(getString(R.string.date_ui));
        String lunarStr = dateInfo.getResult().getData().getLunar();
        tvDate.setText(String.format(getString(R.string.date), dateStr, lunarStr));
    }

    /**
     * 更新UI界面
     *
     * @param weatherInfo 天气信息数据
     */
    private void updateWeatherUI(WeatherInfo weatherInfo) {
        tvCity.setText(weatherInfo.getmCity());
        //更新头部时间
        tvUpdateTime.setText(String.format(getString(R.string.update_time), weatherInfo.getmUpdateTime()));

        //更新天气
        List<WeatherDaysForecast> weatherDaysForecasts = weatherInfo.getmWeatherDaysForecasts();
        //昨天
        WeatherDaysForecast weatherDaysForecast1 = weatherDaysForecasts.get(0);
        //今天
        WeatherDaysForecast weatherDaysForecast2 = weatherDaysForecasts.get(1);
        //明天
        WeatherDaysForecast weatherDaysForecast3 = weatherDaysForecasts.get(2);
        WeatherDaysForecast weatherDaysForecast4 = weatherDaysForecasts.get(3);
        WeatherDaysForecast weatherDaysForecast5 = weatherDaysForecasts.get(4);
        WeatherDaysForecast weatherDaysForecast6 = weatherDaysForecasts.get(5);
        //当前时间的小时数
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        setTemperature(weatherInfo);
        setWeatherType(hour, weatherDaysForecast2);
        tvWind.setText(weatherInfo.getmWindDirection() + " " + weatherInfo.getmWindPower());
        tvHumidity.setText(String.format(getString(R.string.humidity), weatherInfo.getmHumidity()));
        setAqi(weatherInfo);
    }

    /**
     * 设置天气类型
     *
     * @param hour                当前时间小时数
     * @param weatherDaysForecast 天气信息
     */
    private void setWeatherType(int hour, WeatherDaysForecast weatherDaysForecast) {
        if (hour < 18) //白天
            tvWeatherType.setText(weatherDaysForecast.getmTypeDay());
        else //晚上
            tvWeatherType.setText(weatherDaysForecast.getmTypeNight());
    }

    /**
     * 设置温度
     *
     * @param weatherInfo 天气信息
     */
    private void setTemperature(WeatherInfo weatherInfo) {
        String temperature = weatherInfo.getmTemperature();
        if (temperature != null) {
            if (temperature.contains("-")) {//负数
                ivNumber1.setVisibility(View.VISIBLE);
                ivNumber1.setImageResource(R.drawable.ic_minus);
                if (1 == (temperature.length() - 1)) {//1位
                    setTemperatureImage(Integer.parseInt(temperature.substring(1, 2)), ivNumber3);
                } else {//2位
                    ivNumber2.setVisibility(View.VISIBLE);
                    setTemperatureImage(Integer.parseInt(temperature.substring(1, 2)), ivNumber2);
                    setTemperatureImage(Integer.parseInt(temperature.substring(2, 3)), ivNumber3);
                }
            } else {//正数
                if (1 == temperature.length()) {//1位
                    setTemperatureImage(Integer.parseInt(temperature), ivNumber3);
                } else {//2位
                    ivNumber2.setVisibility(View.VISIBLE);
                    setTemperatureImage(Integer.parseInt(temperature.substring(0, 1)), ivNumber2);
                    setTemperatureImage(Integer.parseInt(temperature.substring(1, 2)), ivNumber3);
                }
            }
        } else {
            ivNumber1.setImageResource(R.drawable.number_0);
            ivNumber2.setImageResource(R.drawable.number_0);
            ivNumber3.setImageResource(R.drawable.number_0);
        }
    }

    /**
     * 设置温度图片
     *
     * @param num      数字
     * @param ivNumber 图片控件
     */
    private void setTemperatureImage(int num, ImageView ivNumber) {
        switch (num) {
            case 0:
                ivNumber.setImageResource(R.drawable.number_0);
                break;
            case 1:
                ivNumber.setImageResource(R.drawable.number_1);
                break;
            case 2:
                ivNumber.setImageResource(R.drawable.number_2);
                break;
            case 3:
                ivNumber.setImageResource(R.drawable.number_3);
                break;
            case 4:
                ivNumber.setImageResource(R.drawable.number_4);
                break;
            case 5:
                ivNumber.setImageResource(R.drawable.number_5);
                break;
            case 6:
                ivNumber.setImageResource(R.drawable.number_6);
                break;
            case 7:
                ivNumber.setImageResource(R.drawable.number_7);
                break;
            case 8:
                ivNumber.setImageResource(R.drawable.number_8);
                break;
            case 9:
                ivNumber.setImageResource(R.drawable.number_9);
                break;
        }
    }

    /**
     * 设置空气质量
     *
     * @param weatherInfo 天气信息
     */
    private void setAqi(WeatherInfo weatherInfo) {
        String quality = weatherInfo.getmAQ();
        Drawable drawableLeft = ContextCompat.getDrawable(getContext(), getQualityImageId(quality));
        Drawable drawableRight = ContextCompat.getDrawable(getContext(), R.drawable.ic_right);
        if (drawableLeft != null)
            drawableLeft.setBounds(0, 0, drawableLeft.getMinimumWidth(), drawableLeft.getMinimumHeight());
        if (drawableRight != null)
            drawableRight.setBounds(0, 0, drawableRight.getMinimumWidth(), drawableRight.getMinimumHeight());
        tvAqi.setCompoundDrawables(drawableLeft, null, drawableRight, null);
        tvAqi.setText(weatherInfo.getmAQI() + " " + quality);
    }

    /**
     * 返回空气质量图片id
     *
     * @param quality 空气质量
     * @return 空气质量图片id
     */
    private int getQualityImageId(String quality) {
        int imgId;
        switch (quality) {
            case "优":
                imgId = R.drawable.ic_quality_nice;
                break;
            case "良":
                imgId = R.drawable.ic_quality_good;
                break;
            case "轻度污染":
                imgId = R.drawable.ic_quality_little;
                break;
            case "中度污染":
                imgId = R.drawable.ic_quality_medium;
                break;
            case "重度污染":
                imgId = R.drawable.ic_quality_serious;
                break;
            case "严重污染":
                imgId = R.drawable.ic_quality_terrible;
                break;
            default:
                imgId = R.drawable.ic_quality_nice;
                break;
        }
        return imgId;
    }

    /**
     * 设置生活指数点击事件
     *
     * @param v 被点击的视图
     */
    @OnClick({R.id.rl_exercise, R.id.rl_clothe, R.id.rl_comfort, R.id.rl_influenza, R.id.rl_car, R.id.rl_ultraviolet})
    public void onClick(View v) {

    }

    /**
     * 位置监听
     */
    private class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            //监听一次后注销
            mLocationClient.stop();
            mLocationClient.unRegisterLocationListener(myLocationListener);

            switch (bdLocation.getLocType()) {
                case BDLocation.TypeGpsLocation:
                    LogUtil.i(LOG_TAG, getString(R.string.ok_location_gps));
                    break;
                case BDLocation.TypeOffLineLocation:
                    LogUtil.i(LOG_TAG, getString(R.string.ok_location_offline));
                    break;
                case BDLocation.TypeNetWorkLocation:
                    LogUtil.i(LOG_TAG, getString(R.string.ok_location_net));
                    break;
                case BDLocation.TypeCriteriaException:
                    LogUtil.e(LOG_TAG, getString(R.string.error_location));
                    break;
            }
            if (bdLocation != null) {
                mCity = bdLocation.getCity();
            }
            mProgressDialog.dismiss();
            //截掉返回城市的行政单位：“市”、"县"、“镇”等
            mCity = mCity.substring(0, mCity.length() - 1);
            //使用SharedPreferences保存默认城市
            SharedPreferences sharedPreferences = getActivity().getApplication().getSharedPreferences(Constant
                    .DEFAULT_PREFERENCE, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(Constant.DEFAULT_CITY, mCity);
            editor.apply();

            refreshData();
        }
    }
}
