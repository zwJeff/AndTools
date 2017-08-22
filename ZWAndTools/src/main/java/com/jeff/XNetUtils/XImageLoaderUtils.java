package com.jeff.XNetUtils;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.jeff.AndTool;
import com.jeff.andUtils.UIUtils;
import com.jeff.customView.bannercircleview.R;

import org.xutils.image.ImageOptions;
import org.xutils.x;

/**
 * 说明：
 * 作者： 张武
 * 日期： 2017/8/18.
 * email:wuzhang4@creditease.cn
 */

public class XImageLoaderUtils {

    /**
     * 显示图片（默认情况）
     *
     * @param imageView 图像控件
     * @param iconUrl   图片地址
     */
    public static void display(ImageView imageView, String iconUrl) {
        ImageOptions imageOptions = new ImageOptions.Builder()
                .setIgnoreGif(false)//是否忽略gif图。false表示不忽略。不写这句，默认是true
                .setImageScaleType(ImageView.ScaleType.FIT_XY)
                .setFailureDrawableId(AndTool.getInstanc().defaultPicId)
                .setLoadingDrawableId(AndTool.getInstanc().defaultPicId)
                .setAnimation(AnimationUtils.loadAnimation(AndTool.getInstanc().application, R.anim.alpha_in))
                .setFadeIn(true)
                .setPlaceholderScaleType(ImageView.ScaleType.FIT_XY)
                .build();
        x.image().bind(imageView, iconUrl,imageOptions);
    }

    /**
     * 显示圆角图片
     *
     * @param imageView 图像控件
     * @param iconUrl   图片地址
     * @param radius    圆角半径，
     */
    public static void display(ImageView imageView, String iconUrl, int radius) {
        ImageOptions imageOptions = new ImageOptions.Builder()
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                .setRadius(UIUtils.dip2px(radius))
                .setIgnoreGif(false)
                .setCrop(true)//是否对图片进行裁剪
                .setFailureDrawableId(AndTool.getInstanc().defaultPicId)
                .setLoadingDrawableId(AndTool.getInstanc().defaultPicId)
                .build();
        x.image().bind(imageView, iconUrl, imageOptions);
    }

    /**
     * 显示圆形头像，第三个参数为true
     *
     * @param imageView  图像控件
     * @param iconUrl    图片地址
     * @param isCircluar 是否显示圆形
     */
    public static void display(ImageView imageView, String iconUrl, boolean isCircluar) {
        ImageOptions imageOptions = new ImageOptions.Builder()
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                .setCircular(isCircluar)
                .setCrop(true)
                .setLoadingDrawableId(AndTool.getInstanc().defaultPicId)
                .setFailureDrawableId(AndTool.getInstanc().defaultPicId)
                .build();
        x.image().bind(imageView, iconUrl, imageOptions);
    }


}
