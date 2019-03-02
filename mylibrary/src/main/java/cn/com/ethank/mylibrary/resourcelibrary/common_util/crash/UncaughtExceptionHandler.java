package cn.com.ethank.mylibrary.resourcelibrary.common_util.crash;

import android.content.Context;
import android.content.Intent;

import cn.com.ethank.mylibrary.resourcelibrary.common_util.ConfigDefine;

import java.io.PrintWriter;
import java.io.StringWriter;

import cn.com.ethank.mylibrary.resourcelibrary.application.DefaultApplication;


public class UncaughtExceptionHandler implements
        Thread.UncaughtExceptionHandler {

    private final Context mContext;

    public UncaughtExceptionHandler(Context context) {
        this.mContext = context;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
//        BuglyLog.e("崩溃日志", ex.getMessage());
//        CrashReport.postCatchedException(ex);//上报异常信息
        if (ConfigDefine.isTestVersion) {
            final StringWriter stackTrace = new StringWriter();
            ex.printStackTrace(new PrintWriter(stackTrace));

            final String crashInfo = stackTrace.toString();
            Intent intent = new Intent(mContext, BugReportActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                    | Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(BugReportActivity.STACKTRACE, crashInfo);
            mContext.startActivity(intent);
        }
        DefaultApplication.getInstance().exit();
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);

    }

}
