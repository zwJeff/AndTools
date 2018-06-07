package com.jeff.basic;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.jeff.AndTool;
import com.jeff.customView.TitleView;
import com.jeff.customView.bannercircleview.R;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;


/**
 * 说明： activity 基类
 * 作者： 张武
 * 日期： 2017/5/6.
 * email:wuzhang4@creditease.cn
 */
public abstract class AndToolsBasicActivity extends AppCompatActivity {
    private Toast mToast;
    public Context mContext;
    private FrameLayout contentView;
    protected TitleView mBasicTitleView;
    protected NetErrorView mNetErrorView;
    protected boolean isToChangePermission=false;

    public abstract int getContentViewId();

    public abstract void initAllMembersView(Bundle paramBundle);

    public void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setWindowParams();
        setContentView(R.layout.activity_basic);
        LayoutInflater mLayoutInflater = LayoutInflater.from(this);
        contentView= (FrameLayout) findViewById(R.id.content_view);
        View view = mLayoutInflater.inflate(getContentViewId(), null);
        contentView.addView(view);
        mContext = this;
        ButterKnife.bind(this);
        mBasicTitleView = (TitleView) findViewById(R.id.titleview);
        mNetErrorView = (NetErrorView) findViewById(R.id.net_error);
        mBasicTitleView.setLeftTextOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        initAllMembersView(paramBundle);
        //activity creat时入栈
        ActivityManager.getActivityManager().addActivity(this);
    }


    public void setWindowParams() {

    }

    public void showMessage(String message) {
        if (null != mToast) {
            mToast.setText(message);
        } else {
            mToast = Toast.makeText(mContext, message, Toast.LENGTH_SHORT);
        }
        mToast.show();
    }

    public void showMessage(String message, int duration) {
        if (null != mToast) {
            mToast.setText(message);
        } else {
            mToast = Toast.makeText(mContext, message, duration);
        }
        mToast.show();
    }

    /**
     * 开启等待动画
     */
    public abstract void startWait();
    /**
     * 开启等待动画
     */
    public abstract void startWait(String text);


    /**
     * 关闭等待动画
     */
    public abstract void endWait();


    /**
     * 显示或隐藏网络错误点击重试的view
     *
     */
    public void setErrorView(boolean isError,int imgId,String msg) {
        if (isError) {
            contentView.setVisibility(View.GONE);
            mNetErrorView.setVisibility(View.VISIBLE);
            mNetErrorView.setErrorMsg(msg).setErrorIcon(imgId);
        } else {
            contentView.setVisibility(View.VISIBLE);
            mNetErrorView.setVisibility(View.GONE);
        }
    }

    /**
     * 设置点击事件
     * @param lis
     */
    public void setNetErrorOnClick(View.OnClickListener lis){
        mNetErrorView.setClickListener(lis);
    }



    @Override
    public void onBackPressed() {
        if(ActivityManager.activityStack.size()!=1||isDoubleBack){
            super.onBackPressed();
        }else if(ActivityManager.activityStack.size()==1){
            exitConfirm();
        }
    }


    private boolean isDoubleBack=false;
    public void exitConfirm() {
        isDoubleBack = true;
        Timer timer = new Timer();
        showMessage("再按一次退出"
                + AndTool.getInstanc().application.getResources().getString(AndTool.getInstanc().application.getApplicationInfo().labelRes)
                , Toast.LENGTH_SHORT);
        timer.schedule(new TimerTask() {
            public void run() {
                isDoubleBack = false;
            }
        }, 2000);// 设定指定的时间time,此处为2000毫秒
    }

    public void onDestroy() {
        //activity destroy时出栈
        ActivityManager.getActivityManager().removeActivity(this);
        super.onDestroy();
    }

}