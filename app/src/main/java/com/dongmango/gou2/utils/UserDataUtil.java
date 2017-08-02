package com.dongmango.gou2.utils;

import android.content.Context;
import android.text.TextUtils;

import com.dev.superframe.utils.Json;
import com.dev.superframe.utils.PreferenceUtil;
import com.dongmango.gou2.bean.UserBean;
import com.dongmango.gou2.jpush.JPushUtil;


/**
 * Created by Administrator on 2017/4/11.
 */

public class UserDataUtil {
    public static final String KEY_USER_JSON = "userJson";
    public static final String KEY_USER_TOKEN = "token";
    public static final String KEY_USER_ID = "useId";

    public static UserBean getUserData(Context context) {
        String userJson = PreferenceUtil.getPrefString(context, KEY_USER_JSON, "");
        if (!TextUtils.isEmpty(userJson)) {
            return Json.parseObject(userJson, UserBean.class);
        } else {
            return null;
        }
    }

    public static void clearUserData(Context context) {
        PreferenceUtil.setPrefString(context, KEY_USER_JSON, "");
        PreferenceUtil.setPrefString(context, KEY_USER_TOKEN, "");
        PreferenceUtil.setPrefString(context, KEY_USER_ID, "");

        JPushUtil.setAlias(context, "NULL");
    }
}
