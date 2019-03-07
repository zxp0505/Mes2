package workstation.zjyk.com.scanapp.ui;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.HashMap;
import java.util.Map;

import cn.com.ethank.mylibrary.resourcelibrary.common_util.AppUtils;
import cn.com.ethank.mylibrary.resourcelibrary.intent.StartIntentUtils;
import cn.com.ethank.mylibrary.resourcelibrary.toast.ToastUtil;
import cn.com.ethank.mylibrary.resourcelibrary.utils.UICommonUtil;
import cn.com.ethank.ui.common.dialog.LibraryDialogUtils;
import cn.com.ethank.ui.common.dialog.callback.ResourceDialogCallBackTwo;
import io.reactivex.functions.Consumer;
import workstation.zjyk.com.scanapp.ui.present.ScanSplashPresent;
import workstation.zjyk.com.scanapp.ui.views.ScanSplashView;
import workstation.zjyk.com.scanapp.util.ScanConstants;
import workstation.zjyk.com.scanapp.util.ScanURLBuilder;

/**
 * Created by zjgz on 2018/1/19.
 */

public class ScanSplashActivity extends ScanPermissionActivity<ScanSplashPresent> implements ScanSplashView {
    private static final int VERTICAL = 0;
    private static final int HORZATAIL = 1;
    Handler handler = new Handler(Looper.getMainLooper());
    boolean permissonB = false;
    boolean timeB = false;
    String[] permissions = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        creatPresent();
        if (ScanConstants.isWarn()) {
            //报警  1.测试服务2.传递mac
            identityVerifi();
        } else {
            initPermission(permissions);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    timeB = true;
                    if (permissonB) {
                        checkGo();
                    }
                }
            }, 1500);
        }

    }

    @Override
    protected void creatPresent() {
        currentPresent = new ScanSplashPresent();
        currentPresent.attachView(this);
    }

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (ScanConstants.isWarn()) {
            return;
        }
        //从系统的设置界面设置完返回app的时候，需要重新检测一下权限
        if (Build.VERSION.SDK_INT < 23) {
            allGrantedToDo();
        } else if (!isAllGranted(permissions)) {
            //判断基本的应用权限
            openAppDetails();
        } else if (!initMiuiPermission()) {
            //如果基础的应用权限已经授取；切是小米系统，校验小米的授权管理页面的权限
            openMiuiAppDetails();
        } else {
            //都没有问题了，跳转主页
            allGrantedToDo();
        }
    }

    @Override
    void allGrantedToDo() {
        permissonB = true;
        if (timeB) {
            checkGo();
        }
    }

    @Override
    void hasDeclineToDo() {
        openAppDetails();
    }

    private void goLogin(int oritation) {
        switch (oritation) {
            case HORZATAIL:
//                StartIntentUtils.startIntentUtils(ScanSplashActivity.this, ScanLoginHzActivity.class);
                break;
            case VERTICAL:
                StartIntentUtils.startIntentUtils(ScanSplashActivity.this, ScanLoginActivity.class);
                break;
        }

        finish();
    }

    private void checkGo() {
        testServer();
//        if (!ScanConstants.isCheckTray()) {
//            //质量
//            goLogin(VERTICAL);
//        } else {
//            goScan();
//        }

    }

    private void testServer() {
        currentPresent.testServer(new HashMap<>());
    }

    private void goScan() {
        StartIntentUtils.startIntentUtils(ScanSplashActivity.this, ScanCode2Activity.class);
        finish();
    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacksAndMessages(null);
        handler = null;
        super.onDestroy();
    }


    @Override
    public void showTestServerResult(boolean result) {
        if (result) {
            startUi();
        } else {
            showModifiHostUrlDialog();
        }
    }

    @Override
    public void showidentityVerifiResult(boolean result, Throwable throwable) {
        if (result) {
//            ToastUtil.showInfoCenterShort("身份验证通过");
            startUi();
        } else {
            if (throwable != null) {
                //onerror
                showModifiHostUrlDialog();
            } else {
                ToastUtil.showInfoCenterShort("身份验证失败");
                finish();
            }
        }
    }


    /**
     * 身份验证
     */
    private void identityVerifi() {
        Map<String, String> params = new HashMap<>();
        params.put("mac", UICommonUtil.getAdresseMAC(this));
        currentPresent.identityVerifi(params);

    }

    private void startUi() {
        if (!ScanConstants.isCheckTray()) {
            //质量
            if (ScanConstants.isWarn()) {
                //报警
                if (ScanConstants.isWarnLogin()) {
                    StartIntentUtils.startIntentUtils(this, ScanWarnLoginActivity.class);
                }else{
                    StartIntentUtils.startIntentUtils(this, ScanWaitWarnActivity.class);
                }
                finish();

            } else {
                goLogin(VERTICAL);
            }
        } else {
            goScan();
        }
    }

    private void showModifiHostUrlDialog() {
        LibraryDialogUtils.showSetDomainDialog(this, ScanURLBuilder.getDomain(), "请检查服务器地址", new ResourceDialogCallBackTwo() {
            @Override
            public void OnPositiveClick(Object obj) {
                //重启
                if (obj != null && obj instanceof String) {
                    ScanURLBuilder.setDomain((String) obj);
                }
                AppUtils.restartApp(ScanSplashActivity.class);
                finish();
//                gotoSplash();
            }

            @Override
            public void OnNegativeClick() {
                finish();
            }
        });
        return;
    }
}
