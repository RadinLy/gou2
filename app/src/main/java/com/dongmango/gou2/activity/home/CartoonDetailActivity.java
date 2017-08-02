package com.dongmango.gou2.activity.home;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.dev.superframe.base.BaseActivity;
import com.dev.superframe.http.HttpManager;
import com.dev.superframe.utils.BitmapUtil;
import com.dev.superframe.utils.GetTextForViewUtil;
import com.dev.superframe.utils.NumUtil;
import com.dev.superframe.utils.StringUtil;
import com.dev.superframe.utils.TopBarUtil;
import com.dongmango.gou2.R;
import com.dongmango.gou2.activity.home.fragment.basefragment.CartoonAssessFragment;
import com.dongmango.gou2.activity.home.fragment.basefragment.CartoonDetailFragment;
import com.dongmango.gou2.activity.home.fragment.basefragment.HeaderViewPagerFragment;
import com.dongmango.gou2.adapter.CustomHeaderPagerAdapter;
import com.dongmango.gou2.bean.CartoonBean;
import com.dongmango.gou2.httpmanager.GetJsonUtil;
import com.dongmango.gou2.httpmanager.HttpJsonUtil;
import com.dongmango.gou2.utils.GetViewUtil;
import com.dongmango.gou2.view.GLoadingDialog;
import com.lzy.widget.HeaderViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/7/31.
 */

public class CartoonDetailActivity extends BaseActivity {
    @BindView(R.id.ll_cartoon_container)
    LinearLayout llCartoonContainer;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_bookshop)
    TextView tvBookshop;
    @BindView(R.id.v_fgx)
    View vFgx;
    @BindView(R.id.tv_author)
    TextView tvAuthor;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_price_tip)
    TextView tvPriceTip;
    @BindView(R.id.iv_detail)
    ImageView ivDetail;
    @BindView(R.id.tv_detail)
    TextView tvDetail;
    @BindView(R.id.ll_detail)
    LinearLayout llDetail;
    @BindView(R.id.iv_assess)
    ImageView ivAssess;
    @BindView(R.id.tv_assess)
    TextView tvAssess;
    @BindView(R.id.ll_assess)
    LinearLayout llAssess;
    @BindView(R.id.vp_display)
    ViewPager vpDisplay;
    @BindView(R.id.hvp_display)
    HeaderViewPager hvpDisplay;

    private List<View> viewList = new ArrayList<>();
    private List<HeaderViewPagerFragment> fragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cartoondetail);
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
        TopBarUtil.initTitle(getActivity(), R.id.tv_base_title, "漫画周刊");

        initTab();
    }

    @Override
    public void initData() {
        httpData("");
    }

    @Override
    public void initEvent() {
        vpDisplay.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    resetTabView();
                    tvDetail.setTextColor(getResources().getColor(R.color.red));
                    ivDetail.setVisibility(View.VISIBLE);
                } else if (position == 1) {
                    resetTabView();
                    tvAssess.setTextColor(getResources().getColor(R.color.red));
                    ivAssess.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void resetView() {
        if (viewList != null && viewList.size() > 0) {
            for (View v : viewList) {
                LinearLayout llBaseContainer = v.findViewById(R.id.ll_base_container);
                llBaseContainer.setScaleX(1.0f);
                llBaseContainer.setScaleY(1.0f);

                FrameLayout flDisplay = v.findViewById(R.id.fl_display);
                flDisplay.setBackgroundResource(R.drawable.bg_border_white);

                ImageView ivArrow = v.findViewById(R.id.iv_arrow);
                ivArrow.setVisibility(View.INVISIBLE);
            }
        }
    }

    private void httpData(final String id) {
        GLoadingDialog.showDialogForLoading(getActivity(), true);
        String magazineId = getIntentText(INTENT_ID);
        HttpJsonUtil.getWeeklyDetail(getActivity(), id, magazineId, true, 10001, new HttpManager.OnHttpResponseListener() {
            @Override
            public void onHttpResponse(int requestCode, String resultJson, Exception e) {
                if (0 == GetJsonUtil.getResponseCode(resultJson)) {
                    String json = GetJsonUtil.getResponseData(resultJson);
                    CartoonBean o = JSON.parseObject(json, CartoonBean.class);
                    bindData(o);
                    if (TextUtils.isEmpty(id)) {
                        bindGalleryList(o.getPeriods());
                    }

                    if (fragments.get(0) != null) {
                        ((CartoonDetailFragment) fragments.get(0)).updateUI(json);
                    }
                    if (fragments.get(1) != null) {
                        ((CartoonAssessFragment) fragments.get(1)).updateUI(json);
                    }
                } else {
                    showShortToast(GetJsonUtil.getResponseError(resultJson));
                }
                GLoadingDialog.cancelDialogForLoading();
            }
        });
    }

    private void bindData(CartoonBean o) {
        TopBarUtil.initTitle(getActivity(), R.id.tv_base_title, o.getBook().getBookname());
        tvName.setText(o.getBook().getBookname());
        tvBookshop.setText(o.getBook().getPublishers());
        vFgx.setVisibility(TextUtils.isEmpty(o.getBook().getAuthor().trim()) ? View.GONE : View.VISIBLE);
        tvAuthor.setVisibility(TextUtils.isEmpty(o.getBook().getAuthor().trim()) ? View.GONE : View.VISIBLE);
        if (!TextUtils.isEmpty(o.getBook().getAuthor().trim())) {
            tvAuthor.setText(o.getBook().getAuthor());
        }

        if (!TextUtils.isEmpty(o.getRebate()) && "1".equals(o.getRebate())) {
            tvPrice.setText("￥" + StringUtil.getPrice(NumUtil.getDouble(o.getPrice()) * NumUtil.getDouble(o.getUser_subsidy()) / 100));
        } else {
            tvPrice.setText("￥" + o.getPrice());
        }

        tvPriceTip.setText("原价￥" + o.getRetail_price());
        tvPriceTip.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
    }

    private void bindGalleryList(List<CartoonBean.PeriodsBean> list) {
        for (int i = 0; i < list.size(); i++) {
            CartoonBean.PeriodsBean o = list.get(i);
            View view = GetViewUtil.getView(getActivity(), R.layout.layout_item_cartoon);
            final LinearLayout llBaseContainer = view.findViewById(R.id.ll_base_container);

            final FrameLayout flDisplay = view.findViewById(R.id.fl_display);
            final FrameLayout flInvertedDisplay = view.findViewById(R.id.fl_inverted_display);

            final ImageView ivDisplay = view.findViewById(R.id.iv_display);
            final ImageView ivInvertedDisplay = view.findViewById(R.id.iv_inverted_display);

            //方法中设置asBitmap可以设置回调类型
            Glide.with(getActivity()).load(o.getCover()).asBitmap().into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    ivDisplay.setImageBitmap(resource);
                    ivInvertedDisplay.setImageBitmap(BitmapUtil.createReflectedImage(resource));
                }
            });
            final TextView tvNewTip = view.findViewById(R.id.tv_new_tip);
            final TextView tvName = view.findViewById(R.id.tv_name);

            if (i == 0) {
                tvNewTip.setVisibility(View.VISIBLE);
            } else {
                tvNewTip.setVisibility(View.GONE);
            }

            tvName.setText(o.getPtitle());

            final ImageView ivArrow = view.findViewById(R.id.iv_arrow);
            ivArrow.setVisibility(View.INVISIBLE);
            view.setTag(o.getId());
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    resetView();
                    llBaseContainer.setScaleX(1.1f);
                    llBaseContainer.setScaleY(1.1f);

                    flDisplay.setBackgroundResource(R.drawable.bg_border_red);

                    ivArrow.setVisibility(View.VISIBLE);

                    httpData(GetTextForViewUtil.getTagText(view));
                }
            });
            llCartoonContainer.addView(view);
            viewList.add(view);

            if (i == 0) {
                llBaseContainer.setScaleX(1.1f);
                llBaseContainer.setScaleY(1.1f);

                flDisplay.setBackgroundResource(R.drawable.bg_border_red);
            }
        }
    }

    private void initTab() {
        if (fragments != null && fragments.size() > 0) {
            fragments.clear();
        }
        CartoonDetailFragment fragment = new CartoonDetailFragment();
        fragments.add(fragment);

        CartoonAssessFragment fragment2 = new CartoonAssessFragment();
        fragments.add(fragment2);

        if (fragments != null && fragments.size() > 0) {
            hvpDisplay.setCurrentScrollableContainer(fragments.get(0));
            vpDisplay.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
                @Override
                public void onPageSelected(int position) {
                    hvpDisplay.setCurrentScrollableContainer(fragments.get(position));
                }
            });
            CustomHeaderPagerAdapter pagerAdapter = new CustomHeaderPagerAdapter(getSupportFragmentManager(), fragments);
            vpDisplay.setAdapter(pagerAdapter);
        }
    }

    @OnClick({R.id.ll_detail, R.id.ll_assess})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_detail:
//                resetTabView();
//                tvDetail.setTextColor(getResources().getColor(R.color.red));
//                ivDetail.setVisibility(View.VISIBLE);
                vpDisplay.setCurrentItem(0);
                break;
            case R.id.ll_assess:
//                resetTabView();
//                tvAssess.setTextColor(getResources().getColor(R.color.red));
//                ivAssess.setVisibility(View.VISIBLE);
                vpDisplay.setCurrentItem(1);
                break;
        }
    }

    private void resetTabView() {
        tvDetail.setTextColor(getResources().getColor(R.color.text_color_deep_middle));
        ivDetail.setVisibility(View.INVISIBLE);

        tvAssess.setTextColor(getResources().getColor(R.color.text_color_deep_middle));
        ivAssess.setVisibility(View.INVISIBLE);
    }
}
