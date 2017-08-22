package com.jeff.andUtils;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;

import com.jeff.AndTool;
import com.jeff.customView.bannercircleview.R;

/**
 * 说明：
 * 作者： 张武
 * 日期： 2017/5/18.
 * email:wuzhang4@creditease.cn
 */

public class UIUtils {


    /**
     * 界面返回动画效果
     *
     * @param activity
     */
    public static void pushToRight(Activity activity) {
        activity.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }


    /**
     * 界面前进动画效果
     *
     * @param activity
     */
    public static void popToLeft(Activity activity) {
        activity.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    /**
     * 界面返回动画效果
     *
     * @param activity
     */
    public static void pushToBotom(Activity activity) {
        activity.overridePendingTransition(R.anim.push_bottom_in, R.anim.push_bottom_out);
    }

    /**
     * 界面前进动画效果
     *
     * @param activity
     */
    public static void popToTop(Activity activity) {
        activity.overridePendingTransition(R.anim.push_top_in, R.anim.push_top_out);
    }

    /**
     * dip转px
     *
     * @param dipValue
     * @return
     */
    public static int dip2px(float dipValue) {
        return (int) (dipValue * AndTool.getInstanc().application.getResources().getDisplayMetrics().density + 0.5f);
    }

    public static int dip2px(Context context, float dipValue) {
        return (int) (dipValue * context.getResources().getDisplayMetrics().density + 0.5f);
    }



    /**
     * 开启沉浸式状态栏
     */
    public static void openImmerseStatasBarMode(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = activity.getWindow();
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }


}
