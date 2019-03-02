package com.zjyk.repair.ui.activity;

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

import com.zjyk.repair.R;
import com.zjyk.repair.modle.bean.RPErrorBean;
import com.zjyk.repair.modle.net.RPErrorCode;
import com.zjyk.repair.modle.net.RPErrorResultClickListner;
import com.zjyk.repair.ui.RxLifeActivity;
import com.zjyk.repair.ui.application.RPBaseApplication;
import com.zjyk.repair.ui.loading.VaryViewHelperController;
import com.zjyk.repair.ui.manager.RPUserManager;
import com.zjyk.repair.ui.present.RPRxPresent;
import com.zjyk.repair.util.RPConstants;
import com.zjyk.repair.util.dialog.RPDialogUtils;
import com.zjyk.repair.util.dialog.RPProgressHUD;
import com.zjyk.repair.util.dialog.RPResultBaseDialog;
import com.zjyk.repair.util.dialog.callback.RPDialogCallBackTwo;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.ButterKnife;
import cn.com.ethank.mylibrary.resourcelibrary.common_util.AppManager;
import cn.com.ethank.mylibrary.resourcelibrary.common_util.log.LoggerUtil;
import cn.com.ethank.mylibrary.resourcelibrary.common_util.scan.ScanGun;
import cn.com.ethank.mylibrary.resourcelibrary.common_util.scan.ScanGunKeyEventHelper;
import cn.com.ethank.mylibrary.resourcelibrary.keyboard.KeyboardUtils;
import cn.com.ethank.mylibrary.resourcelibrary.server.MessageEventBean;
import cn.com.ethank.mylibrary.resourcelibrary.server.MyServer;
import cn.com.ethank.mylibrary.resourcelibrary.toast.ToastUtil;
import cn.com.ethank.mylibrary.resourcelibrary.utils.notify.NotifyBean;
import cn.com.ethank.mylibrary.resourcelibrary.utils.notify.NotifyUtil;

/**
 * Created by ping on 2017/2/17.
 */

public abstract class RPBaseActivity<P extends RPRxPresent> extends RxLifeActivity implements View.OnClickListener {

    public FrameLayout mFlContent;

    private VaryViewHelperController mVaryViewHelperController;
    //记录每个界面的数据展示情况
    protected boolean dataNetError = false;
    protected boolean dataEmpty = false;
    private ScanGun mScanGun;
    private RPProgressHUD mProgressHUD;
    private static final String TAG = "CMBaseActivity";
    private ScanGunKeyEventHelper mScanGunKeyEventHelper;

    protected P currentPresent;

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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBase(MessageEventBean messageEventBean) {
        switch (messageEventBean.getType()) {
            case 0:

                //在公共页面
                int refreshType = messageEventBean.getRefreshType();
                switch (refreshType) {
                    case -1:
                        //隐藏输入法
                        break;
                    case MyServer.ACTION_QUIT_APP:
                        //退出apk
                        showInterceptToquitDialog(messageEventBean);
                        break;
                    case MyServer.ACTION_LOGIN_CHANG:
                        //异地登录
                        notifyLoginChange(messageEventBean);
                        break;
                }
                break;
            default:
                onEvent(messageEventBean);
                break;
        }

    }

    private void notifyLoginChange(MessageEventBean messageEventBean) {
        NotifyBean notifyBean = new NotifyBean();
        notifyBean.setMessage(messageEventBean.getMessage());
        notifyBean.setTitle("下线通知");
        NotifyUtil.getInstance().setContext(this).sendMessage(notifyBean);
    }


    //收到推送 弹框 退出
    private void showInterceptToquitDialog(MessageEventBean messageEventBean) {
        RPDialogUtils.showInterceptQuitDialog(this, "", messageEventBean.getMessage(), new RPDialogCallBackTwo() {
            @Override
            public void OnPositiveClick(Object obj) {
                RPBaseApplication.getInstance().exit();
            }

            @Override
            public void OnNegativeClick() {

            }
        });
    }

    protected void onEvent(MessageEventBean messageEventBean) {

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

    protected RPRxPresent getCurrentPresent() {
        return currentPresent;
    }

    private void initScanGunXin() {
        mScanGunKeyEventHelper = new ScanGunKeyEventHelper(new ScanGunKeyEventHelper.OnScanSuccessListener() {
            @Override
            public void onScanSuccess(String barcode) {
                Log.e(TAG, "barcode:" + barcode);
                if (!TextUtils.isEmpty(barcode)) {
                    if (RPBaseActivity.this instanceof RPActivityLogin) {

                    } else {
                        //检查登陆信息
                        if (!RPConstants.isLogin) {
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
            mProgressHUD = RPProgressHUD.show(this, message);
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
                    if (RPBaseActivity.this instanceof RPActivityLogin) {

                    } else {
                        //检查登陆信息
                        if (!RPConstants.isLogin) {
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
//        RPDialogUtils.showResultErrorDialog(this, -1, message, null);
    }

//    @Override
//    public void showResultError(int code, String message, RPErrorResultClickListner errorResultClickListner) {

//        switch (code) {
//            case RPErrorCode.CODE_NET_EXCEPTION:
//                RPDialogUtils.showResultErrorDialog(this, code, message, errorResultClickListner);
//                break;
//            case RPErrorCode.CODE_NET_SOCKET_TIME_OUT:
//                RPDialogUtils.showResultErrorDialog(this, code, message, errorResultClickListner);
//                break;
//            case RPErrorCode.CODE_HTTP_EXCEPTION:
//                RPDialogUtils.showResultErrorDialog(this, code, message, errorResultClickListner);
//                break;
//            case RPErrorCode.CODE_UNKNOWN_EXCEPTION:
//                RPDialogUtils.showResultErrorDialog(this, code, message, errorResultClickListner);
//                break;
//            case RPErrorCode.CODE_DATA_NULL:
//                ToastUtil.showCenterShort(message, true);
//                break;
//            case RPErrorCode.CODE_RESULT_ERROR:
//                RPDialogUtils.showResultErrorDialog(this, code, message, errorResultClickListner);
//                break;
//            default:
//                ToastUtil.showCenterShort(message, true);
//                break;
//        }
//    }

    @Override
    public void showResultError(RPErrorBean errorBean, RPErrorResultClickListner errorResultClickListner) {
        int errorCode = errorBean.getErrorCode();
        String dataMessage = errorBean.getDataMessage();
        Throwable throwable = errorBean.getThrowable();
        String errorMessage = errorBean.getErrorMessage();
        if (!TextUtils.isEmpty(errorMessage) && errorMessage.equals(RPErrorCode.STRING_UNKNOWN_EXCEPTION)) {
            //未知异常
            if (throwable != null) {
                errorMessage = throwable.getMessage();
            }
        }
        boolean isShowTwoTips = false;
        if (!TextUtils.isEmpty(dataMessage)) {
            isShowTwoTips = true;
        }
        switch (errorCode) {
            case RPErrorCode.CODE_NET_EXCEPTION:
            case RPErrorCode.CODE_NET_SOCKET_TIME_OUT:
            case RPErrorCode.CODE_HTTP_EXCEPTION:
            case RPErrorCode.CODE_UNKNOWN_EXCEPTION:
            case RPErrorCode.CODE_HTTP_NOTFOUND:
            case RPErrorCode.CODE_RESULT_ERROR:
                showResultErrorDialog(errorCode, errorMessage, isShowTwoTips, errorResultClickListner);
                break;
            case RPErrorCode.CODE_DATA_NULL:
                ToastUtil.showCenterShort(errorMessage, true);
                break;

            default:
                ToastUtil.showCenterShort(errorMessage, true);
                break;
        }
    }

    @Override
    public void showResultError(int code, String message, RPErrorResultClickListner errorResultClickListner) {
        switch (code) {
            case RPErrorCode.CODE_NET_EXCEPTION:
//                RPDialogUtils.showResultErrorDialog(this, code, message, errorResultClickListner);
//                break;
            case RPErrorCode.CODE_NET_SOCKET_TIME_OUT:
//                RPDialogUtils.showResultErrorDialog(this, code, message, errorResultClickListner);
//                break;
            case RPErrorCode.CODE_HTTP_EXCEPTION:
//                RPDialogUtils.showResultErrorDialog(this, code, message, errorResultClickListner);
//                break;
            case RPErrorCode.CODE_UNKNOWN_EXCEPTION:
//                RPDialogUtils.showResultErrorDialog(this, code, message, errorResultClickListner);
//                break;
            case RPErrorCode.CODE_HTTP_NOTFOUND:

            case RPErrorCode.CODE_RESULT_ERROR:
                showResultErrorDialog(code, message, false, errorResultClickListner);
                break;
            case RPErrorCode.CODE_DATA_NULL:
                ToastUtil.showCenterShort(message, true);
                break;

            default:
                ToastUtil.showCenterShort(message, true);
                break;
        }
    }


    private void showResultErrorDialog(int code, String message, boolean isTwoTips, RPErrorResultClickListner errorResultClickListner) {
        List<RPResultBaseDialog> dialogs = RPUserManager.getInstance().getDialogs();
        for (int i = 0; i < dialogs.size(); i++) {
            RPResultBaseDialog rpResultBaseDialog = dialogs.get(i);
            if (rpResultBaseDialog != null && rpResultBaseDialog.getCode() == code) {
                //code一致 message一致 不新增dialog
                if (!TextUtils.isEmpty(message) && message.equals(rpResultBaseDialog.getTitle())) {
                    return;
                }
            }
        }
        RPUserManager.getInstance().addDialog(RPDialogUtils.showResultErrorDialog(this, code, message,
                isTwoTips, errorResultClickListner));
    }

}
