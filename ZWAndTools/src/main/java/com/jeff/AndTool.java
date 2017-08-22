package com.jeff;

import android.app.Application;
import android.util.Config;
import com.jeff.customView.bannercircleview.R;

import org.xutils.x;

/**
 * 说明：
 * 作者： 张武
 * 日期： 2017/8/22.
 * email:wuzhang4@creditease.cn
 */

public class AndTool {

    private static AndTool instance;

    public Application application;
    public String VERSION_NAME;
    public int defaultPicId = R.mipmap.default_img;

    public static AndTool getInstanc() {
        if (instance == null)
            instance = new AndTool();
        return instance;
    }

    /**
     * 初始化Andtool，对内部封装的xutil等组件进行初始化
     */
    public AndTool init(Application application) {
        this.application = application;
        //初始化xutils3
        x.Ext.init(application);
        x.Ext.setDebug(Config.DEBUG);

        return this;
    }



    /**
     * 设置AndTools内
     *          图片加载工具默认的图片id
     *          轮播图控件的默认图片id
     * @param appVersionName
     * @return
     */
    public AndTool setAppVersion(String appVersionName){
        this.VERSION_NAME=appVersionName;
        return this;
    }

    /**
     * 设置AndTools内
     *          图片加载工具默认的图片id
     *          轮播图控件的默认图片id
     * @param defaultImgId
     * @return
     */
    public AndTool setDefaultImg(int defaultImgId){
        this.defaultPicId=defaultImgId;
        return this;
    }

}
