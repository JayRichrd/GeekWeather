package com.yulong.jiangyu.geekweather.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.yulong.jiangyu.geekweather.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author  RichardJay
 * email  jiangfengfn12@163.com
 * created  2017/6/13 20:36
 * version  v1.0
 * modified  2017/6/13
 * note  xxx
 **/

public class HotCityAdapter extends ArrayAdapter<String> {

    private final Context mContext;

    /**
     * 构造函数
     *
     * @param context
     * @param list    城市列表
     */
    public HotCityAdapter(Context context, List<String> list) {
        super(context, 0, list);
        mContext = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final String hotCity = getItem(position);
        ViewHolder viewHolder;

        if (null == convertView) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_gv_hot_city, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // 设置边框
        if (hotCity != null && hotCity.equals("定位")) {

        } else {
            viewHolder.tvHotCity.setCompoundDrawables(null, null, null, null);
        }

        viewHolder.tvHotCity.setText(hotCity);
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.tv_hot_city)
        TextView tvHotCity;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
