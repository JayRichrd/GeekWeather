package com.yulong.jiangyu.geekweather.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import com.yulong.jiangyu.geekweather.R;
import com.yulong.jiangyu.geekweather.constant.Constant;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * author RichardJay
 * email jiangfengfn12@163.com
 * created 2017/9/1 11:49
 * version v1.0
 * modified 2017/9/1
 * note 欢迎界面Activity
 */
public class WelcomeActivity extends AppCompatActivity {

    private static final String TAG = "WelcomeActivity";

    //动画显示的时间,单位ms
    private static final int ANIMATION_DURATION_TIME = 2000;
    private final Handler mHandler = new MyHandler(WelcomeActivity.this);
    @BindView(R.id.tv_welcome)
    TextView tvWelcome;
    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        unbinder = ButterKnife.bind(this);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        //共用一个动画部件
        //设置动画
        AnimationSet animationSet = new AnimationSet(true);
        animationSet.setDuration(ANIMATION_DURATION_TIME);
        //动画结束后保持状态
        animationSet.setFillAfter(true);

        //缩放动画
        //水平方向和垂直方向都放大1倍
        ScaleAnimation scaleAnimation = new ScaleAnimation(0, 1, 0, 1);
        scaleAnimation.setDuration(ANIMATION_DURATION_TIME);
        animationSet.addAnimation(scaleAnimation);

        //平移动画
        TranslateAnimation translateAnimation = new TranslateAnimation(0, 0, 200, 0);
        translateAnimation.setDuration(ANIMATION_DURATION_TIME);
        animationSet.addAnimation(translateAnimation);

        //字体上执行动画
        tvWelcome.startAnimation(animationSet);

        //设置监听
        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                Log.i(TAG, getString(R.string.log_animation_start));
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Log.i(TAG, getString(R.string.log_animation_end));
                //1秒钟后发送handler消息
                mHandler.sendEmptyMessageDelayed(Constant.WELCOME_ACTIVITY_START_MAIN_ACTIVITY, 1000);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                Log.i(TAG, getString(R.string.log_animation_repeat));
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    /**
     * 自定义更新UI的Handler
     * 需要定义成静态的内部类，是避免内存泄漏
     */
    private static class MyHandler extends Handler {
        private final WeakReference<WelcomeActivity> mActivity;

        public MyHandler(WelcomeActivity activity) {
            mActivity = new WeakReference<WelcomeActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            WelcomeActivity activity = mActivity.get();
            if (activity == null)
                return;
            switch (msg.what) {
                case Constant.WELCOME_ACTIVITY_START_MAIN_ACTIVITY://进入主界面
                    Intent intent = new Intent(activity, MainActivity.class);
                    activity.startActivity(intent);
                    Log.i(TAG, activity.getString(R.string.log_start_main_activity));
                    activity.finish();
                    break;
                default:
                    Log.d(TAG, activity.getString(R.string.log_nothing_to_do));
            }
        }
    }
}
