package com.dongmango.gou2.activity.home.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSON;
import com.dev.superframe.base.BaseNoStatusBarFragment;
import com.dev.superframe.http.HttpManager;
import com.dev.superframe.utils.SetTextForViewUtil;
import com.dev.superframe.utils.glide.GlideUtil;
import com.dev.superframe.view.ptr.PtrClassicFrameLayout;
import com.dongmango.gou2.R;
import com.dongmango.gou2.activity.home.CartoonDetailActivity;
import com.dongmango.gou2.bean.BannerBean;
import com.dongmango.gou2.bean.RecomGoodsBean;
import com.dongmango.gou2.httpmanager.GetJsonUtil;
import com.dongmango.gou2.httpmanager.HttpJsonUtil;
import com.dongmango.gou2.utils.BannerUtil;
import com.dongmango.gou2.utils.GetViewUtil;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/7/27.
 */

public class RecomFragment extends BaseNoStatusBarFragment {
    Unbinder unbinder;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.ll_more_new)
    LinearLayout llMoreNew;
    @BindView(R.id.ll_new_container)
    LinearLayout llNewContainer;
    @BindView(R.id.ll_more_good)
    LinearLayout llMoreGood;
    @BindView(R.id.ll_good_container)
    LinearLayout llGoodContainer;
    @BindView(R.id.pfl_display)
    PtrClassicFrameLayout pflDisplay;

    private String id;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.fragment_recom);
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
        BannerUtil.initBanner(banner, new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                //1书籍 2期刊3周边
                showShortToast("<" + position + ">");
            }
        });
    }

    @Override
    public void initData() {
        httpBannerList();
        httpList();
    }

    @Override
    public void initEvent() {
//        pflDisplay.setLastUpdateTimeRelateObject(this);
//        pflDisplay.setPtrHandler(new PtrDefaultHandler() {
//            @Override
//            public void onRefreshBegin(PtrFrameLayout frame) {
//                pflDisplay.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        httpBannerList();
//                        httpList();
//                    }
//                }, 100);
//            }
//        });
//        pflDisplay.setPullToRefresh(false);
//        pflDisplay.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                pflDisplay.autoRefresh();
//            }
//        }, 100);
    }

    public void updateUI() {

    }

    private void httpBannerList() {
        HttpJsonUtil.getRecomData(id, "banner", 10001, new HttpManager.OnHttpResponseListener() {
            @Override
            public void onHttpResponse(int requestCode, String resultJson, Exception e) {
                if (0 == GetJsonUtil.getResponseCode(resultJson)) {
                    String json = GetJsonUtil.getResponseData(resultJson);

                    List<String> urlList = new ArrayList<>();
                    List<BannerBean> list = JSON.parseArray(json, BannerBean.class);
                    if (list != null && list.size() > 0) {
                        for (BannerBean o : list) {
                            urlList.add(o.getPath());
                        }
                        banner.update(urlList);
                    }
                } else {
                    showShortToast(GetJsonUtil.getResponseError(resultJson));
                }
            }
        });
    }

    private void httpList() {
//        String recomGoodsJson = PreferenceUtil.getPrefString(getActivity(), KeyValueConstants.KEY_RECOM_GOODS_JSON, "");
//        if (!TextUtils.isEmpty(recomGoodsJson)) {
//            RecomGoodsBean o = JSON.parseObject(recomGoodsJson, RecomGoodsBean.class);
//            initNew(o.getNeww());
//            initGood(o.getBoutique());
//        }
        HttpJsonUtil.getRecomData(id, "children", 10001, new HttpManager.OnHttpResponseListener() {
            @Override
            public void onHttpResponse(int requestCode, String resultJson, Exception e) {
                if (0 == GetJsonUtil.getResponseCode(resultJson)) {
                    String json = GetJsonUtil.getResponseData(resultJson);
//                    PreferenceUtil.setPrefString(getActivity(), KeyValueConstants.KEY_RECOM_GOODS_JSON, json);

                    RecomGoodsBean o = JSON.parseObject(json, RecomGoodsBean.class);
                    initNew(o.getNeww());
                    initGood(o.getBoutique());
                } else {
                    showShortToast(GetJsonUtil.getResponseError(resultJson));
                }
//                if (pflDisplay != null) {
//                    pflDisplay.refreshComplete();
//                }
            }
        });
    }

    private void bindData() {

    }

    private void initNew(List<RecomGoodsBean.NewwBean> list) {
        llNewContainer.removeAllViews();
        if (list != null && list.size() > 0) {
            for (RecomGoodsBean.NewwBean o : list) {
                View view = GetViewUtil.getView(getContext(), R.layout.item_new_goods);
                ImageView ivCover = view.findViewById(R.id.iv_cover);
                GlideUtil.loadImageView(getActivity(), R.mipmap.no_img, o.getImage(), ivCover);
                SetTextForViewUtil.setText(view, R.id.tv_name, o.getTitle());

                view.setTag(o);
                view.setOnClickListener(new onNewClickListener());
                llNewContainer.addView(view);
            }
        }
    }

    private class onNewClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            RecomGoodsBean.NewwBean o = (RecomGoodsBean.NewwBean) view.getTag();
            toActivity(new Intent(getActivity(), CartoonDetailActivity.class).putExtra(INTENT_ID, o.getId()));
        }
    }

    private void initGood(List<RecomGoodsBean.BoutiqueBean> list) {
        llGoodContainer.removeAllViews();
        if (list != null && list.size() > 0) {
            for (RecomGoodsBean.BoutiqueBean o : list) {
                View view = GetViewUtil.getView(getContext(), R.layout.item_good_goods);
                ImageView ivCover = view.findViewById(R.id.iv_cover);
                GlideUtil.loadImageView(getActivity(), R.mipmap.no_img, o.getImage(), ivCover);

                view.setTag(o);
                view.setOnClickListener(new onGoodClickListener());
                llGoodContainer.addView(view);
            }
        }
    }

    private class onGoodClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {

        }
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

    @OnClick({R.id.ll_more_new, R.id.ll_more_good})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_more_new:
                break;
            case R.id.ll_more_good:
                break;
        }
    }
}
