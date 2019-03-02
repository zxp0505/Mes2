package com.zjyk.repair.util;

import cn.com.ethank.mylibrary.resourcelibrary.common_util.ConfigDefine;

/**
 * Created by zjgz on 2017/11/2.
 */

public class RPURLBuilder {
    private static boolean isOnline = !ConfigDefine.isTestHost;

    public static String getHostUrl() {
        if (isOnline) {
            return "";
        } else {
//            return "http://192.168.3.228:8080/agent/";
            return "http://192.168.3.140:8080/agent/";
        }
    }

//    public static String PAD_VERSION_URL = "pad/version.action";//获取pad版本号
//
//    public static String PAD_VERIFY_URL = "pad/login.action";//PAD认证
//
//    public static String LOGIN_URL = "worker/login.action";//人员登陆
//
//    public static String PHOTO_UOLOAD_URL = "worker/upload.action";//照片上传
//
//    public static String STATION_ON_URL = "workstation/on.action";//工位开启
//
//    public static String STATION_OFF_URL = "workstation/off.action";//工位关闭
//
//    public static final String GET_WRAP_INFO = "api/line/wrap/info.data";//获取物料包裹中的物料信息
//
//    public static final String GET_RECIVER_MAKESURE = "api/line/receive/data.data";//确认收料
//
//    public static final String GET_RECIVER_RECORD = "api/line/receive/list.data";//获取收料记录
//
//    public static final String GET_STATION_LIST = "api/line/materiel/request/list.data";//获取工位列表
//
//    public static final String GET_STATION_REQUIR_DETAIL = "api/line/workStation/materiel/request/list.data";//获取工位需求详情
//
//    public static final String GET_STATION_MATERAIL_LIST = "api/line/workStation/materiel/requests/list.data";//确认发放物料列表

    //登陆
    public static final String GET_PERSON_SCAN = "api/person/info.data";//扫码登陆
    public static final String UPLOAD_PHOTO = "api/person/upload.data";//上传头像
    public static final String LOGIN_OUT = "api/person/exit.data";//退出登陆
    public static final String GET_ALL_PERSON = "api/person/online/info.data";//获取所有的人员信息

    public static final String CHECK_APK_VERSION = "api/app/info.data";//检查apk版本
    public static final String GET_STATION_NAME = "api/workStation/info.data";//获取工位
    public static final String GET_USER_REPORT_DETAIL = "api/work/report/record/by/today/list.data";//点击头像获取报工详情
//    public static final String GET_BIND_TRAY_SCAN = "api/line/check/tray/use/info.data";//绑定托盘扫码
//    public static final String MAKE_SURE_ISSUR = "api/line/workStation/distribute/materiel/list.data";//确认发料
//    //库存管理
//    public static final String GET_INVENTORY_LIST = "api/line/storage/manager/get/list.data";//获取库存管理列表
//    public static final String UPDATA_INVENTORY_DATA = "api/line/storage/manager/updata/info.data";//修改库存
//
//    public static final String GET_INWARE = "api/line/storage/manager/save/info.data";//入仓
//
//    //异常
//    public static final String GET_EXCEPTION_RECORD = "api/exception/manager/searchRecord/list.data";//根据工位id查询异常输出记录
//    public static final String GET_EXCEPTION_RECORD_DETAIL = "api/exception/manager/searchRecordDetail/list.data";//根据异常输出记录id查询异常输出记录详情
//    public static final String GET_EXCEPTION_RECORD_INFO = "api/exception/manager/print/list.data";//根据异常输出记录id查询异常输出打印信息
//    public static final String SAVE_EXCEPTION_RECORD_OUT = "api/exception/manager/data.data";//保存异常输出信息
//    public static final String PRINTRR_EXCEPTION = "api/exception/manager/print/info.data";//异常打印
//
//    //投递 api/print/tray/info.data
//    public static final String GET_DELIVERY_TRAY = "api/deliver/tray/info.data";//投递托盘
//    public static final String PRINTER_DELIVERY_TRAY_INFO = "api/print/tray/info.data";//打印投递托盘信息
//    public static final String CLEAR_TRAY = "api/emptyTray/tray/info.data";//打印后清空托盘

    public static final String SEND_HEART = "api/pad/beatTime/data.data";//发送心跳

}
