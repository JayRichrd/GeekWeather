package com.yulong.jiangyu.geekweather.ui;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
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
import com.yulong.jiangyu.geekweather.bean.WeatherInfo;
import com.yulong.jiangyu.geekweather.bean.WeatherLifeIndex;
import com.yulong.jiangyu.geekweather.constant.Constant;
import com.yulong.jiangyu.geekweather.dao.WeatherInfoDatabaseHelper;
import com.yulong.jiangyu.geekweather.impl.DateImpl;
import com.yulong.jiangyu.geekweather.listener.HttpCallbackListener;
import com.yulong.jiangyu.geekweather.util.LogUtil;
import com.yulong.jiangyu.geekweather.util.WeatherInfoUtil;
import com.yulong.jiangyu.geekweather.util.WebUtil;

import java.io.ByteArrayInputStream;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
    private DateImpl mDateImpl = null;
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
                    case Constant.UPDATE_UI:
                        WeatherInfo weatherInfo = (WeatherInfo) msg.getData().getSerializable(Constant.WEATHER_INFO);
                        updateUI(weatherInfo);
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
     * 请求日历时间
     *
     * @param dateStr 日期
     */
    private void requestDate(String dateStr) {
        //聚合数据万年历网络请求
        Retrofit dateRetrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl
                (Constant.dateBaseUrl).build();
        mDateImpl = dateRetrofit.create(DateImpl.class);
        //转换日期
        DateFormat dateFormatSource = new SimpleDateFormat(getString(R.string.data_source));
        DateFormat dateFormatTarget = new SimpleDateFormat(getString(R.string.data_target));
        Date date = null;
        try {
            date = dateFormatSource.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (date != null) {
            mDateImpl.getDate(dateFormatTarget.format(date), Constant.dateKey).enqueue(new Callback<DateInfo>() {
                @Override
                public void onResponse(Call<DateInfo> call, Response<DateInfo> response) {
                    DateInfo dateInfo = response.body();
                    if (Constant.errorCode == dateInfo.getError_code()) {//检查返回的结果
                        DateInfo.ResultBean.DataBean dataBean = dateInfo.getResult().getData();
                    }
                }

                @Override
                public void onFailure(Call<DateInfo> call, Throwable t) {
                    LogUtil.e(LOG_TAG, getString(R.string.error_get_data));
                }
            });
        }
    }

    /**
     * 刷新获取天气数据
     */
    private void refreshWeather() {
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
                msg.what = Constant.UPDATE_UI;
                mHandler.sendMessage(msg);
            }

            @Override
            public void onError(Exception e) {
                LogUtil.e(LOG_TAG, e.getMessage());
            }
        });
    }

    /**
     * 更新UI界面
     *
     * @param weatherInfo 天气信息数据
     */
    private void updateUI(WeatherInfo weatherInfo) {
        tvCity.setText(weatherInfo.getmCity());

        tvUpdateTime.setText(weatherInfo.getmUpdateTime());

        tvWind.setText(weatherInfo.getmWindDirection() + weatherInfo.getmWindPower());
        tvHumidity.setText(weatherInfo.getmHumidity());
        tvAqi.setText(weatherInfo.getmAQI());
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

            refreshWeather();
        }
    }
}
