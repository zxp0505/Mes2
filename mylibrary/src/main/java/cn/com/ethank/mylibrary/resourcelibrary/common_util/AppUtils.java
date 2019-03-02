package cn.com.ethank.mylibrary.resourcelibrary.common_util;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.math.BigDecimal;

import cn.com.ethank.mylibrary.resourcelibrary.application.DefaultApplication;

/**
 * Created by hanxue on 17/7/4.
 */

public class AppUtils {
    public static boolean isNetwork(Context ctx) {
        ConnectivityManager cm = (ConnectivityManager) ctx
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null && ni.isConnectedOrConnecting();
    }

    public static int px2dp(Context ctx, int px) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, px,
                ctx.getResources().getDisplayMetrics());
    }

    public static int dp2px(Context ctx, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                ctx.getResources().getDisplayMetrics());
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue
     * @param pxValue （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * @param spValue （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }


    /**
     * 如果是null或者""则返回"",显示信息时很多地方需要用
     *
     * @param str
     * @return
     */
    public static String parseNull(String str) {
        if (TextUtils.isEmpty(str))
            return "";
        return str;
    }

    public static String parseIntegerNull(Integer integer) {
        if (integer == null)
            return "0";
        return String.valueOf(integer);
    }

    public static String parseBigDecimalNull(BigDecimal bigDecimal) {
        if (bigDecimal == null)
            return "0";
        return String.valueOf(bigDecimal.intValue());
    }

    public static boolean isNewer(String serverVersion, String clientVersion) {
        if (TextUtils.isEmpty(serverVersion)) {
            return false;
        }
        String s1[] = serverVersion.split("\\.");
        String s2[] = clientVersion.split("\\.");

        if (s1.length != s2.length) {
            if (s1.length < s2.length) {
                for (int n = 1; n <= s2.length - s1.length; n++) {
                    serverVersion += ".0";
                }
            } else {
                for (int n = 1; n <= s1.length - s2.length; n++) {
                    clientVersion += ".0";
                }
            }
        }
        s1 = serverVersion.split("\\.");
        s2 = clientVersion.split("\\.");
        for (int i = 0; i < s1.length; i++) {
            int t1 = 0;
            int t2 = 0;
            try {
                t1 = Integer.parseInt(s1[i]);
                t2 = Integer.parseInt(s2[i]);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (t1 > t2) {
                return true;
            } else if (t1 < t2)
                return false;
        }
        return false;
    }

    public static int convertBigDecimalToInterger(BigDecimal bigDecimal) {
        if (bigDecimal == null) {
            return 0;
        }
        return bigDecimal.intValue();
    }

    public static int convertStringToInt(String val) {
        if (TextUtils.isEmpty(val)) {
            return 0;
        }
        try {
            return Integer.parseInt(val);
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    public static void hideInputMethod(View view) {
        if (null == view || view.getContext() == null)
            return;
        ((InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE)).
                hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public static void showInputMethod(EditText view) {
        if (null == view || view.getContext() == null)
            return;
        ((InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE)).
                showSoftInput(view, 0);
    }

    public static String getVersionName(Application application) {
        try {
            PackageManager packageManager = application.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(application.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static int getVersionCode(Application application) {
        try {
            PackageManager packageManager = application.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(application.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 判断软键盘是否弹出
     */
    public static boolean isSHowKeyboard(Context context, View v) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.hideSoftInputFromWindow(v.getWindowToken(), 0)) {
            imm.showSoftInput(v, 0);
            return true;
            //软键盘已弹出
        } else {
            return false;
            //软键盘未弹出
        }
    }

    public static void toSetWifiSurface(Context context) {
        // 跳转到系统的网络设置界面
        Intent intent = null;
        // 先判断当前系统版本
        if (android.os.Build.VERSION.SDK_INT > 10) {  // 3.0以上
            intent = new Intent(android.provider.Settings
                    .ACTION_WIFI_SETTINGS);
        } else {
            intent = new Intent();
            intent.setClassName("com.android.settings",
                    Settings.ACTION_WIFI_SETTINGS);
        }
        if ((context instanceof Application)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }

    public static void restartApp(Class clazz) {
        //启动页
        Intent intent = new Intent(DefaultApplication.getContext(), clazz);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        DefaultApplication.getContext().startActivity(intent);
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    public static  void restartApp2() {

        final Intent intent = DefaultApplication.getContext().getPackageManager()
                .getLaunchIntentForPackage(DefaultApplication.getContext().getPackageName());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        DefaultApplication.getContext().startActivity(intent);



    }

    public static void restartApp3(Class clazz) {
        //启动页
        Intent intent = new Intent(DefaultApplication.getContext(), clazz);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        DefaultApplication.getContext().startActivity(intent);
        android.os.Process.killProcess(android.os.Process.myPid());
//        Intent mStartActivity = new Intent(DefaultApplication.getContext(), clazz);
//        int mPendingIntentId = 123456;
//        PendingIntent mPendingIntent = PendingIntent.getActivity(DefaultApplication.getContext(), mPendingIntentId,    mStartActivity, PendingIntent.FLAG_CANCEL_CURRENT);
//        AlarmManager mgr = (AlarmManager)DefaultApplication.getContext().getSystemService(Context.ALARM_SERVICE);
//        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent);
//        System.exit(0);
    }
}
