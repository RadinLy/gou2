package com.dongmango.gou2.activity;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dev.superframe.base.BaseActivity;
import com.dev.superframe.http.HttpManager;
import com.dev.superframe.ui.activity.WebViewActivity;
import com.dev.superframe.utils.GetTextForViewUtil;
import com.dev.superframe.utils.Json;
import com.dev.superframe.utils.PreferenceUtil;
import com.dev.superframe.utils.RegexUtil;
import com.dev.superframe.utils.SMSCodeUtil;
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

public class RegisterActivity extends BaseActivity {

    @BindView(R.id.edt_name)
    EditText edtName;
    @BindView(R.id.edt_phone)
    EditText edtPhone;
    @BindView(R.id.edt_code)
    EditText edtCode;
    @BindView(R.id.btn_code)
    Button btnCode;
    @BindView(R.id.edt_pwd)
    EditText edtPwd;
    @BindView(R.id.tv_check)
    TextView tvCheck;
    @BindView(R.id.tv_protocol)
    TextView tvProtocol;
    @BindView(R.id.btn_ok)
    Button btnOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
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
        TopBarUtil.initTitle(getActivity(), R.id.tv_base_title, "快速注册");
    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {

    }

    private void httpRegister() {
        final String name = GetTextForViewUtil.getText(edtName);
        final String phone = GetTextForViewUtil.getText(edtPhone);
        final String code = GetTextForViewUtil.getText(edtCode);
        final String pwd = GetTextForViewUtil.getText(edtPwd);
        if (TextUtils.isEmpty(name)) {
            showShortToast("请输入昵称");
            return;
        }
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
        boolean isRead = GetTextForViewUtil.getTagBoolean(tvCheck);
        if (!isRead) {
            showShortToast("请阅读动漫购协议");
            return;
        }
        String sign = JPushInterface.getRegistrationID(RegisterActivity.this);
        GLoadingDialog.showDialogForLoading(getActivity(), "正在注册", true);
        HttpJsonUtil.register(name, phone, pwd, code, sign, 10001, new HttpManager.OnHttpResponseListener() {
            @Override
            public void onHttpResponse(int requestCode, String resultJson, Exception e) {
                GLoadingDialog.cancelDialogForLoading();
                if (0 == GetJsonUtil.getResponseCode(resultJson)) {
                    showShortToast("注册成功");
                    PreferenceUtil.setPrefString(getActivity(), UserDataUtil.KEY_USER_JSON, GetJsonUtil.getResponseData(resultJson));
                    UserBean userBean = Json.parseObject(GetJsonUtil.getResponseData(resultJson), UserBean.class);
                    PreferenceUtil.setPrefString(getActivity(), UserDataUtil.KEY_USER_ID, userBean.getUid());

                    if (LoginActivity.loginActivity != null) {
                        LoginActivity.loginActivity.finish();
                    }
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

    @OnClick({R.id.btn_code, R.id.tv_check, R.id.tv_protocol, R.id.btn_ok})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_code:
                httpGetCode();
                break;
            case R.id.tv_check:
                boolean isRead = GetTextForViewUtil.getTagBoolean(tvCheck);
                tvCheck.setTag(!isRead);
                if (isRead) {
                    Drawable drawable = context.getResources().getDrawable(R.mipmap.icon_login_no);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight()); //设置边界
                    tvCheck.setCompoundDrawables(drawable, null, null, null);//画在右边
                } else {
                    Drawable drawable = context.getResources().getDrawable(R.mipmap.icon_login_yes);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight()); //设置边界
                    tvCheck.setCompoundDrawables(drawable, null, null, null);//画在右边
                }
                break;
            case R.id.tv_protocol:
                String url = HttpJsonUtil.getServerUrl().replace("api/", "") + "about/agreement.html";
                toActivity(WebViewActivity.createIntent(getActivity(), "动漫购协议", url));
                break;
            case R.id.btn_ok:
                httpRegister();
                break;
        }
    }
}
