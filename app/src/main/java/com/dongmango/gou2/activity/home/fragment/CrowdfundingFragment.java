package com.dongmango.gou2.activity.home.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.dev.superframe.base.BaseNoStatusBarFragment;
import com.dev.superframe.baseadapter.QuickAdapter;
import com.dev.superframe.http.HttpManager;
import com.dev.superframe.utils.ListBindUtil;
import com.dev.superframe.view.ptr.PtrClassicFrameLayout;
import com.dev.superframe.view.ptr.PtrDefaultHandler2;
import com.dev.superframe.view.ptr.PtrFrameLayout;
import com.dongmango.gou2.R;
import com.dongmango.gou2.activity.home.CrowdfundingDetailActivity;
import com.dongmango.gou2.adapter.AdapterUtil;
import com.dongmango.gou2.bean.CrowdfundingBean;
import com.dongmango.gou2.httpmanager.GetJsonUtil;
import com.dongmango.gou2.httpmanager.HttpJsonUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/7/27.
 */

public class CrowdfundingFragment extends BaseNoStatusBarFragment {
    Unbinder unbinder;
    @BindView(R.id.pfl_display)
    PtrClassicFrameLayout pflDisplay;
    @BindView(R.id.lv_display)
    ListView lvDisplay;

    private String id;

    private List<CrowdfundingBean.ListDataBean> mList = new ArrayList<>();
    private QuickAdapter<CrowdfundingBean.ListDataBean> mAdapter;

    private int page = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.fragment_crowdfunding);
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
        mAdapter = adapterUtil.getCrowdfundingAdapter(getContext(), mList);
        lvDisplay.setAdapter(mAdapter);
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
                        httpList();
                    }
                }, 100);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                pflDisplay.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page = 1;
                        httpList();
                    }
                }, 100);
            }
        });
        pflDisplay.setPullToRefresh(false);

        lvDisplay.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                toActivity(new Intent(getActivity(), CrowdfundingDetailActivity.class).putExtra(INTENT_TITLE, mList.get(i).getTitle()).putExtra(INTENT_ID, mList.get(i).getId()));
            }
        });
    }

    public void updateUI() {

    }

    private void httpList() {
        HttpJsonUtil.getCrowdfuningList(id, page + "", 10001, new HttpManager.OnHttpResponseListener() {
            @Override
            public void onHttpResponse(int requestCode, String resultJson, Exception e) {
                if (0 == GetJsonUtil.getResponseCode(resultJson)) {
                    String json = GetJsonUtil.getResponseData(resultJson);
                    CrowdfundingBean o = JSON.parseObject(json, CrowdfundingBean.class);
                    bindListData(o.getListData());
                } else {
                    showShortToast(GetJsonUtil.getResponseError(resultJson));
                }
                if (pflDisplay != null) {
                    pflDisplay.refreshComplete();
                }
            }
        });
    }

    private void bindListData(List<CrowdfundingBean.ListDataBean> list) {
        if (list == null || list.size() <= 0) {
            return;
        }
        ListBindUtil<CrowdfundingBean.ListDataBean, AbsListView, QuickAdapter> listBindUtil = new ListBindUtil<>();
        listBindUtil.bindList(lvDisplay, mList, mAdapter, page, list);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
