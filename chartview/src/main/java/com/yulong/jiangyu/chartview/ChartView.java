package com.yulong.jiangyu.chartview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * author RichardJay
 * email jiangfengfn12@163.com
 * created 2017/3/2 16:51
 * version v1.0
 * modified 2017/3/2
 * note xxx
 **/
// The plan
//*-----------------------------------------*
//                  SPACE                   *
//*-----------------------------------------*
//                  TEXT                    *
//*-----------------------------------------*
//               TEXT SPACE                 *
//*-----------------------------------------*
//                  RADIUS                  *
//*-----------------------------------------*
//                   |                      *
//                   |                      *
//                   |                      *
//        ---------(x,y)--------            *
//                   |                      *
//                   |                      *
//                   |                      *
//*-----------------------------------------*
//                  RADIUS                  *
//*-----------------------------------------*
//               TEXT SPACE                 *
//*-----------------------------------------*
//                  TEXT                    *
//*-----------------------------------------*
//                  SPACE                   *
//*-----------------------------------------*

public class ChartView extends View {
    //X、Y轴的集合数
    static private int COUNT = 6;
    //字体大小
    private int mTextSize;
    //线条颜色
    private int mDayLineColor;
    private int mNightLineColor;
    //字体颜色
    private int mTextColor;
    //屏幕密度
    private float mDensity;
    //圆的半径
    private float mRadius;
    private float mRadiusToday;
    //控件的左右空白空间
    private float mSpace;
    //文字位移的距离
    private float mTextSpace;
    //线画笔
    private Paint mLinePaint;
    //点画笔
    private Paint mPointPaint;
    //文字画笔
    private Paint mTextPaint;
    //控件的高度
    private int mHeight;
    //X轴上点的集合
    private float[] mXAxis = new float[6];
    //白天温度集合
    private int[] mTemperatureDay = new int[6];
    //夜晚温度集合
    private int[] mTemperatureNight = new int[6];
    //Y轴白天温度集合
    private float[] mYAxisDay = new float[6];
    //Y轴晚上温度集合
    private float[] mYAxisNight = new float[6];

    /**
     * Simple constructor to use when creating a view from code.
     *
     * @param context The Context the view is running in, through which it can
     *                access the current theme, resources, etc.
     */
    public ChartView(Context context) {
        super(context);
    }

    /**
     * Constructor that is called when inflating a view from XML. This is called
     * when a view is being constructed from an XML file, supplying attributes
     * that were specified in the XML file. This version uses a default style of
     * 0, so the only attribute values applied are those in the Context's Theme
     * and the given AttributeSet.
     * <p>
     * <p>
     * The method onFinishInflate() will be called after all children have been
     * added.
     *
     * @param context The Context the view is running in, through which it can
     *                access the current theme, resources, etc.
     * @param attrs   The attributes of the XML tag that is inflating the view.
     * @see # View(Context, AttributeSet, int)
     */
    public ChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    /*
    * 初始化
     */
    @SuppressWarnings("deprecation")
    private void init(Context context, AttributeSet attrs) {
        //获取自定义的样式属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ChartView);
        //获取屏幕缩放密度
        float densityText = getResources().getDisplayMetrics().scaledDensity;
        //获取属性（线条颜色、字体大小和颜色等）
        mTextSize = typedArray.getDimensionPixelSize(R.styleable.ChartView_textSize, (int) (14 * densityText));
        mDayLineColor = typedArray.getColor(R.styleable.ChartView_dayLineColor, getResources().getColor(R.color
                .colorAccent));
        mNightLineColor = typedArray.getColor(R.styleable.ChartView_nightLineColor, getResources().getColor(R.color
                .colorPrimary));
        mTextColor = typedArray.getColor(R.styleable.ChartView_textColor, getResources().getColor(R.color.colorYellow));
        /*
        * 回收TypedArray，以便后面重用
        * 必须调用
        */
        typedArray.recycle();

        //初始化距离、空间值
        mDensity = getResources().getDisplayMetrics().density;
        mRadius = mDensity * 3;
        mRadiusToday = mDensity * 5;
        mSpace = mDensity * 3;
        mTextSpace = mDensity * 10;

        //初始化线画笔
        mLinePaint = new Paint();
        mLinePaint.setAntiAlias(true);//抗锯齿
        mLinePaint.setStrokeWidth(mDensity * 2);//画笔的粗细
        mLinePaint.setStyle(Paint.Style.STROKE);//画笔类型

        //初始化点画笔
        mPointPaint = new Paint();
        mPointPaint.setAntiAlias(true);//抗锯齿

        //初始化文字画笔
        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);//抗锯齿
        mTextPaint.setColor(mTextColor);//文字颜色
        mTextPaint.setTextSize(mTextSize);//文字大小
        mTextPaint.setTextAlign(Paint.Align.CENTER);//文字堆对齐方式
    }

    /**
     * Implement this to do your drawing.
     *
     * @param canvas the canvas on which the background will be drawn
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (0 == mHeight)
            setHeightAndXAxis();
        //计算Y轴温度集合坐标
        computeYAxisValues();
        //画白天温度集合
        drawChart(canvas, mDayLineColor, mTemperatureDay, mYAxisDay, 0);
        //画晚上温度集合
        drawChart(canvas, mNightLineColor, mTemperatureNight, mYAxisNight, 1);
    }

    /*
    *
    * 画折线
    * @param
     */
    private void drawChart(Canvas canvas, int color, int[] temperatures, float[] yAxis, int type) {
        //设置画笔颜色
        mPointPaint.setColor(color);
        mLinePaint.setColor(color);
        //透明度
        int alpha1 = 125;
        int alpha2 = 255;
        //绘制
        for (int i = 0; i < COUNT; i++) {
            //画线
            if (i < COUNT - 1) {//只有COUNT - 1条线段
                if (0 == i) {//昨天
                    mLinePaint.setAlpha(alpha1);
                    //设置虚线效果
                    mLinePaint.setPathEffect(new DashPathEffect(new float[]{2 * mDensity, 2 * mDensity}, 0));
                    //路径
                    Path path = new Path();
                    //路径起点
                    path.moveTo(mXAxis[i], yAxis[i]);
                    //路径终点
                    path.lineTo(mXAxis[i + 1], yAxis[i + 1]);
                    //根根路径画线
                    canvas.drawPath(path, mLinePaint);
                } else {
                    mLinePaint.setAlpha(alpha2);
                    //实线
                    mLinePaint.setPathEffect(null);
                    //根据坐标点画线
                    canvas.drawLine(mXAxis[i], yAxis[i], mXAxis[i + 1], yAxis[i + 1], mLinePaint);
                }
            }

            //画点
            if (0 == 1) {//昨天
                mPointPaint.setAlpha(alpha1);
                canvas.drawCircle(mXAxis[i], yAxis[i], mRadius, mPointPaint);
            } else if (1 == i) {//今天
                mPointPaint.setAlpha(alpha2);
                canvas.drawCircle(mXAxis[i], yAxis[i], mRadiusToday, mPointPaint);
            } else {
                mPointPaint.setAlpha(alpha2);
                canvas.drawCircle(mXAxis[i], yAxis[i], mRadius, mPointPaint);
            }

            //画字
            if (0 == i) //昨天
                mTextPaint.setAlpha(alpha1);
            else
                mTextPaint.setAlpha(alpha2);
            drawText(canvas, mTextPaint, i, temperatures, yAxis, type);
        }
    }

    /*
    * 画字
     */
    private void drawText(Canvas canvas, Paint paint, int index, int[] temperatures, float[] yAxis, int type) {
        switch (type) {
            case 0://白天温度
                canvas.drawText(temperatures[index] + "℃", mXAxis[index], yAxis[index] - mRadius - mTextSpace, paint);
                break;
            case 1://晚上温度
                canvas.drawText(temperatures[index] + "℃", mXAxis[index], yAxis[index] + mRadius + mTextSpace, paint);
                break;
        }
    }

    /*
    * 计算Y轴集合数值
     */
    private void computeYAxisValues() {
        //白天最高温
        int maxTemperatureDay = getMax(mTemperatureDay);
        //白天最低温
        int minTemperatureDay = getMin(mTemperatureDay);
        //夜晚最高温
        int maxTemperatureNight = getMax(mTemperatureNight);
        //夜晚最低温
        int minTemperatureNight = getMin(mTemperatureNight);
        //全天的最高温
        int maxTemperature = maxTemperatureDay > maxTemperatureNight ? maxTemperatureDay : maxTemperatureNight;
        //全天的最低温
        int minTemperature = minTemperatureDay > minTemperatureNight ? minTemperatureNight : minTemperatureDay;
        //Y轴等分数量
        float yParts = maxTemperature - minTemperature;
        //Y轴一端到控件View一端的距离
        float space = mSpace + mTextSize + mTextSpace + mRadius;
        //Y轴的高度
        float yAxisHeight = mHeight - space * 2;
        //计算Y轴集合的坐标
        if (0 == yParts) {//当所有温度相等时
            for (int i = 0; i < COUNT; i++) {
                mYAxisDay[i] = yAxisHeight / 2 + space;
                mYAxisNight[i] = yAxisHeight / 2 + space;
            }
        } else {
            float yPartValue = yAxisHeight / yParts;
            for (int i = 0; i < COUNT; i++) {
                //白天温度集合坐标
                mYAxisDay[i] = yAxisHeight - space - yPartValue * (mTemperatureDay[i] - minTemperature);
                //夜晚温度集合坐标
                mYAxisNight[i] = yAxisHeight - space - yPartValue * (mTemperatureNight[i] - minTemperature);
            }
        }
    }

    /*
    * 求所给数组的最小值
     */
    private int getMin(int[] metric) {
        int min = metric[0];
        for (int temp : metric)
            if (temp < min)
                min = temp;
        return min;

    }

    /*
    * 求所给数组的最大值
     */
    private int getMax(int[] metric) {
        int max = metric[0];
        for (int temp : metric)
            if (temp > max)
                max = temp;
        return max;
    }

    /*
     *设置控件的高度和X轴的集合
     */
    private void setHeightAndXAxis() {
        mHeight = getHeight();
        Log.d("WeatherChartView", "setHeightAndXAxis()/mHeight=" + mHeight);
        //控件宽度
        int width = getWidth();
        /*
        *总共12等分
        * X轴上6个坐标点
         */
        //每一份宽度
        float perWidth = width / 12;
        //每个点的X轴坐标值
        for (int i = 1, j = 0; i < 12 & j < 6; ) {
            mXAxis[j] = i * perWidth;
            i += 2;
            j++;
        }
    }

    /*
    * 设置白天的温度
     */
    public void setTemperatureDay(int[] temperatures) {
        mTemperatureDay = temperatures;
    }

    /*
    *设置晚上的温度
     */
    public void setTemperatureNight(int[] temperatures) {
        mTemperatureNight = temperatures;
    }
}
