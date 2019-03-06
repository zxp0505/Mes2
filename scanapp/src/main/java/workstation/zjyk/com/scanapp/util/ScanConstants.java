package workstation.zjyk.com.scanapp.util;

import android.text.TextUtils;

import cn.com.ethank.mylibrary.resourcelibrary.common_util.ConfogProrerties;
import cn.com.ethank.mylibrary.resourcelibrary.utils.GetIPAddressUtil;
import workstation.zjyk.com.scanapp.BuildConfig;
import workstation.zjyk.com.scanapp.ui.application.ScanBaseApplication;

public class ScanConstants {
    public static boolean isLogin = false;
    //    static boolean isCheckTray = ConfogProrerties.getConfigValue("isCheckTray");
    static boolean isCheckTray = BuildConfig.isCheck;
    static boolean isInspect = BuildConfig.isInspect;
    static boolean isWarn = BuildConfig.isWarn;

    public static boolean isCheckTray() {
        //是否是单独的查询托盘
        return isCheckTray;
    }

    public static boolean isInspect() {
        //是否是巡检
        return isInspect;
    }

    public static boolean isWarn() {
        //是否是报警
        return isWarn;
    }

    static String clientIP;

    public static String getClientIp() {

        if (TextUtils.isEmpty(clientIP)) {
            clientIP = GetIPAddressUtil.getWifiIP(ScanBaseApplication.getInstance());
        }
        if (TextUtils.isEmpty(clientIP)) {
            clientIP = GetIPAddressUtil.getLocalIp();
        }
        if (TextUtils.isEmpty(clientIP)) {
            clientIP = "";
        }
        return clientIP;
    }

}
