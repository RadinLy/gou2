package com.dongmango.gou2.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dev.superframe.base.BaseActivity;
import com.dev.superframe.http.HttpManager;
import com.dev.superframe.utils.GetTextForViewUtil;
import com.dev.superframe.utils.Json;
import com.dev.superframe.utils.PreferenceUtil;
import com.dev.superframe.utils.RegexUtil;
import com.dev.superframe.utils.TopBarUtil;
import com.dongmango.gou2.R;
import com.dongmango.gou2.bean.UserBean;
import com.dongmango.gou2.httpmanager.GetJsonUtil;
import com.dongmango.gou2.httpmanager.HttpJsonUtil;
import com.dongmango.gou2.utils.UserDataUtil;
import com.dongmango.gou2.view.GLoadingDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;

/**
 * Created by Administrator on 2017/7/26.
 */

public class LoginActivity extends BaseActivity {

    @BindView(R.id.edt_phone)
    EditText edtPhone;
    @BindView(R.id.edt_pwd)
    EditText edtPwd;
    @BindView(R.id.btn_ok)
    Button btnOk;
    @BindView(R.id.tv_forgetpwd)
    TextView tvForgetpwd;
    @BindView(R.id.tv_register)
    TextView tvRegister;

    public static LoginActivity loginActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        loginActivity = this;

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
        TopBarUtil.initTitle(getActivity(), R.id.tv_base_title, "登录");
    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {

    }

    private void httpLogin() {
        final String phone = GetTextForViewUtil.getText(edtPhone);
        final String pwd = GetTextForViewUtil.getText(edtPwd);

        if (TextUtils.isEmpty(phone)) {
            showShortToast("请输入手机号");
            return;
        } else {
            if (!RegexUtil.checkMobile(phone)) {
                showShortToast("手机号不正确");
                return;
            }
        }
        if (TextUtils.isEmpty(pwd)) {
            showShortToast("请输入密码");
            return;
        }
        String sign = JPushInterface.getRegistrationID(getActivity());
        GLoadingDialog.showDialogForLoading(getActivity(),"正在登录",true);
        HttpJsonUtil.login(phone, pwd, sign, 10001, new HttpManager.OnHttpResponseListener() {
            @Override
            public void onHttpResponse(int requestCode, String resultJson, Exception e) {
                GLoadingDialog.cancelDialogForLoading();
                if (0 == GetJsonUtil.getResponseCode(resultJson)) {
                    PreferenceUtil.setPrefString(getActivity(), UserDataUtil.KEY_USER_JSON, GetJsonUtil.getResponseData(resultJson));
                    UserBean userBean = Json.parseObject(GetJsonUtil.getResponseData(resultJson), UserBean.class);
                    PreferenceUtil.setPrefString(getActivity(), UserDataUtil.KEY_USER_ID, userBean.getUid());
                    finish();
                } else {
                    showShortToast(GetJsonUtil.getResponseError(resultJson));
                }
            }
        });
    }

    @OnClick({R.id.btn_ok, R.id.tv_forgetpwd, R.id.tv_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_ok:
                httpLogin();
                break;
            case R.id.tv_forgetpwd:
                toActivity(new Intent(getActivity(), ForgetPwdActivity.class));
                break;
            case R.id.tv_register:
                toActivity(new Intent(getActivity(), RegisterActivity.class));
                break;
        }
    }
}
