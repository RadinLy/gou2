package com.dongmango.gou2.adapter;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.dongmango.gou2.activity.home.fragment.basefragment.HeaderViewPagerFragment;

import java.util.List;

/**
 * Created by Administrator on 2016/3/23.
 */
public class CustomHeaderPagerAdapter extends FragmentPagerAdapter {
    List<HeaderViewPagerFragment> fragments;

    public CustomHeaderPagerAdapter(FragmentManager fm, List<HeaderViewPagerFragment> fragments) {

        super(fm);
        this.fragments = fragments;
    }


    @Override
    public HeaderViewPagerFragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
