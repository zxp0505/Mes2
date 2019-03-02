package workstation.zjyk.workstation.util;

import android.text.TextUtils;

import cn.com.ethank.mylibrary.resourcelibrary.common_util.ConfigDefine;
import cn.com.ethank.mylibrary.resourcelibrary.common_util.SharePreferenceKeyUtil;
import cn.com.ethank.mylibrary.resourcelibrary.shareutils.SharedPreferencesUitl;

/**
 * Created by zjgz on 2017/11/2.
 */

public class WSURLBuilder {
    private static boolean isOnline = !ConfigDefine.isTestHost;
//    private static String domain_defaul = "agent.elcomes.com:80";
    private static String domain_defaul = "192.168.60.38";
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

    public static final String TEST = "1";//测试

    //登陆
    public static final String GET_PERSON_SCAN = "api/person/info.data";//扫码登陆
    public static final String REQUEST_INSPECT_LOGIN = "manager/quality!qualityLogin.action";//巡检登录
    public static final String UPLOAD_PHOTO = "api/person/upload.data";//上传头像
    public static final String LOGIN_OUT = "api/person/exit.data";//退出登陆
    public static final String GET_ALL_PERSON = "api/person/online/info.data";//获取所有的人员信息
    public static final String CHECK_APK_VERSION = "api/app/info.data";//检查apk版本

    public static final String GET_STATION_NAME = "api/workStation/info.data";//获取工位
    public static final String GET_STATION_LIST = "api/work/get/normal/workStation/list.data";//获取工位列表
    public static final String UPDATE_STATION = "api/work/change/workStation/by/ip/data.data";//更换工位

    public static final String SEND_HEART = "api/pad/beatTime/data.data";//发送心跳

    //任务列表
    public static final String GET_TASK_DATE_LIST = "api/work/calendar/task/data.data";//获取工位任务日期列表
    public static final String GET_TASK_LIST_COUNT = "api/workStation/task/count/list.data";//获取工位任务列表数量
    public static final String GET_TASK_LIST = "api/workStation/task/list.data";//获取工位任务列表
    public static final String GET_TRAY_INFO = "api/workStation/tray/code/info.data";//根据条码获取托盘承载信息
    public static final String RECIVER_MATERAIL = "api/workStation/receive/material/data.data";//接收物料
    public static final String RECIVER_MAKING = "api/workStation/receive/wip/data.data";//接收在制品
    public static final String RECIVER_MATERAIL_CHECK_TRAY = "api/workStation/change/tray/data.data";//接收物料检查是否绑定托盘
    public static final String RECIVER_MATERAIL_AND_MAKING = "api/workStation/receive/wipAndMaterial/data.data";//接收物料和在制品
    public static final String GET_WS_INFO = "api/workStation/task/main/info.data";//获取工位任务主要信息
    public static final String GET_WS_OTHER_INFO = "api/workStation/task/other/info.data";//获取工位任务其他信息
    public static final String GET_BOM_INFO = "api/workStation/bom/info.data";//获取bom清单
    public static final String COMMIT_BOM_FOLLOWED = "api/workStation/save/concern/materiel/data.data";//提交关注物料列表
    public static final String GET_BOM_FOLLOWED = "api/workStation/get/concern/materiel/list.data";//获取关注物料列表


    public static final String GET_USER_REPORT_DETAIL = "api/work/report/record/by/today/list.data";//点击头像获取报工详情

    public static final String PRINT_BOM_INFO = "api/workStation/print/bom/info/data.data";//打印bom清单
    public static final String GET_WS_MATERAIL_MAKING_LIST = "api/workStation/material/and/wip/list.data";//根据工位获取工位输入物料和在制品列表
    public static final String GET_WS_TRAY_MATERAIL_LIST = "api/workStation/tray/material/list.data";//获取托盘中的物料列表
    public static final String GET_WS_TRAY_MAKING_LIST = "api/workStation/tray/wip/list.data";//获取托盘中的在制品列表
    public static final String CHECK_GUIDE_VERSION = "api/workStation/task/valid/processInstruction/data.data";//判断任务的工艺指导书与服务端是否一致

    public static final String GET_RECIVERED_TRAY = "api/workStation/task/by/workStation/trayCode/info.data";//扫描已接收到的托盘

    public static final String GET_STATION_MATERAIL_DETAIL = "api/workStation/material/list.data";//获取工位物料清单
    public static final String GET_WS_MATERAIL_TRAY_LIST = "api/workStation/material/tray/list.data";//获取物料的托盘列表
    public static final String GET_WS_MAKING_TRAY_LIST = "api/workStation/wip/tray/list.data";//获取在制品的托盘列表
    //在制品输出
    public static final String MAKING_OUT_BIND_TRAY = "api/workStation/bind/tray/data.data";//绑定托盘
    public static final String MAKING_OUT_DELIVERY = "api/workStation/deliver/wip/tray/data.data";//投递在制品托盘
    public static final String GET_DELIVELY_RECORD = "api/workStation/deliver/wip/history/list.data";//获取投递托盘记录
    public static final String GET_DELIVELY_PRINT = "api/work/print/out/info.data";//投递成功打印
    //报工
    public static final String GET_STEP_LIST = "api/workStation/getProcedureStep/list.data";//根据工序获取工步列表（报工用的）
    public static final String GET_STEP_CONFIRM = "api/workStation/daily/work/data.data";//确认报工
    public static final String GET_STEP_LIST_HISTORY = "api/workStation/daily/work/history/list.data";//获取报工历史记录
    //异常
    public static final String GET_EXCEPT_LIST = "api/workStation/exception/list.data";//获取异常列表
    public static final String GET_EXCEPT_HISTORY_LIST = "api/workStation/exception/out/record/list.data";//获取异常历史列表
    public static final String EXCEPT_DELIVERY = "api/workStation/deliver/tray/data.data";//投递异常托盘

    public static final String GET_EXCEPTION_PRINT = "api/work/print/exception/out/info.data";//投递成功打印
    //解绑 清空托盘
    public static final String CLEAR_TRAY = "api/workStation/empty/tray/data.data";//清空托盘
    //工位转移
    public static final String GET_STATION_TRANSFER_LIST = "api/workStation/task/workStation/list.data";//获取工位转移列表
    public static final String STATION_TRANSFER_CONFIRM = "api/workStation/task/change/data.data";//工位转移

    public static final String OPREAT_STATION_STATUS = "api/workStation/task/beginOrEnd/data.data";//工位任务开关
    //返工
    public static final String REVIEWWROK_SCAN_TRAY = "api/workStation/tray/rework/info.data";//返工扫描托盘获取信息
    public static final String REVIEWWORK_GET_STATION_INFO = "api/workStation/before/workStation/by/task/procedure/data.data";//返工钱获取工位信息
    public static final String REVIEWWORK = "api/workStation/confirm/rework/data.data";//确认返工
    public static final String GET_REVIEWWORK_HISTORY = "api/workStation/get/returnWork/history/list.data";//获取返工历史记录
    //维修
    public static final String REPAIR_CONFIRM = "api/workStation/confirm/maintain/data.data";//确认维修
    public static final String GET_REAPIR_HISTORY = "api/workStation/get/repair/history/list.data";//获取维修历史记录

    //报验
    public static final String GET_INSPECTION_TRAY_LIST = "api/workStation/need/check/tray/by/task/data.data";//获取报验托盘列表
    public static final String CHECK_INSPECTION_TRAY = "api/workStation/check/tray/by/check/data.data";//验证报验托盘是否被绑定
    public static final String TO_INSPECTION = "api/workStation/check/tray/data.data";//报验

    //标签打印
    public static final String REQUEST_LABEL_TYPES = "api/work/print/label/kind/data.data";//请求标签类型
    public static final String PRINT_LABEL = "api/work/print/label/Info.data";//打印标签

    public static final String REPAIR_RESEON_TYPE = "api/work/maintain/get/reason/Info.data";//维修原因类型
    public static final String GET_INSPECT_LIST= "api/work/get/order/check/list.data";//查询巡检列表
    public static final String UPDATE_INSPECT_STATUS= "api/work/update/order/check/data.data";//更新巡检状态
}
