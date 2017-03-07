package com.yulong.jiangyu.geekweather.ui;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
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
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.github.mikephil.charting.charts.LineChart;
import com.j256.ormlite.dao.Dao;
import com.yulong.jiangyu.geekweather.R;
import com.yulong.jiangyu.geekweather.bean.DateInfo;
import com.yulong.jiangyu.geekweather.bean.HeWeatherInfo;
import com.yulong.jiangyu.geekweather.bean.JuHeWeatherInfo;
import com.yulong.jiangyu.geekweather.bean.WeatherUIInfo;
import com.yulong.jiangyu.geekweather.constant.Constant;
import com.yulong.jiangyu.geekweather.dao.DatabaseHelper;
import com.yulong.jiangyu.geekweather.impl.DateImpl;
import com.yulong.jiangyu.geekweather.impl.HeWeatherImpl;
import com.yulong.jiangyu.geekweather.impl.JuHeWeatherImpl;
import com.yulong.jiangyu.geekweather.util.WebUtil;

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

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {
    private static final String TAG = "MainFragment";
    //界面数据实体
    private static WeatherUIInfo weatherUIInfo = null;
    @BindView(R.id.tv_city)
    TextView tvCity;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_update_time)
    TextView tvUpdateTime;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.iv_weather)
    ImageView ivWeather;
    @BindView(R.id.tv_temperature)
    TextView tvTemperature;
    @BindView(R.id.tv_weather)
    TextView tvWeather;
    @BindView(R.id.tv_humidity)
    TextView tvHumidity;
    @BindView(R.id.tv_wind_dir)
    TextView tvWindDir;
    @BindView(R.id.tv_wind_sc)
    TextView tvWindSc;
    @BindView(R.id.tv_pm25)
    TextView tvPm25;
    @BindView(R.id.weather_chart)
    LineChart weatherChart;
    @BindView(R.id.tv_exercise)
    TextView tvExercise;
    @BindView(R.id.rl_exercise)
    RelativeLayout rlExercise;
    @BindView(R.id.tv_clothe)
    TextView tvClothe;
    @BindView(R.id.rl_clothe)
    RelativeLayout rlClothe;
    @BindView(R.id.tv_comfort)
    TextView tvComfort;
    @BindView(R.id.rl_comfort)
    RelativeLayout rlComfort;
    @BindView(R.id.tv_influenza)
    TextView tvInfluenza;
    @BindView(R.id.rl_influenza)
    RelativeLayout rlInfluenza;
    @BindView(R.id.tv_car)
    TextView tvCar;
    @BindView(R.id.rl_car)
    RelativeLayout rlCar;
    @BindView(R.id.tv_ultraviolet)
    TextView tvUltraviolet;
    @BindView(R.id.rl_ultraviolet)
    RelativeLayout rlUltraviolet;
    @BindView(R.id.frame_content)
    FrameLayout frameContent;
    @BindView(R.id.ll_root_content)
    LinearLayout llRootContent;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.dl_main)
    DrawerLayout dlMain;
    //数据库操作
    Dao<WeatherUIInfo, Integer> dao = null;
    private Unbinder mUnbinder;
    //百度定位服务
    private LocationClient mLocationClient = null;
    private MyLocationListener myLocationListener = new MyLocationListener();
    //定位提示框
    private ProgressDialog mProgressDialog = null;
    //当前城市
    private String mCity = "深圳";
    //请求天气接口
    private HeWeatherImpl heWeatherImpl = null;
    private JuHeWeatherImpl juHeWeatherImpl = null;
    //请求日历接口
    private DateImpl dateImpl = null;
    //数据库
    private DatabaseHelper databaseHelper = null;
    private ActionBarDrawerToggle mActionBarDrawerToggle = null;
    //抽屉Drawer全部被拉出时的宽度
    private int mDrawerWidth = 0;
    //抽屉被拉出部分的宽度
    private float mScrollWidth = 0f;

    public MainFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        init();
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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //初始化控件
    private void init() {
        //初始化
        try {
            databaseHelper = DatabaseHelper.getInstance(getActivity().getApplicationContext());
            dao = databaseHelper.getDao();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        weatherUIInfo = new WeatherUIInfo();

        toolbar.setTitleTextColor(Color.WHITE);
        //将默认标题设置为空，从而使用textview的内容
        toolbar.setTitle("");
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        //设置Drawerlayout的滑入滑出
        mActionBarDrawerToggle = new ActionBarDrawerToggle(getActivity(), dlMain, toolbar, R.string.drawer_open, R
                .string
                .drawer_close) {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                Log.i(TAG, "***onDrawerSlide***:Drawer's position changes");
                Log.i(TAG, "***onDrawerSlide***:slideOffset=" + slideOffset);
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

            //根据id返回字符串内容
            private String getString(int resourceID) {
                return getResources().getString(resourceID);
            }
        });
        //初始化百度定位服务
        mLocationClient = new LocationClient(getActivity().getApplicationContext());
        mLocationClient.registerLocationListener(myLocationListener);
        initLocation();
        initRetrofit();
        initDialog();
        mProgressDialog.show();
        mLocationClient.start();
        Log.i(TAG, "开始定位……");
    }

    //初始化定位进度对话框
    private void initDialog() {
        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setMessage("正在努力定位中……");
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        //是否可以使用返回键取消
        mProgressDialog.setCancelable(false);
    }

    //初始化定位服务
    private void initLocation() {
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
        mLocationClient.setLocOption(locationClientOption);
    }

    //显示信息对话框
    private void showDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setNeutralButton(R.string.btn_confirm, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.i(TAG, "***setNeutralButton/setNeutralButton***:点击了按钮");
            }
        });
        builder.show();
    }

    //初始化网络请求
    private void initRetrofit() {
        //和风天气网络请求
        Retrofit heWeatherRetrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl
                (Constant.heWeatherBaseUrl).build();
        heWeatherImpl = heWeatherRetrofit.create(HeWeatherImpl.class);
        //聚合数据天气网络请求
        Retrofit juHeWeatherRetrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl
                (Constant.juHeWeatherBaseUrl).build();
        juHeWeatherImpl = juHeWeatherRetrofit.create(JuHeWeatherImpl.class);
        //聚合数据万年历网络请求
        Retrofit dateRetrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl
                (Constant.dateBaseUrl).build();
        dateImpl = dateRetrofit.create(DateImpl.class);

    }

    //请求天气数据
    private void requestWeather(String city, int requestType) {
        //异步请求网络数据
        switch (requestType) {
            case Constant.HEWEATHER://和风天气
                requestHeWeather(city);
                break;
            case Constant.JUHEWEATHER://聚合数据天气
                requestJuHeWeather(city);
                break;
            default:
                break;
        }
    }

    //请求和风天气数据
    private void requestHeWeather(final String city) {
        heWeatherImpl.getWeather(city, Constant.heWeatherKey).enqueue(new Callback<HeWeatherInfo>() {
            @Override
            public void onResponse(Call<HeWeatherInfo> call, Response<HeWeatherInfo> response) {
                HeWeatherInfo weatherInfo = response.body();
                if (weatherInfo.getHeWeather5().get(0).getStatus().equals(Constant.statusOK)) {//检查返回的结果
                    HeWeatherInfo.HeWeather5Bean heWeather5Bean = weatherInfo.getHeWeather5().get(0);
                    //城市
                    weatherUIInfo.setCity(heWeather5Bean.getBasic().getCity());
                    //当前温度
                    weatherUIInfo.setTemperature(heWeather5Bean.getNow().getTmp() + "℃");
                    //当前天气
                    weatherUIInfo.setWeather(heWeather5Bean.getNow().getCond().getTxt());
                    //湿度
                    weatherUIInfo.setHumidity(heWeather5Bean.getNow().getHum() + "%");
                    //风向
                    weatherUIInfo.setWindDir(heWeather5Bean.getNow().getWind().getDir());
                    //风力
                    weatherUIInfo.setWindSc(heWeather5Bean.getNow().getWind().getSc() + "级");
                    //pm2.5
                    weatherUIInfo.setPm25(heWeather5Bean.getAqi().getCity().getPm25());
                    HeWeatherInfo.HeWeather5Bean.SuggestionBean suggestionBean = weatherInfo.getHeWeather5()
                            .get(0).getSuggestion();
                    //锻炼
                    weatherUIInfo.setSport_brf(suggestionBean.getSport().getBrf());
                    weatherUIInfo.setSport_txt(suggestionBean.getSport().getTxt());
                    //穿衣
                    weatherUIInfo.setDrsg_brf(suggestionBean.getDrsg().getBrf());
                    weatherUIInfo.setDrsg_txt(suggestionBean.getDrsg().getTxt());
                    //舒适度
                    weatherUIInfo.setComf_brf(suggestionBean.getComf().getBrf());
                    weatherUIInfo.setComf_txt(suggestionBean.getComf().getTxt());
                    //感冒指数
                    weatherUIInfo.setFlu_brf(suggestionBean.getFlu().getBrf());
                    weatherUIInfo.setFlu_txt(suggestionBean.getFlu().getTxt());
                    //洗车指数
                    weatherUIInfo.setCw_brf(suggestionBean.getCw().getBrf());
                    weatherUIInfo.setCw_txt(suggestionBean.getCw().getTxt());
                    //紫外线指数
                    weatherUIInfo.setUv_brf(suggestionBean.getUv().getBrf());
                    weatherUIInfo.setUv_txt(suggestionBean.getUv().getTxt());
                    //更新时间yyyy-MM-dd HH:mm:ss
                    String time = heWeather5Bean.getBasic().getUpdate().getLoc();
                    weatherUIInfo.setTime(time);
                    requestDate(time);
                } else
                    requestWeather(city, Constant.JUHEWEATHER);
            }

            @Override
            public void onFailure(Call<HeWeatherInfo> call, Throwable t) {
                Log.e(TAG, "获取天气数据失败！");
            }
        });
    }

    //请求聚合数据天气
    private void requestJuHeWeather(String city) {
        juHeWeatherImpl.getWeather(city, Constant.juHeWeatherKey).enqueue(new Callback<JuHeWeatherInfo>() {
            @Override
            public void onResponse(Call<JuHeWeatherInfo> call, Response<JuHeWeatherInfo> response) {
                JuHeWeatherInfo weatherInfo1 = response.body();
                if (Constant.errorCode == weatherInfo1.getError_code()) {//检查返回状态
                    JuHeWeatherInfo.ResultBean.DataBean dataBean = weatherInfo1.getResult().getData();
                    //城市
                    weatherUIInfo.setCity(dataBean.getRealtime().getCity_name());
                    //温度
                    weatherUIInfo.setTemperature(dataBean.getRealtime().getWeather().getTemperature() + "℃");
                    //当前天气
                    weatherUIInfo.setWeather(dataBean.getRealtime().getWeather().getInfo());
                    //湿度
                    weatherUIInfo.setHumidity(dataBean.getRealtime().getWeather().getHumidity() + "%");
                    //风向
                    weatherUIInfo.setWindDir(dataBean.getRealtime().getWind().getDirect());
                    //风力
                    weatherUIInfo.setWindSc(dataBean.getRealtime().getWind().getPower());
                    //pm2.5
                    weatherUIInfo.setPm25(dataBean.getPm25().getPm25().getPm25());
                    JuHeWeatherInfo.ResultBean.DataBean.LifeBean.InfoBean infoBean = weatherInfo1.getResult().getData()
                            .getLife().getInfo();
                    //锻炼
                    weatherUIInfo.setSport_brf(infoBean.getYundong().get(0));
                    weatherUIInfo.setSport_txt(infoBean.getYundong().get(1));
                    //穿衣
                    weatherUIInfo.setDrsg_brf(infoBean.getChuanyi().get(0));
                    weatherUIInfo.setDrsg_txt(infoBean.getChuanyi().get(1));
                    //舒适度
                    weatherUIInfo.setComf_brf(infoBean.getKongtiao().get(0));
                    weatherUIInfo.setComf_txt(infoBean.getKongtiao().get(1));
                    //感冒指数
                    weatherUIInfo.setFlu_brf(infoBean.getGanmao().get(0));
                    weatherUIInfo.setFlu_txt(infoBean.getGanmao().get(1));
                    //洗车指数
                    weatherUIInfo.setCw_brf(infoBean.getXiche().get(0));
                    weatherUIInfo.setCw_txt(infoBean.getXiche().get(1));
                    //紫外线指数
                    weatherUIInfo.setUv_brf(infoBean.getZiwaixian().get(0));
                    weatherUIInfo.setUv_txt(infoBean.getZiwaixian().get(1));
                    //更新时间yyyy-MM-dd HH:mm:ss
                    String time = dataBean.getPubdate() + " " + dataBean.getPubtime();
                    weatherUIInfo.setTime(time);
                    requestDate(time);
                }
            }

            @Override
            public void onFailure(Call<JuHeWeatherInfo> call, Throwable t) {
                Log.e(TAG, "获取天气数据失败！");
            }
        });
    }

    //请求日历时间
    private void requestDate(String dateStr) {
        //转换日期
        DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        DateFormat dateFormat2 = new SimpleDateFormat("yyyy-M-d");
        Date date = null;
        try {
            date = dateFormat1.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (date != null) {
            dateImpl.getDate(dateFormat2.format(date), Constant.dateKey).enqueue(new Callback<DateInfo>() {
                @Override
                public void onResponse(Call<DateInfo> call, Response<DateInfo> response) {
                    DateInfo dateInfo = response.body();
                    if (Constant.errorCode == dateInfo.getError_code()) {//检查返回的结果
                        DateInfo.ResultBean.DataBean dataBean = dateInfo.getResult().getData();
                        //日期
                        weatherUIInfo.setDate(dataBean.getLunarYear() + "\n" + dataBean.getLunar() + "\n" + dataBean
                                .getWeekday());
                        updateUI();
                    }
                }

                @Override
                public void onFailure(Call<DateInfo> call, Throwable t) {
                    Log.e(TAG, "获取日历数据失败！");
                }
            });
        }
    }

    //更新UI界面
    private void updateUI() {
        if (weatherUIInfo != null) {
            //城市
            tvCity.setText(weatherUIInfo.getCity());
            //当前温度
            tvTemperature.setText(weatherUIInfo.getTemperature());
            //当前天气
            tvWeather.setText(weatherUIInfo.getWeather());
            //湿度
            tvHumidity.setText(weatherUIInfo.getHumidity());
            //风向
            tvWindDir.setText(weatherUIInfo.getWindDir());
            //风力
            tvWindSc.setText(weatherUIInfo.getWindSc());
            //pm2.5
            tvPm25.setText(weatherUIInfo.getPm25());
            //锻炼
            tvExercise.setText(weatherUIInfo.getSport_brf());
            //穿衣
            tvClothe.setText(weatherUIInfo.getDrsg_brf());
            //舒适度
            tvComfort.setText(weatherUIInfo.getComf_brf());
            //感冒指数
            tvInfluenza.setText(weatherUIInfo.getFlu_brf());
            //洗车指数
            tvCar.setText(weatherUIInfo.getCw_brf());
            //紫外线指数
            tvUltraviolet.setText(weatherUIInfo.getUv_brf());
            //更新时间yyyy-MM-dd HH:mm:ss
            tvUpdateTime.setText(weatherUIInfo.getTime() + "更新");
            //日期
            tvDate.setText(weatherUIInfo.getDate());
            //插入数据到数据库中
            try {
                dao.createOrUpdate(weatherUIInfo);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //监听点击事件
    @OnClick({R.id.rl_exercise, R.id.rl_clothe, R.id.rl_comfort, R.id.rl_influenza, R.id.rl_car, R.id.rl_ultraviolet})
    public void onClick(View v) {
        try {
            switch (v.getId()) {
                case R.id.rl_exercise://点击运动指数
                    showDialog("运动指数", dao.queryForId(Constant.ID).getSport_txt());
                    break;
                case R.id.rl_clothe://点击穿衣指数
                    showDialog("穿衣指数", dao.queryForId(Constant.ID).getDrsg_txt());
                    break;
                case R.id.rl_comfort://点击舒适指数
                    showDialog("舒适度指数", dao.queryForId(Constant.ID).getComf_txt());
                    break;
                case R.id.rl_influenza://点击感冒指数
                    showDialog("感冒指数", dao.queryForId(Constant.ID).getFlu_txt());
                    break;
                case R.id.rl_car://点击洗车指数
                    showDialog("洗车指数", dao.queryForId(Constant.ID).getCw_brf());
                    break;
                case R.id.rl_ultraviolet://点击紫外线指数
                    showDialog("紫外线指数", dao.queryForId(Constant.ID).getUv_txt());
                    break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //位置监听
    private class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            switch (bdLocation.getLocType()) {
                case BDLocation.TypeGpsLocation:
                    Log.i(TAG, "GPS定位成功");
                    break;
                case BDLocation.TypeOffLineLocation:
                    Log.i(TAG, "离线定位成功");
                    break;
                case BDLocation.TypeNetWorkLocation:
                    Log.i(TAG, "网络定位成功");
                    break;
                case BDLocation.TypeCriteriaException:
                    Log.e(TAG, "定位失败！");
                    break;
            }
            if (bdLocation != null) {
                mCity = bdLocation.getCity();
            }
            mProgressDialog.dismiss();
            //截掉返回城市的行政单位：“市”、"县"、“镇”等
            String city = mCity.substring(0, mCity.length() - 1);
            String address_weather = getString(R.string.weather_base_url, city);
            requestWeather(mCity.substring(0, mCity.length() - 1), Constant.JUHEWEATHER);
            WebUtil.requestWeather(address_weather);
        }

    }
}
