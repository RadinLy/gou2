package com.dongmango.gou2.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        api = WXAPIFactory.createWXAPI(this, WXConfig.APP_ID, false);
        api.registerApp(WXConfig.APP_ID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        setIntent(intent);
        api.handleIntent(intent, this);
    }

    // 微信发送请求到第三方应用时，会回调到该方法
    @Override
    public void onReq(BaseReq req) {
        Toast.makeText(this, "openid = " + req.openId, Toast.LENGTH_SHORT).show();
        switch (req.getType()) {
            case ConstantsAPI.COMMAND_GETMESSAGE_FROM_WX:
                break;
            case ConstantsAPI.COMMAND_SHOWMESSAGE_FROM_WX:
                //goToShowMsg((ShowMessageFromWX.Req) req);
                break;
            case ConstantsAPI.COMMAND_LAUNCH_BY_WX:
                break;
            default:
                break;
        }
    }

    // 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法
    @Override
    public void onResp(BaseResp resp) {
        switch (resp.getType()) {
            case ConstantsAPI.COMMAND_SENDAUTH:
                //登录回调,处理登录成功的逻辑
                switch (resp.errCode) {
                    case BaseResp.ErrCode.ERR_OK:
                        //Toast.makeText(WXEntryActivity.this, "微信登录成功", Toast.LENGTH_LONG).show();
                        final String state = ((SendAuth.Resp) resp).state;
                        if ("userinfo".equals(state)) {
                            SendAuth.Resp sr = (SendAuth.Resp) resp;
                            final String code = sr.code;
                            Log.e("微信code", code + "");
                            new Thread() {// 开启工作线程进行网络请求
                                public void run() {
                                    String result = GetWechatJson.getWXOpenID(code);
                                    WXCodeBean wxCodeBean = JSON.parseObject(result, WXCodeBean.class);
                                    String resultUserInfo = GetWechatJson.getWXUserinfo(wxCodeBean.getOpenid(), wxCodeBean.getAccess_token());
                                    Message msg = new Message();
                                    msg.obj = resultUserInfo;
                                    msg.what = 0;
                                    handlerUserInfo.sendMessage(msg);
                                }
                            }.start();
                        } else {
                            SendAuth.Resp sr = (SendAuth.Resp) resp;
                            final String code = sr.code;
                            new Thread() {// 开启工作线程进行网络请求
                                public void run() {
                                    String result = GetWechatJson.getWXOpenID(code);
                                    WXCodeBean wxCodeBean = JSON.parseObject(result, WXCodeBean.class);
                                    Message msg = new Message();
                                    msg.obj = wxCodeBean.getOpenid();
                                    msg.what = 1;
                                    handlerUserInfo.sendMessage(msg);
                                }
                            }.start();
                        }
                        break;
                    case BaseResp.ErrCode.ERR_USER_CANCEL:
                        Toast.makeText(WXEntryActivity.this, "微信授权取消", Toast.LENGTH_LONG).show();
                        finish();
                        break;
                    case BaseResp.ErrCode.ERR_AUTH_DENIED:
                        Toast.makeText(WXEntryActivity.this, "微信授权发送被拒绝", Toast.LENGTH_LONG).show();
                        finish();
                        break;
                    default:
                        Toast.makeText(WXEntryActivity.this, "微信授权发送返回", Toast.LENGTH_LONG).show();
                        finish();
                        break;
                }

                break;
            case ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX:
                //分享回调,处理分享成功后的逻辑
                switch (resp.errCode) {
                    case BaseResp.ErrCode.ERR_OK:
                        Toast.makeText(WXEntryActivity.this, "微信分享成功", Toast.LENGTH_LONG).show();
                        finish();
                        break;
                    case BaseResp.ErrCode.ERR_USER_CANCEL:
                        Toast.makeText(WXEntryActivity.this, "微信分享取消", Toast.LENGTH_LONG).show();
                        finish();
                        break;
                    default:
                        Toast.makeText(WXEntryActivity.this, "微信分享失败", Toast.LENGTH_LONG).show();
                        finish();
                        break;
                }
                break;
            default:
                break;
        }
    }

    public WXEntryActivity getActivity() {
        return this;
    }

    private Handler handlerUserInfo = new Handler() {

        @Override
        public void handleMessage(final Message msg) {
            // TODO Auto-generated method stub
            final Message message = msg;
            if (message.what == 0) {
//            LogCatUtils.e("UserInfo", message.obj + "");
                final WXUserInfoBean wxUserInfoBean = JSON.parseObject(message.obj + "", WXUserInfoBean.class);

            } else {

            }
        }
    };

}