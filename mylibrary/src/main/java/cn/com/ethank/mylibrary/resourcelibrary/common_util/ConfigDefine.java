package cn.com.ethank.mylibrary.resourcelibrary.common_util;

public class ConfigDefine {
    //测试时是true,正式是false,如果isTestVersion是正式,所有的都是false
    public static boolean isTestVersion = getV("isTestVersion");
    public static boolean isTestHost = getV("isTestHost") && isTestVersion;
    public static boolean isShowLog = getV("isShowLog") && isTestVersion;
    public static boolean isShowLeak = getV("isShowLeak") && isTestVersion;

    private static boolean getV(String name) {
        return ConfogProrerties.getConfigValue(name);//true是测试,false是正式
    }
}