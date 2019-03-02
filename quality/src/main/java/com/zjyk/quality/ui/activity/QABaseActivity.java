package com.zjyk.quality.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.InputDevice;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.zjyk.quality.R;
import com.zjyk.quality.modle.net.QAErrorCode;
import com.zjyk.quality.modle.net.QAErrorResultClickListner;
import com.zjyk.quality.ui.RxLifeActivity;
import com.zjyk.quality.ui.loading.VaryViewHelperController;
import com.zjyk.quality.ui.present.QARxPresent;
import com.zjyk.quality.util.QAConstants;
import com.zjyk.quality.util.dialog.QADialogUtils;
import com.zjyk.quality.util.dialog.QAProgressHUD;

import butterknife.ButterKnife;
import cn.com.ethank.mylibrary.resourcelibrary.common_util.AppManager;
import cn.com.ethank.mylibrary.resourcelibrary.common_util.log.LoggerUtil;
import cn.com.ethank.mylibrary.resourcelibrary.common_util.scan.ScanGun;
import cn.com.ethank.mylibrary.resourcelibrary.common_util.scan.ScanGunKeyEventHelper;
import cn.com.ethank.mylibrary.resourcelibrary.keyboard.KeyboardUtils;
import cn.com.ethank.mylibrary.resourcelibrary.toast.ToastUtil;

/**
 * Created by ping on 2017/2/17.
 */

public abstract class QABaseActivity<P extends QARxPresent> extends RxLifeActivity implements View.OnClickListener {

    public FrameLayout mFlContent;

    private VaryViewHelperController mVaryViewHelperController;
    //记录每个界面的数据展示情况
    protected boolean dataNetError = false;
    protected boolean dataEmpty = false;
    private ScanGun mScanGun;
    private QAProgressHUD mProgressHUD;
    private static final String TAG = "CMBaseActivity";
    private ScanGunKeyEventHelper mScanGunKeyEventHelper;

    protected QARxPresent currentPresent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_base);
        AppManager.getAppManager().addActivity(this);
        initPresent();
        initBaseView();
        initScanGunXin();
        initStateControl();

    }

    protected void initPresent() {
        if (currentPresent == null) {
            creatPresent();
        }
        if (currentPresent != null) {
            currentPresent.attachView(this);
        }
    }

    protected abstract void creatPresent();

    protected QARxPresent getCurrentPresent() {
        return currentPresent;
    }

    private void initScanGunXin() {
        mScanGunKeyEventHelper = new ScanGunKeyEventHelper(new ScanGunKeyEventHelper.OnScanSuccessListener() {
            @Override
            public void onScanSuccess(String barcode) {
                Log.e(TAG, "barcode:" + barcode);
                if (!TextUtils.isEmpty(barcode)) {
                    if (QABaseActivity.this instanceof QAActivityLogin) {

                    } else {
                        //检查登陆信息
                        if (!QAConstants.isLogin) {
                            ToastUtil.showCenterShort("您尚未登陆，请先登陆", true);
                            return;
                        }
                    }
                    onScanResult(barcode);
                }
            }
        });
    }

    //获取布局文件ID
    protected abstract int getLayoutId();

    private void initBaseView() {
        mFlContent = findViewById(R.id.fl_content);
        View view_content = LayoutInflater.from(this).inflate(getLayoutId(), null);
        ButterKnife.bind(this, view_content);
        mFlContent.addView(view_content);
    }


    protected void initStateControl() {
        if (null != getLoadingTargetView()) {
            mVaryViewHelperController = new VaryViewHelperController(getLoadingTargetView());
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onContentChanged() {

        super.onContentChanged();
    }


    public void setTitleName(String title) {
//        mTvTitle.setText(title);
    }

    /**
     * 设置标题
     */
//    public abstract void setTitle();
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
//        Log.e(TAG, "dispatchTouchEvent");
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (KeyboardUtils.isShouldHideKeyboard(v, ev)) {
                KeyboardUtils.hideKeyboardOnly((EditText) this.getCurrentFocus());
            }
        }
        return super.dispatchTouchEvent(ev);
    }


    /**
     * get loading target view
     */
    protected abstract View getLoadingTargetView();

    /**
     * toggle show loading
     */
    protected void toggleShowLoading(boolean toggle, String msg) {
        if (null == mVaryViewHelperController) {
            throw new IllegalArgumentException("You must return a right target view for loading");
        }

        if (toggle) {
            mVaryViewHelperController.showLoading(msg);
        } else {
            mVaryViewHelperController.restore();
        }
    }

    /**
     * toggle show empty
     */
    protected void toggleShowEmpty(boolean toggle, String msg, View.OnClickListener onClickListener) {
        if (null == mVaryViewHelperController) {
            throw new IllegalArgumentException("You must return a right target view for loading");
        }

        if (toggle) {
            mVaryViewHelperController.showEmpty(msg, onClickListener);
        } else {
            mVaryViewHelperController.restore();
        }
    }

    /**
     * toggle show error
     */
    protected void toggleShowError(boolean toggle, String msg, View.OnClickListener onClickListener) {
        if (null == mVaryViewHelperController) {
            throw new IllegalArgumentException("You must return a right target view for loading");
        }

        if (toggle) {
            mVaryViewHelperController.showError(msg, onClickListener);
        } else {
            mVaryViewHelperController.restore();
        }
    }

    /**
     * toggle show network error
     */
    protected void toggleNetworkError(boolean toggle, View.OnClickListener onClickListener) {
        if (null == mVaryViewHelperController) {
            throw new IllegalArgumentException("You must return a right target view for loading");
        }

        if (toggle) {
            mVaryViewHelperController.showNetworkError(onClickListener);
        } else {
            mVaryViewHelperController.restore();
        }
    }

    @Override
    public void showLoading(String msg) {
        toggleShowLoading(true, getString(R.string.loading));
    }

    @Override
    public void hideLoading() {
        toggleShowLoading(false, getString(R.string.loading));
    }

    @Override
    public void showLoadingDialog(String message) {
        if (mProgressHUD != null && mProgressHUD.isShowing()) {
            return;
        }
        if (mProgressHUD == null) {
            mProgressHUD = QAProgressHUD.show(this, message);
        }
    }

    @Override
    public void hideLoadingDialog() {
        if (mProgressHUD != null && mProgressHUD.isShowing()) {
            mProgressHUD.dismiss();
            mProgressHUD = null;
        }

    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public void showException(String msg) {

    }

    @Override
    public void showNetError() {
        toggleNetworkError(true, this);
        dataNetError = true;
    }

    @Override
    public void hideNetError() {
        toggleNetworkError(false, null);
        dataNetError = false;
    }

    @Override
    public void showEmpty(String msg) {
        toggleShowEmpty(true, msg, null);
        dataEmpty = true;
    }

    @Override
    public void hideEmpty() {
        toggleShowEmpty(false, "", null);
        dataEmpty = false;
    }

    @Override
    public boolean getEmptyData() {
        return dataEmpty;
    }

    @Override
    public boolean getNetData() {
        return dataNetError;
    }


    /**
     * 在网络出错的界面点击重试
     */
    protected void clickNetErrorRetry() {

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        hideLoadingDialog();
        AppManager.getAppManager().removeActivityFromStack(this);
        if (getCurrentPresent() != null) {
            getCurrentPresent().detachView(false);
        }
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_net_error:
                clickNetErrorRetry();
                break;
        }
    }

    private void initScanGun() {
        // 设置key事件最大间隔，默认20ms，部分低端扫码枪效率低
        ScanGun.setMaxKeysInterval(50);
        //                            DMG.showNoamlToast("内容=" + scanResult);
        mScanGun = new ScanGun(new ScanGun.ScanGunCallBack() {

            @Override
            public void onScanFinish(String scanResult) {
                LoggerUtil.e(scanResult + "::" + this.getClass());
//                StarLog.d(TAG, "scanResult=" + scanResult);
////                            DMG.showNoamlToast("内容=" + scanResult);
                if (!TextUtils.isEmpty(scanResult)) {
                    if (QABaseActivity.this instanceof QAActivityLogin) {

                    } else {
                        //检查登陆信息
                        if (!QAConstants.isLogin) {
                            ToastUtil.showCenterShort("您尚未登陆，请先登陆", true);
                            return;
                        }
                    }
                    onScanResult(scanResult);
                }
            }
        });
    }

    public void onScanResult(String scanResult) {

    }


    /**
     * 键盘按下时回调
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.e(TAG, "onKeyDown");
        InputDevice.getDeviceIds();
        // 按下键盘上返回按钮
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
//        if (keyCode <= 6) {
//            return super.dispatchKeyEvent(event);
//        }
//        if (mScanGun != null && mScanGun.isMaybeScanning(keyCode, event)) {
//            return true;
//        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
//        Log.e(TAG, "dispatchKeyEvent");
        if (mScanGunKeyEventHelper.isScanGunEvent(event)) {
            mScanGunKeyEventHelper.analysisKeyEvent(event);
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    public void onUserInteraction() {
//        Log.e(TAG, "onUserInteraction");
        super.onUserInteraction();
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
//        Log.e(TAG, "onKeyUp");
        return super.onKeyUp(keyCode, event);
    }


    @Override
    public void showResultError(String message) {
        QADialogUtils.showResultErrorDialog(this, -1, message, null);
    }

    @Override
    public void showResultError(int code, String message, QAErrorResultClickListner errorResultClickListner) {
        switch (code) {
            case QAErrorCode.CODE_NET_EXCEPTION:
                QADialogUtils.showResultErrorDialog(this, code, message, errorResultClickListner);
                break;
            case QAErrorCode.CODE_NET_SOCKET_TIME_OUT:
                QADialogUtils.showResultErrorDialog(this, code, message, errorResultClickListner);
                break;
            case QAErrorCode.CODE_HTTP_EXCEPTION:
                QADialogUtils.showResultErrorDialog(this, code, message, errorResultClickListner);
                break;
            case QAErrorCode.CODE_UNKNOWN_EXCEPTION:
                QADialogUtils.showResultErrorDialog(this, code, message, errorResultClickListner);
                break;
            case QAErrorCode.CODE_DATA_NULL:
                ToastUtil.showCenterShort(message, true);
                break;
            case QAErrorCode.CODE_RESULT_ERROR:
                QADialogUtils.showResultErrorDialog(this, code, message, errorResultClickListner);
                break;
            default:
                ToastUtil.showCenterShort(message, true);
                break;
        }
    }

}
