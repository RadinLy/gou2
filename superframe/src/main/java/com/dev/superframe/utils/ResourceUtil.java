/*
 * Copyright  2017 [AllenCoderr]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dev.superframe.utils;

import android.content.Context;
import android.util.Log;

import java.lang.reflect.Field;

/**
 * 获取资源工具类
 *
 * @author AllenCoder
 * @version 1.0
 */
public class ResourceUtil {
    private static Context mContext;

    public ResourceUtil(Context mContext) {
        super();
        ResourceUtil.mContext = mContext.getApplicationContext();
    }

    private static final String TAG = ResourceUtil.class.getName();

    private static Class<?> CDrawable = null;

    private static Class<?> CLayout = null;

    private static Class<?> CId = null;

    private static Class<?> CAnim = null;

    private static Class<?> CStyle = null;

    private static Class<?> CString = null;

    private static Class<?> CArray = null;

    static {
        try {
            CDrawable = Class.forName(mContext.getPackageName() + ".R$drawable");
            CLayout = Class.forName(mContext.getPackageName() + ".R$layout");
            CId = Class.forName(mContext.getPackageName() + ".R$id");
            CAnim = Class.forName(mContext.getPackageName() + ".R$anim");
            CStyle = Class.forName(mContext.getPackageName() + ".R$style");
            CString = Class.forName(mContext.getPackageName() + ".R$string");
            CArray = Class.forName(mContext.getPackageName() + ".R$array");

        } catch (ClassNotFoundException e) {
            Log.i(TAG, e.getMessage());
        }
    }

    public static int getDrawableId(String resName) {
        return getResId(CDrawable, resName);
    }

    public static int getLayoutId(String resName) {
        return getResId(CLayout, resName);
    }

    public static int getIdId(String resName) {
        return getResId(CId, resName);
    }

    public static int getAnimId(String resName) {
        return getResId(CAnim, resName);
    }

    public static int getStyleId(String resName) {
        return getResId(CStyle, resName);
    }

    public static int getStringId(String resName) {
        return getResId(CString, resName);
    }

    public static int getArrayId(String resName) {
        return getResId(CArray, resName);
    }

    private static int getResId(Class<?> resClass, String resName) {
        if (resClass == null) {
            Log.i(TAG, "getRes(null," + resName + ")");
            throw new IllegalArgumentException(
                    "ResClass is not initialized. Please make sure you have added neccessary resources. Also make sure you have "
                            + mContext.getPackageName()
                            + ".R$* configured in obfuscation. field="
                            + resName);
        }

        try {
            Field field = resClass.getField(resName);
            return field.getInt(resName);
        } catch (Exception e) {
            Log.i(TAG, "getRes(" + resClass.getName() + ", " + resName + ")");
            Log.i(TAG,
                    "Error getting resource. Make sure you have copied all resources (res/) from SDK to your project. ");
            Log.i(TAG, e.getMessage());
        }

        return -1;
    }
}
