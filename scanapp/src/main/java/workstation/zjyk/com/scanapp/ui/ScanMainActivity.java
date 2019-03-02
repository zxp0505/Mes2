package workstation.zjyk.com.scanapp.ui;

import android.Manifest;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.ethank.mylibrary.resourcelibrary.intent.StartIntentUtils;
import cn.com.ethank.mylibrary.resourcelibrary.toast.ToastUtil;
import io.reactivex.functions.Consumer;
import workstation.zjyk.com.scanapp.R;
import workstation.zjyk.com.scanapp.modle.bean.QualityHandleDetailVO;
import workstation.zjyk.com.scanapp.modle.bean.ScanTrayInfoVo;
import workstation.zjyk.com.scanapp.ui.present.ScanMainPresent;
import workstation.zjyk.com.scanapp.ui.views.ScanMainView;

/**
 * Created by zjgz on 2018/1/19.
 */

public class ScanMainActivity extends ScanPermissionActivity<ScanMainPresent> implements ScanMainView {
    @BindView(R.id.tv_scan_result)
    TextView tvScanResult;
    @BindView(R.id.tv_start_scan)
    TextView tvStartScan;
    @BindView(R.id.edit_number)
    EditText editNumber;
    @BindView(R.id.ll_root)
    LinearLayout llRoot;
    @BindView(R.id.tv_send)
    TextView tvSend;
    String[] permissions = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initPermission(permissions);
    }


    @Override
    protected void onRestart() {
        super.onRestart();
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
    protected void creatPresent() {
        currentPresent = new ScanMainPresent();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onStart() {
        super.onStart();
//        requestPermiss();
    }

    @Override
    protected View getLoadingTargetView() {
        return llRoot;
    }

    private void requestPermiss() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean granted) throws Exception {
                        if (granted) {
                            //
                            ToastUtil.showInfoCenterShort("权限同意");
                        } else {
                            ToastUtil.showCenterShort("扫描二维码需要打开相机和散光灯的权限");
                        }
                    }
                });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private boolean checkPerssion() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {//&& ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
            AppOpsManager appOpsManager = (AppOpsManager) getSystemService(Context.APP_OPS_SERVICE);
            int locationOp = appOpsManager.checkOp(AppOpsManager.OPSTR_CAMERA, Binder.getCallingUid(), getPackageName());
            if (locationOp == AppOpsManager.MODE_IGNORED) {
                return false;
            }
            ToastUtil.showInfoCenterShort("权限已有");
            return true;
        }
        ToastUtil.showInfoCenterShort("权限没有");
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @OnClick({R.id.tv_start_scan, R.id.tv_send, R.id.tv_start_scan2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_start_scan:
                StartIntentUtils.startIntentUtilsFromResult(ScanMainActivity.this, ScanCodeActivity.class, 0);
                break;
            case R.id.tv_start_scan2:
                StartIntentUtils.startIntentUtilsFromResult(ScanMainActivity.this, ScanCode2Activity.class, 0);
                break;
            case R.id.tv_send:
//                String editstr = editNumber.getText().toString();
//                if (!TextUtils.isEmpty(editstr)) {
//                    requestByScancode(editstr.trim());
//                } else {
//                    ToastUtil.showCenterShort("条码不能为空!");
//                }
                if (!checkPerssion()) {
                    requestPermiss();
                }

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 0:
                if (data != null) {
                    boolean hasResult = data.getBooleanExtra("hasResult", false);
                    if (hasResult) {
                        String result = data.getStringExtra("scanResult");
                        tvScanResult.setText("扫描结果: " + result);
                        requestByScancode(result);
                    }
                }
                break;
        }
    }


    private void requestByScancode(String scanCode) {
        Map<String, String> params = new HashMap<>();
        params.put("code", scanCode);
        currentPresent.requestByScancode(params);
    }


    @Override
    public void scanResult(ScanTrayInfoVo trayInfoVo) {

    }

    @Override
    public void scanResult(String message, Throwable throwable) {

    }

    @Override
    public void upLoadResult(boolean result) {

    }

    @Override
    public void commitExceptResult(boolean result) {

    }

    @Override
    public void refuseResult(boolean result) {

    }

    @Override
    public void showDetail(QualityHandleDetailVO qualityHandleDetailVO) {

    }

    @Override
    public void showDownPicResult(String requestUrl, String picPath) {

    }


    @Override
    void allGrantedToDo() {
        ToastUtil.showInfoCenterShort("allGrantedToDo");
    }

    @Override
    void hasDeclineToDo() {
        ToastUtil.showInfoCenterShort("hasDeclineToDo");
        openAppDetails();
    }
}
