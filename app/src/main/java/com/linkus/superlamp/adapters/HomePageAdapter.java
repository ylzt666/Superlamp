package com.linkus.superlamp.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.ListFragment;

import java.util.List;

/**
 * Created by Administrator on 2017/9/25 0025.
 */

public class HomePageAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragments;

    public HomePageAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.mFragments = fragments;
    }


    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

}
