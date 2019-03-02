package workstation.zjyk.workstation.util;

import android.text.TextUtils;

import cn.com.ethank.mylibrary.resourcelibrary.common_util.ConfigDefine;
import cn.com.ethank.mylibrary.resourcelibrary.common_util.SharePreferenceKeyUtil;
import cn.com.ethank.mylibrary.resourcelibrary.shareutils.SharedPreferencesUitl;
import cn.com.ethank.mylibrary.resourcelibrary.utils.GetIPAddressUtil;
import workstation.zjyk.workstation.BuildConfig;
import workstation.zjyk.workstation.ui.application.WSBaseApplication;

/**
 * Created by ping on 2017/3/2.
 */

public class WSConstants {

    private static boolean onLine = !ConfigDefine.isTestHost;//控制正式环境还是测试环境
    public static boolean isInspect = BuildConfig.isInspect;//控制巡检还是工位
    //    public static boolean isLogin = true;
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

    static String clientIP;

    public static String getClientIp() {

        if (TextUtils.isEmpty(clientIP)) {
            clientIP = GetIPAddressUtil.getWifiIP(WSBaseApplication.getInstance());
        }
        if (TextUtils.isEmpty(clientIP)) {
            clientIP = GetIPAddressUtil.getLocalIp();
        }
        if (TextUtils.isEmpty(clientIP)) {
            clientIP = "";
        }
        return clientIP;
    }

    public static void setCurrentUserName(String name) {

    }

}
