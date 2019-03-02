package workstation.zjyk.line.util;

import android.text.TextUtils;

import cn.com.ethank.mylibrary.resourcelibrary.shareutils.SharedPreferencesUitl;
import cn.com.ethank.mylibrary.resourcelibrary.common_util.ConfigDefine;
import cn.com.ethank.mylibrary.resourcelibrary.common_util.SharePreferenceKeyUtil;
import cn.com.ethank.mylibrary.resourcelibrary.utils.GetIPAddressUtil;
import workstation.zjyk.line.BuildConfig;
import workstation.zjyk.line.ui.BaseApplication;

/**
 * Created by ping on 2017/3/2.
 */

public class Constants {

    public static final boolean isReciver = BuildConfig.is_reciver;//区分收料与分料
    private static boolean onLine = !ConfigDefine.isTestHost;//控制正式环境还是测试环境
    public static boolean isLogin = false;
    public static boolean isScan = false;//为了区分扫码枪事件
    public static final String APK_NAME = "workstation_v";
    //从startactivityforresult  intent传递key
    public static final String RESULT_KEY = "result";
    public static final String RESULT_DATA = "resultdata";
    public static final int RECIVER_DIALOG = 0; //收料dialog
    public static final int FEED_DIALOG = 1; //分料dialog
    public static final int START_FEED = 2; //开始分料(物料清单)
    public static final int WAREHOUSE = 3; //入仓(物料清单)
    public static final int RECIVER_FEED = 4; //收料并分料
    public static final int RECIVER_MAKE_SURE = 5; //确认收料
    public static final int UNUSAL = 6; //异常(物料清单)

    public static final int DELIVERY_DIALOG = 7; //投递dialog
    public static final int LOGIN_ACTIVITY = 8; //首页跳转登陆activity
    public static final int DELIVERY_EXCEPTION_DIALOG = 9; //投递异常dialog
    public static final int RECIVER_AND_FEED_FRAGMENT = 0;//收发料fragment
    public static final int FEED_THREE_FRAGMENT = 1;//绑定托盘fragment

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

    private static String clientIP = "";

    public static String getClientIp() {
        if (TextUtils.isEmpty(clientIP)) {
            clientIP = GetIPAddressUtil.getWifiIP(BaseApplication.getInstance());
        }
        return clientIP;
    }

}
