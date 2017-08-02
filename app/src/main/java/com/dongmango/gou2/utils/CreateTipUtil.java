package com.dongmango.gou2.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.dev.superframe.bean.KeyValueBean;
import com.dev.superframe.utils.DensityUtil;
import com.dongmango.gou2.R;

import java.util.ArrayList;
import java.util.List;

import static com.baidu.mapapi.BMapManager.getContext;

/**
 * Created by Administrator on 2017/7/27.
 */

public class CreateTipUtil {
    public interface OnCheckedChangeListener {
        void OnCheckedChangeListener(String key, String value);
    }

    public static void createTipsToView(final Context context, LinearLayout llTipContainer, List<KeyValueBean> list, boolean isCheck, final OnCheckedChangeListener listener) {
        if (llTipContainer == null) {
            return;
        }
        //选项卡布局
        RadioGroup mRadioGroup = new RadioGroup(getContext());
        mRadioGroup.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mRadioGroup.setOrientation(LinearLayout.HORIZONTAL);
        mRadioGroup.setGravity(Gravity.CENTER);
        mRadioGroup.setPadding(DensityUtil.dip2px(context, 5), DensityUtil.dip2px(context, 5), DensityUtil.dip2px(context, 10), DensityUtil.dip2px(context, 5));
        llTipContainer.addView(mRadioGroup);

        List<RadioButton> mRadioButtons = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            String text = list.get(i).getValue();
            String classId = list.get(i).getKey();
            RadioButton radio = new RadioButton(getContext());
            radio.setButtonDrawable(android.R.color.transparent);
            radio.setBackgroundResource(R.drawable.radiobtn_tip_selector);
            ColorStateList csl = context.getResources().getColorStateList(R.color.radiobtn_tip_textcolor_selector);
            radio.setTextColor(csl);
//            LinearLayout.LayoutParams l = new LinearLayout.LayoutParams((int) DensityUtil.dip2px(this, 80), ViewGroup.LayoutParams.MATCH_PARENT, Gravity.CENTER);
            RadioGroup.LayoutParams lp = new RadioGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
            lp.setMargins(DensityUtil.dip2px(context, 8), 0, 0, 0);
            radio.setLayoutParams(lp);
            radio.setTextSize(16);
            radio.setGravity(Gravity.CENTER);
            radio.setText(text);
            radio.setTag(classId);

            radio.setPadding(DensityUtil.dip2px(context, 10), DensityUtil.dip2px(context, 2), DensityUtil.dip2px(context, 10), DensityUtil.dip2px(context, 2));
            mRadioGroup.addView(radio);
            mRadioButtons.add(radio);
        }
        if (listener != null) {
            mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    int radioButtonId = group.getCheckedRadioButtonId();
                    //根据ID获取RadioButton的实例
                    RadioButton rb = ((Activity) context).findViewById(radioButtonId);
                    listener.OnCheckedChangeListener(rb.getTag().toString(), rb.getText().toString());
                }
            });
        }
        //设定默认被选中的选项卡为第一项
        if (isCheck) {
            if (list != null && list.size() > 0) {
                mRadioGroup.check(mRadioGroup.getChildAt(0).getId());
            }
        }
    }
}
