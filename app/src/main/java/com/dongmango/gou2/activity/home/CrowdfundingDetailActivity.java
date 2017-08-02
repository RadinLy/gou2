package com.dongmango.gou2.activity.home;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.dev.superframe.base.BaseActivity;
import com.dev.superframe.http.HttpManager;
import com.dev.superframe.utils.DateTimeUtil;
import com.dev.superframe.utils.NumUtil;
import com.dev.superframe.utils.TopBarUtil;
import com.dev.superframe.utils.glide.GlideUtil;
import com.dev.superframe.view.CircleImageView;
import com.dongmango.gou2.R;
import com.dongmango.gou2.bean.CrowdfundingDetailBean;
import com.dongmango.gou2.httpmanager.GetJsonUtil;
import com.dongmango.gou2.httpmanager.HttpJsonUtil;
import com.dongmango.gou2.utils.BannerUtil;
import com.dongmango.gou2.utils.GetStateValueUtil;
import com.dongmango.gou2.view.GLoadingDialog;
import com.dongmango.gou2.view.numberprogressbar.NumberProgressBar;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/8/1.
 */

public class CrowdfundingDetailActivity extends BaseActivity {
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.tv_state)
    TextView tvState;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.iv_avatar)
    CircleImageView ivAvatar;
    @BindView(R.id.tv_info)
    TextView tvInfo;
    @BindView(R.id.npb_display)
    NumberProgressBar npbDisplay;
    @BindView(R.id.tv_zcrs)
    TextView tvZcrs;
    @BindView(R.id.tv_ycje)
    TextView tvYcje;
    @BindView(R.id.tv_jjssj)
    TextView tvJjssj;
    @BindView(R.id.web_display)
    WebView webDisplay;
    @BindView(R.id.tv_zcprice)
    TextView tvZcprice;
    @BindView(R.id.tv_pay)
    TextView tvPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crowdfundingdetail);
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
        TopBarUtil.initTitle(getActivity(), R.id.tv_base_title, "众筹");
        autoSetTitle();

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
        httpData();
    }

    @Override
    public void initEvent() {

    }

    private void httpData() {
        GLoadingDialog.showDialogForLoading(getActivity(), true);
        String id = getIntentText(INTENT_ID);
        HttpJsonUtil.getCrowdfuningDetail(id, 10001, new HttpManager.OnHttpResponseListener() {
            @Override
            public void onHttpResponse(int requestCode, String resultJson, Exception e) {
                if (0 == GetJsonUtil.getResponseCode(resultJson)) {
                    String json = GetJsonUtil.getResponseData(resultJson);
                    CrowdfundingDetailBean o = JSON.parseObject(json, CrowdfundingDetailBean.class);
                    bindData(o);
                } else {
                    showShortToast(GetJsonUtil.getResponseError(resultJson));
                }
                GLoadingDialog.cancelDialogForLoading();
            }
        });
    }

    private void bindData(CrowdfundingDetailBean o) {
        List<String> url = new ArrayList<>();
        url.add(o.getImage());
        banner.update(url);

        tvState.setText(GetStateValueUtil.getCrowdfundingStateValue(o.getStatus()));
        tvName.setText(o.getTitle());

        GlideUtil.loadImageView(context, R.mipmap.icon_logo, o.getAuthor_avatar(), ivAvatar);
        tvInfo.setText(o.getIntro());

        Map<String, String> mapTotal = DateTimeUtil.getTimeDifference(new Date(Long.valueOf(o.getCreate_time() + "000")), new Date(Long.valueOf(o.getEnd_time() + "000")));
        Map<String, String> mapPass = DateTimeUtil.getTimeDifference(new Date(Long.valueOf(o.getCreate_time() + "000")), new Date());
        Map<String, String> map = DateTimeUtil.getTimeDifference(new Date(), new Date(Long.valueOf(o.getEnd_time() + "000")));
        int progress = (NumUtil.getInt(mapPass.get("days")) * 24 + NumUtil.getInt(mapPass.get("hours"))) * 100 / (NumUtil.getInt(mapTotal.get("days")) * 24 + NumUtil.getInt(mapTotal.get("hours")));
        npbDisplay.setProgress(progress);

        tvZcrs.setText(o.getPeople_num() + "人");
        tvYcje.setText("￥" + o.getNow_amount());
        tvJjssj.setText(map.get("days") + "天" + map.get("hours") + "小时");

        tvZcprice.setText("支持金额" + o.getLeast_money() + "元起");

        bindWeb(o.getContent());
    }

    private void bindWeb(String html) {
        //能够的调用JavaScript代码
        webDisplay.getSettings().setJavaScriptEnabled(true);
        webDisplay.getSettings().setSupportZoom(true);
        //SMALLEST(50),SMALLER(75),NORMAL(100),LARGER(150),LARGEST(200);
        webDisplay.getSettings().setTextSize(WebSettings.TextSize.NORMAL);
        webDisplay.getSettings().setDefaultTextEncodingName("UTF-8");//设置默认为utf-8
        webDisplay.loadData("<html><body>" + html + "</body></html>", "text/html; charset=UTF-8", null);//这种写法可以正确解码
    }

    @OnClick(R.id.tv_pay)
    public void onClick() {
    }
}
