package workstation.zjyk.com.scanapp.util;

import cn.com.ethank.mylibrary.resourcelibrary.common_util.ConfogProrerties;
import workstation.zjyk.com.scanapp.BuildConfig;

public class ScanConstants {
    public static boolean isLogin = false;
    //    static boolean isCheckTray = ConfogProrerties.getConfigValue("isCheckTray");
    static boolean isCheckTray = BuildConfig.isCheck;
    static boolean isInspect = BuildConfig.isInspect;

    public static boolean isCheckTray() {
        //是否是单独的查询托盘
        return isCheckTray;
    }

    public static boolean isInspect() {
        //是否是巡检
        return isInspect;
    }


}
