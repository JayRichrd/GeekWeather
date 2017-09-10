package com.yulong.jiangyu.geekweather.net;

import android.content.Context;
import android.util.Log;

import com.yulong.jiangyu.geekweather.R;
import com.yulong.jiangyu.geekweather.constant.Constant;
import com.yulong.jiangyu.geekweather.entity.JuHeDateEntity;
import com.yulong.jiangyu.geekweather.interfaces.IDataEntity;
import com.yulong.jiangyu.geekweather.module.IDataRequest;
import com.yulong.jiangyu.geekweather.interfaces.JuHeDateImpl;
import com.yulong.jiangyu.geekweather.listener.IHttpCallbackListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * author RichardJay
 * email jiangfengfn12@163.com
 * created 2017/9/4 14:02
 * version v1.0
 * modified 2017/9/4
 * note 调用聚合数据API请求日历数据
 */

public class JuHeDateRequestNet implements IDataRequest {
    //日志TAG
    private static final String TAG = "JuHeDateRequestNet";

    /**
     * 无参数构造函数
     */
    public JuHeDateRequestNet() {
    }

    @Override
    public void requestData(final Context context, String dateStr, boolean isCityCode, boolean isRefresh, final
    IHttpCallbackListener
            httpCallbackListener) {
        //聚合数据万年历网络请求
        Retrofit dateRetrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl
                (context.getString(R.string.config_juhe_date_request_base_url)).build();
        JuHeDateImpl juHeDateImpl = dateRetrofit.create(JuHeDateImpl.class);
        juHeDateImpl.getDate(dateStr, context.getString(R.string.config_juhe_date_request_key)).enqueue(new Callback<JuHeDateEntity>() {
            @Override
            public void onResponse(Call<JuHeDateEntity> call, retrofit2.Response<JuHeDateEntity> response) {
                JuHeDateEntity juHeDateEntity = response.body();
                IDataEntity baseDateEntity = null;
                //检查返回的结果
                if (Constant.errorCode == juHeDateEntity.getErrorCode())
                    baseDateEntity = trans2Base(juHeDateEntity);
                if (httpCallbackListener != null)
                    httpCallbackListener.onFinished(baseDateEntity);
            }

            @Override
            public void onFailure(Call<JuHeDateEntity> call, Throwable t) {
                Log.e(TAG, context.getString(R.string.log_failed_to_get_date));
                if (httpCallbackListener != null)
                    httpCallbackListener.onError(t);
            }
        });
    }

    @Override
    public IDataEntity trans2Base(IDataEntity dataEntity) {
        return dataEntity;
    }
}
