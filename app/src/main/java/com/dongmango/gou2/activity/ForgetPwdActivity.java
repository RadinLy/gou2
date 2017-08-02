package com.dongmango.gou2.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.dev.superframe.base.BaseActivity;
import com.dev.superframe.http.HttpManager;
import com.dev.superframe.utils.GetTextForViewUtil;
import com.dev.superframe.utils.RegexUtil;
import com.dev.superframe.utils.SMSCodeUtil;
import com.dev.superframe.utils.TopBarUtil;
import com.dongmango.gou2.R;
import com.dongmango.gou2.httpmanager.GetJsonUtil;
import com.dongmango.gou2.httpmanager.HttpJsonUtil;
import com.dongmango.gou2.view.GLoadingDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Administrator on 2017/1/6.
 */

public class ForgetPwdActivity extends BaseActivity {

    @BindView(R.id.edt_phone)
    EditText edtPhone;
    @BindView(R.id.btn_code)
    Button btnCode;
    @BindView(R.id.edt_code)
    EditText edtCode;
    @BindView(R.id.edt_pwd)
    EditText edtPwd;
    @BindView(R.id.btn_ok)
    Button btnOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpwd);
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
        TopBarUtil.initTitle(getActivity(), R.id.tv_base_title, "密码找回");
    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {

    }


    private void httpEditPwd() {
        final String phone = GetTextForViewUtil.getText(edtPhone);
        final String code = GetTextForViewUtil.getText(edtCode);
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
        if (TextUtils.isEmpty(code)) {
            showShortToast("请输入验证码");
            return;
        }
        if (TextUtils.isEmpty(pwd)) {
            showShortToast("请输入密码");
            return;
        }
        if (pwd.length() < 6 || pwd.length() > 16) {
            showShortToast("请输入密码(6~16位字母或数字)");
            return;
        }
        GLoadingDialog.showDialogForLoading(getActivity(), "找回中...", true);
        HttpJsonUtil.getFindPwd(phone, pwd, code, 10001, new HttpManager.OnHttpResponseListener() {
            @Override
            public void onHttpResponse(int requestCode, String resultJson, Exception e) {
                GLoadingDialog.cancelDialogForLoading();
                if (0 == GetJsonUtil.getResponseCode(resultJson)) {
                    showShortToast("找回密码成功,请登录");

                    edtPhone.setText("");
                    edtPwd.setText("");
                    edtCode.setText("");

                    finish();
                } else {
                    showShortToast(GetJsonUtil.getResponseError(resultJson));
                }
            }
        });
    }

    private void httpGetCode() {
        final String phone = GetTextForViewUtil.getText(edtPhone);

        if (TextUtils.isEmpty(phone)) {
            showShortToast("请输入手机号");
            return;
        } else {
            if (!RegexUtil.checkMobile(phone)) {
                showShortToast("手机号不正确");
                return;
            }
        }
        SMSCodeUtil.startBtnTimer(btnCode);

        HttpJsonUtil.getToSMS(phone, 10001, new HttpManager.OnHttpResponseListener() {
            @Override
            public void onHttpResponse(int requestCode, String resultJson, Exception e) {
                if (0 == GetJsonUtil.getResponseCode(resultJson)) {
                    showShortToast("短信验证码已发送成功,请注意查收");
                } else {
                    showShortToast(GetJsonUtil.getResponseError(resultJson));
                    SMSCodeUtil.cancelTimer(btnCode);
                }
            }
        });
    }

    @OnClick({R.id.btn_code, R.id.btn_ok})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_code:
                httpGetCode();
                break;
            case R.id.btn_ok:
                httpEditPwd();
                break;
        }
    }
}
