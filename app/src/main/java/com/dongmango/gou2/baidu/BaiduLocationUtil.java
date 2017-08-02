package com.dongmango.gou2.baidu;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.dev.superframe.utils.PreferenceUtil;
import com.socks.library.KLog;


public class BaiduLocationUtil {
    public final static String LOCATION_LNG = "LOCATION_LNG";
    public final static String LOCATION_LAT = "LOCATION_LAT";
    public final static String LL_CITY = "LL_CITY";

    private OnBDLocationListener listener;

    public interface OnBDLocationListener {
        void onBDLocationListener(String lat, String lng, String city);
    }

    private Context mContext;
    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyLocationListener();

    private DevLocation devLocation = new DevLocation();

    public BaiduLocationUtil(Context context) {
        this.mContext = context;

        mLocationClient = new LocationClient(context); // 声明LocationClient类
        mLocationClient.registerLocationListener(myListener); // 注册监听函数

        initLocalLocation(context);
        initLocation();
    }

    public void setBDLocationListener(OnBDLocationListener listener) {
        this.listener = listener;
    }

    /**
     * 服务启动
     */
    public void LocationStart() {
        mLocationClient.start();
    }

    private void initLocalLocation(Context context) {
        String lng = PreferenceUtil.getPrefString(context, LOCATION_LNG, "");
        String lat = PreferenceUtil.getPrefString(context, LOCATION_LAT, "");
        if (!TextUtils.isEmpty(lng) && !TextUtils.isEmpty(lat) && !"4.9E-324".equals(lng) && !"4.9E-324".equals(lat)) {
            double lngD = Double.parseDouble(lng);
            double latD = Double.parseDouble(lat);
            devLocation.setLongitude(lngD);
            devLocation.setLatitude(latD);
        }
    }

    // 高精度定位模式：这种定位模式下，会同时使用网络定位和GPS定位，优先返回最高精度的定位结果；
    // 低功耗定位模式：这种定位模式下，不会使用GPS，只会使用网络定位（Wi-Fi和基站定位）；
    // 仅用设备定位模式：这种定位模式下，不需要连接网络，只使用GPS进行定位，这种模式下不支持室内环境的定位。

    /**
     * 初始化定位
     */
    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);// 可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("gcj02");// 可选，默认gcj02，设置返回的定位结果坐标系
        int span = 1000;
        option.setScanSpan(span);// 可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);// 可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);// 可选，默认false,设置是否使用gps
        option.setLocationNotify(true);// 可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);// 可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);// 可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);// 可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);// 可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);// 可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }

    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // Receive Location
            if (listener != null) {
                listener.onBDLocationListener(location.getLatitude() + "", location.getLongitude() + "", location.getCity() + "");
            }
            if (location != null) {
                if (devLocation.getLatitude() == location.getLatitude() && devLocation.getLongitude() == location.getLongitude()) {
                    // BmobLog.i("两次获取坐标相同");// 若两次请求获取到的地理位置坐标是相同的，则不再定位
                    mLocationClient.stop();
                    return;
                } else if ("4.9E-324".equals(location.getLatitude() + "") && "4.9E-324".equals(location.getLongitude() + "")) {
                    mLocationClient.stop();
                    KLog.e("百度地图秘钥错误！！！");
                    return;
                }
            }
            saveBDLocation(location);

            // StringBuffer sb = new StringBuffer(256);
            // sb.append("time : ");
            // sb.append(location.getTime());
            // sb.append("\nerror code : ");
            // sb.append(location.getLocType());
            // sb.append("\nlatitude : ");
            // sb.append(location.getLatitude());
            // sb.append("\nlontitude : ");
            // sb.append(location.getLongitude());
            // sb.append("\nradius : ");
            // sb.append(location.getRadius());
            // if (location.getLocType() == BDLocation.TypeGpsLocation) {//
            // GPS定位结果
            // sb.append("\nspeed : ");
            // sb.append(location.getSpeed());// 单位：公里每小时
            // sb.append("\nsatellite : ");
            // sb.append(location.getSatelliteNumber());
            // sb.append("\nheight : ");
            // sb.append(location.getAltitude());// 单位：米
            // sb.append("\ndirection : ");
            // sb.append(location.getDirection());// 单位度
            // sb.append("\naddr : ");
            // sb.append(location.getAddrStr());
            // sb.append("\ndescribe : ");
            // sb.append("gps定位成功");
            //
            // } else if (location.getLocType() ==
            // BDLocation.TypeNetWorkLocation) {// 网络定位结果
            // sb.append("\naddr : ");
            // sb.append(location.getAddrStr());
            // // 运营商信息
            // sb.append("\noperationers : ");
            // sb.append(location.getOperators());
            // sb.append("\ndescribe : ");
            // sb.append("网络定位成功");
            // } else if (location.getLocType() ==
            // BDLocation.TypeOffLineLocation) {// 离线定位结果
            // sb.append("\ndescribe : ");
            // sb.append("离线定位成功，离线定位结果也是有效的");
            // } else if (location.getLocType() == BDLocation.TypeServerError) {
            // sb.append("\ndescribe : ");
            // sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
            // } else if (location.getLocType() ==
            // BDLocation.TypeNetWorkException) {
            // sb.append("\ndescribe : ");
            // sb.append("网络不同导致定位失败，请检查网络是否通畅");
            // } else if (location.getLocType() ==
            // BDLocation.TypeCriteriaException) {
            // sb.append("\ndescribe : ");
            // sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
            // }
            // sb.append("\nlocationdescribe : ");
            // sb.append(location.getLocationDescribe());// 位置语义化信息
            // List<Poi> list = location.getPoiList();// POI数据
            // if (list != null) {
            // sb.append("\npoilist size = : ");
            // sb.append(list.size());
            // for (Poi p : list) {
            // sb.append("\npoi= : ");
            // sb.append(p.getId() + " " + p.getName() + " " + p.getRank());
            // }
            // }
            // Log.i("BaiduLocationApiDem", sb.toString());
        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {

        }
    }

    /**
     * 保存百度坐标
     */
    private void saveBDLocation(BDLocation location) {
        double latitude = location.getLatitude();
        double longtitude = location.getLongitude();

        Log.e("获取百度坐标::", "latitude::" + latitude + "##longtitude::"
                + longtitude);
        Log.e("获取百度城市::", location.getCity() + "");

        if (!"4.9E-324".equals(latitude + "") && !"4.9E-324".equals(longtitude + "")) {
            PreferenceUtil.setPrefString(mContext, LOCATION_LNG, longtitude + "");
            PreferenceUtil.setPrefString(mContext, LOCATION_LAT, latitude + "");
            PreferenceUtil.setPrefString(mContext, LL_CITY, location.getCity() + "");
        }
        initLocalLocation(mContext);
    }
    // 61 ： GPS定位结果，GPS定位成功。
    // 62 ： 无法获取有效定位依据，定位失败，请检查运营商网络或者wifi网络是否正常开启，尝试重新请求定位。
    // 63 ： 网络异常，没有成功向服务器发起请求，请确认当前测试手机网络是否通畅，尝试重新请求定位。
    // 65 ： 定位缓存的结果。
    // 66 ： 离线定位结果。通过requestOfflineLocaiton调用时对应的返回结果。
    // 67 ： 离线定位失败。通过requestOfflineLocaiton调用时对应的返回结果。
    // 68 ： 网络连接失败时，查找本地离线定位时对应的返回结果。
    // 161： 网络定位结果，网络定位定位成功。
    // 162： 请求串密文解析失败，一般是由于客户端SO文件加载失败造成，请严格参照开发指南或demo开发，放入对应SO文件。
    // 167： 服务端定位失败，请您检查是否禁用获取位置信息权限，尝试重新请求定位。
    // 502： key参数错误，请按照说明文档重新申请KEY。
    // 505： key不存在或者非法，请按照说明文档重新申请KEY。
    // 601： key服务被开发者自己禁用，请按照说明文档重新申请KEY。
    // 602： key
    // mcode不匹配，您的ak配置过程中安全码设置有问题，请确保：sha1正确，“;”分号是英文状态；且包名是您当前运行应用的包名，请按照说明文档重新申请KEY。
    // 501～700：key验证失败，请按照说明文档重新申请KEY。
}
