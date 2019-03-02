package cn.com.ethank.mylibrary.resourcelibrary.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

public class UICommonUtil {

    // pixs =
    public static int dip2px(Context context, float dipValue) {
        if (context != null) {
            final float scale = context.getResources().getDisplayMetrics().density;
            return (int) (dipValue * scale + 0.5f);
        }
        return (int) (1.5f * dipValue);

    }

    // dips=(pixs*160)/densityDpi
    public static int px2dip(Context context, float pxValue) {
        if (context != null) {
            final float scale = context.getResources().getDisplayMetrics().density;
            return (int) (pxValue / scale + 0.5f);
        }
        return (int) (pxValue / 1.5f);

    }

    public static int px2sp(Context context, float pxValue) {
        if (context != null) {
            final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
            return (int) (pxValue / fontScale + 0.5f);
        }
        return (int) (pxValue / 1.5f);
    }

    public static int sp2px(Context context, float spValue) {
        if (context != null) {
            final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
            return (int) (spValue * fontScale + 0.5f);
        }
        return (int) (1.5f * spValue);
    }

    public static int getScreenWidthPixels(Context context) {
        if (context != null) {
            DisplayMetrics dm = new DisplayMetrics();
            dm = context.getResources().getDisplayMetrics();
            return dm.widthPixels;
        }
        return 720;

    }

    public static int getScreenHeightPixels(Context context) {
        if (context != null) {
            DisplayMetrics dm = new DisplayMetrics();
            dm = context.getResources().getDisplayMetrics();
            return dm.heightPixels;
        }
        return 1080;
    }

    /**
     * 获取状态栏高度
     *
     * @param context
     * @return
     */
    public static int getScreenTopPixels(Activity context) {
        if (context != null) {
            Rect rect = new Rect();
            context.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
            return rect.top;
        }
        return 70;

    }

    /**
     * 获取控件在屏幕的高度
     *
     * @param view
     * @return
     */
    public static int getHeightOnScreen(View view) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int height = location[1];
        return height;
    }

    public static int[] getLocationOnScreen(View view) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        return location;
    }


    /***
     * 有没有屏幕上的虚拟按键
     *
     * @param
     * @return
     */
    public static boolean hasBottomBackKey() {
        boolean hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
        boolean hasHomeKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_HOME);
        boolean hasMenuKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_MENU);
        if (hasHomeKey && !hasBackKey) {
            // 魅族手机
            return true;
        }
        if (hasBackKey && hasHomeKey) {
            // 没有虚拟按键
        } else {
            return true;
            // 有虚拟按键：99%可能。
        }
        return false;

    }

    public static void setLayoutParamsDynamic(int textLength, View ll_item) {
        setLayoutParamsDynamic(textLength, 70, ll_item);
    }

    public static void setLayoutParamsDynamic(int textLength, int defaultHeight, View ll_item) {
        ViewGroup.LayoutParams layoutParams = ll_item.getLayoutParams();
        if (textLength <= 20) {
            layoutParams.height = defaultHeight;
            ll_item.setLayoutParams(layoutParams);
        } else if (textLength > 20 && textLength <= 40) {
            layoutParams.height = defaultHeight * 2;
            ll_item.setLayoutParams(layoutParams);
        } else if (textLength > 40) {
            layoutParams.height = defaultHeight * 3;
            ll_item.setLayoutParams(layoutParams);
        }
    }

    public static void setLayoutParamsDynamic(int textLength, int defaultHeight, int singleLineTextlenth, View ll_item) {
        ViewGroup.LayoutParams layoutParams = ll_item.getLayoutParams();
        if (textLength <= singleLineTextlenth) {
            layoutParams.height = defaultHeight;
            ll_item.setLayoutParams(layoutParams);
        } else if (textLength > singleLineTextlenth && textLength <= 2 * singleLineTextlenth) {
            if ((2 * singleLineTextlenth - textLength) < singleLineTextlenth) {
                layoutParams.height = defaultHeight + defaultHeight / 2;
            } else {
                layoutParams.height = defaultHeight * 2;
            }
            ll_item.setLayoutParams(layoutParams);
        } else if (textLength > 2 * singleLineTextlenth) {
            if ((3 * singleLineTextlenth - textLength) < singleLineTextlenth) {
                layoutParams.height = 2 * defaultHeight + defaultHeight / 2;
            } else {
                layoutParams.height = defaultHeight * 3;
            }
            ll_item.setLayoutParams(layoutParams);
        }
    }

    public static void setTextViewDrawableRight(Context context, int res, TextView textView) {
        Drawable drawable = context.getResources().getDrawable(res);
        textView.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
    }

    public static void setTextViewDrawableLeft(Context context, int res, TextView textView) {
        Drawable drawable = context.getResources().getDrawable(res);
        textView.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
    }

    public static void setTextViewDrawableTop(Context context, int res, TextView textView) {
        Drawable drawable = context.getResources().getDrawable(res);
        textView.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
    }

    private static final String marshmallowMacAddress = "02:00:00:00:00:00";
    private static final String fileAddressMac = "/sys/class/net/wlan0/address";

    public static String getAdresseMAC(Context context) {


        WifiManager wifiMan = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInf = wifiMan.getConnectionInfo();

        if (wifiInf != null && marshmallowMacAddress.equals(wifiInf.getMacAddress())) {
            String result = null;
            try {
                result = getAdressMacByInterface();
                if (result != null) {
                    return result;
                } else {
                    result = getAddressMacByFile(wifiMan);
                    return result;
                }
            } catch (IOException e) {
                Log.e("MobileAccess", "Erreur lecture propriete Adresse MAC");
            } catch (Exception e) {
                Log.e("MobileAcces", "Erreur lecture propriete Adresse MAC ");
            }
        } else {
            if (wifiInf != null && wifiInf.getMacAddress() != null) {
                return wifiInf.getMacAddress();
            } else {
                return "";
            }
        }
        return marshmallowMacAddress;
    }

    private static String getAdressMacByInterface() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (nif.getName().equalsIgnoreCase("wlan0")) {
                    byte[] macBytes = nif.getHardwareAddress();
                    if (macBytes == null) {
                        return "";
                    }

                    StringBuilder res1 = new StringBuilder();
                    for (byte b : macBytes) {
                        res1.append(String.format("%02X:", b));
                    }

                    if (res1.length() > 0) {
                        res1.deleteCharAt(res1.length() - 1);
                    }
                    return res1.toString();
                }
            }

        } catch (Exception e) {
            Log.e("MobileAcces", "Erreur lecture propriete Adresse MAC ");
        }
        return null;
    }

    private static String getAddressMacByFile(WifiManager wifiMan) throws Exception {
        String ret;
        int wifiState = wifiMan.getWifiState();

        wifiMan.setWifiEnabled(true);
        File fl = new File(fileAddressMac);
        FileInputStream fin = new FileInputStream(fl);
        ret = crunchifyGetStringFromStream(fin);
        fin.close();

        boolean enabled = WifiManager.WIFI_STATE_ENABLED == wifiState;
        wifiMan.setWifiEnabled(enabled);
        return ret;
    }

    private static String crunchifyGetStringFromStream(InputStream crunchifyStream) throws IOException {
        if (crunchifyStream != null) {
            Writer crunchifyWriter = new StringWriter();

            char[] crunchifyBuffer = new char[2048];
            try {
                Reader crunchifyReader = new BufferedReader(new InputStreamReader(crunchifyStream, "UTF-8"));
                int counter;
                while ((counter = crunchifyReader.read(crunchifyBuffer)) != -1) {
                    crunchifyWriter.write(crunchifyBuffer, 0, counter);
                }
            } finally {
                crunchifyStream.close();
            }
            return crunchifyWriter.toString();
        } else {
            return "No Contents";
        }
    }

}

