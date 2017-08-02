package com.dongmango.gou2.activity.tab.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.dev.superframe.base.BaseNoStatusBarFragment;
import com.dev.superframe.baseadapter.QuickAdapter;
import com.dev.superframe.http.HttpManager;
import com.dev.superframe.utils.Json;
import com.dev.superframe.utils.ListBindUtil;
import com.dev.superframe.utils.PreferenceUtil;
import com.dev.superframe.utils.TopBarUtil;
import com.dev.superframe.view.NonScrollGridView;
import com.dev.superframe.view.ptr.PtrClassicFrameLayout;
import com.dev.superframe.view.ptr.PtrDefaultHandler;
import com.dev.superframe.view.ptr.PtrFrameLayout;
import com.dongmango.gou2.R;
import com.dongmango.gou2.activity.freeread.BookPagerActivity;
import com.dongmango.gou2.activity.freeread.BooksTabActivity;
import com.dongmango.gou2.adapter.AdapterUtil;
import com.dongmango.gou2.bean.FreeReadBean;
import com.dongmango.gou2.config.KeyValueConstants;
import com.dongmango.gou2.httpmanager.GetJsonUtil;
import com.dongmango.gou2.httpmanager.HttpJsonUtil;
import com.dongmango.gou2.utils.BannerUtil;
import com.dongmango.gou2.view.GLoadingDialog;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/4/14.
 */

public class FreeReadFragment extends BaseNoStatusBarFragment {
    Unbinder unbinder;
    @BindView(R.id.pfl_display)
    PtrClassicFrameLayout pflDisplay;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.ll_more_new)
    LinearLayout llMoreNew;
    @BindView(R.id.gv_display_new)
    NonScrollGridView gvDisplayNew;
    @BindView(R.id.ll_more_hot)
    LinearLayout llMoreHot;
    @BindView(R.id.gv_display_hot)
    NonScrollGridView gvDisplayHot;


    private List<FreeReadBean.XinBean> mList = new ArrayList<>();
    private QuickAdapter<FreeReadBean.XinBean> mAdapter;

    private List<FreeReadBean.HotBean> mList2 = new ArrayList<>();
    private QuickAdapter<FreeReadBean.HotBean> mAdapter2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.fragment_freeread);
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
        TopBarUtil.initTitle(view, R.id.tv_base_title, "免费阅读区");

        gvDisplayNew.setFocusable(false);
        gvDisplayHot.setFocusable(false);
    }

    @Override
    public void initData() {
        BannerUtil.initBanner(banner, new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {

            }
        });
        AdapterUtil adapterUtil = new AdapterUtil<>();
        mAdapter = adapterUtil.getNowBookAdapter(getContext(), mList);
        gvDisplayNew.setAdapter(mAdapter);

        mAdapter2 = adapterUtil.getHotBookAdapter(getContext(), mList2);
        gvDisplayHot.setAdapter(mAdapter2);

        httpData();
    }

    @Override
    public void initEvent() {
        pflDisplay.setLastUpdateTimeRelateObject(this);
        pflDisplay.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                pflDisplay.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        httpData();
                    }
                }, 100);
            }
        });
        gvDisplayNew.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String id = mList.get(i).getId();
                String title = mList.get(i).getBookname();
                BookPagerActivity.ImageSize imageSize = new BookPagerActivity.ImageSize(gvDisplayNew.getMeasuredWidth(), gvDisplayNew.getMeasuredHeight());
                BookPagerActivity.createIntent(getContext(), id, title, imageSize);
            }
        });
        gvDisplayHot.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String id = mList2.get(i).getId();
                String title = mList2.get(i).getBookname();
                BookPagerActivity.ImageSize imageSize = new BookPagerActivity.ImageSize(gvDisplayHot.getMeasuredWidth(), gvDisplayHot.getMeasuredHeight());
                BookPagerActivity.createIntent(getContext(), id, title, imageSize);
            }
        });
    }

    public void updateUI() {

    }

    private void httpData() {
        GLoadingDialog.showDialogForLoading(getContext(), true);
        String json = PreferenceUtil.getPrefString(getActivity(), KeyValueConstants.KEY_FREEREAD_JSON, "");
        bindData(Json.parseObject(json, FreeReadBean.class));
        HttpJsonUtil.getFreeList(10001, new HttpManager.OnHttpResponseListener() {
            @Override
            public void onHttpResponse(int requestCode, String resultJson, Exception e) {
                if (0 == GetJsonUtil.getResponseCode(resultJson)) {
                    String json = GetJsonUtil.getResponseData(resultJson);
                    PreferenceUtil.setPrefString(getActivity(), KeyValueConstants.KEY_FREEREAD_JSON, json);
                    bindData(Json.parseObject(json, FreeReadBean.class));
                } else {
                    showShortToast(GetJsonUtil.getResponseError(resultJson));
                }
                if (pflDisplay != null) {
                    pflDisplay.refreshComplete();
                }
                GLoadingDialog.cancelDialogForLoading();
            }
        });
    }

    private void bindData(FreeReadBean o) {
        if (o == null) {
            return;
        }
        List<String> urlList = new ArrayList<>();
        for (FreeReadBean.SlideBean so : o.getSlide()) {
            urlList.add(so.getCover());
        }
        banner.update(urlList);

        ListBindUtil<FreeReadBean.XinBean, AbsListView, QuickAdapter> listBindUtil = new ListBindUtil<>();
        listBindUtil.bindList(gvDisplayNew, mList, mAdapter, 1, o.getXin());
        ListBindUtil<FreeReadBean.HotBean, AbsListView, QuickAdapter> listBindUtil2 = new ListBindUtil<>();
        listBindUtil2.bindList(gvDisplayHot, mList2, mAdapter2, 1, o.getHot());
    }

    @Override
    public void onStart() {
        super.onStart();
        //开始轮播
        banner.startAutoPlay();
    }

    @Override
    public void onStop() {
        super.onStop();
        //结束轮播
        banner.stopAutoPlay();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.ll_more_new, R.id.ll_more_hot})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_more_new:
                toActivity(new Intent(getActivity(), BooksTabActivity.class).putExtra(INTENT_TITLE, "最新书籍"));
                break;
            case R.id.ll_more_hot:
                toActivity(new Intent(getActivity(), BooksTabActivity.class).putExtra(INTENT_TITLE, "最热书籍"));
                break;
        }
    }
}
