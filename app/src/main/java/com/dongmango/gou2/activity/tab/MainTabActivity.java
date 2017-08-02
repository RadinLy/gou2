package com.dongmango.gou2.activity.tab;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dev.superframe.base.BaseNoStatusBarActivity;
import com.dev.superframe.http.HttpManager;
import com.dev.superframe.utils.Json;
import com.dev.superframe.utils.PreferenceUtil;
import com.dev.superframe.view.dialog.AlertStyleDialog;
import com.dongmango.gou2.R;
import com.dongmango.gou2.activity.tab.fragment.ClassFragment;
import com.dongmango.gou2.activity.tab.fragment.FreeReadFragment;
import com.dongmango.gou2.activity.tab.fragment.HomeFragment;
import com.dongmango.gou2.activity.tab.fragment.MyFragment;
import com.dongmango.gou2.activity.tab.fragment.ShopCartFragment;
import com.dongmango.gou2.baidu.BaiduLocationUtil;
import com.dongmango.gou2.bean.CityBean;
import com.dongmango.gou2.config.AppConfig;
import com.dongmango.gou2.config.KeyValueConstants;
import com.dongmango.gou2.httpmanager.GetJsonUtil;
import com.dongmango.gou2.httpmanager.HttpJsonUtil;
import com.dongmango.gou2.jpush.JPushUtil;
import com.dongmango.gou2.utils.UserDataUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.weyye.hipermission.HiPermission;
import me.weyye.hipermission.PermissionCallback;
import me.weyye.hipermission.PermissionItem;


public class MainTabActivity extends BaseNoStatusBarActivity {
    @BindView(R.id.fl_tab_container)
    FrameLayout flTabContainer;
    @BindView(R.id.iv_tab0)
    ImageView ivTab0;
    @BindView(R.id.tv_tab0)
    TextView tvTab0;
    @BindView(R.id.ll_tab0)
    LinearLayout llTab0;
    @BindView(R.id.iv_tab1)
    ImageView ivTab1;
    @BindView(R.id.tv_tab1)
    TextView tvTab1;
    @BindView(R.id.ll_tab1)
    LinearLayout llTab1;
    @BindView(R.id.iv_tab2)
    ImageView ivTab2;
    @BindView(R.id.tv_tab2)
    TextView tvTab2;
    @BindView(R.id.ll_tab2)
    LinearLayout llTab2;
    @BindView(R.id.iv_tab3)
    ImageView ivTab3;
    @BindView(R.id.tv_tab3)
    TextView tvTab3;
    @BindView(R.id.ll_tab3)
    LinearLayout llTab3;
    @BindView(R.id.iv_tab4)
    ImageView ivTab4;
    @BindView(R.id.tv_tab4)
    TextView tvTab4;
    @BindView(R.id.ll_tab4)
    LinearLayout llTab4;

    //启动方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    /**
     * 启动这个Activity的Intent
     *
     * @param context
     * @return
     */
    public static Intent createIntent(Context context) {
        return new Intent(context, MainTabActivity.class);
    }

    public static MainTabActivity mMainTabActivity;

    public static void finishMainZFTActivity() {
        mMainTabActivity.finish();
    }

    //启动方法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    private static final String TAG = "MainZFTActivity";

    @SuppressWarnings("unused")
    private View[] llBottomTabTabs;
    private ImageView[] ivBottomTabTabs;
    private TextView[] tvBottomTabTabs;

    private static final String[] TABS = {"动漫购", "分类", "免费阅读区", "购物车", "我的"};
    private static final int[][] TAB_IMAGE_RES_IDS = {
            {R.mipmap.tab_normal0, R.mipmap.tab_pressed0},
            {R.mipmap.tab_normal1, R.mipmap.tab_pressed1},
            {R.mipmap.tab_normal2, R.mipmap.tab_pressed2},
            {R.mipmap.tab_normal3, R.mipmap.tab_pressed3},
            {R.mipmap.tab_normal4, R.mipmap.tab_pressed4}
    };

    protected int currentPosition = 0;
    private Fragment[] fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tab);
        ButterKnife.bind(this);

        initPermission();
        initView();
        initData();
        initEvent();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("onStart()", ">>>>>");
        mMainTabActivity = this;
        //设置极光别名
        String jpushid = PreferenceUtil.getPrefString(getActivity(), UserDataUtil.KEY_USER_ID, "");
        if (!TextUtils.isEmpty(jpushid)) {
            JPushUtil.setAlias(getActivity(), jpushid);
        } else {
            JPushUtil.setAlias(getActivity(), "NULL");
        }
        toJPushAct(null);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (currentPosition == 0) {
            ((HomeFragment) fragments[0]).updateUI();
        }
        if (currentPosition == 3 || currentPosition == 4) {
            String uid = PreferenceUtil.getPrefString(getActivity(), UserDataUtil.KEY_USER_ID, "");
            if (TextUtils.isEmpty(uid)) {
                selectFragment(0);
                return;
            } else {
                if (currentPosition == 3) {
                    ((ShopCartFragment) fragments[3]).updateUI();
                } else if (currentPosition == 4) {
                    ((MyFragment) fragments[4]).updateUI();
                }
            }
        }
    }

    public void toJPushAct(String json) {
        Log.e("toJPushAct()", ">>>>>1");
        String token = PreferenceUtil.getPrefString(context, "token", "");
        if (TextUtils.isEmpty(token)) {
            return;
        }
        Log.e("toJPushAct()", ">>>>>2");
        String jpushData = "";
        if (json == null) {
            jpushData = PreferenceUtil.getPrefString(getActivity(), "JPUSH_JSON", "");
            PreferenceUtil.setPrefString(getActivity(), "JPUSH_JSON", "");
        } else {
            jpushData = json;
        }
        Log.e("toJPushAct()", "消息:" + json);
//        if (!TextUtils.isEmpty(jpushData)) {
//            Log.e("判断", "消息类型执行跳转");
//            JPushBean jPushBean = Json.parseObject(jpushData, JPushBean.shopclass);
//            if (jPushBean != null) {
//                if ("1".equals(jPushBean.getType())) {
//                    httpData(jPushBean.getMsgid());
//                    toActivity(new Intent(getActivity(), OrderDetailActivity.shopclass).putExtra(INTENT_ID, jPushBean.getOrderid()));
//                } else {
//                    toActivity(new Intent(getActivity(), MsgDetailActivity.shopclass).putExtra(INTENT_ID, jPushBean.getMsgid()));
//                }
//            }
//        }
    }

    private void initPermission() {
        List<PermissionItem> permissionItems = new ArrayList<>();
        permissionItems.add(new PermissionItem(Manifest.permission.ACCESS_FINE_LOCATION, "位置", R.drawable.permission_ic_location));
        permissionItems.add(new PermissionItem(Manifest.permission.READ_EXTERNAL_STORAGE, "读取内存卡", R.drawable.permission_ic_storage));
        permissionItems.add(new PermissionItem(Manifest.permission.WRITE_EXTERNAL_STORAGE, "写入内存卡", R.drawable.permission_ic_storage));
        HiPermission.create(getActivity()).permissions(permissionItems).checkMutiPermission(new PermissionCallback() {
            @Override
            public void onClose() {
                showShortToast("取消了权限授权请求");
            }

            @Override
            public void onFinish() {
                //showShortToast("授权完成的所有权限");
                BaiduLocationUtil baiduLocationUtil = new BaiduLocationUtil(getApplicationContext());
                baiduLocationUtil.setBDLocationListener(new BaiduLocationUtil.OnBDLocationListener() {
                    @Override
                    public void onBDLocationListener(String lat, String lng, String city) {
                        initCity();
                    }
                });
                baiduLocationUtil.LocationStart();
            }

            @Override
            public void onDeny(String permission, int position) {

            }

            @Override
            public void onGuarantee(String permission, int position) {

            }
        });
    }

    @Override
    public void initView() {
        exitAnim = R.anim.bottom_push_out;

        llBottomTabTabs = new View[TABS.length];
        llBottomTabTabs[0] = llTab0;
        llBottomTabTabs[1] = llTab1;
        llBottomTabTabs[2] = llTab2;
        llBottomTabTabs[3] = llTab3;
        llBottomTabTabs[4] = llTab4;

        ivBottomTabTabs = new ImageView[TABS.length];
        ivBottomTabTabs[0] = ivTab0;
        ivBottomTabTabs[1] = ivTab1;
        ivBottomTabTabs[2] = ivTab2;
        ivBottomTabTabs[3] = ivTab3;
        ivBottomTabTabs[4] = ivTab4;

        tvBottomTabTabs = new TextView[TABS.length];
        tvBottomTabTabs[0] = tvTab0;
        tvBottomTabTabs[1] = tvTab1;
        tvBottomTabTabs[2] = tvTab2;
        tvBottomTabTabs[3] = tvTab3;
        tvBottomTabTabs[4] = tvTab4;

        for (int i = 0; i < TABS.length; i++) {
            tvBottomTabTabs[i].setText(TABS[i]);
        }
    }

    public void toFragment(int iFgm) {
        selectFragment(iFgm);
    }

    /**
     * 获取新的Fragment
     *
     * @param position
     * @return
     */
    protected Fragment getFragment(int position) {
        switch (position) {
            case 1:
                return new ClassFragment();
            case 2:
                return new FreeReadFragment();
            case 3:
                return new ShopCartFragment();
            case 4:
                return new MyFragment();
            default:
                HomeFragment fragment = new HomeFragment();
                return fragment;
        }
    }

    /**
     * 选择并显示fragment
     *
     * @param position
     */
    public void selectFragment(int position) {
        if (currentPosition == position) {
            if (fragments[position] != null && fragments[position].isVisible()) {
                Log.e(TAG, "selectFragment currentPosition == position" +
                        " >> fragments[position] != null && fragments[position].isVisible()" +
                        " >> return;	");
                return;
            }
        }

        for (int i = 0; i < llBottomTabTabs.length; i++) {
            ivBottomTabTabs[i].setImageResource(TAB_IMAGE_RES_IDS[i][i == position ? 1 : 0]);
            tvBottomTabTabs[i].setTextColor(getResources().getColor(i == position ? R.color.theme_red : R.color.text_color_deep_middle));
        }

        if (fragments[position] == null) {
            fragments[position] = getFragment(position);
        }

        // 用全局的fragmentTransaction因为already committed 崩溃
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.hide(fragments[currentPosition]);
        if (fragments[position].isAdded() == false) {
            fragmentTransaction.add(R.id.fl_tab_container, fragments[position]);
        }
        fragmentTransaction.show(fragments[position]).commitAllowingStateLoss();

        try {
            if (position == 0) {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        //显示dialog
                        //((HomeFragment) fragments[0]).initTopbar();
                    }
                }, 200);   //2秒
            } else if (position == 1) {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        //显示dialog
                        //((YXHousesFragment) fragments[1]).updataUI();
                    }
                }, 200);   //2秒
            } else if (position == 2) {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        //显示dialog
//                        ((UserFragment) fragments[2]).updateUI();
                    }
                }, 200);   //2秒
            } else if (position == 3) {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        //显示dialog
                        ((ShopCartFragment) fragments[3]).updateUI();
                    }
                }, 200);   //2秒
            } else if (position == 4) {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        //显示dialog
                        ((MyFragment) fragments[4]).updateUI();
                    }
                }, 200);   //2秒
            }
        } catch (Exception e) {
            Log.e(TAG, "点击切换初始化标题BUG:" + e);
        }
        this.currentPosition = position;
    }

    @Override
    public void initData() {
//        String llCity = PreferenceUtil.getPrefString(getActivity(), BaiduLocationUtil.LL_CITY, "");
//        if (!TextUtils.isEmpty(llCity)) {
//            initCity();
//        }
        initShopClass();

        // fragmentActivity子界面初始化<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
        fragments = new Fragment[TABS.length];
        selectFragment(currentPosition);
        // fragmentActivity子界面初始化>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    }

    private void initCity() {
        HttpJsonUtil.getCityInfoList(10001, new HttpManager.OnHttpResponseListener() {
            @Override
            public void onHttpResponse(int requestCode, final String resultJson, Exception e) {
                if (0 == GetJsonUtil.getResponseCode(resultJson)) {
                    String json = GetJsonUtil.getResponseData(resultJson);
                    PreferenceUtil.setPrefString(getActivity(), KeyValueConstants.KEY_CITY_JSON, json);

                    //定位城市
                    final String llCity = PreferenceUtil.getPrefString(getActivity(), BaiduLocationUtil.LL_CITY, "");
                    //当前城市
                    String nowCity = PreferenceUtil.getPrefString(getActivity(), KeyValueConstants.KEY_CITY_NAME, "");

                    //第一次安装未选择城市
                    if (TextUtils.isEmpty(nowCity)) {
                        new AlertStyleDialog(getActivity(), "", "定位当前城市为" + llCity + "，是否切换", "确定", "取消", 0, new AlertStyleDialog.OnDialogButtonClickListener() {
                            @Override
                            public void onDialogButtonClick(int requestCode, boolean isPositive) {
                                if (isPositive) {

                                    String cityId = getCityId(Json.parseArray(GetJsonUtil.getResponseData(resultJson), CityBean.class), llCity);
                                    String cityName = getCityName(Json.parseArray(GetJsonUtil.getResponseData(resultJson), CityBean.class), llCity);
                                    if (TextUtils.isEmpty(cityId) && TextUtils.isEmpty(cityName)) {
                                        showShortToast("此城市尚未开放使用,默认北京市");
                                        cityId = AppConfig.DE_CITY_ID;
                                        cityName = AppConfig.DE_CITY_NAME;
                                    }
                                    PreferenceUtil.setPrefString(getActivity(), KeyValueConstants.KEY_CITY_ID, cityId);
                                    PreferenceUtil.setPrefString(getActivity(), KeyValueConstants.KEY_CITY_NAME, cityName);

                                    new Handler().postDelayed(new Runnable() {
                                        public void run() {
                                            //显示dialog
                                            if (fragments[0] != null) {
                                                ((HomeFragment) fragments[0]).updateUI();
                                            }
                                        }
                                    }, 200);   //2秒
                                } else {

                                }
                            }
                        }).show();
                    }
                    //已经选择城市比对定位城市和当前城市
                    else if (!nowCity.equals(llCity) && !nowCity.contains(llCity) && !llCity.contains(nowCity) && !TextUtils.isEmpty(nowCity)) {
                        new AlertStyleDialog(getActivity(), "", "定位当前城市为" + llCity + "，是否切换", "确定", "取消", 0, new AlertStyleDialog.OnDialogButtonClickListener() {
                            @Override
                            public void onDialogButtonClick(int requestCode, boolean isPositive) {
                                if (isPositive) {

                                    String cityId = getCityId(Json.parseArray(GetJsonUtil.getResponseData(resultJson), CityBean.class), llCity);
                                    String cityName = getCityName(Json.parseArray(GetJsonUtil.getResponseData(resultJson), CityBean.class), llCity);
                                    if (TextUtils.isEmpty(cityId) && TextUtils.isEmpty(cityName)) {
                                        showShortToast("此城市尚未开放使用,默认北京市");
                                        cityId = AppConfig.DE_CITY_ID;
                                        cityName = AppConfig.DE_CITY_NAME;
                                    }
                                    PreferenceUtil.setPrefString(getActivity(), KeyValueConstants.KEY_CITY_ID, cityId);
                                    PreferenceUtil.setPrefString(getActivity(), KeyValueConstants.KEY_CITY_NAME, cityName);

                                    new Handler().postDelayed(new Runnable() {
                                        public void run() {
                                            //显示dialog
                                            if (fragments[0] != null) {
                                                ((HomeFragment) fragments[0]).updateUI();
                                            }
                                        }
                                    }, 200);   //2秒
                                } else {

                                }
                            }
                        }).show();
                    }
                } else {
                    showShortToast(GetJsonUtil.getResponseError(resultJson));
                }
            }
        });
    }

    /**
     * 获取城市的ID
     */
    private String getCityId(List<CityBean> cityList, String cityName) {
        String cityId = "";
        for (CityBean o : cityList) {
            if (cityName.equals(o.getRegion_name()) || cityName.contains(o.getRegion_name()) || o.getRegion_name().contains(cityName)) {
                cityId = o.getRegion_id();
            }
        }
        return cityId;
    }

    /**
     * 获取城市的名称
     */
    private String getCityName(List<CityBean> cityList, String cityName) {
        String cName = "";
        for (CityBean o : cityList) {
            if (cityName.equals(o.getRegion_name()) || cityName.contains(o.getRegion_name()) || o.getRegion_name().contains(cityName)) {
                cName = o.getRegion_name();
            }
        }
        return cName;
    }

    private void initShopClass() {
        HttpJsonUtil.getShopClass("0", 10001, new HttpManager.OnHttpResponseListener() {
            @Override
            public void onHttpResponse(int requestCode, String resultJson, Exception e) {
                if (0 == GetJsonUtil.getResponseCode(resultJson)) {
                    String json = GetJsonUtil.getResponseData(resultJson);
                    PreferenceUtil.setPrefString(getActivity(), KeyValueConstants.KEY_CLASS_JSON, json);
                } else {
                    showShortToast(GetJsonUtil.getResponseError(resultJson));
                }
            }
        });
    }

    @Override
    public void initEvent() {
        for (int i = 0; i < llBottomTabTabs.length; i++) {
            final int which = i;
            llBottomTabTabs[which].setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    selectFragment(which);
                }
            });
        }
    }

    @NonNull
    @Override
    public BaseNoStatusBarActivity getActivity() {
        return this;
    }

    // 系统自带监听方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    private long firstTime = 0;//第一次返回按钮计时

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                long secondTime = System.currentTimeMillis();
                if (secondTime - firstTime > 2000) {
                    showShortToast("再按一次退出");
                    firstTime = secondTime;
                } else {//完全退出
                    moveTaskToBack(false);//应用退到后台
                    System.exit(0);
                }
                return true;
        }

        return super.onKeyUp(keyCode, event);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMainTabActivity = null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMainTabActivity = null;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (currentPosition == 4) {
            fragments[currentPosition].onActivityResult(requestCode, resultCode, data);
        }
    }
}
