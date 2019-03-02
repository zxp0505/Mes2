package workstation.zjyk.workstation.ui.application;

import com.squareup.leakcanary.LeakCanary;

import java.lang.ref.WeakReference;

import cn.com.ethank.mylibrary.resourcelibrary.application.DefaultApplication;
import cn.com.ethank.mylibrary.resourcelibrary.common_util.AppManager;
import cn.com.ethank.mylibrary.resourcelibrary.common_util.ConfigDefine;
import cn.com.ethank.mylibrary.resourcelibrary.common_util.ConfogProrerties;
import cn.com.ethank.mylibrary.resourcelibrary.common_util.crash.UncaughtExceptionHandler;
import cn.com.ethank.mylibrary.resourcelibrary.common_util.log.LoggerUtil;
import cn.com.ethank.mylibrary.resourcelibrary.core.sys.CoyoteSystem;
import cn.com.ethank.mylibrary.resourcelibrary.core.sys.CoyoteSystemImp;
import cn.com.ethank.mylibrary.resourcelibrary.core.sys.SysInfoImp;
import cn.com.ethank.mylibrary.resourcelibrary.core.threading.DefaultThreadingService;
import cn.com.ethank.mylibrary.resourcelibrary.core.threading.IThreadingService;
import workstation.zjyk.workstation.util.dialog.WSCommonDialog;

/**
 * Created by zjgz on 2017/10/24.
 */

public class WSBaseApplication extends DefaultApplication {

    private WeakReference<WSCommonDialog> wsCommonDialogWeakReference;

    @Override
    public void onCreate() {
        super.onCreate();
        LoggerUtil.init("station");
        ConfogProrerties.init(this);
//        initLeak();
        initCrash();
        initSys();
    }

    private void initLeak() {
        if (ConfigDefine.isTestVersion) {
            if (LeakCanary.isInAnalyzerProcess(this)) {
                // This process is dedicated to LeakCanary for heap analysis.
                // You should not init your app in this process.
                return;
            }
            LeakCanary.install(this);
        }
    }

    private void initCrash() {
        if (ConfigDefine.isTestVersion) {
            Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler(this));
        }
    }

    private void initSys() {
        SysInfoImp sysInfoImp = new SysInfoImp(getApplicationContext(), null, 0);
        CoyoteSystemImp coyoteSystemImp = new CoyoteSystemImp(getApplicationContext(), sysInfoImp);
        CoyoteSystem.setCurrent(coyoteSystemImp);


        IThreadingService iThreadingService = new DefaultThreadingService();
        coyoteSystemImp.addService(IThreadingService.class, iThreadingService);
    }


    @Override
    public void exit() {
        AppManager.getAppManager().AppExit(this);
    }


    public void setCurrentDialog(WSCommonDialog currentDialog) {
        wsCommonDialogWeakReference = new WeakReference<>(currentDialog);
    }

    public WSCommonDialog getCurrentDialog() {
        if (wsCommonDialogWeakReference != null) {
            return wsCommonDialogWeakReference.get();
        }
        return null;
    }
}
