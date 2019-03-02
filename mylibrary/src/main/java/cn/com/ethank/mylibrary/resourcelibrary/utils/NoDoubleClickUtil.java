package cn.com.ethank.mylibrary.resourcelibrary.utils;

import java.util.Calendar;

/**
 * Created by zjgz on 2018/2/3.
 */

public class NoDoubleClickUtil {
    public static int MIN_CLICK_DELAY_TIME = 1000;
    private static long lastClickTime = 0;

    public static boolean doubleClick(int minclicktime) {
        if (minclicktime > 0) {
            MIN_CLICK_DELAY_TIME = minclicktime;
        }
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
            lastClickTime = currentTime;
            return false;
        }

        return true;
    }
}
