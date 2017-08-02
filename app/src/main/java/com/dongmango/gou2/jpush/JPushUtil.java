package com.dongmango.gou2.jpush;

import android.content.Context;

import com.dev.superframe.utils.PreferenceUtil;
import com.socks.library.KLog;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;


/**
 * Created by Administrator on 2017/4/11.
 */

public class JPushUtil {
    public static final String KeySetAlias = "Set_Alias";
    public static final String KeyRemoveAlias = "Remove_Alias";
    public static final String KeySetAliasAndTags = "Set_Alias_And_Tags";
    public static final String KeyRemoveAliasAndTags = "Remove_Alias_And_Tags";

    /**
     * 设置别名
     */
    public static void setAlias(final Context context, String alias) {
        JPushInterface.setAlias(context, alias, new TagAliasCallback() {
            @Override
            public void gotResult(int i, String s, Set<String> set) {
                switch (i) {
                    case 0:
                        PreferenceUtil.setPrefBoolean(context, KeySetAlias, true);
                        String logs = "Set tag and alias success";
                        KLog.i("JPushUtil_SA", logs);
                        // 建议这里往 SharePreference 里写一个成功设置的状态。成功设置一次后，以后不必再次设置了。
                        break;
                    case 6002:
                        PreferenceUtil.setPrefBoolean(context, KeySetAlias, false);
                        logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                        KLog.i("JPushUtil_SA", logs);
                        // 延迟 60 秒来调用 Handler 设置别名
                        break;
                    default:
                        PreferenceUtil.setPrefBoolean(context, KeySetAlias, false);
                        logs = "Failed with errorCode = " + i;
                        KLog.e("JPushUtil_SA", logs);
                }
            }
        });
    }

    /**
     * 移除别名
     */
    public static void removeAlias(final Context context) {
        JPushInterface.setAlias(context, "NULL", new TagAliasCallback() {
            @Override
            public void gotResult(int i, String s, Set<String> set) {
                switch (i) {
                    case 0:
                        PreferenceUtil.setPrefBoolean(context, KeyRemoveAlias, true);
                        String logs = "Set tag and alias success";
                        KLog.i("JPushUtil_RA", logs);
                        // 建议这里往 SharePreference 里写一个成功设置的状态。成功设置一次后，以后不必再次设置了。
                        break;
                    case 6002:
                        PreferenceUtil.setPrefBoolean(context, KeyRemoveAlias, false);
                        logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                        KLog.i("JPushUtil_RA", logs);
                        // 延迟 60 秒来调用 Handler 设置别名
                        break;
                    default:
                        PreferenceUtil.setPrefBoolean(context, KeyRemoveAlias, false);
                        logs = "Failed with errorCode = " + i;
                        KLog.e("JPushUtil_RA", logs);
                }
            }
        });
    }

    /**
     * 设置别名与标签
     */
    public static void setAliasAndTags(final Context context, String alias, Map<String, String> map) {
        Set<String> tags = new HashSet<String>();
        if (map != null) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                //tags.add(entry.getKey() + ":" + entry.getValue());
                tags.add(entry.getValue());
            }
        }
        JPushInterface.setAliasAndTags(context, alias, tags, new TagAliasCallback() {
            @Override
            public void gotResult(int i, String s, Set<String> set) {
                switch (i) {
                    case 0:
                        PreferenceUtil.setPrefBoolean(context, KeySetAliasAndTags, true);
                        String logs = "Set tag and alias success";
                        KLog.i("JPushUtil_SAT", logs);
                        // 建议这里往 SharePreference 里写一个成功设置的状态。成功设置一次后，以后不必再次设置了。
                        break;
                    case 6002:
                        PreferenceUtil.setPrefBoolean(context, KeySetAliasAndTags, false);
                        logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                        KLog.i("JPushUtil_SAT", logs);
                        // 延迟 60 秒来调用 Handler 设置别名
                        break;
                    default:
                        PreferenceUtil.setPrefBoolean(context, KeySetAliasAndTags, false);
                        logs = "Failed with errorCode = " + i;
                        KLog.e("JPushUtil_SAT", logs);
                }
            }
        });
    }

    /**
     * 移除别名与标签
     */
    public static void removeAliasAndTags(final Context context) {
        Set<String> tags = new HashSet<String>();
        tags.add("NULL");
        JPushInterface.setAliasAndTags(context, "NULL", tags, new TagAliasCallback() {
            @Override
            public void gotResult(int i, String s, Set<String> set) {
                switch (i) {
                    case 0:
                        PreferenceUtil.setPrefBoolean(context, KeyRemoveAliasAndTags, true);
                        String logs = "Set tag and alias success";
                        KLog.i("JPushUtil_RAT", logs);
                        // 建议这里往 SharePreference 里写一个成功设置的状态。成功设置一次后，以后不必再次设置了。
                        break;
                    case 6002:
                        PreferenceUtil.setPrefBoolean(context, KeyRemoveAliasAndTags, false);
                        logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                        KLog.i("JPushUtil_RAT", logs);
                        // 延迟 60 秒来调用 Handler 设置别名
                        break;
                    default:
                        PreferenceUtil.setPrefBoolean(context, KeyRemoveAliasAndTags, false);
                        logs = "Failed with errorCode = " + i;
                        KLog.e("JPushUtil_RAT", logs);
                }
            }
        });
    }
}
