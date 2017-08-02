package com.dongmango.gou2.activity.freeread.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;

import com.dev.superframe.base.BaseFragment;
import com.dev.superframe.baseadapter.QuickAdapter;
import com.dev.superframe.http.HttpManager;
import com.dev.superframe.utils.Json;
import com.dev.superframe.utils.ListBindUtil;
import com.dev.superframe.view.ptr.PtrClassicFrameLayout;
import com.dev.superframe.view.ptr.PtrDefaultHandler2;
import com.dev.superframe.view.ptr.PtrFrameLayout;
import com.dongmango.gou2.R;
import com.dongmango.gou2.activity.freeread.BookPagerActivity;
import com.dongmango.gou2.adapter.AdapterUtil;
import com.dongmango.gou2.bean.BookClassBean;
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

public class BookFragment extends BaseFragment {
    Unbinder unbinder;
    @BindView(R.id.pfl_display)
    PtrClassicFrameLayout pflDisplay;
    @BindView(R.id.gv_display)
    GridView gvDisplay;

    private String cid;

    private List<BookClassBean.ListDataBean> mList = new ArrayList<>();
    private QuickAdapter<BookClassBean.ListDataBean> mAdapter;

    private int page = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.fragment_book);
        unbinder = ButterKnife.bind(this, view);

        if (getArguments() != null) {
            cid = getArguments().getString(INTENT_ID);
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
        mAdapter = adapterUtil.getBookAdapter(getContext(), mList);
        gvDisplay.setAdapter(mAdapter);
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

        gvDisplay.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String id = mList.get(i).getId();
                String title = mList.get(i).getBookname();
                BookPagerActivity.ImageSize imageSize = new BookPagerActivity.ImageSize(gvDisplay.getMeasuredWidth(), gvDisplay.getMeasuredHeight());
                BookPagerActivity.createIntent(getContext(), id, title, imageSize);
            }
        });
    }

    public void updateUI() {

    }

    private void httpList() {
        HttpJsonUtil.getFreeBookList(cid, page + "", 10001, new HttpManager.OnHttpResponseListener() {
            @Override
            public void onHttpResponse(int requestCode, String resultJson, Exception e) {
                if (0 == GetJsonUtil.getResponseCode(resultJson)) {
                    String json = GetJsonUtil.getResponseData(resultJson);
                    bindListData(Json.parseObject(json, BookClassBean.class));
                } else {
                    showShortToast(GetJsonUtil.getResponseError(resultJson));
                }
                if (pflDisplay != null) {
                    pflDisplay.refreshComplete();
                }
            }
        });
    }


    private void bindListData(BookClassBean o) {
        if (o == null) {
            return;
        }
        ListBindUtil<BookClassBean.ListDataBean, AbsListView, QuickAdapter> listBindUtil = new ListBindUtil<>();
        listBindUtil.bindList(gvDisplay, mList, mAdapter, page, o.getListData());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
