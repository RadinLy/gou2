package com.dongmango.gou2.activity.home.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.dev.superframe.base.BaseNoStatusBarFragment;
import com.dev.superframe.baseadapter.QuickAdapter;
import com.dev.superframe.bean.KeyValueBean;
import com.dev.superframe.http.HttpManager;
import com.dev.superframe.utils.Json;
import com.dev.superframe.utils.ListBindUtil;
import com.dev.superframe.view.ptr.PtrClassicFrameLayout;
import com.dev.superframe.view.ptr.PtrDefaultHandler2;
import com.dev.superframe.view.ptr.PtrFrameLayout;
import com.dongmango.gou2.R;
import com.dongmango.gou2.activity.home.CartoonDetailActivity;
import com.dongmango.gou2.adapter.AdapterUtil;
import com.dongmango.gou2.bean.ClassBean;
import com.dongmango.gou2.httpmanager.GetJsonUtil;
import com.dongmango.gou2.httpmanager.HttpJsonUtil;
import com.dongmango.gou2.utils.CreateTipUtil;
import com.dongmango.gou2.utils.TestDataUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/7/27.
 */

public class GoodsFragment extends BaseNoStatusBarFragment {
    Unbinder unbinder;
    @BindView(R.id.ll_tip_container)
    LinearLayout llTipContainer;
    @BindView(R.id.hsv_tip)
    HorizontalScrollView hsvTip;
    @BindView(R.id.pfl_display)
    PtrClassicFrameLayout pflDisplay;
    @BindView(R.id.gv_display)
    GridView gvDisplay;

    private String id;

    private List<ClassBean> mList = new ArrayList<>();
    private QuickAdapter<ClassBean> mAdapter;

    private int page = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.fragment_goods);
        unbinder = ButterKnife.bind(this, view);

        if (getArguments() != null) {
            id = getArguments().getString(INTENT_ID);
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
        AdapterUtil adapterUtil = new AdapterUtil<>();
        mAdapter = adapterUtil.getGoodsAdapter(getContext(), mList);
        gvDisplay.setAdapter(mAdapter);

        bindListData(TestDataUtil.getList());
    }

    @Override
    public void initData() {
        httpList();
    }

    @Override
    public void initEvent() {
        pflDisplay.setLastUpdateTimeRelateObject(this);
        pflDisplay.setPtrHandler(new PtrDefaultHandler2() {
            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {
                pflDisplay.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page++;
                        bindListData(TestDataUtil.getList());
                        if (pflDisplay != null) {
                            pflDisplay.refreshComplete();
                        }
                    }
                }, 1000);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                pflDisplay.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page = 1;
                        bindListData(TestDataUtil.getList());
                        if (pflDisplay != null) {
                            pflDisplay.refreshComplete();
                        }
                    }
                }, 1000);
            }
        });
        pflDisplay.setPullToRefresh(false);

        gvDisplay.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                toActivity(new Intent(getActivity(), CartoonDetailActivity.class));
            }
        });
    }

    public void updateUI() {

    }

    private void httpList() {
        HttpJsonUtil.getShopClass(id, 10001, new HttpManager.OnHttpResponseListener() {
            @Override
            public void onHttpResponse(int requestCode, String resultJson, Exception e) {
                if (0 == GetJsonUtil.getResponseCode(resultJson)) {
                    String json = GetJsonUtil.getResponseData(resultJson);
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
        List<KeyValueBean> kvList = new ArrayList<>();
        for (ClassBean o : list) {
            KeyValueBean kv = new KeyValueBean();
            kv.setKey(o.getCid());
            kv.setValue(o.getCname());
            kvList.add(kv);
        }
        CreateTipUtil.createTipsToView(getContext(), llTipContainer, kvList, true, new CreateTipUtil.OnCheckedChangeListener() {
            @Override
            public void OnCheckedChangeListener(String key, String value) {

            }
        });
    }

    private void bindListData(List<ClassBean> list) {
        if (list == null || list.size() <= 0) {
            return;
        }
        ListBindUtil<ClassBean, AbsListView, QuickAdapter> listBindUtil = new ListBindUtil<>();
        listBindUtil.bindList(gvDisplay, mList, mAdapter, page, list);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
