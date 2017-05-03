package com.jeff.mybannercircledemo;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by 张武 on 2016/9/5.
 */
public class BannerCircleAdapter extends PagerAdapter {

    private List<String> picUrlList;
    private List<View.OnClickListener> mClickListenerList;
    private MyImageLoader mImageLoader;
    private Context context;
    private boolean isAutoCircle;


    public BannerCircleAdapter(List<String> picUrlList, List<View.OnClickListener> mClickListenerList, MyImageLoader mImageLoader, Context context,boolean isAutoCircle) {
        this.picUrlList = picUrlList;
        this.mClickListenerList = mClickListenerList;
        this.mImageLoader = mImageLoader;
        this.isAutoCircle=isAutoCircle;
        this.context = context;
    }

    @Override
    public int getCount() {
        //如果需要循环，将Count设置为Integer最大值
        return isAutoCircle?Integer.MAX_VALUE:picUrlList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView iv = new ImageView(context);
        mImageLoader.loadImage(picUrlList.get(getPosition(position)),iv);
        iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
        ((ViewPager) container).addView(iv);
        // 在这个方法里面设置图片的点击事件
        iv.setOnClickListener(mClickListenerList.get(getPosition(position)));
        return iv;
    }


    private int getPosition(int position){
        //通过取模运算得出当前显示图片的真实序号
        return isAutoCircle?position%picUrlList.size():position;
    }

    @Override
    public int getItemPosition(Object object) {
        return isAutoCircle?super.getItemPosition(object)%picUrlList.size():super.getItemPosition(object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

    }
}
