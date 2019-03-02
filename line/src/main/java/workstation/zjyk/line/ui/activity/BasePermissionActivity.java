package workstation.zjyk.line.ui.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;

import workstation.zjyk.line.util.DialogCallBack;
import workstation.zjyk.line.util.DialogUtil;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

/**
 * 主要针对有权限的activity
 * Created by ping on 2017/8/24.
 */

public abstract class BasePermissionActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks {

    //读写storage权限
    protected static String[] PERMISSIONS_CAMERA = {
            Manifest.permission.CAMERA
    };
    //拍照权限
    protected static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    //权限

    /**
     * 查看有无权限
     *
     * @param persimmions
     * @return
     */
    protected boolean hasPersimmions(String[] persimmions) {
        return EasyPermissions.hasPermissions(this, persimmions);
    }

    /**
     * 请求权限
     *
     * @param requestName 请求权限的说明
     * @param requestCode 请求code
     * @param persimmions 权限数组
     */
    protected void requestNewPersimmions(String requestName, int requestCode, String[] persimmions) {
        EasyPermissions.requestPermissions(this, requestName, requestCode, persimmions);
    }

    @TargetApi(23)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    /**
     * 权限同意 注意针对不同组别的权限会多次回掉
     *
     * @param requestCode
     * @param nowperms
     */
    @Override
    public void onPermissionsGranted(int requestCode, List<String> nowperms) {
        permissionGranted(requestCode, nowperms);
    }

    /**
     * 权限拒绝 注意针对不同组别的权限会多次回掉
     *
     * @param requestCode
     * @param perms
     */
    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        permissionsDenied(requestCode, perms);
    }

    public abstract void permissionGranted(int requestCode, List<String> nowperms);

    public abstract void permissionsDenied(int requestCode, List<String> nowperms);

    protected void showDialogToGetPermission(String requestName) {
        DialogUtil.showDialog(this, requestName, new DialogCallBack() {
            @Override
            public void OnPositiveClick() {
                Intent localIntent = new Intent();
                localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if (Build.VERSION.SDK_INT >= 9) {
                    localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                    localIntent.setData(Uri.fromParts("package", getPackageName(), null));
                } else if (Build.VERSION.SDK_INT <= 8) {
                    localIntent.setAction(Intent.ACTION_VIEW);
                    localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
                    localIntent.putExtra("com.android.settings.ApplicationPkgName", getPackageName());
                }
                startActivity(localIntent);
            }

            @Override
            public void OnNegativeClick() {

            }
        });
    }
}
