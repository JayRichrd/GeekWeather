package com.yulong.jiangyu.geekweather.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.yulong.jiangyu.chartview.ChartView;
import com.yulong.jiangyu.geekweather.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class CustomedViewActivity extends AppCompatActivity {

    @BindView(R.id.line_chart)
    ChartView lineChart;
    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Log.d("TAG","***onCreate()");
        setContentView(R.layout.activity_customed_view);
        unbinder = ButterKnife.bind(this);
        lineChart.setTemperatureDay(new int[]{14, 15, 16, 17, 9, 9, 20, 18});
        lineChart.setTemperatureNight(new int[]{7, 5, 9, 10, 3, 2, 10, 10});
        lineChart.invalidate();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }


}
