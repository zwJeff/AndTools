package com.jeff.basic;

import android.app.Activity;
import android.content.Context;
import android.os.Build;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

/**
 * 说明： 自维护的activity管理类
 * Created by 张武 on 2016/8/2.
 */
public class ActivityManager {

    public static ActivityManager getActivityManager() {
        if (activityManager == null)
            activityManager = new ActivityManager();
        return activityManager;
    }

    public static ActivityManager activityManager;

    public static Stack<Activity> activityStack;

    /**
     * 获取栈顶Activity
     */
    public AndToolsBasicActivity getTopActivity() {
        if (activityStack == null)
            return null;
        return (AndToolsBasicActivity)activityStack.get(activityStack.size()-1);
    }

    /**
     * 压入Activity
     *
     * @param activity
     */
    public void addActivity(Activity activity) {
        if (activityStack == null)
            activityStack = new Stack<Activity>();
        activityStack.push(activity);
    }

    /**
     * 弹出Activity
     *
     * @return
     */
    public Activity popActivity() {
        if (activityStack == null || activityStack.size() <= 0)
            return null;
        return activityStack.pop();
    }

    /**
     * 移出activity
     *
     * @param activity
     */
    public void removeActivity(Activity activity) {
        if (activityStack == null || activityStack.size() <= 0)
            return;
        activity.finish();
        activityStack.remove(activity);
    }

    /**
     * 关闭除**以外的activity
     *
     * @param cls
     */
    public void closeAllExcept(Class<?> cls) {
        if (activityStack == null || activityStack.size() <= 0)
            return;
        Iterator<Activity> iterator = activityStack.iterator();
        Activity mActivity;
        List<Activity> removeList = new ArrayList<>();
        while (iterator.hasNext()) {
            mActivity = iterator.next();
            if (mActivity.getClass().equals(cls)) {
                //保留当前activity
            } else {
                removeList.add(mActivity);
            }
        }
        for (Activity a : removeList) {
            activityStack.remove(a);
            a.finish();
        }

    }


    /**
     * 关闭指定的activity
     *
     * @param cls
     */
    public void closeActivityByName(Class<?> cls) {
        if (activityStack == null || activityStack.size() <= 0)
            return;
        Iterator<Activity> iterator = activityStack.iterator();
        Activity mActivity;
        List<Activity> removeList = new ArrayList<>();
        while (iterator.hasNext()) {
            mActivity = iterator.next();
            if (mActivity.getClass().equals(cls)) {
                activityStack.add(mActivity);
                break;
            }
        }
        for (Activity a : removeList) {
            activityStack.remove(a);
            a.finish();
        }

    }


    /**
     * 关闭所有activity
     */
    public void closeAll() {
        if (activityStack == null || activityStack.size() <= 0)
            return;
        Iterator<Activity> iterator = activityStack.iterator();
        Activity mActivity;
        List<Activity> removeList = new ArrayList<>();
        while (iterator.hasNext()) {
            mActivity = iterator.next();
            //关闭activity并从stack中移除
            removeList.add(mActivity);
        }
        for (Activity a : removeList) {
            activityStack.remove(a);
            a.finish();
        }
    }

    /**
     * 关闭所有界面并结束所有进程线程
     *
     * @param context
     */
    public void exitApp(Context context) {
        try {
            // 关闭所有界面
            closeAll();
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.FROYO) {
                android.app.ActivityManager activityMgr = (android.app.ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
                // 系统会将，该包下的 ，所有进程，服务，全部杀掉，就可以杀干净了
                activityMgr.restartPackage(context.getPackageName());
            } else {
                android.app.ActivityManager activityMgr = (android.app.ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
                activityMgr.killBackgroundProcesses(context.getPackageCodePath());
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }


}
