package com.dongmango.gou2;


import com.baidu.mapapi.SDKInitializer;
import com.dev.superframe.BaseApp;
import com.dongmango.gou2.baidu.BaiduLocationUtil;

import cn.jpush.android.api.JPushInterface;


/**
 * Created by Administrator on 2017/4/14.
 */

public class App extends BaseApp {
    public static App applicationContext;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = this;
        //CrashHandler.getInstance().init(this);
        // 百度地图VIEW使用初始化
        SDKInitializer.initialize(this);
        BaiduLocationUtil baiduLocationUtil = new BaiduLocationUtil(getApplicationContext());
        baiduLocationUtil.LocationStart();

        JPushInterface.setDebugMode(true);    // 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);            // 初始化 JPush
    }

    public static App getInstance() {
        return applicationContext;
    }
}
