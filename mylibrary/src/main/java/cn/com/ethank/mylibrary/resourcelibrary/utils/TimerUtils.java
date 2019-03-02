package cn.com.ethank.mylibrary.resourcelibrary.utils;

import android.text.format.DateFormat;
import android.util.Log;

import java.util.Calendar;
import java.util.GregorianCalendar;

import cn.com.ethank.mylibrary.resourcelibrary.server.MessageEventBean;

/**
 * Created by zjgz on 2017/12/7.
 */

public class TimerUtils {
    //12小时制
    public static String getCurrentTimer() {
        long sysTime = System.currentTimeMillis();
        String currentTimer = DateFormat.format("hh:mm", sysTime) + "";
        GregorianCalendar ca = new GregorianCalendar();
        if (ca.get(GregorianCalendar.AM_PM) == 0) {
            currentTimer = "上午: " + currentTimer;
        } else {
            currentTimer = "下午: " + currentTimer;
        }
        return currentTimer;
    }

    //24小时制
    public static String getCurrentTimer24() {
        long sysTime = System.currentTimeMillis();
        String currentTimer = DateFormat.format("HH:mm", sysTime) + "";
        return currentTimer;
    }

    public static String getNowDate() {
        StringBuffer buffer = new StringBuffer();
        Calendar now = Calendar.getInstance();
        buffer.append(now.get(Calendar.YEAR) + "-").append((now.get(Calendar.MONTH) + 1) + "-")
                .append(now.get(Calendar.DAY_OF_MONTH));
        return buffer.toString();
    }


    /**
     * @param
     */
    public static String getStringTime(long mss) {
        StringBuilder builder = new StringBuilder();
        long days = mss / (1000 * 60 * 60 * 24);
        long hours = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
        long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);
        long seconds = (mss % (1000 * 60)) / 1000;
        if (days > 0) {
            builder.append(days + "天");
        }
        if (hours > 0) {
            builder.append(hours + "时");
        }
        if (minutes > 0) {
            builder.append(minutes + "分");
        }
        if (seconds > 0 && seconds < 60 && minutes == 0) {
            minutes = 0;
            builder.append(minutes + "分");
        }
        return builder.toString();
    }

    //秒转换天-时 分
    public static String getSecondsStringTime(long mss) {
        StringBuilder builder = new StringBuilder();
        long days = mss / (60 * 60 * 24);
        if (days > 0) {
            mss = mss - days * (60 * 60 * 24);
        }
        long hours = (mss / (60 * 60));
        if (hours > 0) {
            mss = mss - hours * (60 * 60);
        }
        long minutes = (mss / (60));
        if (minutes > 0) {
            mss = mss - minutes * (60);
        }
        long seconds = mss;
        if (days > 0) {
            builder.append(days + "天");//getNumber(days,3)
        } else {
            builder.append("0天");
        }
        if (hours > 0) {
            builder.append(hours + "时");
        } else {
            builder.append("0时");
        }
        if (minutes > 0) {
            builder.append(minutes + "分");
        } else {
            minutes = 0;
            builder.append("0分");
        }
        return builder.toString();
    }

    public static String getThreeStringTime(long mss) {
        StringBuilder builder = new StringBuilder();
        long days = mss / (60 * 60 * 24);
        if (days > 0) {
            mss = mss - days * (60 * 60 * 24);
        }
        long hours = (mss / (60 * 60));
        if (hours > 0) {
            mss = mss - hours * (60 * 60);
        }
        long minutes = (mss / (60));
        if (minutes > 0) {
            mss = mss - minutes * (60);
        }
        long seconds = mss;
        if (days > 0) {
            builder.append(days + "天");//getNumber(days,3)
        }
        if (hours > 0) {
            builder.append(hours + "时");
        }else{
            builder.append(0 + "时");
        }
        if (minutes > 0) {
            builder.append(minutes + "分");
        }else{
            builder.append(0 + "分");
        }
        return builder.toString();
    }

    private static String getNumber(long number, int requireLenth) {
        String num = number + "";
        switch (num.length()) {
            case 1:
                if (requireLenth == 3) {
                    num = "00" + num;
                } else if (requireLenth == 2) {
                    num = "0" + num;
                }
                break;
            case 2:
                if (requireLenth == 3) {
                    num = "0" + num;
                } else if (requireLenth == 2) {
                }
                break;
        }
        return num;
    }
}
