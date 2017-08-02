package com.dongmango.gou2.activity.my;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.dev.superframe.base.BaseActivity;
import com.dev.superframe.utils.TopBarUtil;
import com.dev.superframe.utils.VersionUtil;
import com.dongmango.gou2.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/7/27.
 */

public class AboutUsActivity extends BaseActivity {

    @BindView(R.id.tv_ver)
    TextView tvVer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutus);
        ButterKnife.bind(this);

        initView();
        initData();
        initEvent();
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void initView() {
        TopBarUtil.initBtnBack(getActivity(), R.id.tv_base_back);
        TopBarUtil.initTitle(getActivity(), R.id.tv_base_title, "关于我们");

        tvVer.setText("版本V" + VersionUtil.getVersionName(getActivity()));
    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {

    }
}
