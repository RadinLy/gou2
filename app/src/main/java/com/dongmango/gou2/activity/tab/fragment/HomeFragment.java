package com.dongmango.gou2.activity.tab.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dev.superframe.base.BaseNoStatusBarFragment;
import com.dev.superframe.http.HttpManager;
import com.dev.superframe.utils.Json;
import com.dev.superframe.utils.PreferenceUtil;
import com.dev.superframe.utils.TopBarUtil;
import com.dongmango.gou2.R;
import com.dongmango.gou2.activity.home.SelectCityActivity;
import com.dongmango.gou2.activity.home.fragment.CrowdfundingFragment;
import com.dongmango.gou2.activity.home.fragment.GoodsFragment;
import com.dongmango.gou2.activity.home.fragment.RecomFragment;
import com.dongmango.gou2.adapter.CustomPagerAdapter;
import com.dongmango.gou2.bean.CityBean;
import com.dongmango.gou2.bean.ClassBean;
import com.dongmango.gou2.config.KeyValueConstants;
import com.dongmango.gou2.httpmanager.GetJsonUtil;
import com.dongmango.gou2.httpmanager.HttpJsonUtil;
import com.dongmango.gou2.view.GLoadingDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/4/14.
 */

public class HomeFragment extends BaseNoStatusBarFragment {
    Unbinder unbinder;
    @BindView(R.id.tv_base_city)
    TextView tvBaseCity;
    @BindView(R.id.tv_base_search)
    TextView tvBaseSearch;
    @BindView(R.id.tv_base_msg)
    TextView tvBaseMsg;
    @BindView(R.id.ll_base_topbar)
    LinearLayout llBaseTopbar;
    @BindView(R.id.v_base_line)
    View vBaseLine;
    @BindView(R.id.tl_display)
    TabLayout tlDisplay;
    @BindView(R.id.vp_display)
    ViewPager vpDisplay;

    private List<Fragment> fragments = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.fragment_home);
        unbinder = ButterKnife.bind(this, view);

        //功能归类分区方法，必须调用<<<<<<<<<<
        initView();
        initData();
        initEvent();
        //功能归类分区方法，必须调用>>>>>>>>>>

        return view;//返回值必须为view
    }

    @Subscribe
    public void onEvent(CityBean cityBean) {
        tvBaseCity.setText(cityBean.getRegion_name());
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void initView() {
        TopBarUtil.initSpace(view, R.id.v_space);
    }

    @Override
    public void initData() {
        httpList();
        updateUI();
    }

    @Override
    public void initEvent() {

    }

    public void updateUI() {
        String cityName = PreferenceUtil.getPrefString(getActivity(), KeyValueConstants.KEY_CITY_NAME, "");
        if (!TextUtils.isEmpty(cityName)) {
            tvBaseCity.setText(cityName);
        }
    }

    private void httpList() {
        GLoadingDialog.showDialogForLoading(getActivity(), true);
//        String json = PreferenceUtil.getPrefString(getActivity(), KeyValueConstants.KEY_CLASS_JSON, "");
//        bindData(Json.parseArray(json, ClassBean.class));
        HttpJsonUtil.getShopClass("0", 10001, new HttpManager.OnHttpResponseListener() {
            @Override
            public void onHttpResponse(int requestCode, String resultJson, Exception e) {
                GLoadingDialog.cancelDialogForLoading();
                if (0 == GetJsonUtil.getResponseCode(resultJson)) {
                    String json = GetJsonUtil.getResponseData(resultJson);
                    PreferenceUtil.setPrefString(getActivity(), KeyValueConstants.KEY_CLASS_JSON, json);
                    bindData(Json.parseArray(json, ClassBean.class));
                } else {
                    showShortToast(GetJsonUtil.getResponseError(resultJson));
                }
            }
        });
    }

    private void bindData(List<ClassBean> list) {
        if (list == null || list.size() <= 0) {
            return;
        }
        for (ClassBean o : list) {
            Bundle bundle = new Bundle();
            bundle.putString(INTENT_ID, o.getCid());
            if ("推荐".equals(o.getCname())) {
                RecomFragment fragment = new RecomFragment();
                fragment.setArguments(bundle);
                fragments.add(fragment);
            } else if ("众筹".equals(o.getCname())) {
                CrowdfundingFragment fragment = new CrowdfundingFragment();
                fragment.setArguments(bundle);
                fragments.add(fragment);
            } else {
                GoodsFragment fragment = new GoodsFragment();
                fragment.setArguments(bundle);
                fragments.add(fragment);
            }
        }

        CustomPagerAdapter myPagerAdapter = new CustomPagerAdapter(getFragmentManager(), fragments);
        vpDisplay.setAdapter(myPagerAdapter);
        tlDisplay.setupWithViewPager(vpDisplay);
        for (int i = 0; i < list.size(); i++) {
            tlDisplay.getTabAt(i).setText(list.get(i).getCname());
        }
        tlDisplay.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.tv_base_city, R.id.tv_base_search, R.id.tv_base_msg})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_base_city:
                toActivity(new Intent(getActivity(), SelectCityActivity.class));
                break;
            case R.id.tv_base_search:
                break;
            case R.id.tv_base_msg:
                break;
        }
    }
}
