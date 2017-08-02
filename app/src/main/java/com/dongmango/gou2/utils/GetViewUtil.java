package com.dongmango.gou2.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by Administrator on 2017/7/27.
 */

public class GetViewUtil {
    public static View getView(Context context, int resId) {
        View view = LayoutInflater.from(context).inflate(resId, null);
        return view;
    }
}
