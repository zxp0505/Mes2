package cn.com.ethank.mylibrary.resourcelibrary.utils;

import android.os.Build;

/**
 * Created by zjgz on 2017/12/25.
 */

public class PhoneUtils {

    /**
     * 判断是否是华为pad
     * @return
     */
    public static boolean isHuaweiPad() {
        return Build.BRAND.startsWith("HUAWEI");
    }
}
