package workstation.zjyk.workstation.ui.reciver;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.List;

import cn.com.ethank.mylibrary.resourcelibrary.toast.ToastUtil;
import workstation.zjyk.workstation.ui.activity.WSSplashActivity;

public class BootBroadcastReceiver extends BroadcastReceiver {

    private static final String PACKAGE_NAME = "workstation.zjyk.workstation";

    private static final String ACTION_BOOT = "android.intent.action.BOOT_COMPLETED";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (!isAppRunning(context, PACKAGE_NAME)) {
            //开机后,自动启动应用
            Intent it = new Intent(context, WSSplashActivity.class);
            // 在上下文之外启动activity,需要创建一个新的任务栈
            it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(it);
        }

//        if (intent.getAction().equals(ACTION_BOOT)) {
//        }
    }

    /**
     * 方法描述：判断某一应用是否正在运行
     * Created by cafeting on 2017/2/4.
     *
     * @param context     上下文
     * @param packageName 应用的包名
     * @return true 表示正在运行，false 表示没有运行
     */
    public static boolean isAppRunning(Context context, String packageName) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(100);
        if (list.size() <= 0) {
            return false;
        }
        for (ActivityManager.RunningTaskInfo info : list) {
            if (info.baseActivity.getPackageName().equals(packageName)) {
                return true;
            }
        }
        return false;
    }
} 