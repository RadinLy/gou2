package com.dongmango.gou2.httpmanager;

import android.content.Context;
import android.text.TextUtils;

import com.dev.selectavatar.utils.compress.CompressHelper;
import com.dev.superframe.BuildConfig;
import com.dev.superframe.config.AppConstantValue;
import com.dev.superframe.http.HttpManager;
import com.dev.superframe.http.bean.Parameter;
import com.dev.superframe.utils.MD5Util;
import com.dev.superframe.utils.PreferenceUtil;
import com.dev.superframe.utils.StringUtil;
import com.dongmango.gou2.config.AppConfig;
import com.dongmango.gou2.utils.UserDataUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * HTTP请求工具类
 *
 * @author Lemon
 * @use 添加请求方法xxxMethod >> HttpJsonUtil.xxxMethod(...)
 * @must 所有请求的url、请求方法(GET, POST等)、请求参数(key-value方式，必要key一定要加，没提供的key不要加，value要符合指定范围)
 * 都要符合后端给的接口文档
 */
public class HttpJsonUtil {
    private static final String TAG = "HttpJsonUtil";
    /**
     * 基础URL，这里服务器设置可切换
     */
    private static final String URL_BASE = BuildConfig.LOG_DEBUG ? AppConstantValue.SERVER_TEST_URL : AppConstantValue.SERVER_URL;
    private static final String KEY_PAGE_NUM = AppConstantValue.PAGE_SIZE + "";

    public static String getServerUrl() {
        return URL_BASE;
    }

    /**
     * 添加请求参数，value为空时不添加
     *
     * @param list
     * @param key
     * @param value
     */
    private static void addExistParameter(List<Parameter> list, String key, Object value) {
        if (list == null) {
            list = new ArrayList<>();
        }
        if (StringUtil.isNotEmpty(key, true) && StringUtil.isNotEmpty(value, true)) {
            list.add(new Parameter(key, value));
        }
    }

    /**
     * 添加请求参数，token为空时不添加
     *
     * @param list
     */
    private static void addTokenParameter(Context context, List<Parameter> list) {
//        String token = PreferenceUtil.getPrefString(context, UserDataUtil.KEY_USER_TOKEN, "");
//        if (!TextUtils.isEmpty(token)) {
//            //根据服务器需求字段修改KEY::token
//            list.add(new Parameter("token", token));
//        }
        String userId = PreferenceUtil.getPrefString(context, UserDataUtil.KEY_USER_ID, "");
        if (!TextUtils.isEmpty(userId)) {
            //根据服务器需求字段修改KEY::userId
            list.add(new Parameter("uid", userId));
        }
    }

    //SMS<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    /**
     * 短信验证码
     *
     * @param mobile
     * @param requestCode
     * @param listener
     */
    public static void getToSMS(final String mobile, final int requestCode, final HttpManager.OnHttpResponseListener listener) {
        List<Parameter> paramList = new ArrayList<Parameter>();

        addExistParameter(paramList, "mobile", mobile);
        addExistParameter(paramList, "action", "pwdcaptcha");
        addExistParameter(paramList, "token", MD5Util.MD5(AppConfig.TOKEN + "captcha"));

        HttpManager.getInstance().get(paramList, URL_BASE + "users/register", requestCode, listener);
    }
    //SMS<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //Upload<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    /**
     * 上传头像
     *
     * @param img
     * @param requestCode
     * @param listener
     */
    public static void getLogo(final Context context, final String img, final int requestCode, final HttpManager.OnHttpResponseListener listener) {
        List<Parameter> paramList = new ArrayList<Parameter>();
        addTokenParameter(context, paramList);
        File file = CompressHelper.getDefault(context).compressToFile(new File(img));
        addExistParameter(paramList, "image", file);
        //http://ywd.zai0312.com/index.php/api/Member/content_face?member_id=1&token=50807819
        HttpManager.getInstance().upload(paramList, URL_BASE + "users/myavatar", requestCode, listener);
    }
    // Upload<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    //获取Html<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    /**
     * 获取html
     *
     * @param context
     * @param url
     * @param requestCode
     * @param listener
     */
    public static void getHtml(final Context context, final String url, final int requestCode, final HttpManager.OnHttpResponseListener listener) {
        List<Parameter> paramList = new ArrayList<Parameter>();
        HttpManager.getInstance().get(paramList, url, requestCode, listener);
    }
    //获取Html<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    //account<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    /**
     * 上传设备号
     *
     * @param requestCode
     * @param listener
     */
    public static void getDeviceNumber(final Context context, final String device_number, final int requestCode, final HttpManager.OnHttpResponseListener listener) {
        List<Parameter> paramList = new ArrayList<Parameter>();
        addTokenParameter(context, paramList);
        addExistParameter(paramList, "device_number", device_number);
        HttpManager.getInstance().get(paramList, URL_BASE + "/api/login/device_number", requestCode, listener);
    }

    /**
     * 注册
     *
     * @param listener
     */
    public static void register(final String nickname, final String mobile, final String password, final String code, final String sign,
                                final int requestCode, final HttpManager.OnHttpResponseListener listener) {
        List<Parameter> paramList = new ArrayList<Parameter>();
        addExistParameter(paramList, "nickname", nickname);
        addExistParameter(paramList, "mobile", mobile);
        addExistParameter(paramList, "password", password);
        addExistParameter(paramList, "captcha", code);
        addExistParameter(paramList, "action", "submit");
        addExistParameter(paramList, "token", MD5Util.MD5(AppConfig.TOKEN + "submit"));
        addExistParameter(paramList, "sign", sign);

        HttpManager.getInstance().post(paramList, URL_BASE + "users/register", requestCode, listener);
    }

    /**
     * 登陆
     *
     * @param mobile
     * @param password
     * @param listener
     */
    public static void login(final String mobile, final String password, final String sign,
                             final int requestCode, final HttpManager.OnHttpResponseListener listener) {
        List<Parameter> paramList = new ArrayList<Parameter>();
        addExistParameter(paramList, "mobile", mobile);
        addExistParameter(paramList, "password", password);
        addExistParameter(paramList, "sign", sign);
        addExistParameter(paramList, "action", "login");
        addExistParameter(paramList, "token", MD5Util.MD5(AppConfig.TOKEN + "login"));

        HttpManager.getInstance().post(paramList, URL_BASE + "users/register", requestCode, listener);
    }

    /**
     * 找回
     *
     * @param mobile
     * @param newpassword
     * @param listener
     */
    public static void getFindPwd(final String mobile, final String newpassword, final String captcha,
                                  final int requestCode, final HttpManager.OnHttpResponseListener listener) {
        List<Parameter> paramList = new ArrayList<Parameter>();
        addExistParameter(paramList, "mobile", mobile);
        addExistParameter(paramList, "newpassword", newpassword);
        addExistParameter(paramList, "captcha", captcha);
        addExistParameter(paramList, "action", "getPassword");
        addExistParameter(paramList, "token", MD5Util.MD5(AppConfig.TOKEN + "getPassword"));

        HttpManager.getInstance().post(paramList, URL_BASE + "users/register", requestCode, listener);
    }

    /**
     * 城市列表
     *
     * @param listener
     */
    public static void getCityInfoList(final int requestCode, final HttpManager.OnHttpResponseListener listener) {
        List<Parameter> paramList = new ArrayList<>();
        addExistParameter(paramList, "token", MD5Util.MD5(AppConfig.TOKEN));
        HttpManager.getInstance().get(paramList, URL_BASE + "index/city", requestCode, listener);
    }

    /**
     * 分类
     *
     * @param listener
     */
    public static void getShopClass(final String cid, final int requestCode, final HttpManager.OnHttpResponseListener listener) {
        List<Parameter> paramList = new ArrayList<>();
        addExistParameter(paramList, "cid", cid);
        HttpManager.getInstance().get(paramList, URL_BASE + "book/cate", requestCode, listener);
    }

    /**
     * 免费阅读
     *
     * @param listener
     */
    public static void getFreeList(final int requestCode, final HttpManager.OnHttpResponseListener listener) {
        List<Parameter> paramList = new ArrayList<>();
        HttpManager.getInstance().get(paramList, URL_BASE + "book/free", requestCode, listener);
    }

    /**
     * 免费阅读详情
     *
     * @param listener
     */
    public static void getFreeDetail(final String id, final String page, final int requestCode, final HttpManager.OnHttpResponseListener listener) {
        List<Parameter> paramList = new ArrayList<>();
        addExistParameter(paramList, "id", id);
        addExistParameter(paramList, "page", page);
        HttpManager.getInstance().get(paramList, URL_BASE + "book/freedetail", requestCode, listener);
    }

    /**
     * 免费阅读分类
     *
     * @param listener
     */
    public static void getFreeBookList(final String classid, final String page, final int requestCode, final HttpManager.OnHttpResponseListener listener) {
        List<Parameter> paramList = new ArrayList<>();
        addExistParameter(paramList, "classid", classid);
        addExistParameter(paramList, "page", page);
        HttpManager.getInstance().get(paramList, URL_BASE + "book/freelist", requestCode, listener);
    }

    /**
     * 首页推荐栏列表
     *
     * @param listener
     */
    public static void getRecomData(final String cid, final String action, final int requestCode, final HttpManager.OnHttpResponseListener listener) {
        List<Parameter> paramList = new ArrayList<>();
        addExistParameter(paramList, "cid", cid);
        //banner轮播图  children新品和精品
        addExistParameter(paramList, "action", action);
        HttpManager.getInstance().get(paramList, URL_BASE + "index/recommmend", requestCode, listener);
    }

    /**
     * 周刊详情
     *
     * @param listener
     */
    public static void getWeeklyDetail(Context context, final String id, final String magazine_id, final boolean isHome, final int requestCode, final HttpManager.OnHttpResponseListener listener) {
        List<Parameter> paramList = new ArrayList<>();
        addTokenParameter(context, paramList);
        addExistParameter(paramList, "id", id);
        addExistParameter(paramList, "magazine_id", magazine_id);
        if (isHome) {
            addExistParameter(paramList, "from", "home");
        }
        addExistParameter(paramList, "token", MD5Util.MD5(AppConfig.TOKEN + "detail"));
        HttpManager.getInstance().get(paramList, URL_BASE + "index/magazinedetail", requestCode, listener);
    }

    /**
     * 众筹列表
     *
     * @param listener
     */
    public static void getCrowdfuningList(final String cid, final String page, final int requestCode, final HttpManager.OnHttpResponseListener listener) {
        List<Parameter> paramList = new ArrayList<>();
        addExistParameter(paramList, "cid", cid);
        addExistParameter(paramList, "page", page);
        HttpManager.getInstance().get(paramList, URL_BASE + "index/crowdfund", requestCode, listener);
    }

    /**
     * 众筹详情
     *
     * @param listener
     */
    public static void getCrowdfuningDetail(final String id, final int requestCode, final HttpManager.OnHttpResponseListener listener) {
        List<Parameter> paramList = new ArrayList<>();
        addExistParameter(paramList, "id", id);
        HttpManager.getInstance().get(paramList, URL_BASE + "index/crowdfunddetail", requestCode, listener);
    }
}