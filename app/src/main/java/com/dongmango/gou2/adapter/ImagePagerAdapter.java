package com.dongmango.gou2.adapter;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/31.
 */

public class ImagePagerAdapter extends PagerAdapter {

    private Context context;
    private List<View> viewList = new ArrayList<>();

    public ImagePagerAdapter(Context context, List<View> viewList) {
        this.context = context;
        this.viewList = viewList;
    }

    @Override
    public int getCount() {
        if (viewList == null) return 0;
        return viewList.size();
    }


    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = viewList.get(position);
        if (view != null) {
            container.addView(view, 0);
        }
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

}
