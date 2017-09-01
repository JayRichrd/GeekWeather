package com.yulong.jiangyu.geekweather.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * author RichardJay
 * email jiangfengfn12@163.com
 * created 2017/9/1 11:49
 * version v1.0
 * modified 2017/9/1
 * note 基础的Activity类
 */

public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initVariables();
        initViews(savedInstanceState);
        loadData();
    }

    /**
     * 初始化变量
     */
    protected abstract void initVariables();

    /**
     * 初始化视图，主要就是初始化控件
     */
    protected abstract void initViews(@Nullable Bundle savedInstanceState);

    /**
     * 加载数据
     */
    protected abstract void loadData();
}
