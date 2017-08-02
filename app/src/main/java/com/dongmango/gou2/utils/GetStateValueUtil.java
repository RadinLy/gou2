package com.dongmango.gou2.utils;

/**
 * Created by Administrator on 2017/8/1.
 */

public class GetStateValueUtil {
    /**
     * 众筹状态
     * 0未开始 1进行中 2已结束
     */
    public static String getCrowdfundingStateValue(String state) {
        String stateValue = "";
        if ("0".equals(state)) {
            stateValue = "未开始";
        } else if ("1".equals(state)) {
            stateValue = "进行中";
        } else if ("2".equals(state)) {
            stateValue = "已结束";
        }
        return stateValue;
    }
}
