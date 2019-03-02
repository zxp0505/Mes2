package com.zjyk.quality.util;

import android.text.TextUtils;

import cn.com.ethank.mylibrary.resourcelibrary.common_util.ConfigDefine;
import cn.com.ethank.mylibrary.resourcelibrary.common_util.SharePreferenceKeyUtil;
import cn.com.ethank.mylibrary.resourcelibrary.shareutils.SharedPreferencesUitl;

/**
 * Created by ping on 2017/3/2.
 */

public class QAConstants {

    private static boolean onLine = !ConfigDefine.isTestHost;//控制正式环境还是测试环境
    public static boolean isLogin = false;
    public static final int LOGIN_ACTIVITY = 8; //首页跳转登陆activity


    public static final String fileDir = "WorkStation";// app sd卡缓存目录
    private static String token = "";

    public static String getToken() {
        if (TextUtils.isEmpty(token)) {
            token = SharedPreferencesUitl.getStringData("token");
        }
        return token;
    }

    public static void releaseToken() {
        token = "";
        SharedPreferencesUitl.saveStringData("token", "");
    }

    /**
     * 获取工位名称  a0001
     *
     * @return
     */
    public static String getStationName() {
        return SharedPreferencesUitl.getStringData(SharePreferenceKeyUtil.STATION_NAME);
    }

    /**
     * 获取工位名称  a0001
     *
     * @return
     */
    public static String getStationId() {
        return SharedPreferencesUitl.getStringData(SharePreferenceKeyUtil.STATION_ID);
    }

}
