package com.dongmango.gou2.activity.tab.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dev.superframe.base.BaseNoStatusBarFragment;
import com.dev.superframe.utils.PreferenceUtil;
import com.dev.superframe.utils.TopBarUtil;
import com.dongmango.gou2.R;
import com.dongmango.gou2.activity.LoginActivity;
import com.dongmango.gou2.utils.UserDataUtil;

/**
 * Created by Administrator on 2017/4/14.
 */

public class ShopCartFragment extends BaseNoStatusBarFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.fragment_shopcart);

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
        TopBarUtil.initTitle(view, R.id.tv_base_title, "购物车");
    }

    @Override
    public void initData() {
        httpData();
    }

    @Override
    public void initEvent() {

    }

    public void updateUI() {
        String uid = PreferenceUtil.getPrefString(getActivity(), UserDataUtil.KEY_USER_ID, "");
        if (TextUtils.isEmpty(uid)) {
            toActivity(new Intent(getActivity(), LoginActivity.class));

//            ivHeadLogo.setImageResource(R.mipmap.no_user_head);
//            tvName.setText("未登录");
//            flSexage.setVisibility(View.GONE);
//            tvAge.setText("0");
//            tvName.setText("姓名");
//            tvAddress.setText("所在地点");

            return;
        } else {

        }
    }

    private void httpData() {
//        String json = PreferenceUtil.getPrefString(getActivity(), KeyValueConstants.KEY_HOME, "");
//        bindData(Json.parseObject(json, HomeBean.shopclass));
//        HttpJsonUtil.getHome(getContext(), 10001, new HttpManager.OnHttpResponseListener() {
//            @Override
//            public void onHttpResponse(int requestCode, String resultJson, Exception e) {
//                SimpleHUD.dismiss();
//                prsvDisplay.onRefreshComplete();
//                if (10001 == GetJsonUtils.getResponseCode(resultJson)) {
//                    String json = GetJsonUtils.getResponseResult(resultJson);
//                    PreferenceUtils.setPrefString(getActivity(), KeyValueConstants.KEY_HOME, json);
//                    bindData(Json.parseObject(json, HomeBean.shopclass));
//                } else {
//                    showShortToast(GetJsonUtils.getResponseError(resultJson));
//                }
//            }
//        });
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
    }
}
