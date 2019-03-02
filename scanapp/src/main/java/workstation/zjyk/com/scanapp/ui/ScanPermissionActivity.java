package workstation.zjyk.com.scanapp.ui;

import android.Manifest;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AlertDialog;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import workstation.zjyk.com.scanapp.R;
import workstation.zjyk.com.scanapp.ui.present.ScanMainPresent;
import workstation.zjyk.com.scanapp.ui.present.ScanRxPresent;
import workstation.zjyk.com.scanapp.util.JumpPermissionManagement;

/**
 * Created by zjgz on 2018/3/7.
 */

public abstract class ScanPermissionActivity<T extends ScanRxPresent> extends ScanBaseActivity<T> {

    //检测MIUI
    private static final String KEY_MIUI_VERSION_CODE = "ro.miui.ui.version.code";
    private static final String KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name";
    private static final String KEY_MIUI_INTERNAL_STORAGE = "ro.miui.internal.storage";
    private AlertDialog openMiuiAppDetDialog;
    private AlertDialog openAppDetDialog;

    public void initPermission(String[] permissions) {
//        判断是否是6.0以上的系统
        if (Build.VERSION.SDK_INT >= 23) {
            //
            if (isAllGranted(permissions)) {
                if (isMIUI()) {
                    if (!initMiuiPermission()) {
                        openMiuiAppDetails();
                        return;
                    }
                }
                allGrantedToDo();
                return;
            } else {
                requestPermissions(permissions);
            }
        } else {
            allGrantedToDo();
        }
    }

    /**
     * 检查是否拥有指定的所有权限
     */
    private boolean checkPermissionAllGranted(String[] permissions) {
//        https://my.oschina.net/u/990728/blog/549914
        int targetSdkVersion = getApplicationInfo().targetSdkVersion;
        if (targetSdkVersion >= Build.VERSION_CODES.M) {
            // targetSdkVersion >= Android M, we can
            // use Context#checkSelfPermission
            for (String permission : permissions) {
                if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                    // 只要有一个权限没有被授予, 则直接返回 false
                    return false;
                }
            }
//            result = context.checkSelfPermission(permission)
//                    == PackageManager.PERMISSION_GRANTED;
        } else {
            // targetSdkVersion < Android M, we have to use PermissionChecker
            for (String permission : permissions) {
                if (PermissionChecker.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                    // 只要有一个权限没有被授予, 则直接返回 false
                    return false;
                }
            }
//            result = PermissionChecker.checkSelfPermission(this, permission)
//                    == PermissionChecker.PERMISSION_GRANTED;
        }
        return true;
    }

    protected static final int MY_PERMISSION_REQUEST_CODE = 10000;

    /**
     * 第 2 步: 请求权限
     */
    protected void requestPermissions(String[] permissions) {

        // 一次请求多个权限, 如果其他有权限是已经授予的将会自动忽略掉
        ActivityCompat.requestPermissions(
                this,
                permissions,
                MY_PERMISSION_REQUEST_CODE
        );
    }

    /**
     * 第 3 步: 申请权限结果返回处理
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == MY_PERMISSION_REQUEST_CODE) {
            boolean isAllGranted = true;

            // 判断是否所有的权限都已经授予了
            for (int grant : grantResults) {
                if (grant != PackageManager.PERMISSION_GRANTED) {
                    isAllGranted = false;
                    break;
                }
            }

            if (isAllGranted) {
                // 如果所有的权限都授予了, 跳转到主页
                allGrantedToDo();
//                gotoHomeActivity();
            } else {
                // 弹出对话框告诉用户需要权限的原因, 并引导用户去应用权限管理中手动打开权限按钮
                hasDeclineToDo();
//                openAppDetails();
            }
        }
    }

    /**
     * 打开 APP 的详情设置
     */
    protected void openAppDetails() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.app_name) + "需要访问 \"相机\" 和 \"外部存储器\",请到 \"应用信息 -> 权限\" 中授予！");
        builder.setPositiveButton("手动授权", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                intent.setData(Uri.parse("package:" + getPackageName()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                startActivity(intent);
                openAppDetDialog.hide();
            }
        });
        builder.setCancelable(false);
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                openAppDetDialog.hide();
            }
        });
        openAppDetDialog = builder.create();
        openAppDetDialog.show();

    }

    /**
     * 打开 APP 的详情设置
     */
    protected void openMiuiAppDetails() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(getString(R.string.app_name) + "需要访问 \"相机\" 和 \"外部存储器\",请到 \"应用信息 -> 权限\" 中授予！");
        builder.setPositiveButton("手动授权", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                JumpPermissionManagement.GoToSetting(ScanPermissionActivity.this);
                openMiuiAppDetDialog.hide();
            }
        });
        builder.setCancelable(false);
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                openMiuiAppDetDialog.hide();
            }
        });
        openMiuiAppDetDialog = builder.create();
        openMiuiAppDetDialog.show();
    }


    /**
     * 检测权限
     *
     * @return true 所需权限全部授取  false 存在未授权的权限
     */
    public boolean isAllGranted(String[] permissions) {
        /**
         * 第 1 步: 检查是否有相应的权限
         */
        boolean isAllGranted = checkPermissionAllGranted(
                permissions
        );

        return isAllGranted;
    }

    /**
     * 检查手机是否是miui系统
     *
     * @return
     */

    public boolean isMIUI() {
        String device = Build.MANUFACTURER;
        System.out.println("Build.MANUFACTURER = " + device);
        if (device.equals("Xiaomi")) {
            System.out.println("this is a xiaomi device");
            Properties prop = new Properties();
            try {
                prop.load(new FileInputStream(new File(Environment.getRootDirectory(), "build.prop")));
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
            return prop.getProperty(KEY_MIUI_VERSION_CODE, null) != null
                    || prop.getProperty(KEY_MIUI_VERSION_NAME, null) != null
                    || prop.getProperty(KEY_MIUI_INTERNAL_STORAGE, null) != null;
        } else {
            return false;
        }
    }

    /**
     * 判断小米MIUI系统中授权管理中对应的权限授取
     *
     * @return false 存在核心的未收取的权限   true 核心权限已经全部授权
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public boolean initMiuiPermission() {
        AppOpsManager appOpsManager = (AppOpsManager) getSystemService(Context.APP_OPS_SERVICE);
        int locationOp = appOpsManager.checkOp(AppOpsManager.OPSTR_FINE_LOCATION, Binder.getCallingUid(), getPackageName());
        if (locationOp == AppOpsManager.MODE_IGNORED) {
            return false;
        }

        int cameraOp = appOpsManager.checkOp(AppOpsManager.OPSTR_CAMERA, Binder.getCallingUid(), getPackageName());
        if (cameraOp == AppOpsManager.MODE_IGNORED) {
            return false;
        }

        int phoneStateOp = appOpsManager.checkOp(AppOpsManager.OPSTR_READ_PHONE_STATE, Binder.getCallingUid(), getPackageName());
        if (phoneStateOp == AppOpsManager.MODE_IGNORED) {
            return false;
        }

        int readSDOp = appOpsManager.checkOp(AppOpsManager.OPSTR_READ_EXTERNAL_STORAGE, Binder.getCallingUid(), getPackageName());
        if (readSDOp == AppOpsManager.MODE_IGNORED) {
            return false;
        }

        int writeSDOp = appOpsManager.checkOp(AppOpsManager.OPSTR_WRITE_EXTERNAL_STORAGE, Binder.getCallingUid(), getPackageName());
        if (writeSDOp == AppOpsManager.MODE_IGNORED) {
            return false;
        }
        return true;
    }

    abstract void allGrantedToDo();

    abstract void hasDeclineToDo();
}
