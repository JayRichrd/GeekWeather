package com.yulong.jiangyu.geekweather.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.yulong.jiangyu.geekweather.R;
import com.yulong.jiangyu.geekweather.adapter.CityManageAdapter;
import com.yulong.jiangyu.geekweather.bean.CityInfo;
import com.yulong.jiangyu.geekweather.bean.CityManage;
import com.yulong.jiangyu.geekweather.bean.WeatherInfo;
import com.yulong.jiangyu.geekweather.constant.Constant;
import com.yulong.jiangyu.geekweather.dao.CityMangeDao;
import com.yulong.jiangyu.geekweather.listener.HttpCallbackListener;
import com.yulong.jiangyu.geekweather.util.WeatherInfoUtil;
import com.yulong.jiangyu.geekweather.util.WebUtil;

import java.io.ByteArrayInputStream;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class CityManageActivity extends AppCompatActivity {

    // 打开Activity的请求码
    private static final int CITY_MANAGE_REQUEST_CODE = 1;
    // 更新city_manage界面
    private static final int UPDATE_CITY_MANAGE = 2;

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.iv_refresh)
    ImageView ivRefresh;
    @BindView(R.id.iv_edit)
    ImageView ivEdit;
    @BindView(R.id.gv_city_manage)
    GridView gvCityManage;
    @BindView(R.id.iv_ok)
    ImageView ivOk;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    private Unbinder unbinder;

    // 城市列表
    private List<CityManage> mCityManageList;
    // 操作数据库的实例
    private CityMangeDao mCityMangeDao;
    // 城市管理列表适配器
    private CityManageAdapter mCityManageAdapter;
    //更新UI的Handler
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case UPDATE_CITY_MANAGE:
                    updateUI();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_manage);
        unbinder = ButterKnife.bind(this);
        mCityMangeDao = new CityMangeDao(this);
        initAdapter();
        initViews();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == CITY_MANAGE_REQUEST_CODE) {
            CityInfo cityInfo = (CityInfo) data.getSerializableExtra(Constant.CHOSEN_CITY);
            final String weatherCode = cityInfo.getWeatherCode();
            // 禁止点击城市列表
            gvCityManage.setOnItemClickListener(null);
            // 请求天气数据
            WebUtil.requestWeatherData(this, weatherCode, true, new HttpCallbackListener() {
                @Override
                public void onFinished(Object response) {
                    String weatherInfoStr = (String) response;
                    // 解析处理获取的天气数据
                    WeatherInfo weatherInfo = WeatherInfoUtil.handleWeatherInfo(new ByteArrayInputStream
                            (weatherInfoStr.getBytes()));
                    // 插入数据库
                    boolean result = mCityMangeDao.insert(new CityManage(null, weatherInfo.getmCity(), weatherInfo
                            .getmWeatherDaysForecasts().get(1).getmHighTemperature(), weatherInfo
                            .getmWeatherDaysForecasts().get(1).getmLowTemperature(), weatherInfo
                            .getmWeatherDaysForecasts().get(1).getmTypeDay(),
                            weatherInfo.getmWeatherDaysForecasts().get(1).getmTypeNight(), weatherCode));
                    if (result) { // 天气数据刷新成功，通知更新UI
                        /**
                         * 使用Handler更新UI界面
                         * 此时还在非UI线程，不能直接更新界面
                         */
                        Message msg = new Message();
                        msg.what = UPDATE_CITY_MANAGE;
                        mHandler.sendMessage(msg);
                    }
                }

                @Override
                public void onError(Throwable t) {

                }
            });
        }
    }

    /**
     * 初始化适配器
     */
    private void initAdapter() {
        mCityManageList = mCityMangeDao.queryAll();
        // 最后一个添加城市的item
        mCityManageList.add(new CityManage());
        // TODO don't forget to delete test data
        CityManage cityManageDemo1 = new CityManage();
        cityManageDemo1.setLocationCity("自动定位");
        cityManageDemo1.setCityName("深圳");
        cityManageDemo1.setTempLow("23");
        cityManageDemo1.setTempTop("28");
        cityManageDemo1.setWeatherTypeDay("阵雨");
        cityManageDemo1.setWeatherTypeNight("阵雨");
        cityManageDemo1.setWeatherCode("101280601");
//        mCityManageList.add(cityManageDemo1);
        mCityManageAdapter = new CityManageAdapter(this, 0, mCityManageList);

    }

    /**
     * 初始化布局
     */
    private void initViews() {
        tvTitle.setText(R.string.city_manage);
        ivRefresh.setVisibility(View.VISIBLE);
        ivEdit.setVisibility(View.VISIBLE);

        gvCityManage.setAdapter(mCityManageAdapter);
        gvCityManage.setOnItemClickListener(new OnItemClickListenerImpl());
    }

    /**
     * 更新UI界面
     */
    private void updateUI() {
        if (!mCityManageList.isEmpty())
            mCityManageList.clear();
        /**
         * 从其他Activity返回时不在回调onCreate
         * 所以就不会回调initAdapter和initViews
         * 这里要刷新界面就得重新主动调用initAdapter函数和initViews函数
         * 从而使得重新绑定数据
         */
        initAdapter();
        initViews();
        mCityManageAdapter.notifyDataSetChanged();
    }

    @OnClick({R.id.iv_back, R.id.iv_refresh, R.id.iv_edit, R.id.iv_ok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back: // 直接返回
                finish();
                break;
            case R.id.iv_refresh: // 刷新数据
                refresh();
                break;
            case R.id.iv_edit: // 编辑
                mCityManageAdapter.setVisibleForDeleteBtn(true);
                mCityManageAdapter.notifyDataSetChanged();
                ivEdit.setVisibility(View.GONE);
                ivOk.setVisibility(View.VISIBLE);
                break;
            case R.id.iv_ok: // 编辑完成
                mCityManageAdapter.setVisibleForDeleteBtn(false);
                mCityManageAdapter.notifyDataSetChanged();
                ivEdit.setVisibility(View.VISIBLE);
                ivOk.setVisibility(View.GONE);
                break;
        }
    }

    /**
     * 刷新数据
     */
    private void refresh() {
        for (final CityManage cityManage : mCityManageList) {
            WebUtil.requestWeatherData(this, cityManage.getWeatherCode(), true, new HttpCallbackListener() {
                @Override
                public void onFinished(Object response) {
                    String weatherInfoStr = (String) response;
                    // 解析处理获取的天气数据
                    WeatherInfo weatherInfo = WeatherInfoUtil.handleWeatherInfo(new ByteArrayInputStream
                            (weatherInfoStr.getBytes()));
                    // 刷新数据
                    mCityMangeDao.update(new CityManage(null, weatherInfo.getmCity(), weatherInfo
                            .getmWeatherDaysForecasts().get(1).getmHighTemperature(), weatherInfo
                            .getmWeatherDaysForecasts().get(1).getmLowTemperature(), weatherInfo
                            .getmWeatherDaysForecasts().get(1).getmTypeDay(),
                            weatherInfo.getmWeatherDaysForecasts().get(1).getmTypeNight(), cityManage.getWeatherCode
                            ()));
                }

                @Override
                public void onError(Throwable t) {

                }
            });
        }
        updateUI();
    }

    class OnItemClickListenerImpl implements AdapterView.OnItemClickListener {

        /**
         * Callback method to be invoked when an item in this AdapterView has
         * been clicked.
         * <p>
         * Implementers can call getItemAtPosition(position) if they need
         * to access the data associated with the selected item.
         *
         * @param parent   The AdapterView where the click happened.
         * @param view     The view within the AdapterView that was clicked (this
         *                 will be a view provided by the adapter)
         * @param position The position of the view in the adapter.
         * @param id       The row id of the item that was clicked.
         */
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (position == mCityManageList.size() - 1) {// 最后一项添加城市
                Intent intent = new Intent(CityManageActivity.this, AddCityActivity.class);
                startActivityForResult(intent, CITY_MANAGE_REQUEST_CODE);
            } else { // 点击返回
                Intent intent = getIntent();
                intent.putExtra(Constant.CHOSEN_CITY, mCityManageList.get(position));
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        }
    }

}
