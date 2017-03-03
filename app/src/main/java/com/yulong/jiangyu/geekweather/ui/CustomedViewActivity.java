package com.yulong.jiangyu.geekweather.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.yulong.jiangyu.chartview.ChartView;
import com.yulong.jiangyu.geekweather.R;

public class CustomedViewActivity extends AppCompatActivity {

    private ChartView lineChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customed_view);
        lineChart = (ChartView) findViewById(R.id.line_chart);
        lineChart.setTemperatureDay(new int[]{14, 15, 16, 17, 9, 9});
        lineChart.setTemperatureNight(new int[]{7, 5, 9, 10, 3, 2});
        lineChart.invalidate();
    }
}
