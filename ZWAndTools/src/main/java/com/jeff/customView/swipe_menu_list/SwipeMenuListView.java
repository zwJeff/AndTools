package com.jeff.customView.swipe_menu_list;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

/**
 * 说明：
 * 作者： 张武
 * 日期： 2017/7/19.
 * email:wuzhang4@creditease.cn
 */

public class SwipeMenuListView extends ListView {

    private boolean isScrollable = true;
    SwipeMenuItemView mSwipeMenuItemView = null;
    private int currentItemId=0;

    public SwipeMenuListView(Context context) {
        super(context);
    }

    public SwipeMenuListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                int x = (int) ev.getX();
                int y = (int) ev.getY();
                int position = pointToPosition(x, y);
                if (position != INVALID_POSITION) {
                    mSwipeMenuItemView = (SwipeMenuItemView) getChildAt(position - getFirstVisiblePosition());
                    currentItemId=position - getFirstVisiblePosition();
                }
                break;
        }
        if (mSwipeMenuItemView != null) {
            mSwipeMenuItemView.onTouchEvent(ev);
            mSwipeMenuItemView.setSwipeMenuItemChangeListener(new SwipeMenuItemChangeListener() {
                @Override
                public void onSwipeMenuItemChange(boolean isMenuShow) {
                    if(isMenuShow){
                        for(int i=getFirstVisiblePosition();i<=getLastVisiblePosition();i++){
                            if(i!=currentItemId)
                            ((SwipeMenuItemView) getChildAt(i)).setIsMenuShow(false);
                        }
                    }
                }
            });
        }

        //如果list不可滑动，拦截所有move事件
        if (ev.getAction() == MotionEvent.ACTION_MOVE)
            return isScrollable?super.onTouchEvent(ev):true;

        return super.onTouchEvent(ev);
    }

    public void resetAllItem(){
        for(int i=getFirstVisiblePosition();i<=getLastVisiblePosition();i++){
            if(i!=currentItemId)
                ((SwipeMenuItemView) getChildAt(i)).setIsMenuShow(false);
        }
    }

    public SwipeMenuListView setIsScrollable(boolean isScrollable){
        this.isScrollable=isScrollable;
        return this;
    }

}
