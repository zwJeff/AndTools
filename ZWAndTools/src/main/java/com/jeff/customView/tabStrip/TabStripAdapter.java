package com.jeff.customView.tabStrip;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * 说明：
 * 作者： 张武
 * 日期： 2017/5/5.
 * email:wuzhang4@creditease.cn
 */

public class TabStripAdapter extends FragmentPagerAdapter {
    public int currentFragmentType;
    private ArrayList<Fragment> mList;//与标题对应的fragment集合
    private String[] mSubName;//标题集合

    public TabStripAdapter(FragmentManager fm,String[] mSubName, ArrayList<Fragment> fragmentList) {
        super(fm);
        this.mSubName=mSubName;
        mList = fragmentList;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        currentFragmentType = 8 + position;
        super.setPrimaryItem(container, position, object);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = mSubName[position];
        return title;
    }

    @Override
    public Fragment getItem(int position) {
        return mList.get(position);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

}