package cn.com.ethank.mylibrary.resourcelibrary.toast;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.Toast;

import cn.com.ethank.mylibrary.resourcelibrary.application.DefaultApplication;
import cn.com.ethank.mylibrary.resourcelibrary.toast.custom_toast.Toasty;


/**
 * Toast工具类
 */
public class ToastUtil {

    static Toast toast;
    private static final int CUSTOM_TOAST = 1;//自定义toast

    static Handler handler = new Handler(Looper.getMainLooper(), new Callback() {

        @Override
        public boolean handleMessage(Message msg) {
            int what = msg.what;
            if (what == CUSTOM_TOAST) {
                Bundle data = msg.getData();
                String text = data.getString("text");
                int infoStatus = data.getInt("infoStatus");
                showCustomToastReal(text,infoStatus);
            } else {
                Object[] obj = (Object[]) msg.obj;
                int type = (Integer) obj[1];
                if (type == Toast.LENGTH_LONG) {
                    ToastUtil.showLong(obj[0].toString());
                } else {
                    ToastUtil.showShort(obj[0].toString());
                }
            }

            return false;
        }
    });

    private static void show(String text, int type, int area) {
        try {
            if (null == text) {
                return;
            }
            if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
                createToast(text, type);
                if (area == Gravity.CENTER) {
                    toast.setGravity(Gravity.CENTER, 0, 0);
                }
                toast.show();
            } else {
                // 不是主线程
                Message message = new Message();
                Object[] obj = {text, type};
                message.obj = obj;
                handler.sendMessage(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("ShowToast")
    private static void createToast(String text, int type) {
        if (toast == null) {
            toast = Toast.makeText(DefaultApplication.getInstance(), text, type);
        } else {
            toast.setText(text);
        }
    }

    public static void showLong(String text) {
        show(text, Toast.LENGTH_LONG, Gravity.BOTTOM);
    }

    public static void showLong(int resId) {
        String text = DefaultApplication.getInstance().getResources().getString(resId);
        show(text, Toast.LENGTH_LONG, Gravity.BOTTOM);
    }

    public static void showShort(int resId) {
        String text = DefaultApplication.getInstance().getResources().getString(resId);
        show(text, Toast.LENGTH_SHORT, Gravity.BOTTOM);
    }

    public static void showShort(String text) {
        show(text, Toast.LENGTH_SHORT, Gravity.BOTTOM);
    }

    public static void showCenterShort(String text, boolean isCustom) {
        if (isCustom) {
            showCustomToast(text, 0);
        } else {
            if (!TextUtils.isEmpty(text)) {
                show(text, Toast.LENGTH_SHORT, Gravity.CENTER);
            }
        }
    }

    public static void showInfoCenterShort(String text) {
        showCustomToast(text, 1);

    }

    public static void showCenterShort(String text) {
        show(text, Toast.LENGTH_SHORT, Gravity.CENTER);
    }

    public static void showCenterShort(int resId) {
        String text = DefaultApplication.getInstance().getResources().getString(resId);
        show(text, Toast.LENGTH_SHORT, Gravity.CENTER);
    }

    public static void showCenterLong(String text) {
        show(text, Toast.LENGTH_LONG, Gravity.CENTER);
    }

    public static void showCenterLong(int resId) {
        String text = DefaultApplication.getInstance().getResources().getString(resId);
        show(text, Toast.LENGTH_LONG, Gravity.CENTER);
    }


    public static void showCustomToast(String text, int infoStatus) {
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            showCustomToastReal(text, infoStatus);
        } else {
            Message message = handler.obtainMessage();
            message.what = CUSTOM_TOAST;
            Bundle bundle = new Bundle();
            bundle.putString("text", text);
            bundle.putInt("infoStatus", infoStatus);
            message.setData(bundle);
            handler.sendMessage(message);
        }


    }

    public static void showCustomToastReal(String text, int infoStatus) {
        switch (infoStatus) {
            case 0:
                //类似error状态 紧急
                Toasty.error(DefaultApplication.getInstance(), text).show();
                break;
            case 1:
                //提示一般的信息
                Toasty.info(DefaultApplication.getInstance(), text).show();
                break;
        }
    }

    public static void cancelToast() {
        if (toast != null) {
            toast.cancel();
            toast = null;
        }
    }
}
