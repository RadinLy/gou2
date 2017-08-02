package com.dongmango.gou2.activity.home.fragment.basefragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.dev.superframe.utils.StringUtil;
import com.dongmango.gou2.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/4/14.
 */

public class WebFragment extends HeaderViewPagerFragment {
    public static final String TAG = WebFragment.class.getSimpleName();
    Unbinder unbinder;
    @BindView(R.id.wv_display)
    WebView wvDisplay;

    private String html = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.fragment_web);
        unbinder = ButterKnife.bind(this, view);

        argument = getArguments();
        if (argument != null) {
            html = StringUtil.getCorrectUrl(argument.getString(INTENT_CONTENT));
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
        initWeb();
        wvDisplay.setFocusable(false);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {

    }

    private void initWeb() {
        //能够的调用JavaScript代码
        wvDisplay.getSettings().setJavaScriptEnabled(true);
        //SMALLEST(50),SMALLER(75),NORMAL(100),LARGER(150),LARGEST(200);
        wvDisplay.getSettings().setTextSize(WebSettings.TextSize.NORMAL);
        wvDisplay.getSettings().setDefaultTextEncodingName("UTF -8");//设置默认为utf-8
        wvDisplay.loadData("<html><body>" + html + "</body></html>", "text/html; charset=UTF-8", null);//这种写法可以正确解码
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public View getScrollableView() {
        return wvDisplay;
    }
}
