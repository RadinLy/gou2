package com.dongmango.gou2.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";

    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 创建一个线性布局
        LinearLayout mLayout = new LinearLayout(this);
        // 接着创建一个TextView
        TextView mTextView = new TextView(this);

        // 第一个参数为宽的设置，第二个参数为高的设置。
        mTextView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        // 设置mTextView的文字
        mTextView.setText("正在处理微信返回结果...");
        // 设置字体大小
        mTextView.setTextSize(20);
        // 设置字体颜色
        mTextView.setTextColor(Color.parseColor("#666666"));
        //设置居中
        mTextView.setGravity(Gravity.CENTER);
        // 将TextView添加到Linearlayout中去
        mLayout.addView(mTextView);
        // 展现这个线性布局
        setContentView(mLayout);

        api = WXAPIFactory.createWXAPI(this, WXConfig.APP_ID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            //0	    成功	展示成功页面
            //-1	错误	可能的原因：签名错误、未注册APPID、项目设置APPID不正确、注册的APPID与设置的不匹配、其他异常等。
            //-2	用户取消	无需处理。发生场景：用户不支付了，点击取消，返回APP。
            if (resp.errCode == 0) {
                //支付成功
                Toast.makeText(this, "支付成功", Toast.LENGTH_SHORT).show();
                finish();
            } else if (resp.errCode == -2) {
                Toast.makeText(this, "取消支付", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                //支付失败
                Toast.makeText(this, "支付失败", Toast.LENGTH_SHORT).show();
                finish();
            }
        } else {
            Log.i("baseResp.getType()", ">>" + resp.getType());
            finish();
        }
    }
}