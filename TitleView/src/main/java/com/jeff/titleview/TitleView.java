package com.jeff.titleview;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 说明： 自定义标题栏
 * 作者： 张武
 * 日期： 2017/5/3.
 * email:wuzhang4@creditease.cn
 */

public class TitleView extends RelativeLayout {

    private Context mContext;

    /**
     * 控件
     */
    //标题
    private TextView mTitle;

    //左边文字
    private TextView mLeftText;

    //左边图片
    private ImageView mLeftImg;

    //右边文字
    private TextView mRightText;

    //右边图片
    private ImageView mRightImg;


    /**
     * 属性
     */
    //默认文字颜色
    private int textColor= 0xffffff;
    //默认背景颜色
    private int backgroudColor= 0x000000;

    public TitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initViiew(attrs);
    }

    /**
     * 初始化view
     *
     * @param attrs
     */
    private void initViiew(AttributeSet attrs) {

        LayoutInflater mLayoutInflater = LayoutInflater.from(mContext);
        final View contentView = mLayoutInflater.inflate(R.layout.title_layout, null);
        addView(contentView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        mTitle = (TextView) findViewById(R.id.title_tv);
        mLeftText = (TextView) findViewById(R.id.left_tv);
        mLeftImg = (ImageView) findViewById(R.id.left_iv);
        mRightText = (TextView) findViewById(R.id.right_tv);
        mRightImg = (ImageView) findViewById(R.id.right_iv);

        TypedArray a = mContext.obtainStyledAttributes(attrs, R.styleable.TitleView);
        setTitleText(a.getString(R.styleable.TitleView_titleText));
        setLeftText(a.getString(R.styleable.TitleView_leftText));
        setLeftImgSrc(a.getResourceId(R.styleable.TitleView_leftImgSrc, -1));
        setTitleText(a.getString(R.styleable.TitleView_titleText));
        setRightText(a.getString(R.styleable.TitleView_rightText));
        setTextColor(a.getColor(R.styleable.TitleView_titleTextColor, textColor));
        setBackgroundColor(a.getColor(R.styleable.TitleView_titleBackgroundColor, backgroudColor));
        a.recycle();
    }

    private TitleView setTextColor(int color) {
        mTitle.setTextColor(color);
        mLeftText.setTextColor(color);
        mRightText.setTextColor(color);
        return this;
    }

    public TitleView setTitleText(String s) {
        if (TextUtils.isEmpty(s))
            mTitle.setVisibility(GONE);
        else {
            mTitle.setVisibility(VISIBLE);
            mTitle.setText(s);
        }
        return this;
    }

    public TitleView setLeftText(String s) {
        if (TextUtils.isEmpty(s))
            mLeftText.setVisibility(GONE);
        else {
            mLeftText.setVisibility(VISIBLE);
            mLeftText.setText(s);
        }
        return this;
    }

    public TitleView setLeftImgSrc(int src) {
        if (src == -1)
            mLeftImg.setVisibility(GONE);
        else {
            mLeftImg.setVisibility(VISIBLE);
            mLeftImg.setImageDrawable(getResources().getDrawable(src));
        }
        return this;
    }

    public TitleView setRightText(String s) {
        if (TextUtils.isEmpty(s))
            mRightText.setVisibility(GONE);
        else {
            mRightText.setVisibility(VISIBLE);
            mRightText.setText(s);
        }
        return this;
    }

    public TitleView setRightImgSrc(int src) {
        if (src == -1)
            mRightImg.setVisibility(GONE);
        else {
            mRightImg.setVisibility(VISIBLE);
            mRightImg.setImageDrawable(getResources().getDrawable(src));
        }
        return this;
    }

    public TitleView setTitleTextOnClick(OnClickListener listener) {
        mTitle.setOnClickListener(listener);
        return this;
    }

    public TitleView setLeftTextOnClick(OnClickListener listener) {
        mLeftText.setOnClickListener(listener);
        return this;
    }

    public TitleView setLeftImgOnClick(OnClickListener listener) {
        mLeftImg.setOnClickListener(listener);
        return this;
    }

    public TitleView setRightTextOnClick(OnClickListener listener) {
        mRightText.setOnClickListener(listener);
        return this;
    }

    public TitleView setRightImgOnClick(OnClickListener listener) {
        mRightImg.setOnClickListener(listener);
        return this;
    }




}
