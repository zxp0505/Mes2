package cn.com.ethank.mylibrary.resourcelibrary.common_util;

import android.content.Context;
import android.util.Log;


import java.io.InputStream;
import java.util.Properties;

import cn.com.ethank.mylibrary.resourcelibrary.application.DefaultApplication;


public class ConfogProrerties {
    private static String CONFIG_FILE = "config.properties";
    private static Properties properties;

    private ConfogProrerties() {
    }

    /**
     * true是测试版,false是正式版
     *
     * @param switchName
     * @return
     */
    public static boolean getConfigValue(String switchName) {
        boolean isTest = false;
        if (properties == null) {
            init(DefaultApplication.getInstance());
        }
        if (properties != null) {
            isTest = properties.getProperty(switchName, "0").equals("1");//为1时是测试版
            Log.i(switchName, isTest + "");
        }
        return isTest;
    }


    public synchronized static void init(Context context) {
        try {
            if (properties == null) {
                Properties properties = new Properties();
                InputStream inStream = context.getAssets().open(CONFIG_FILE);
                properties.load(inStream);//is是通过上面获得的输入流
                ConfogProrerties.properties = properties;
                if (inStream != null) {
                    inStream.close();
                    inStream = null;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}