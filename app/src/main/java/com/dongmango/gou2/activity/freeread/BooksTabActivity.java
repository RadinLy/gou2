package com.dongmango.gou2.activity.freeread;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.alibaba.fastjson.JSON;
import com.dev.superframe.base.BaseActivity;
import com.dev.superframe.http.HttpManager;
import com.dev.superframe.utils.TopBarUtil;
import com.dongmango.gou2.R;
import com.dongmango.gou2.activity.freeread.fragment.BookFragment;
import com.dongmango.gou2.adapter.CustomPagerAdapter;
import com.dongmango.gou2.bean.BookClassBean;
import com.dongmango.gou2.httpmanager.GetJsonUtil;
import com.dongmango.gou2.httpmanager.HttpJsonUtil;
import com.dongmango.gou2.view.GLoadingDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/5/3.
 */

public class BooksTabActivity extends BaseActivity {

    @BindView(R.id.tl_display)
    TabLayout tlDisplay;
    @BindView(R.id.vp_display)
    ViewPager vpDisplay;

    private List<Fragment> fragments = new ArrayList<Fragment>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookstab);
        ButterKnife.bind(this);

        initView();
        initData();
        initEvent();
    }

    public Activity getActivity() {
        return this;
    }

    @Override
    public void initView() {
        TopBarUtil.initBtnBack(getActivity(), R.id.tv_base_back);
        autoSetTitle();
    }

    @Override
    public void initData() {
        httpList();
    }

    @Override
    public void initEvent() {

    }

    private void initTab(BookClassBean o) {
        if (o == null) {
            return;
        }
        for (int i = 0; i < o.getCate().size(); i++) {
            Fragment fragment = new BookFragment();
            Bundle bundle = new Bundle();
            bundle.putString(INTENT_ID, getId(o.getCate(), o.getCate().get(i).getCname()));
            fragment.setArguments(bundle);
            fragments.add(fragment);
        }

        CustomPagerAdapter pagerAdapter = new CustomPagerAdapter(
                getSupportFragmentManager(), fragments);
        vpDisplay.setAdapter(pagerAdapter);
        vpDisplay.setOffscreenPageLimit(4);

        tlDisplay.setupWithViewPager(vpDisplay);
        for (int i = 0; i < o.getCate().size(); i++) {
            tlDisplay.getTabAt(i).setText(o.getCate().get(i).getCname());
        }
        tlDisplay.setTabGravity(TabLayout.GRAVITY_FILL);
        tlDisplay.setTabMode(TabLayout.MODE_FIXED);
    }

    /**
     * 获取ID
     */
    private String getId(List<BookClassBean.CateBean> mList, String name) {
        String id = "";
        for (BookClassBean.CateBean o : mList) {
            if (name.equals(o.getCname()) || name.contains(o.getCname()) || o.getCname().contains(name)) {
                id = o.getCid();
                break;
            }
        }
        return id;
    }

    private void httpList() {
        GLoadingDialog.showDialogForLoading(getActivity(), true);
        HttpJsonUtil.getFreeBookList("-1", "1", 10001, new HttpManager.OnHttpResponseListener() {
            @Override
            public void onHttpResponse(int requestCode, String resultJson, Exception e) {
                if (0 == GetJsonUtil.getResponseCode(resultJson)) {
                    String json = GetJsonUtil.getResponseData(resultJson);
                    initTab(JSON.parseObject(json, BookClassBean.class));
                } else {
                    showShortToast(GetJsonUtil.getResponseError(resultJson));
                }
                GLoadingDialog.cancelDialogForLoading();
            }
        });
    }
}