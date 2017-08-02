package com.dongmango.gou2.activity.tab.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import com.dev.superframe.base.BaseNoStatusBarFragment;
import com.dev.superframe.baseadapter.QuickAdapter;
import com.dev.superframe.http.HttpManager;
import com.dev.superframe.utils.Json;
import com.dev.superframe.utils.ListBindUtil;
import com.dev.superframe.utils.PreferenceUtil;
import com.dev.superframe.utils.TopBarUtil;
import com.dongmango.gou2.R;
import com.dongmango.gou2.adapter.AdapterUtil;
import com.dongmango.gou2.bean.ClassBean;
import com.dongmango.gou2.config.KeyValueConstants;
import com.dongmango.gou2.httpmanager.GetJsonUtil;
import com.dongmango.gou2.httpmanager.HttpJsonUtil;
import com.dongmango.gou2.view.GLoadingDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/4/14.
 */

public class ClassFragment extends BaseNoStatusBarFragment {
    Unbinder unbinder;
    @BindView(R.id.list1)
    ListView list1;
    @BindView(R.id.grid2)
    GridView grid2;

    private AdapterUtil<ClassBean> adapterUtil;
    private ListBindUtil<ClassBean, AbsListView, QuickAdapter> listBindUtil;

    private List<ClassBean> mList = new ArrayList<>();
    private QuickAdapter<ClassBean> mAdapter;

    private List<ClassBean> mList2 = new ArrayList<>();
    private QuickAdapter<ClassBean> mAdapter2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.fragment_class);
        unbinder = ButterKnife.bind(this, view);

        //功能归类分区方法，必须调用<<<<<<<<<<
        initView();
        initData();
        initEvent();
        //功能归类分区方法，必须调用>>>>>>>>>>

        return view;//返回值必须为view
    }


    @Override
    public void initView() {
        TopBarUtil.initSpace(view, R.id.v_space);
        TopBarUtil.initTitle(view, R.id.tv_base_title, "分类");
    }

    @Override
    public void initData() {
        adapterUtil = new AdapterUtil<>();
        listBindUtil = new ListBindUtil<>();

        mAdapter = adapterUtil.getClassFatherAdapter(getContext(), mList);
        mAdapter2 = adapterUtil.getClassChildAdapter(getContext(), mList2);

        list1.setAdapter(mAdapter);
        grid2.setAdapter(mAdapter2);

        httpList();
    }

    @Override
    public void initEvent() {
        list1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mList2 != null && mList2.size() > 0) {
                    mList2.clear();
                    mAdapter2.clear();
                }
                httpList(mList.get(position).getCid());
                adapterUtil.iCheckFather = position;
                mAdapter.notifyDataSetChanged();
            }
        });
        grid2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showShortToast("点击了:" + mList2.get(position).getCname());
            }
        });
    }

    public void updateUI() {

    }

    private void httpList() {
        GLoadingDialog.showDialogForLoading(getActivity(), true);
        String json = PreferenceUtil.getPrefString(getActivity(), KeyValueConstants.KEY_CLASS_JSON, "");
        bindFatherData(Json.parseArray(json, ClassBean.class));
        HttpJsonUtil.getShopClass("0", 10001, new HttpManager.OnHttpResponseListener() {
            @Override
            public void onHttpResponse(int requestCode, String resultJson, Exception e) {
                GLoadingDialog.cancelDialogForLoading();
                if (0 == GetJsonUtil.getResponseCode(resultJson)) {
                    String json = GetJsonUtil.getResponseData(resultJson);
                    PreferenceUtil.setPrefString(getActivity(), KeyValueConstants.KEY_CLASS_JSON, json);
                    bindFatherData(Json.parseArray(json, ClassBean.class));
                } else {
                    showShortToast(GetJsonUtil.getResponseError(resultJson));
                }
            }
        });
    }

    private void httpList(final String cid) {
        HttpJsonUtil.getShopClass(cid, 10001, new HttpManager.OnHttpResponseListener() {
            @Override
            public void onHttpResponse(int requestCode, String resultJson, Exception e) {
                if (0 == GetJsonUtil.getResponseCode(resultJson)) {
                    String json = GetJsonUtil.getResponseData(resultJson);
                    bindChildData(Json.parseArray(json, ClassBean.class));
                } else {
                    showShortToast(GetJsonUtil.getResponseError(resultJson));
                }
            }
        });
    }

    private void bindFatherData(List<ClassBean> list) {
        listBindUtil.bindList(list1, mList, mAdapter, 1, list);
        if (list != null && list.size() > 0) {
            httpList(list.get(0).getCid());
        }
    }

    private void bindChildData(List<ClassBean> list) {
        listBindUtil.bindList(grid2, mList2, mAdapter2, 1, list);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
