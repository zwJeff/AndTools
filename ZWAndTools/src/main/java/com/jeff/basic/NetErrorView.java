package com.jeff.basic;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jeff.customView.bannercircleview.R;


/**
 * 说明： 网络加载失败 点击重试页面
 * 作者： 张武
 * 日期： 2017/5/10.
 * email:wuzhang4@creditease.cn
 */

public class NetErrorView extends RelativeLayout {

    private View contentView;
    private ImageView img;
    private TextView tv;


    public NetErrorView(Context context) {
        super(context);
        initView(context);
    }

    public NetErrorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater mLayoutInflater = LayoutInflater.from(context);
        contentView = mLayoutInflater.inflate(R.layout.net_error_view, null);
        img = (ImageView) contentView.findViewById(R.id.img);
        tv = (TextView) contentView.findViewById(R.id.tv);
        removeAllViews();
        addView(contentView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    }

    public NetErrorView setClickListener(OnClickListener lis) {
        contentView.setOnClickListener(lis);
        return this;
    }

    public NetErrorView setErrorIcon(int resourceId) {
        img.setImageResource(resourceId);
        return this;
    }


    public NetErrorView setErrorMsg(String errorMsg) {
        tv.setText(errorMsg);
        return this;
    }

}