package com.yulong.jiangyu.geekweather.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.j256.ormlite.dao.Dao;
import com.yulong.jiangyu.geekweather.R;
import com.yulong.jiangyu.geekweather.adapter.CityManageAdapter;
import com.yulong.jiangyu.geekweather.bean.CityManage;
import com.yulong.jiangyu.geekweather.dao.CityManageDBHelper;
import com.yulong.jiangyu.geekweather.impl.CityManageDBListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class CityManageActivity extends AppCompatActivity {

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

    private Unbinder unbinder;
    // 城市列表
    private List<CityManage> mCityManageList;

    private Dao<CityManage, Integer> mCityManageDBDao;
    private CityManageAdapter mCityManageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_manage);
        unbinder = ButterKnife.bind(this);
        initAdapter();
        initViews();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    /**
     * 初始化适配器
     */
    private void initAdapter() {
        mCityManageList = new ArrayList<>();
        mCityManageDBDao = CityManageDBHelper.getInstance(getApplicationContext()).getCityManageDBDao();
//        try {
//            mCityManageList = mCityManageDBDao.queryForAll();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }

        // TODO don't forget to delete test data
        CityManage cityManageDemo1 = new CityManage();
        cityManageDemo1.setLocationCity("自动定位");
        cityManageDemo1.setCityName("深圳");
        cityManageDemo1.setTempLow("23");
        cityManageDemo1.setTempTop("28");
        cityManageDemo1.setWeatherType("阵雨");
        cityManageDemo1.setWeatherTypeDay("阵雨");
        cityManageDemo1.setWeatherTypeNight("阵雨");
        cityManageDemo1.setWeatherCode("101280601");
        mCityManageList.add(cityManageDemo1);

        CityManage cityManageDemo2 = new CityManage();
        cityManageDemo2.setCityName("北京");
        cityManageDemo2.setTempLow("23");
        cityManageDemo2.setTempTop("28");
        cityManageDemo2.setWeatherType("晴");
        cityManageDemo2.setWeatherTypeDay("晴");
        cityManageDemo2.setWeatherTypeNight("晴");
        cityManageDemo2.setWeatherCode("101010100");
        mCityManageList.add(cityManageDemo2);

        // 最后一个添加城市的item
        mCityManageList.add(new CityManage());

        mCityManageAdapter = new CityManageAdapter(this, 0, mCityManageList);
        mCityManageAdapter.setCityManageDBListener(new CityManageDBListener() {
            @Override
            public void onDBDataChanged() {

            }
        });

    }

    /**
     * 初始化布局
     */
    private void initViews() {
        gvCityManage.setAdapter(mCityManageAdapter);
        gvCityManage.setOnItemClickListener(new OnItemClickListenerImpl());
    }

    @OnClick({R.id.iv_back, R.id.iv_refresh, R.id.iv_edit, R.id.iv_ok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                break;
            case R.id.iv_refresh:
                break;
            case R.id.iv_edit:
                break;
            case R.id.iv_ok:
                break;
        }
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

            } else {

            }
        }
    }
}
