package com.dongmango.gou2.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import com.dev.superframe.base.BaseNoStatusBarActivity;
import com.dev.superframe.utils.DensityUtil;
import com.dev.superframe.utils.PreferenceUtil;
import com.dev.superframe.utils.VersionUtil;
import com.dongmango.gou2.R;
import com.dongmango.gou2.activity.tab.MainTabActivity;


/**
 * Created by zhang on 2016/9/30.
 */

public class SplashActivity extends BaseNoStatusBarActivity {

    private static final int sleepTime = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initView();
        initData();
        initEvent();
    }

    @Override
    protected void onStart() {
        super.onStart();

        new Thread(new Runnable() {
            public void run() {
                if (false) {
                    // auto login mode, make sure all group and conversation is loaed before enter the main screen
                    long start = System.currentTimeMillis();
                    long costTime = System.currentTimeMillis() - start;
                    //wait
                    if (sleepTime - costTime > 0) {
                        try {
                            Thread.sleep(sleepTime - costTime);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    //enter main screen
                    IntentAct();
                    finish();
                } else {
                    try {
                        Thread.sleep(sleepTime);
                    } catch (InterruptedException e) {
                    }
                    IntentAct();
                    finish();
                }
            }
        }).start();

    }

    @NonNull
    @Override
    public BaseNoStatusBarActivity getActivity() {
        return this;
    }

    @Override
    public void initView() {
//        LinearLayout rootLayout = (LinearLayout) findViewById(R.id.ll_splash);
//        AlphaAnimation animation = new AlphaAnimation(0.3f, 1.0f);
//        animation.setDuration(1500);
//        rootLayout.startAnimation(animation);
        /**
         * 获取状态栏高度
         * */
        int statusBarHeight = -1;
        //获取status_bar_height资源的ID
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight = getResources().getDimensionPixelSize(resourceId);
        }
        Log.e("状态栏-高度:", "高度:" + statusBarHeight);
        Log.e("状态栏-高度:", "高度:" + DensityUtil.dip2px(getActivity(), statusBarHeight));
    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {

    }

    private void IntentAct() {
        // 判断是否APP第一次启动
        int versionCode = PreferenceUtil.getPrefInt(this, "versionCode", -1);
        if (versionCode == -1) {
            // 跳转教程页(用户第一次安装)
            PreferenceUtil.setPrefInt(this, "versionCode", VersionUtil.getVersionCode(this));
            startActivity(MainTabActivity.createIntent(SplashActivity.this));
        }
        // 跳转主页(用户已经安装)
        else if (VersionUtil.getVersionCode(this) == versionCode) {
            // 安装APP版本号相同，直接跳转主页面
            startActivity(MainTabActivity.createIntent(SplashActivity.this));
        }
        // 跳转教程页(用户已经安装，但是版本已经升级，也跳转教程页)
        else if (VersionUtil.getVersionCode(this) > versionCode) {
            // 重新存入数据
            PreferenceUtil.setPrefInt(this, "versionCode", VersionUtil.getVersionCode(this));
            startActivity(MainTabActivity.createIntent(SplashActivity.this));
        }
    }

    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.fade, R.anim.hold);
    }
}
