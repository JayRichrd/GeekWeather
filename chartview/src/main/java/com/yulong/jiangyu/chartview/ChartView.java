package com.yulong.jiangyu.chartview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
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
    //白天
    static private final int TYPE_DAY = 0;
    //晚上
    static private final int TYPE_NIGHT = 1;
    //X、Y轴的集合数
    public static int COUNT_NIGHT = 0;
    public static int COUNT_DAY = 0;
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
    private float[] mXAxis = null;
    //白天温度集合
    private int[] mTemperatureDay = null;
    //夜晚温度集合
    private int[] mTemperatureNight = null;
    //Y轴白天温度集合
    private float[] mYAxisDay = null;
    //Y轴晚上温度集合
    private float[] mYAxisNight = null;

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
        if (COUNT_NIGHT != 0 && COUNT_NIGHT == COUNT_DAY) {
            if (0 == mHeight)
                //计算X轴温度集合坐标与控件高度
                setHeightAndXAxis(COUNT_DAY);
            //计算Y轴温度集合坐标
            computeYAxisValues(COUNT_DAY);
            if (mYAxisDay != null && mYAxisNight != null && mXAxis != null) {
                //画白天温度集合
                drawChart(canvas, mDayLineColor, mTemperatureDay, mYAxisDay, COUNT_DAY, TYPE_DAY);
                //画晚上温度集合
                drawChart(canvas, mNightLineColor, mTemperatureNight, mYAxisNight, COUNT_NIGHT, TYPE_NIGHT);
            }
        }
    }

    /**
    *
    * 画折线
     * @param canvas 画布
     * @param color 颜色
     * @param temperatures 温度集合
     * @param yAxis Y轴坐标集合
     * @param count 数量
     * @param type 线条类型 0 白天 1 晚上
     */
    private void drawChart(Canvas canvas, int color, int[] temperatures, float[] yAxis, int count, int type) {
        //设置画笔颜色
        mPointPaint.setColor(color);
        mLinePaint.setColor(color);
        //透明度
        int alpha1 = 125;
        int alpha2 = 255;
        //绘制
        for (int i = 0; i < count; i++) {
            //画线
            if (i < count - 1) {//只有COUNT - 1条线段
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

    /**
     *
    * 画字
     * @param canvas 画布
     * @param paint 画笔
     * @param index 索引
     * @param temperatures 温度集合
     * @param yAxis Y轴坐标集合
     * @param type 线条类型 0 白天 1 晚上
     */
    private void drawText(Canvas canvas, Paint paint, int index, int[] temperatures, float[] yAxis, int type) {
        switch (type) {
            case TYPE_DAY://白天温度
                canvas.drawText(temperatures[index] + "℃", mXAxis[index], yAxis[index] - mRadius - mTextSpace, paint);
                break;
            case TYPE_NIGHT://晚上温度
                canvas.drawText(temperatures[index] + "℃", mXAxis[index], yAxis[index] + mRadius + mTextSpace +
                        mTextSize, paint);//此处注意起始点的坐标位置，并非是从左上角开始
                break;
        }
    }

    /**
    * 计算Y轴集合数值
     */
    private void computeYAxisValues(int count) {
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
        //初始化集合
        mYAxisDay = new float[count];
        mYAxisNight = new float[count];
        //计算Y轴集合的坐标
        if (0 == yParts) {//当所有温度相等时
            for (int i = 0; i < count; i++) {
                mYAxisDay[i] = yAxisHeight / 2 + space;
                mYAxisNight[i] = yAxisHeight / 2 + space;
            }
        } else {
            float yPartValue = yAxisHeight / yParts;
            for (int i = 0; i < count; i++) {
                //白天温度集合坐标
                mYAxisDay[i] = mHeight - space - yPartValue * (mTemperatureDay[i] - minTemperature);
                //夜晚温度集合坐标
                mYAxisNight[i] = mHeight - space - yPartValue * (mTemperatureNight[i] - minTemperature);
            }
        }
    }

    /**
    * 求所给数组的最小值
     */
    private int getMin(int[] metric) {
        int min = metric[0];
        for (int temp : metric)
            if (temp < min)
                min = temp;
        return min;

    }

    /**
    * 求所给数组的最大值
     */
    private int getMax(int[] metric) {
        int max = metric[0];
        for (int temp : metric)
            if (temp > max)
                max = temp;
        return max;
    }

    /**
     *设置控件的高度和X轴的集合
     */
    private void setHeightAndXAxis(int count) {
        mHeight = getHeight();
        //控件宽度
        int width = getWidth();
        /**
         * 总共12等分
        * X轴上6个坐标点
         */
        //每一份宽度
        float perWidth = width / (2 * count);
        //初始化集合
        mXAxis = new float[count];
        //每个点的X轴坐标值
        for (int i = 1, j = 0; i < 2 * count & j < count; ) {
            mXAxis[j] = i * perWidth;
            i += 2;
            j++;
        }
    }

    /**
    * 设置白天的温度
    * 暴露给调用者使用的api
     */
    public void setTemperatureDay(int[] temperatures) {
        mTemperatureDay = temperatures;
        COUNT_DAY = temperatures.length;
    }

    /**
     * 设置晚上的温度
    * 暴露给调用者使用的api
     */
    public void setTemperatureNight(int[] temperatures) {
        mTemperatureNight = temperatures;
        COUNT_NIGHT = temperatures.length;
    }
}
