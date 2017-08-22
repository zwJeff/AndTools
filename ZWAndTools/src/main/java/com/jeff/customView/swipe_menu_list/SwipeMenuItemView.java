package com.jeff.customView.swipe_menu_list;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.Scroller;

import com.jeff.lhcircle.Utils.UIUtils;

/**
 * 说明：
 * 作者： 张武
 * 日期： 2017/7/19.
 * email:wuzhang4@creditease.cn
 */

public class SwipeMenuItemView extends LinearLayout {

    private static final int MENU_HIDE = -1;
    private static final int MENU_CHANGING = 0;
    private static final int MENU_SHOW = 1;

    private SwipeMenuItemChangeListener lis;
    private Context context;
    private Scroller mScroller;
    //显示隐藏动画的时间
    private static final int AUTO_SCROLL_TIEM = 300;

    //右侧菜单
    private View menuView;
    //内容主体
    private View contentView;

    //主体宽度
    private int mWidth;
    //菜单宽度
    private int mMenuWidth;

    private int menuStatu = MENU_HIDE;

    private int mDownPosX = 0;
    private int mDownPosY = 0;
    private int mOldPosX = 0;
    private int mOldPosY = 0;

    private int moveDistance = 0;

    public SwipeMenuItemView(Context context, int width, View contentView, View menuView) {
        super(context);
        this.context = context;
        this.mWidth = width;
        this.contentView = contentView;
        this.menuView = menuView;
        mScroller = new Scroller(context, new AccelerateInterpolator());
        initView();
    }

    public SwipeMenuItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
    }

    private void initView() {
        setOrientation(HORIZONTAL);
        LayoutParams param1 = new LayoutParams(mWidth, LayoutParams.MATCH_PARENT);
        addView(contentView, param1);
        LayoutParams param2 = new LayoutParams(UIUtils.dip2px(context, 60), LayoutParams.MATCH_PARENT);
        addView(menuView, param2);

        /**
         * 测量menu的宽
         */
        menuView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            public boolean onPreDraw() {
                mMenuWidth = menuView.getMeasuredWidth();
                return true;
            }
        });
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownPosX = (int) event.getRawX();
                mDownPosY = (int) event.getRawY();
                mOldPosX = mDownPosX;
                mOldPosY = mDownPosY;
                break;
            case MotionEvent.ACTION_MOVE:
                int dX = (int) event.getRawX() - mOldPosX;
                moveDistance = (int) (event.getRawX() - mDownPosX);
                //item在最左边就不往左滑，在menu最右边 就不往右再滑了
                if (menuStatu == MENU_HIDE && dX > 0 || menuStatu == MENU_SHOW && dX < 0 || moveDistance == 0)
                    break;
                if (moveDistance >= mWidth) {
                    menuStatu=MENU_HIDE;
                } else if (moveDistance < -mMenuWidth) {
                    menuStatu=MENU_SHOW;
                }
                menuStatu = MENU_CHANGING;
                if (Math.abs(dX) > Math.abs(mOldPosY - (int) event.getRawY()))
                    scrollBy(-dX, 0);
                mOldPosX = (int) event.getRawX();
                break;
            case MotionEvent.ACTION_UP:
                moveDistance = (int) (event.getRawX() - mDownPosX);
                if (menuStatu == MENU_HIDE && moveDistance > 0 || menuStatu == MENU_SHOW && moveDistance < 0)
                    break;

                if (Math.abs(moveDistance) < 0.3 * mMenuWidth) {
                    setIsMenuShow(false);
                } else {
                    setIsMenuShow(moveDistance < 0);
                }
                break;
        }

        return super.onTouchEvent(event);
    }

    public SwipeMenuItemView setIsMenuShow(boolean isMenuShow) {

        int[] position = new int[2];
        contentView.getLocationOnScreen(position);

        if (isMenuShow) {
            if(menuStatu == MENU_SHOW)
                return this;
            lis.onSwipeMenuItemChange(true);
            menuStatu = MENU_SHOW;
            smoothScrollTo(-position[0],mScroller.getFinalY(),mMenuWidth,mScroller.getFinalY(),300);
        } else {
            if(menuStatu == MENU_HIDE)
                return this;
            lis.onSwipeMenuItemChange(false);
            menuStatu = MENU_HIDE;
            smoothScrollTo(-position[0],mScroller.getFinalY(),0,mScroller.getFinalY(),300);
        }
        invalidate();
        return this;
    }

    private void smoothScrollTo(int startX, int startY,int endX, int endY, int duration) {
        mScroller.startScroll(startX,startY, 0, 0, duration);
        mScroller.setFinalX(endX);
        mScroller.setFinalY(endY);
        invalidate();
    }

    @Override
    public void computeScroll() {

        //先判断mScroller滚动是否完成
        if (mScroller.computeScrollOffset()) {

            //这里调用View的scrollTo()完成实际的滚动
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());

            //必须调用该方法，否则不一定能看到滚动效果
            postInvalidate();
        }
        super.computeScroll();
    }


    public void setSwipeMenuItemChangeListener(SwipeMenuItemChangeListener lis) {
        this.lis = lis;
    }
}
