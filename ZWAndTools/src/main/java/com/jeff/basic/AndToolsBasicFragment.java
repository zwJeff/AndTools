package com.jeff.basic;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;


import com.jeff.customView.bannercircleview.R;
import com.jeff.customView.bannercircleview.R2;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhangqi on 2016/12/14.
 * Fragment抽象基类
 */

public abstract class AndToolsBasicFragment extends Fragment {

    public abstract int getContentViewId();

    protected Context mContext;
    protected View mRootView;
    protected View mContentView;
    @BindView(R2.id.net_error_view)
    protected NetErrorView mNetErrorView;
    protected FrameLayout mViewContainer;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {

        mRootView = inflater.inflate(R.layout.fragment_basic_layout, container, false);
        mContentView = inflater.inflate(getContentViewId(), null);
        mViewContainer = (FrameLayout) mRootView.findViewById(R.id.fragment_container);
        mViewContainer.removeAllViews();
        mViewContainer.addView(mContentView);
        ButterKnife.bind(this, mRootView);//绑定framgent
        this.mContext = getActivity();
        initAllMembersView(savedInstanceState);
        return mRootView;
    }

    protected abstract void initAllMembersView(Bundle savedInstanceState);


    public void showMessage(String message) {
        if (getActivity() == null)
            return;
        ActivityManager.getActivityManager().getTopActivity().showMessage(message);
    }

    public void showMessage(String message, int duration) {
        if (getActivity() == null)
            return;
        ActivityManager.getActivityManager().getTopActivity().showMessage(message, duration);
    }

    /**
     * 开启等待动画
     */
    public void startWait() {
        if (getActivity() == null)
            return;
        ActivityManager.getActivityManager().getTopActivity().startWait();
    }

    /**
     * 开启等待动画,自定义文案
     *
     * @param text
     */
    public void startWait(String text) {
        if (getActivity() == null)
            return;
        ActivityManager.getActivityManager().getTopActivity().startWait(text);
    }

    /**
     * 关闭等待动画
     */
    public void endWait() {
        if (getActivity() == null)
            return;
        ActivityManager.getActivityManager().getTopActivity().endWait();
    }


    /**
     * 显示或隐藏网络错误点击重试的view
     *
     */
    public void setErrorView(boolean isError,int imgId,String msg) {
        if (isError) {
            mViewContainer.setVisibility(View.GONE);
            mNetErrorView.setVisibility(View.VISIBLE);
            mNetErrorView.setErrorMsg(msg).setErrorIcon(imgId);
        } else {
            mViewContainer.setVisibility(View.VISIBLE);
            mNetErrorView.setVisibility(View.GONE);
        }
    }

    /**
     * 设置点击事件
     *
     * @param lis
     */
    public void setNetErrorOnClick(View.OnClickListener lis) {
        mNetErrorView.setClickListener(lis);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
