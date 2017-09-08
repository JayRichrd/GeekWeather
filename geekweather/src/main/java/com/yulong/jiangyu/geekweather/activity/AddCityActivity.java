package com.yulong.jiangyu.geekweather.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.yulong.jiangyu.geekweather.R;
import com.yulong.jiangyu.geekweather.adapter.HotCityAdapter;
import com.yulong.jiangyu.geekweather.constant.Constant;
import com.yulong.jiangyu.geekweather.dao.CityEntityDao;
import com.yulong.jiangyu.geekweather.dao.CityMangeEntityDao;
import com.yulong.jiangyu.geekweather.entity.CityEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * author RichardJay
 * email jiangfengfn12@163.com
 * created 2017/9/4 15:50
 * version v1.0
 * modified 2017/9/4
 * note 添加城市的Activity
 */
public class AddCityActivity extends AppCompatActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.gv_hot_city)
    GridView gvHotCity;
    @BindView(R.id.ll_hot_city)
    LinearLayout llHotCity;
    @BindView(R.id.tv_no_matched_city)
    TextView tvNoMatchedCity;
    @BindView(R.id.lv_search_city)
    ListView lvSearchCity;
    @BindView(R.id.et_search_city)
    EditText etSearchCity;
    @BindView(R.id.iv_clear)
    ImageView ivClear;

    private Unbinder mUnbinder;

    // 查询城市列表
    private List<CityEntity> mSearchedCityEntityList;
    // 热门城市适配器
    private HotCityAdapter mHotCityAdapter;
    // 搜索出的城市列表
    private List<SpannableString> mSearchList;
    // 搜索城市适配器
    private ArrayAdapter<SpannableString> mSearchCityListAdapter;
    // 数据库操作实例
    private CityEntityDao mCityEntityDao;
    private CityMangeEntityDao mCityMangeEntityDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_city);
        mUnbinder = ButterKnife.bind(this);
        initDB();
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

    @OnClick({R.id.iv_back, R.id.iv_clear})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                // 直接返回
                finish();
                break;
            case R.id.iv_clear:
                // 清除编辑框的内容
                etSearchCity.setText("");
                break;
            default:
                break;
        }
    }

    /**
     * 初始化数据库
     */
    private void initDB() {
        mCityEntityDao = new CityEntityDao(this);
        // 根据数据库中表格的记录数判定是否需要建立表
        if (0 == mCityEntityDao.queryCount())
            mCityEntityDao.createTable();
        mCityMangeEntityDao = new CityMangeEntityDao(this);
    }

    /**
     * 初始化视图
     */
    private void initView() {
        // 设置标题
        tvTitle.setText(R.string.add_city_activity_tv_title);

        // 设置搜索编辑框
        etSearchCity.addTextChangedListener(new TextWatcherImpl());

        // 设置搜索列表
        mSearchList = new ArrayList<>();
        mSearchCityListAdapter = new ArrayAdapter<>(this, R.layout.item_lv_search_city, mSearchList);
        lvSearchCity.setAdapter(mSearchCityListAdapter);
        lvSearchCity.setOnItemClickListener(new SearchCityOnItemClickListener());

        // 设置热门城市
        List<String> hotCityList = new ArrayList<>();
        if (!hotCityList.isEmpty())
            hotCityList.clear();
        // 从资源文件中获取热门城市并转存到集合hotCityList中
        String[] hotCities = getResources().getStringArray(R.array.hot_city);
        Collections.addAll(hotCityList, hotCities);
        mHotCityAdapter = new HotCityAdapter(this, hotCityList);
        gvHotCity.setAdapter(mHotCityAdapter);
        gvHotCity.setOnItemClickListener(new HotCityOnItemClickListener());


    }

    /**
     * 根据点击的结果返回给上一个界面city数据
     *
     * @param cityEntity 准备返回的city数据
     */
    private void onFinish(CityEntity cityEntity) {
        Intent intent = getIntent();
        intent.putExtra(Constant.CHOSE_CITY, cityEntity);
        setResult(Constant.ADD_CITY_ACTIVITY_RESULT_CODE, intent);
        finish();
    }

    private class TextWatcherImpl implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        /**
         * 获取一个指定范围高亮的字符串
         *
         * @param ss    原始字符串
         * @param begin 开始位置
         * @param end   结束位置
         * @param color 高亮颜色
         * @return 指定范围高亮的字符串
         */
        private SpannableString getSpannableString(String ss, int begin, int end, int color) {
            SpannableString spannableString = new SpannableString(ss);
            ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(color);
            // 将这个span应用到指定返回的字符
            spannableString.setSpan(foregroundColorSpan, begin, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            return spannableString;
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // 输入的城市名
            String cityName = s.toString();
            if (!TextUtils.isEmpty(cityName)) { // 输入不为空
                // TODO 当list正在滑动中如果清除列表程序会崩溃，因为正在滑动中的项目索引不存在
                if (!mSearchList.isEmpty())
                    mSearchList.clear();
                // 隐藏热门城市列表
                llHotCity.setVisibility(View.GONE);
                //显示清除按钮
                ivClear.setVisibility(View.VISIBLE);
                // 查询城市列表
                mSearchedCityEntityList = mCityEntityDao.queryCity(cityName.toLowerCase(), true);

                if (mSearchedCityEntityList != null) { // 匹配成功
                    for (CityEntity cityInfo : mSearchedCityEntityList) {
                        String city = cityInfo.getCity();
                        // 高亮开始(包含)
                        int begin = 0;
                        // 高亮结束(不包含)
                        int end = 0;
                        //确定高亮的范围
                        switch (cityInfo.getMatchType()) {
                            case 1: // "-"之前被匹配上
                                begin = 0;
                                end = city.indexOf("-") - 1;
                                break;
                            case 2: // "-"之后被匹配上
                                begin = city.indexOf("-") + 2;
                                end = city.length();
                                break;
                            default:
                                break;
                        }
                        // 添加查询到的城市
                        SpannableString spannableString = getSpannableString(city, begin, end,
                                getResources().getColor(R.color.colorAccent, null));
                        mSearchList.add(spannableString);
                    }
                    // 通知数据改变，刷新
                    mSearchCityListAdapter.notifyDataSetChanged();
                    // 显示搜索城市列表布局
                    lvSearchCity.setVisibility(View.VISIBLE);
                    // 默认选中第一个
                    lvSearchCity.setSelection(0);
                    // 去除无匹配结果布局
                    tvNoMatchedCity.setVisibility(View.GONE);
                } else { // 匹配失败
                    // 去除搜索城市列表布局
                    lvSearchCity.setVisibility(View.GONE);
                    // 显示无匹配结果布局
                    tvNoMatchedCity.setVisibility(View.VISIBLE);
                }
            } else { // 输入内容为空
                // 显示热门城市列表布局
                llHotCity.setVisibility(View.VISIBLE);
                // 隐藏删除按钮
                ivClear.setVisibility(View.INVISIBLE);
                // 去除搜索城市列表布局
                lvSearchCity.setVisibility(View.GONE);
                // 去除显示无匹配结果布局
                tvNoMatchedCity.setVisibility(View.GONE);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

    /**
     * 热点城市点击回调
     */
    private class HotCityOnItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String cityName = mHotCityAdapter.getItem(position);
            if (!mCityMangeEntityDao.query(cityName)) { // 检查是否已经添加过
                // 根据城市名精确查找城市记录
                List<CityEntity> cityEntityList = mCityEntityDao.queryCity(cityName != null ? cityName.toLowerCase()
                        : null, false);
                if (cityEntityList.size() == 1) { // 正常情况下精确查找的结果只有1项
                    onFinish(cityEntityList.get(0));
                }
            } else {
                Toast.makeText(AddCityActivity.this, String.format(getString(
                        R.string.format_add_city_activity_toast_re_add_city), cityName,
                        getString(R.string.add_city_activity_toast_re_add_city)), Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 搜索项点击回调
     */
    private class SearchCityOnItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            CityEntity cityEntity = mSearchedCityEntityList.get(position);
            if (!mCityMangeEntityDao.query(Integer.parseInt(cityEntity.getWeatherCode()))) { // 检查是否已经添加过
                onFinish(cityEntity);
            } else {
                Toast.makeText(AddCityActivity.this, String.format(getString(
                        R.string.format_add_city_activity_toast_re_add_city), cityEntity.getCity(),
                        getString(R.string.add_city_activity_toast_re_add_city)), Toast.LENGTH_SHORT).show();
            }
        }
    }

}
