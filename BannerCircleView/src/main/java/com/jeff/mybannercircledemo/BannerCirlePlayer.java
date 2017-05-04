package com.jeff.mybannercircledemo;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 说明： 图片轮播主类
 * 实现原理：通过将adapter的count设置为极大值，实现同方向无穷轮播
 * 通过Timer，在每一页图片显示的时候，设置一个延时跳到下一页的操作
 * 通过Glide加载图片
 * <p>
 * 优点：最后一张图片到第一张图片过渡自然
 * 每一张图片都会显示指定延迟时间，不会因为人为滑动导致的跳页和时间错乱
 * 通过Glide加载图片可实现图片缓存，不会出现过多图片造成的oor
 * 通过传入url的数量动态决定轮播图的数量
 * <p>
 * 作者： 张武
 * 日期： 2016/9/2.
 * email: jeff_zw@qq.com
 */
public class BannerCirlePlayer extends RelativeLayout {


    //图片url集合
    private List<String> picUrlList;

    //banner点击事件集合
    private List<OnClickListener> mClickListenerList;

    //图片显示的容器viewpager
    private ViewPager viewpage;

    //图片下面指示点的容器
    private LinearLayout pointLinearLayout;

    //显示的图片id
    private int showPicId;

    //计时器-用于轮播
    private Timer currentTimer;

    //轮播延时（默认3秒）
    private int showTime = 3;

    //显示此view的上下文
    private Context context;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                if (showPicId < Integer.MAX_VALUE)
                    viewpage.setCurrentItem(showPicId);
                else {
                    showPicId = 0;
                    viewpage.setCurrentItem(showPicId, false);
                }
            }
        }
    };


    public BannerCirlePlayer(Context context, AttributeSet attr) {
        super(context, attr);
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.pic_circle_player, this);
        initViewWithoutAdapter();
    }

    private BannerCirlePlayer initViewWithoutAdapter() {
        viewpage = (ViewPager) findViewById(R.id.view_pager);
        pointLinearLayout = (LinearLayout) findViewById(R.id.indicate_points);
        return this;
    }

    public BannerCirlePlayer setData(final List<String> urlList, List<OnClickListener> lis, final boolean isAutoCircle) {
        picUrlList = urlList;
        mClickListenerList = lis;

        for (int i = 0; i < picUrlList.size(); i++) {
            View pot = new View(context);
            pot.setBackgroundResource(R.drawable.dot_normal);
            LinearLayout.LayoutParams mParam = new LinearLayout.LayoutParams(10, 10);
            mParam.setMargins(2, 2, 2, 2);
            pot.setLayoutParams(mParam);
            pointLinearLayout.addView(pot);

        }

        viewpage.setAdapter(new BannerCircleAdapter(context,urlList, lis, isAutoCircle));


        pointLinearLayout.getChildAt(0).setBackgroundResource(R.drawable.dot_focused);
        if (isAutoCircle) {
            currentTimer = new Timer();
            currentTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    showPicId = 1;
                    handler.sendMessage(handler.obtainMessage(1));
                }
            }, 1000 * showTime);
        }


        viewpage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            int oldPosition = 0;

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                pointLinearLayout.getChildAt(oldPosition).setBackgroundResource(R.drawable.dot_normal);
                pointLinearLayout.getChildAt(position % urlList.size()).setBackgroundResource(R.drawable.dot_focused);
                oldPosition = position % urlList.size();
                showPicId = position + 1;
                if (isAutoCircle) {
                    currentTimer.cancel();
                    currentTimer = new Timer();
                    currentTimer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            handler.sendMessage(handler.obtainMessage(1));
                        }
                    }, 1000 * showTime);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        return this;
    }

    public BannerCirlePlayer setData(List<String> urlList, List<OnClickListener> lis, boolean isAutoCircle, int showTime) {
        this.showTime = showTime;
        setData(urlList, lis, isAutoCircle);
        return this;
    }



    /**
     * dip转px
     *
     * @param context
     * @param dipValue
     * @return
     */
    public static int dip2px(Context context, float dipValue) {
        return (int) (dipValue * context.getResources().getDisplayMetrics().density + 0.5f);
    }

}
