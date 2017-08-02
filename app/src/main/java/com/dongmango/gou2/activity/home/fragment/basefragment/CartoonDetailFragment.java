package com.dongmango.gou2.activity.home.fragment.basefragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.alibaba.fastjson.JSON;
import com.dev.superframe.baseadapter.QuickAdapter;
import com.dev.superframe.utils.StringUtil;
import com.dev.superframe.view.NonScrollListView;
import com.dongmango.gou2.R;
import com.dongmango.gou2.adapter.AdapterUtil;
import com.dongmango.gou2.bean.CartoonBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/4/14.
 */

public class CartoonDetailFragment extends HeaderViewPagerFragment {
    public static final String TAG = CartoonDetailFragment.class.getSimpleName();
    Unbinder unbinder;
    @BindView(R.id.sv_display)
    ScrollView svDisplay;
    @BindView(R.id.wv_display)
    WebView wvDisplay;
    @BindView(R.id.lv_display)
    NonScrollListView lvDisplay;
    @BindView(R.id.ll_near)
    LinearLayout llNear;

    private String json = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.fragment_cartoondetail);
        unbinder = ButterKnife.bind(this, view);

        argument = getArguments();
        if (argument != null) {
            json = StringUtil.getCorrectUrl(argument.getString(INTENT_CONTENT));
        }

        //功能归类分区方法，必须调用<<<<<<<<<<
        initView();
        initData();
        initEvent();
        //功能归类分区方法，必须调用>>>>>>>>>>

        return view;//返回值必须为view
    }

    @Override
    public void initView() {
        wvDisplay.setFocusable(false);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {

    }

    public void updateUI(String json) {
        CartoonBean o = JSON.parseObject(json, CartoonBean.class);
        bindWeb(o.getBook().getInformation());
        bindList(o.getPeripherys());
    }

    private void bindWeb(String html) {
        //能够的调用JavaScript代码
        wvDisplay.getSettings().setJavaScriptEnabled(true);
        //SMALLEST(50),SMALLER(75),NORMAL(100),LARGER(150),LARGEST(200);
        wvDisplay.getSettings().setTextSize(WebSettings.TextSize.NORMAL);
        wvDisplay.getSettings().setDefaultTextEncodingName("UTF -8");//设置默认为utf-8
        wvDisplay.loadData("<html><body>" + html + "</body></html>", "text/html; charset=UTF-8", null);//这种写法可以正确解码
    }

    private void bindList(List<CartoonBean.PeripherysBean> list) {
        AdapterUtil adapterUtil = new AdapterUtil();
        if (list != null && list.size() > 0) {
            llNear.setVisibility(View.VISIBLE);
            QuickAdapter mAdapter = adapterUtil.getNearCartoonAdapter(getContext(), list);
            lvDisplay.setAdapter(mAdapter);
        } else {
            llNear.setVisibility(View.GONE);
            QuickAdapter mAdapter = adapterUtil.getNearCartoonAdapter(getContext(), new ArrayList());
            lvDisplay.setAdapter(mAdapter);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public View getScrollableView() {
        return svDisplay;
    }
}
