package cn.com.ethank.mylibrary.resourcelibrary.common_util;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import cn.com.ethank.mylibrary.resourcelibrary.common_util.log.LoggerUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


/**
 * Activity管理系统
 *
 * @author dddd
 */
public class AppManager {
    public static Stack<Activity> activityStack = new Stack<Activity>();
    private static AppManager instance;
    public static Stack<Activity> mActivities = new Stack<Activity>();

    public static void clear() {
        if (activityStack != null) {
            activityStack.clear();
        }
    }

    /**
     * 单一实例
     */

    public static AppManager getAppManager() {

        if (instance == null) {

            instance = new AppManager();

        }

        return instance;

    }

    /**
     * 添加Activity到堆栈
     */

    public void addActivity(Activity activity) {

        if (activityStack == null) {

            activityStack = new Stack<Activity>();

        }

        activityStack.add(activity);
    }

    public boolean isCurrentActivity(Activity activity) {
        if (activity != null && currentActivity() != null) {
            return activity == currentActivity();
        }
        return false;

    }

    public void removeActivity(Activity activity) {
        try {
            if (activityStack != null) {
                if (activityStack.contains(activity)) {
                    activityStack.remove(activity);
                    try {

                        if (activity != null) {
                            activity.finish();
                            mActivities = null;
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    //单纯从栈中移除
    public void removeActivityFromStack(Activity activity) {
        try {
            if (activityStack != null) {
                if (activityStack.contains(activity)) {
                    activityStack.remove(activity);
                }
            }
        } catch (Exception e) {

        }


    }

    /**
     * 移除Activity到堆栈,调用onDestroy时调用此方法
     */

    public void removeActivity(Stack<Activity> activity) {
        try {
            if (activityStack != null) {
                if (activityStack.containsAll(activity)) {
                    activityStack.removeAll(activity);
                    try {

                        if (activity != null) {
                            for (int i = 0; i < activity.size(); i++) {
                                activity.get(i).finish();
                            }
                            mActivities = null;
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */

    public Activity currentActivity() {

        Activity activity = activityStack.lastElement();

        return activity;

    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */

    public void finishActivity() {

        Activity activity = activityStack.lastElement();

        if (activity != null) {

            activity.finish();

            activity = null;

        }

    }

    /**
     * 结束指定类名的Activity
     */

    public void finishActivity(Class<?> cls) {

        if (mActivities == null)
            mActivities = new Stack<Activity>();
        for (Activity activity : activityStack) {
            if (activity != null && activity.getClass().equals(cls)) {
                //removeActivity(activity);
                mActivities.add(activity);
            }
        }

        removeActivity(mActivities);

    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        if (mActivities == null)
            mActivities = new Stack<Activity>();
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                //removeActivity(activityStack);
                mActivities.add(activityStack.get(i));
            }
        }
        removeActivity(mActivities);
    }

    /**
     * 打印所有的站内信息
     */
    public void printAllActivity() {
        List<String> list = new ArrayList<>();
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                list.add(activityStack.get(i).getClass().getName());
            }
        }
        LoggerUtil.d(list);
    }

    public void finishOtherActivity(Activity activity) {
        if (activityStack == null) {
            return;
        }
        if (mActivities == null)
            mActivities = new Stack<Activity>();
        for (int i = 0, size = activityStack.size(); i < size; i++) {

            if (null != activityStack.get(i) && activityStack.get(i) != activity) {
                //removeActivity(activityStack.get(i));
                mActivities.add(activityStack.get(i));
            }

        }

        removeActivity(mActivities);
    }

    /**
     * 退出应用程序
     */

    public void AppExit(Context context) {

        try {

            finishAllActivity();

            ActivityManager activityMgr = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            activityMgr.killBackgroundProcesses(context.getPackageName());
//            Process.killProcess(Process.myPid());
            System.exit(0);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
