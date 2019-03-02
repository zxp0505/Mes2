package workstation.zjyk.com.scanapp.util;

import android.text.TextUtils;

import cn.com.ethank.mylibrary.resourcelibrary.common_util.ConfigDefine;
import cn.com.ethank.mylibrary.resourcelibrary.common_util.SharePreferenceKeyUtil;
import cn.com.ethank.mylibrary.resourcelibrary.shareutils.SharedPreferencesUitl;

/**
 * Created by zjgz on 2017/11/2.
 */

public class ScanURLBuilder {
    private static boolean isOnline = !ConfigDefine.isTestHost;
    private static String domain_defaul = "agent.elcomes.com:80";
    private static String domain = "";

    public static String getDomain() {
        if (TextUtils.isEmpty(domain)) {
            if (!TextUtils.isEmpty(SharedPreferencesUitl.getStringData(SharePreferenceKeyUtil.DOMAIN))) {
                domain = SharedPreferencesUitl.getStringData(SharePreferenceKeyUtil.DOMAIN);
            } else {
                domain = domain_defaul;
                SharedPreferencesUitl.saveStringData(SharePreferenceKeyUtil.DOMAIN, domain_defaul);
            }
        }
        return domain;
    }

    public static void setDomain(String domainStr) {
        domain = domainStr;
        SharedPreferencesUitl.saveStringData(SharePreferenceKeyUtil.DOMAIN, domain);
    }

    public static String getHostUrl() {
        if (isOnline) {
            return "";
        } else {
            return "http://" + getDomain() + "/agent/";
//            return "http://www.wanandroid.com/tools/mockapi/1867/";
//            return "http://192.168.4.77:8080/agent/";
//            return "http://192.168.4.3:8080/agent/";
        }
    }
//    public static String getHostUrl() {
//        if (isOnline) {
//            return "";
//        } else {
//            return "http://agent.elcomes.com:80/agent/";
////            return "http://192.168.104.251:8080/agent/";
////            return "http://www.wanandroid.com/tools/mockapi/1867/";
//        }
//    }

    public static final String GET_TRAY_INFO = "api/query/tray/info.data";//查新托盘信息
    public static final String UPLOAD_IMAGES = "query/save/qualityRecord/and/accessory/data.data";//上传文件
    public static final String REQUEST_REFUSE = "query/quality/refuse/data.data";//拒绝
    public static final String REQUEST_LOGIN = "manager/quality!qualityLogin.action";//登录
    public static final String REQUEST_HISTORY_LIST = "manager/quality!getHandledRecord.action";//历史记录
    public static final String REQUEST_HISTORY_DETAIL = "manager/quality!getHandledRecordDetail.action";//历史记录详情


}
