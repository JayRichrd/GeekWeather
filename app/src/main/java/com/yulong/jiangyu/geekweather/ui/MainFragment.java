package com.yulong.jiangyu.geekweather.ui;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.yulong.jiangyu.chartview.ChartView;
import com.yulong.jiangyu.geekweather.R;
import com.yulong.jiangyu.geekweather.bean.CityManage;
import com.yulong.jiangyu.geekweather.entity.WeatherForecastDaysEntity;
import com.yulong.jiangyu.geekweather.entity.LifeIndexEntity;
import com.yulong.jiangyu.geekweather.constant.Constant;
import com.yulong.jiangyu.geekweather.dao.LifeIndexDao;
import com.yulong.jiangyu.geekweather.entity.DateEntity;
import com.yulong.jiangyu.geekweather.entity.WeatherEntity;
import com.yulong.jiangyu.geekweather.listener.HttpCallbackListener;
import com.yulong.jiangyu.geekweather.net.RequestData;
import com.yulong.jiangyu.geekweather.utils.DateUtil;
import com.yulong.jiangyu.geekweather.utils.LogUtil;
import com.yulong.jiangyu.geekweather.utils.Utils;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class MainFragment extends Fragment {
    //日志TAG
    private static final String TAG = "MainFragment";

    //toolbar
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    //主布局
    @BindView(R.id.ll_root_content)
    LinearLayout llRootContent;
    //下拉刷新
    @BindView(R.id.ptrsv_root)
    PullToRefreshScrollView ptrsvRoot;
    //抽屉控件
    @BindView(R.id.nav_view)
    NavigationView navView;
    //抽屉布局
    @BindView(R.id.dl_main)
    DrawerLayout dlMain;
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
    @BindView(R.id.iv_symbol)
    ImageView ivSymbol;
    @BindView(R.id.iv_number1)
    ImageView ivNumber1;
    @BindView(R.id.iv_number2)
    ImageView ivNumber2;
    //天气类型
    @BindView(R.id.tv_weather_type)
    TextView tvWeatherType;
    //日历
    @BindView(R.id.tv_date)
    TextView tvDate;
    //星期
    @BindView(R.id.tv_weather_forecast_week1)
    TextView tvForecastWeek1;
    @BindView(R.id.tv_weather_forecast_week2)
    TextView tvForecastWeek2;
    @BindView(R.id.tv_weather_forecast_week3)
    TextView tvForecastWeek3;
    @BindView(R.id.tv_weather_forecast_week4)
    TextView tvForecastWeek4;
    @BindView(R.id.tv_weather_forecast_week5)
    TextView tvForecastWeek5;
    @BindView(R.id.tv_weather_forecast_week6)
    TextView tvForecastWeek6;
    //日历
    @BindView(R.id.tv_weather_forecast_date1)
    TextView tvForecastDate1;
    @BindView(R.id.tv_weather_forecast_date2)
    TextView tvForecastDate2;
    @BindView(R.id.tv_weather_forecast_date3)
    TextView tvForecastDate3;
    @BindView(R.id.tv_weather_forecast_date4)
    TextView tvForecastDate4;
    @BindView(R.id.tv_weather_forecast_date5)
    TextView tvForecastDate5;
    @BindView(R.id.tv_weather_forecast_date6)
    TextView tvForecastDate6;
    //白天天气图片
    @BindView(R.id.iv_weather_forecast_day1)
    ImageView ivWeatherForecastDay1;
    @BindView(R.id.iv_weather_forecast_day2)
    ImageView ivWeatherForecastDay2;
    @BindView(R.id.iv_weather_forecast_day3)
    ImageView ivWeatherForecastDay3;
    @BindView(R.id.iv_weather_forecast_day4)
    ImageView ivWeatherForecastDay4;
    @BindView(R.id.iv_weather_forecast_day5)
    ImageView ivWeatherForecastDay5;
    @BindView(R.id.iv_weather_forecast_day6)
    ImageView ivWeatherForecastDay6;
    //白天天气类型
    @BindView(R.id.tv_weather_forecast_type_day1)
    TextView tvWeatherForecastTypeDay1;
    @BindView(R.id.tv_weather_forecast_type_day2)
    TextView tvWeatherForecastTypeDay2;
    @BindView(R.id.tv_weather_forecast_type_day3)
    TextView tvWeatherForecastTypeDay3;
    @BindView(R.id.tv_weather_forecast_type_day4)
    TextView tvWeatherForecastTypeDay4;
    @BindView(R.id.tv_weather_forecast_type_day5)
    TextView tvWeatherForecastTypeDay5;
    @BindView(R.id.tv_weather_forecast_type_day6)
    TextView tvWeatherForecastTypeDay6;
    //温度折线图
    @BindView(R.id.line_chart)
    ChartView lineChart;
    //晚上天气图片
    @BindView(R.id.iv_weather_forecast_night1)
    ImageView ivWeatherForecastNight1;
    @BindView(R.id.iv_weather_forecast_night2)
    ImageView ivWeatherForecastNight2;
    @BindView(R.id.iv_weather_forecast_night3)
    ImageView ivWeatherForecastNight3;
    @BindView(R.id.iv_weather_forecast_night4)
    ImageView ivWeatherForecastNight4;
    @BindView(R.id.iv_weather_forecast_night5)
    ImageView ivWeatherForecastNight5;
    @BindView(R.id.iv_weather_forecast_night6)
    ImageView ivWeatherForecastNight6;
    //晚上天气类型
    @BindView(R.id.tv_weather_forecast_type_night1)
    TextView tvWeatherForecastTypeNight1;
    @BindView(R.id.tv_weather_forecast_type_night2)
    TextView tvWeatherForecastTypeNight2;
    @BindView(R.id.tv_weather_forecast_type_night3)
    TextView tvWeatherForecastTypeNight3;
    @BindView(R.id.tv_weather_forecast_type_night4)
    TextView tvWeatherForecastTypeNight4;
    @BindView(R.id.tv_weather_forecast_type_night5)
    TextView tvWeatherForecastTypeNight5;
    @BindView(R.id.tv_weather_forecast_type_night6)
    TextView tvWeatherForecastTypeNight6;
    //风向
    @BindView(R.id.tv_weather_forecast_wind_direction1)
    TextView tvWeatherForecastWindDirection1;
    @BindView(R.id.tv_weather_forecast_wind_direction2)
    TextView tvWeatherForecastWindDirection2;
    @BindView(R.id.tv_weather_forecast_wind_direction3)
    TextView tvWeatherForecastWindDirection3;
    @BindView(R.id.tv_weather_forecast_wind_direction4)
    TextView tvWeatherForecastWindDirection4;
    @BindView(R.id.tv_weather_forecast_wind_direction5)
    TextView tvWeatherForecastWindDirection5;
    @BindView(R.id.tv_weather_forecast_wind_direction6)
    TextView tvWeatherForecastWindDirection6;
    //风力
    @BindView(R.id.tv_weather_forecast_wind_power1)
    TextView tvWeatherForecastWindPower1;
    @BindView(R.id.tv_weather_forecast_wind_power2)
    TextView tvWeatherForecastWindPower2;
    @BindView(R.id.tv_weather_forecast_wind_power3)
    TextView tvWeatherForecastWindPower3;
    @BindView(R.id.tv_weather_forecast_wind_power4)
    TextView tvWeatherForecastWindPower4;
    @BindView(R.id.tv_weather_forecast_wind_power5)
    TextView tvWeatherForecastWindPower5;
    @BindView(R.id.tv_weather_forecast_wind_power6)
    TextView tvWeatherForecastWindPower6;
    // 生活指数
    @BindView(R.id.tv_weather_life_index_exercise)
    TextView tvWeatherLifeIndexExercise;
    @BindView(R.id.tv_weather_life_index_clothe)
    TextView tvWeatherLifeIndexClothe;
    @BindView(R.id.tv_weather_life_index_comfort)
    TextView tvWeatherLifeIndexComfort;
    @BindView(R.id.tv_weather_life_index_influenza)
    TextView tvWeatherLifeIndexInfluenza;
    @BindView(R.id.tv_weather_life_index_wash_car)
    TextView tvWeatherLifeIndexWashCar;
    @BindView(R.id.tv_weather_life_index_ultraviolet)
    TextView tvWeatherLifeIndexUltraviolet;
    //    @BindView(R.id.iv_add_city)
//    ImageView ivAddCity;
    // 生活指数数据库操作
    private LifeIndexDao mLifeIndexDao;
    // 天气信息对象
    private WeatherEntity mWeatherEntity;

    private Unbinder mUnbinder;
    private ActionBarDrawerToggle mActionBarDrawerToggle = null;
    //百度定位服务
    private LocationClient mLocationClient = null;
    private BaiDuLocationListener mBaiDuLocationListener = null;
    //定位提示框
    private ProgressDialog mProgressDialog = null;
    //当前城市
    private String mCity = "北京";
    //抽屉Drawer全部被拉出时的宽度
    private int drawerWidth = 0;
    //抽屉被拉出部分的宽度
    private float scrollWidth = 0f;
    //更新UI的Handler
    private Handler mHandler = new Handler() {
        /**
         * Subclasses must implement this to receive messages.
         *
         * @param msg 处理的消息
         */
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Constant.UPDATE_WEATHER_UI://更新天气
                    WeatherEntity weatherInfo = (WeatherEntity) msg.getData().getSerializable(Constant.WEATHER_INFO);
                    // 先停止刷新
                    if (ptrsvRoot.isRefreshing())
                        stopRefresh();
                    Toast.makeText(getActivity(), getString(R.string.main_fragment_toast_update_finished), Toast
                            .LENGTH_SHORT).show();
//                    Toast.makeText(getActivity(), "更新数据完毕", Toast.LENGTH_SHORT).show();
                    // 再刷新天气
                    updateWeatherUI(weatherInfo);
                    break;
                case Constant.UPDATE_DATE_UI://更新日历
                    DateEntity dateInfo = (DateEntity) msg.getData().getSerializable(Constant.DATE_INFO);
                    updateDateUI(dateInfo);
                    break;
            }
        }
    };

    public MainFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        mLifeIndexDao = new LifeIndexDao(getActivity());
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
     * 初始化控件
     */
    private void initView() {
        toolbar.setTitleTextColor(Color.WHITE);
        //将默认标题设置为空，从而使用TextView的内容
        toolbar.setTitle("");
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        //设置DrawerLayout的滑入滑出
        mActionBarDrawerToggle = new ActionBarDrawerToggle(getActivity(), dlMain, toolbar, R.string
                .main_fragment_dl_drawer_open, R.string.main_fragment_dl_drawer_close) {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                drawerWidth = navView.getMeasuredWidth();
                scrollWidth = drawerWidth * slideOffset;
                //TODO 背景没有随着被挤压！
                //根据Drawer抽屉被拉出的宽度伸缩主布局内容
                llRootContent.setScrollX((int) (-1 * scrollWidth));
            }
        };
        mActionBarDrawerToggle.syncState();
        dlMain.addDrawerListener(mActionBarDrawerToggle);
        //设置菜单menu项点击事件
        navView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.item_about:
                                showDialog(getString(R.string.main_fragment_drawer_about_title), getString(R.string
                                        .main_fragment_drawer_app_info));
                                break;
                            case R.id.item_version:
                                Context context = getActivity().getApplicationContext();
                                try {
                                    //获取应用的版本号
                                    String version = context.getPackageManager().getPackageInfo(context
                                            .getPackageName(), 0)
                                            .versionName;
                                    showDialog(getString(R.string.main_fragment_drawer_version_title),
                                            getString(R.string.main_fragment_drawer_version_app_version_prefix) +
                                                    version);

                                } catch (PackageManager.NameNotFoundException e) {
                                    Log.e(ContentValues.TAG, "failed to get application version!");
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
                     * @param resourceID 资源id
                     * @return 字符串内容
                     */
                    private String getString(int resourceID) {
                        return getResources().getString(resourceID);
                    }
                });

        //lineChart.setTemperatureDay(new int[]{14, 15, 16, 17, 9, 9});
        //lineChart.setTemperatureNight(new int[]{7, 5, 9, 10, 3, 2});
        //lineChart.invalidate();

        //初始化定位进度对话框
        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setMessage(getString(R.string.main_fragment_progress_dlg_locating));
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        //是否可以使用返回键取消
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();

        // 设置下拉刷新控件
        ptrsvRoot.getLoadingLayoutProxy().setPullLabel(getString(R.string.main_fragment_ptrsv_pull_to_refresh));
        ptrsvRoot.getLoadingLayoutProxy().setRefreshingLabel(getString(R.string.main_fragment_ptrsv_refreshing));
        ptrsvRoot.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
                //更新数据
                refreshData();
            }
        });
        ptrsvRoot.getLoadingLayoutProxy().setReleaseLabel(getString(R.string.main_fragment_ptrsv_loosen_to_refresh));
    }

    /**
     * 停止刷新
     */
    private void stopRefresh() {
        // 结束刷新
        ptrsvRoot.onRefreshComplete();
        // 结束动画
        ptrsvRoot.clearAnimation();
    }

    /**
     * 开始位置服务
     */
    private void startLocation() {
        LocationClientOption locationClientOption = new LocationClientOption();
        //设置位置服务参数
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
        if (mLocationClient == null) {
            mLocationClient = new LocationClient(getActivity().getApplicationContext());
        }
        mLocationClient.setLocOption(locationClientOption);
        if (mBaiDuLocationListener == null) {
            mBaiDuLocationListener = new BaiDuLocationListener();
        }
        mLocationClient.registerLocationListener(mBaiDuLocationListener);
        mLocationClient.start();
        Log.i(TAG, getString(R.string.main_fragment_log_start_locate));
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
        Toast.makeText(getActivity(), getString(R.string.main_fragment_toast_update_start), Toast.LENGTH_SHORT).show();
        // 请求天气数据
        RequestData.requestWeatherData(getActivity(), mCity, false, new HttpCallbackListener() {
            @Override
            public void onFinished(Object response) {
                String weatherInfoStr = (String) response;
                // 解析天气数据
                mWeatherEntity = Utils.handleWeatherInfo(new ByteArrayInputStream(weatherInfoStr.getBytes()));
                // 将生活指数数据存库
                mLifeIndexDao.insert(mWeatherEntity.getmWeatherLifeIndies());
                //发送Handler消息来更新UI界面
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constant.WEATHER_INFO, mWeatherEntity);
                Message msg = new Message();
                msg.setData(bundle);
                msg.what = Constant.UPDATE_WEATHER_UI;
                mHandler.sendMessage(msg);

            }

            @Override
            public void onError(Throwable t) {
            }
        });

        //请求日历数据
        String dateStr = Utils.getCurrentDate(getString(R.string.format_request_date));
        RequestData.requestDateData(dateStr, new HttpCallbackListener() {
            @Override
            public void onFinished(Object response) {
                //发送Handler消息来更新UI界面
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constant.DATE_INFO, (DateEntity) response);
                Message msg = new Message();
                msg.setData(bundle);
                msg.what = Constant.UPDATE_DATE_UI;
                mHandler.sendMessage(msg);
            }

            @Override
            public void onError(Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });
    }

    /**
     * 更新日期界面
     *
     * @param dateEntity 日期信息
     */
    private void updateDateUI(DateEntity dateEntity) {
        String dateStr = DateUtil.getCurrentDate(getString(R.string.format_ui_date));
        String lunarStr = dateEntity.getResult().getData().getLunar();
        tvDate.setText(String.format(getString(R.string.format_date), dateStr, lunarStr));
    }

    /**
     * 更新UI界面
     *
     * @param weatherEntity 天气信息数据
     */
    private void updateWeatherUI(WeatherEntity weatherEntity) {
        // 设置城市
        tvCity.setText(weatherEntity.getmCity());
        // 更新头部时间
        tvUpdateTime.setText(String.format(getString(R.string.format_update_time), weatherEntity.getmUpdateTime()));

        List<WeatherForecastDaysEntity> weatherForecastDaysList = weatherEntity.getmWeatherDaysForecasts();
        //昨天
        //WeatherDaysForecast weatherDaysForecast1 = weatherDaysForecasts.get(0);
        //今天
        //WeatherDaysForecast weatherDaysForecast2 = weatherDaysForecasts.get(1);
        //明天
        //WeatherDaysForecast weatherDaysForecast3 = weatherDaysForecasts.get(2);
        //WeatherDaysForecast weatherDaysForecast4 = weatherDaysForecasts.get(3);
        //WeatherDaysForecast weatherDaysForecast5 = weatherDaysForecasts.get(4);
        //WeatherDaysForecast weatherDaysForecast6 = weatherDaysForecasts.get(5);
        //当前时间的小时数
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        setTemperature(weatherEntity);
        setWeatherType(hour, weatherForecastDaysList.get(1));
        tvWind.setText(weatherEntity.getmWindDirection() + " " + weatherEntity.getmWindPower());
        tvHumidity.setText(String.format(getString(R.string.format_humidity), weatherEntity.getmHumidity()));
        setAqi(weatherEntity);
        //设置未来几天天气
        if (6 == weatherForecastDaysList.size()) {
            setDaysForecast(weatherForecastDaysList, calendar, hour);
        }
        // 更新生活指数信息
        setLifeIndex(weatherEntity.getmWeatherLifeIndies());
    }

    /**
     * 设置多日天气预报
     *
     * @param weatherDaysForecasts 多日天气数据
     * @param calendar             当前日历
     */
    private void setDaysForecast(List<WeatherForecastDaysEntity> weatherDaysForecasts, Calendar calendar,
                                 int hour) {
        //星期几
        List<String> weeks = new ArrayList<>();
        //日
        List<String> days = new ArrayList<>();
        //白天天气类型
        List<String> weatherDayTypes = new ArrayList<>();
        //晚上天气类型
        List<String> weatherNightTypes = new ArrayList<>();
        //最高温
        List<String> weatherHighTemps = new ArrayList<>();
        //最低温
        List<String> weatherLowTemps = new ArrayList<>();
        //解析日期数据、天气类型、温度等数据并填充
        for (WeatherForecastDaysEntity weatherDayForecast : weatherDaysForecasts) {
            if (weatherDayForecast != null) {
                String[] results = DateUtil.parseDate(weatherDayForecast.getmDate());
                if (2 == results.length) {
                    days.add(results[0]);
                    weeks.add(results[1]);
                }
                weatherDayTypes.add(weatherDayForecast.getmTypeDay());
                weatherNightTypes.add(weatherDayForecast.getmTypeNight());
                weatherHighTemps.add(weatherDayForecast.getmHighTemperature());
                weatherLowTemps.add(weatherDayForecast.getmLowTemperature());
            }
        }
        //设置星期几
        if (!weeks.isEmpty()) {
            tvForecastWeek1.setText(getString(R.string.yesterday));
            tvForecastWeek2.setText(getString(R.string.today));
            tvForecastWeek3.setText(weeks.get(2));
            tvForecastWeek4.setText(weeks.get(3));
            tvForecastWeek5.setText(weeks.get(4));
            tvForecastWeek6.setText(weeks.get(5));
        }

        //月
        List<String> months = new ArrayList<>();
        for (int i = 1; i <= 6; i++) {
            if (1 == i)
            //将当前日期往前推1天，以便计算昨天的日期
            {
                calendar.add(Calendar.DATE, -1);
            } else
            //将当前日期往后推1天
            {
                calendar.add(Calendar.DATE, 1);
            }
            /**
             * 这里需要注意：用Calendar返回的月是以0位起始，即：
             * 0-一月
             * 1-二月
             * 2-三月
             * ……
             * 故这里需要加1
             */
            months.add(calendar.get(Calendar.MONTH) + 1 + "");
        }
        //设置日历
        if (!months.isEmpty()) {
            tvForecastDate1.setText(
                    String.format(getString(R.string.mm_dd), months.get(0), days.get(0)));
            tvForecastDate2.setText(
                    String.format(getString(R.string.mm_dd), months.get(1), days.get(1)));
            tvForecastDate3.setText(
                    String.format(getString(R.string.mm_dd), months.get(2), days.get(2)));
            tvForecastDate4.setText(
                    String.format(getString(R.string.mm_dd), months.get(3), days.get(3)));
            tvForecastDate5.setText(
                    String.format(getString(R.string.mm_dd), months.get(4), days.get(4)));
            tvForecastDate6.setText(
                    String.format(getString(R.string.mm_dd), months.get(5), days.get(5)));
        }
        //设置白天天气图片
        ivWeatherForecastDay1.setImageResource(
                Utils.getWeatherImageId(weatherDayTypes.get(0), true));
        ivWeatherForecastDay2.setImageResource(
                Utils.getWeatherImageId(weatherDayTypes.get(1), true));
        ivWeatherForecastDay3.setImageResource(
                Utils.getWeatherImageId(weatherDayTypes.get(2), true));
        ivWeatherForecastDay4.setImageResource(
                Utils.getWeatherImageId(weatherDayTypes.get(3), true));
        ivWeatherForecastDay5.setImageResource(
                Utils.getWeatherImageId(weatherDayTypes.get(4), true));
        ivWeatherForecastDay6.setImageResource(
                Utils.getWeatherImageId(weatherDayTypes.get(5), true));
        //设置白天天气类型
        tvWeatherForecastTypeDay1.setText(weatherDayTypes.get(0));
        tvWeatherForecastTypeDay2.setText(weatherDayTypes.get(1));
        tvWeatherForecastTypeDay3.setText(weatherDayTypes.get(2));
        tvWeatherForecastTypeDay4.setText(weatherDayTypes.get(3));
        tvWeatherForecastTypeDay5.setText(weatherDayTypes.get(4));
        tvWeatherForecastTypeDay6.setText(weatherDayTypes.get(5));
        //设置天气的折线图
        int size = weatherHighTemps.size();
        //白天温度集合
        int[] dayTemperatures = new int[size];
        //晚上温度集合
        int[] nightTemperatures = new int[size];
        for (int j = 0; j < size; j++) {
            dayTemperatures[j] = Utils.parseTemperature(weatherHighTemps.get(j));
            nightTemperatures[j] = Utils.parseTemperature(weatherLowTemps.get(j));
        }
        lineChart.setTemperatureDay(dayTemperatures);
        lineChart.setTemperatureNight(nightTemperatures);
        lineChart.invalidate();
        //设置夜晚天气类型
        tvWeatherForecastTypeNight1.setText(weatherNightTypes.get(0));
        tvWeatherForecastTypeNight2.setText(weatherNightTypes.get(1));
        tvWeatherForecastTypeNight3.setText(weatherNightTypes.get(2));
        tvWeatherForecastTypeNight4.setText(weatherNightTypes.get(3));
        tvWeatherForecastTypeNight5.setText(weatherNightTypes.get(4));
        tvWeatherForecastTypeNight6.setText(weatherNightTypes.get(5));
        //设置晚上天气图片
        ivWeatherForecastNight1.setImageResource(
                Utils.getWeatherImageId(weatherNightTypes.get(0), false));
        ivWeatherForecastNight2.setImageResource(
                Utils.getWeatherImageId(weatherNightTypes.get(1), false));
        ivWeatherForecastNight3.setImageResource(
                Utils.getWeatherImageId(weatherNightTypes.get(2), false));
        ivWeatherForecastNight4.setImageResource(
                Utils.getWeatherImageId(weatherNightTypes.get(3), false));
        ivWeatherForecastNight5.setImageResource(
                Utils.getWeatherImageId(weatherNightTypes.get(4), false));
        ivWeatherForecastNight6.setImageResource(
                Utils.getWeatherImageId(weatherNightTypes.get(5), false));
        //设置风向、风力
        if (hour < 18) {//白天
            tvWeatherForecastWindDirection1.setText(
                    weatherDaysForecasts.get(0).getmWindDirectionDay());
            tvWeatherForecastWindDirection2.setText(
                    weatherDaysForecasts.get(1).getmWindDirectionDay());
            tvWeatherForecastWindDirection3.setText(
                    weatherDaysForecasts.get(2).getmWindDirectionDay());
            tvWeatherForecastWindDirection4.setText(
                    weatherDaysForecasts.get(3).getmWindDirectionDay());
            tvWeatherForecastWindDirection5.setText(
                    weatherDaysForecasts.get(4).getmWindDirectionDay());
            tvWeatherForecastWindDirection6.setText(
                    weatherDaysForecasts.get(5).getmWindDirectionDay());

            tvWeatherForecastWindPower1.setText(weatherDaysForecasts.get(0).getmWindPowerDay());
            tvWeatherForecastWindPower2.setText(weatherDaysForecasts.get(1).getmWindPowerDay());
            tvWeatherForecastWindPower3.setText(weatherDaysForecasts.get(2).getmWindPowerDay());
            tvWeatherForecastWindPower4.setText(weatherDaysForecasts.get(3).getmWindPowerDay());
            tvWeatherForecastWindPower5.setText(weatherDaysForecasts.get(4).getmWindPowerDay());
            tvWeatherForecastWindPower6.setText(weatherDaysForecasts.get(5).getmWindPowerDay());
        } else {//晚上
            tvWeatherForecastWindDirection1.setText(
                    weatherDaysForecasts.get(0).getmWindDirectionNight());
            tvWeatherForecastWindDirection2.setText(
                    weatherDaysForecasts.get(1).getmWindDirectionNight());
            tvWeatherForecastWindDirection3.setText(
                    weatherDaysForecasts.get(2).getmWindDirectionNight());
            tvWeatherForecastWindDirection4.setText(
                    weatherDaysForecasts.get(3).getmWindDirectionNight());
            tvWeatherForecastWindDirection5.setText(
                    weatherDaysForecasts.get(4).getmWindDirectionNight());
            tvWeatherForecastWindDirection6.setText(
                    weatherDaysForecasts.get(5).getmWindDirectionNight());

            tvWeatherForecastWindPower1.setText(weatherDaysForecasts.get(0).getmWindPowerNight());
            tvWeatherForecastWindPower2.setText(weatherDaysForecasts.get(1).getmWindPowerNight());
            tvWeatherForecastWindPower3.setText(weatherDaysForecasts.get(2).getmWindPowerNight());
            tvWeatherForecastWindPower4.setText(weatherDaysForecasts.get(3).getmWindPowerNight());
            tvWeatherForecastWindPower5.setText(weatherDaysForecasts.get(4).getmWindPowerNight());
            tvWeatherForecastWindPower6.setText(weatherDaysForecasts.get(5).getmWindPowerNight());
        }
    }

    /**
     * 设置天气类型
     *
     * @param hour                当前时间小时数
     * @param weatherDaysForecast 天气信息
     */
    private void setWeatherType(int hour, WeatherForecastDaysEntity weatherDaysForecast) {
        if (hour < 18) //白天
        {
            tvWeatherType.setText(weatherDaysForecast.getmTypeDay());
        } else //晚上
        {
            tvWeatherType.setText(weatherDaysForecast.getmTypeNight());
        }
    }

    /**
     * 设置温度
     *
     * @param weatherInfo 天气信息
     */
    private void setTemperature(WeatherEntity weatherInfo) {
        String temperature = weatherInfo.getmTemperature();
        if (temperature != null) {
            if (temperature.contains("-")) {//负数
                ivSymbol.setVisibility(View.VISIBLE);
                ivSymbol.setImageResource(R.drawable.ic_minus);
                if (1 == (temperature.length() - 1)) {//1位
                    setTemperatureImage(Integer.parseInt(temperature.substring(1, 2)), ivNumber2);
                } else {//2位
                    ivNumber1.setVisibility(View.VISIBLE);
                    setTemperatureImage(Integer.parseInt(temperature.substring(1, 2)), ivNumber1);
                    setTemperatureImage(Integer.parseInt(temperature.substring(2, 3)), ivNumber2);
                }
            } else {//正数
                if (1 == temperature.length()) {//1位
                    setTemperatureImage(Integer.parseInt(temperature), ivNumber2);
                } else {//2位
                    ivNumber1.setVisibility(View.VISIBLE);
                    setTemperatureImage(Integer.parseInt(temperature.substring(0, 1)), ivNumber1);
                    setTemperatureImage(Integer.parseInt(temperature.substring(1, 2)), ivNumber2);
                }
            }
        } else {
            ivSymbol.setImageResource(R.drawable.number_0);
            ivNumber1.setImageResource(R.drawable.number_0);
            ivNumber2.setImageResource(R.drawable.number_0);
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
    private void setAqi(WeatherEntity weatherInfo) {
        String quality = weatherInfo.getmAQ();
        if (quality == null) {
            return;
        }
        Drawable drawableLeft = ContextCompat.getDrawable(getContext(),
                Utils.getQualityImageId(quality));
        Drawable drawableRight = ContextCompat.getDrawable(getContext(), R.drawable.ic_right);
        if (drawableLeft != null) {
            drawableLeft.setBounds(0, 0, drawableLeft.getMinimumWidth(),
                    drawableLeft.getMinimumHeight());
        }
        if (drawableRight != null) {
            drawableRight.setBounds(0, 0, drawableRight.getMinimumWidth(),
                    drawableRight.getMinimumHeight());
        }
        tvAqi.setCompoundDrawables(drawableLeft, null, drawableRight, null);
        tvAqi.setText(weatherInfo.getmAQI() + " " + quality);
    }

    /**
     * 设置生活指数
     */
    private void setLifeIndex(List<LifeIndexEntity> lifeIndexList) {
        for (LifeIndexEntity lifeIndex : lifeIndexList) {
            switch (lifeIndex.getmIndexName()) {
                case "晨练指数":
                    tvWeatherLifeIndexExercise.setText(lifeIndex.getmIndexSuggestion());
                    break;
                case "穿衣指数":
                    tvWeatherLifeIndexClothe.setText(lifeIndex.getmIndexSuggestion());
                    break;
                case "舒适度":
                    tvWeatherLifeIndexComfort.setText(lifeIndex.getmIndexSuggestion());
                    break;
                case "感冒指数":
                    tvWeatherLifeIndexInfluenza.setText(lifeIndex.getmIndexSuggestion());
                    break;
                case "洗车指数":
                    tvWeatherLifeIndexWashCar.setText(lifeIndex.getmIndexSuggestion());
                    break;
                case "紫外线强度":
                    tvWeatherLifeIndexUltraviolet.setText(lifeIndex.getmIndexSuggestion());
                    break;
            }
        }
    }

    /**
     * 设置生活指数点击事件
     *
     * @param v 被点击的视图
     */
    @OnClick({R.id.rl_exercise, R.id.rl_clothe, R.id.rl_comfort, R.id.rl_influenza,
            R.id.rl_wash_car, R.id
            .rl_ultraviolet, R.id.iv_add_city, R.id.tv_city})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_city:
                // Intent intent = new Intent(getActivity(), AddCityActivity.class);
                // Intent intent = new Intent(getActivity(), CityManageActivity.class);
                // startActivityForResult(intent, MAIN_FRAGMENT_REQUEST_CODE);
                break;
            case R.id.iv_add_city:
                // Intent intent = new Intent(getActivity(), AddCityActivity.class);
                Intent intent = new Intent(getActivity(), CityManageActivity.class);
                startActivityForResult(intent, Constant.MAIN_FRAGMENT_REQUEST_CODE);
                break;
            case R.id.rl_exercise:
                weatherLifeIndexClicked(getString(R.string.main_fragment_tv_life_index_exercise));
                break;
            case R.id.rl_clothe:
                weatherLifeIndexClicked(getString(R.string.main_fragment_tv_life_index_clothes));
                break;
            case R.id.rl_comfort:
                weatherLifeIndexClicked(getString(R.string.main_fragment_tv_life_index_comfort));
                break;
            case R.id.rl_influenza:
                weatherLifeIndexClicked(getString(R.string.main_fragment_tv_life_index_influenza));
                break;
            case R.id.rl_wash_car:
                weatherLifeIndexClicked(getString(R.string.main_fragment_tv_life_index_wash_car));
                break;
            case R.id.rl_ultraviolet:
                weatherLifeIndexClicked(
                        getString(R.string.main_fragment_tv_life_index_ultraviolet));
                break;
        }
    }

    /**
     * 点击生活指数显示详情对话框
     *
     * @param indexName 生活指数名
     */
    private void weatherLifeIndexClicked(String indexName) {
        List<LifeIndexEntity> weatherLifeIndexList;
        weatherLifeIndexList = mLifeIndexDao.query(indexName);
        if (!weatherLifeIndexList.isEmpty() && 1 == weatherLifeIndexList.size()) {
            String msg = weatherLifeIndexList.get(0).getmIndexDetail();
            showDialog(indexName, msg);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (Constant.MAIN_FRAGMENT_REQUEST_CODE == requestCode
                && resultCode == Activity.RESULT_OK) {
            CityManage cityManage = (CityManage) data.getSerializableExtra(Constant.CHOSEN_CITY);
            mCity = cityManage.getCityName();
            refreshData();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    /**
     * 位置监听
     */
    private class BaiDuLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            //监听一次后注销
            mLocationClient.stop();
            mLocationClient.unRegisterLocationListener(mBaiDuLocationListener);

            switch (bdLocation.getLocType()) {
                case BDLocation.TypeGpsLocation:
                    LogUtil.i(TAG, getString(R.string.ok_location_gps));
                    break;
                case BDLocation.TypeOffLineLocation:
                    LogUtil.i(TAG, getString(R.string.ok_location_offline));
                    break;
                case BDLocation.TypeNetWorkLocation:
                    LogUtil.i(TAG, getString(R.string.ok_location_net));
                    break;
                case BDLocation.TypeCriteriaException:
                    LogUtil.e(TAG, getString(R.string.error_location));
                    break;
            }
            if (bdLocation != null) {
                mCity = bdLocation.getCity();
            }
            mProgressDialog.dismiss();
            //截掉返回城市的行政单位：“市”、"县"、“镇”等
            mCity = mCity.substring(0, mCity.length() - 1);
            //使用SharedPreferences保存默认城市
            SharedPreferences sharedPreferences =
                    getActivity().getApplication().getSharedPreferences(Constant
                            .DEFAULT_PREFERENCE, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(Constant.DEFAULT_CITY, mCity);
            editor.apply();
            // 刷新数据
            refreshData();
        }
    }
}
