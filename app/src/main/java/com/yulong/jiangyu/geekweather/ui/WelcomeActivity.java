package com.yulong.jiangyu.geekweather.ui;

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
import com.yulong.jiangyu.geekweather.activity.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author jiangyu
 * @time 2016/12/29
 * 欢迎界面
 */
public class WelcomeActivity extends AppCompatActivity {

    private static final String TAG = "WelcomeActivity";
    private static final int START_MAIN_ACTIVITY = 0x001;

    @BindView(R.id.tv_welcome)
    TextView tvWelcome;
    private Unbinder unbinder;
    //设置动画
    private AnimationSet animationSet;
    //动画显示的时间
    private int animationTime = 2000;
    private Handler handler = new Handler() {
        /**
         * Subclasses must implement this to receive messages.
         *
         * @param msg
         */
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case START_MAIN_ACTIVITY://进入主界面
                    Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                    startActivity(intent);
                    Log.i(TAG, "***handleMessage***:start main activity");
                    finish();
                    break;
                default:
                    Log.d(TAG, "***handleMessage***:nothing to handled");
            }
        }
    };

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
        animationSet = new AnimationSet(true);
        animationSet.setDuration(animationTime);
        //动画结束后保持状态
        animationSet.setFillAfter(true);

        //缩放动画
        //水平方向和垂直方向都放大1倍
        ScaleAnimation scaleAnimation = new ScaleAnimation(0, 1, 0, 1);
        scaleAnimation.setDuration(animationTime);
        animationSet.addAnimation(scaleAnimation);

        //平移动画
        TranslateAnimation translateAnimation = new TranslateAnimation(0, 0, 200, 0);
        translateAnimation.setDuration(animationTime);
        animationSet.addAnimation(translateAnimation);

        //字体上执行动画
        tvWelcome.startAnimation(animationSet);

        //设置监听
        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                Log.i(TAG, "***onAnimationStart***:Animation start");
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Log.i(TAG, "***onAnimationEnd***:Animation end");
                //1秒钟后发送handler消息
                handler.sendEmptyMessageDelayed(START_MAIN_ACTIVITY, 1000);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                Log.i(TAG, "***onAnimationRepeat***:Animation repeat");
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
