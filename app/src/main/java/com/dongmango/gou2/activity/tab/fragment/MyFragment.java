package com.dongmango.gou2.activity.tab.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.dev.selectavatar.utils.SDFileUtil;
import com.dev.selectavatar.utils.SelectAvatarUtil;
import com.dev.superframe.base.BaseNoStatusBarFragment;
import com.dev.superframe.http.HttpManager;
import com.dev.superframe.ui.activity.WebViewActivity;
import com.dev.superframe.utils.PreferenceUtil;
import com.dev.superframe.utils.TopBarUtil;
import com.dev.superframe.utils.glide.GlideUtil;
import com.dev.superframe.view.CircleImageView;
import com.dev.superframe.view.dialog.AlertStyleDialog;
import com.dongmango.gou2.R;
import com.dongmango.gou2.activity.LoginActivity;
import com.dongmango.gou2.activity.my.AboutUsActivity;
import com.dongmango.gou2.bean.AvatarBean;
import com.dongmango.gou2.bean.UserBean;
import com.dongmango.gou2.config.KeyValueConstants;
import com.dongmango.gou2.httpmanager.GetJsonUtil;
import com.dongmango.gou2.httpmanager.HttpJsonUtil;
import com.dongmango.gou2.utils.UserDataUtil;
import com.dongmango.gou2.view.GLoadingDialog;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * Created by Administrator on 2017/4/14.
 */

public class MyFragment extends BaseNoStatusBarFragment {
    Unbinder unbinder;

    @BindView(R.id.iv_avatar)
    CircleImageView ivAvatar;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_edithead)
    TextView tvEdithead;
    @BindView(R.id.ll_myorder)
    LinearLayout llMyorder;
    @BindView(R.id.ll_mysc)
    LinearLayout llMysc;
    @BindView(R.id.ll_about)
    LinearLayout llAbout;
    @BindView(R.id.ll_use)
    LinearLayout llUse;
    @BindView(R.id.ll_msg)
    LinearLayout llMsg;
    @BindView(R.id.ll_hb)
    LinearLayout llHb;
    @BindView(R.id.tv_exit)
    TextView tvExit;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.fragment_my);

        //功能归类分区方法，必须调用<<<<<<<<<<
        initView();
        initData();
        initEvent();
        //功能归类分区方法，必须调用>>>>>>>>>>

        unbinder = ButterKnife.bind(this, view);
        return view;//返回值必须为view
    }


    @Override
    public void initView() {
        TopBarUtil.initSpace(view, R.id.v_space);
        TopBarUtil.initTitle(view, R.id.tv_base_title, "我的");
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

            GlideUtil.loadImageView(getContext(), R.mipmap.icon_logo, null, ivAvatar);
            tvName.setText("未登录");
        } else {
            UserBean o = UserDataUtil.getUserData(getContext());
            String avatar = PreferenceUtil.getPrefString(getContext(), KeyValueConstants.KEY_AVATAR_URL, "");
            if (TextUtils.isEmpty(avatar)) {
                GlideUtil.loadImageView(getContext(), R.mipmap.icon_logo, o.getAvatar(), ivAvatar);
            } else {
                GlideUtil.loadImageView(getContext(), R.mipmap.icon_logo, avatar, ivAvatar);
            }
            tvName.setText(o.getNickname());
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
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.tv_edithead, R.id.ll_myorder, R.id.ll_mysc, R.id.ll_about, R.id.ll_use, R.id.ll_msg, R.id.ll_hb, R.id.tv_exit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_edithead:
                SelectAvatarUtil.showSheet(getActivity(), getFragmentManager());
                break;
            case R.id.ll_myorder:
                break;
            case R.id.ll_mysc:
                break;
            case R.id.ll_about:
                toActivity(new Intent(getActivity(), AboutUsActivity.class));
                break;
            case R.id.ll_use:
                String url = HttpJsonUtil.getServerUrl().replace("api/", "") + "about/agreement.html";
                toActivity(WebViewActivity.createIntent(getActivity(), "使用协议", url));
                break;
            case R.id.ll_msg:
                break;
            case R.id.ll_hb:
                break;
            case R.id.tv_exit:
                new AlertStyleDialog(getActivity(), "", "您确定退出此账户吗?", true, 0, new AlertStyleDialog.OnDialogButtonClickListener() {
                    @Override
                    public void onDialogButtonClick(int requestCode, boolean isPositive) {
                        if (isPositive) {
                            UserDataUtil.clearUserData(getActivity());
                            toActivity(new Intent(getActivity(), LoginActivity.class));
                        }
                    }
                }).show();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SelectAvatarUtil.OpenCamera) {
            SelectAvatarUtil.startPhotoZoom(getActivity(), SDFileUtil.getPhotoCachePath() + "temp.jpg");
        } else if (requestCode == SelectAvatarUtil.OpenAlbum && data != null) {
            SelectAvatarUtil.startPhotoZoom(getActivity(), SelectAvatarUtil.getRealFilePath(getActivity(), data.getData()));
        } else if (requestCode == SelectAvatarUtil.OpenPreview) {
            if (data != null) {
                String path = SelectAvatarUtil.setPicToView(data);
                File file = new File(path);
                Glide.with(this).load(file).into(ivAvatar);
                GLoadingDialog.showDialogForLoading(getContext(), "上传中...", true);
                uploadAvatar(path);
            }
        }
    }

    private void uploadAvatar(String path) {
        HttpJsonUtil.getLogo(getContext(), path, 10001, new HttpManager.OnHttpResponseListener() {
            @Override
            public void onHttpResponse(int requestCode, String resultJson, Exception e) {
                GLoadingDialog.cancelDialogForLoading();
                if (0 == GetJsonUtil.getResponseCode(resultJson)) {
                    String json = GetJsonUtil.getResponseData(resultJson);
                    AvatarBean o = JSON.parseObject(json, AvatarBean.class);
                    PreferenceUtil.setPrefString(getContext(), KeyValueConstants.KEY_AVATAR_URL, o.getAvatar());
                    GlideUtil.loadImageView(getContext(), R.mipmap.icon_logo, o.getAvatar(), ivAvatar);
                    showShortToast("头像上传成功");
                } else {
                    showShortToast(GetJsonUtil.getResponseError(resultJson));
                }
            }
        });
    }
}
