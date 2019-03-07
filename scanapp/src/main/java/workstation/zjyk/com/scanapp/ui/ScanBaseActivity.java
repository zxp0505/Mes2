package workstation.zjyk.com.scanapp.ui;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.com.ethank.mylibrary.resourcelibrary.common_util.AppManager;
import cn.com.ethank.mylibrary.resourcelibrary.common_util.scan.ScanGun;
import cn.com.ethank.mylibrary.resourcelibrary.intent.StartIntentUtils;
import cn.com.ethank.mylibrary.resourcelibrary.server.MessageEventBean;
import cn.com.ethank.mylibrary.resourcelibrary.toast.ToastUtil;
import workstation.zjyk.com.scanapp.R;
import workstation.zjyk.com.scanapp.modle.bean.ScanErrorBean;
import workstation.zjyk.com.scanapp.modle.net.ScanErrorCode;
import workstation.zjyk.com.scanapp.modle.net.ScanErrorResultClickListner;
import workstation.zjyk.com.scanapp.ui.loading.VaryViewHelperController;
import workstation.zjyk.com.scanapp.ui.present.ScanRxPresent;
import workstation.zjyk.com.scanapp.util.BundleParams;
import workstation.zjyk.com.scanapp.util.ScanConstants;
import workstation.zjyk.com.scanapp.util.dialog.ScanDialogUtils;
import workstation.zjyk.com.scanapp.util.dialog.ScanProgressHUD;

/**
 * Created by ping on 2017/2/17.
 */

public abstract class ScanBaseActivity<P extends ScanRxPresent> extends RxLifeActivity implements View.OnClickListener {

    public FrameLayout mFlContent;
    ImageView ivBack;
    TextView tvTitle;
    ImageView ivScan;
    protected int flag;//1 记录 - 扫描 - 详情
    private VaryViewHelperController mVaryViewHelperController;
    //记录每个界面的数据展示情况
    protected boolean dataNetError = false;
    protected boolean dataEmpty = false;
    private ScanGun mScanGun;
    private ScanProgressHUD mProgressHUD;
    private static final String TAG = "CMBaseActivity";
//    private ScanGunKeyEventHelper mScanGunKeyEventHelper;

    protected P currentPresent;
    protected View mRlTitle;
    protected Button mBtRightOne;
    protected Button mBtRightTwo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!(this instanceof ScanSplashActivity)) {
            super.setContentView(R.layout.activity_base);
            initPresent();
            initBaseView();
            initOnCreate();
            initStateControl();
        }
        EventBus.getDefault().register(this);
        AppManager.getAppManager().addActivity(this);


    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBase(MessageEventBean messageEventBean) {
        switch (messageEventBean.getType()) {
            case 110:
                if (ScanConstants.isWarnLogin()) {
                    onEvent(messageEventBean);
                }
                break;
            case 111:
                if (!ScanConstants.isWarnLogin()) {
                    onEvent(messageEventBean);
                }
                break;
            default:
                onEvent(messageEventBean);
                break;
        }

    }

    //收到推送 弹框 退出
    private void showInterceptToquitDialog(MessageEventBean messageEventBean) {
//        WSDialogUtils.showInterceptQuitDialog(this, "", messageEventBean.getMessage(), new WSDialogCallBackTwo() {
//            @Override
//            public void OnPositiveClick(Object obj) {
//                ScanBaseApplication.getInstance().exit();
//            }
//
//            @Override
//            public void OnNegativeClick() {
//
//            }
//        });
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

    protected ScanRxPresent getCurrentPresent() {
        return currentPresent;
    }


    //获取布局文件ID
    protected abstract int getLayoutId();

    private void initBaseView() {
        mRlTitle = findViewById(R.id.rl_title);
        mFlContent = findViewById(R.id.fl_content);
        ivBack = findViewById(R.id.iv_back);
        tvTitle = findViewById(R.id.tv_title);
        ivScan = findViewById(R.id.iv_scan);
        mBtRightOne = findViewById(R.id.bt_right_one);
        mBtRightTwo = findViewById(R.id.bt_right_two);
        View view_content = LayoutInflater.from(this).inflate(getLayoutId(), null);
        ButterKnife.bind(this, view_content);
        mFlContent.addView(view_content);
        ivBack.setOnClickListener(this);
        ivScan.setOnClickListener(this);
    }

    public void initOnCreate() {

    }

    ;

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

    int loadingCount = 0;

    @Override
    public void showLoadingDialog(String message) {
        loadingCount++;
        if (mProgressHUD != null && mProgressHUD.isShowing()) {
            return;
        }

        if (mProgressHUD == null) {
            mProgressHUD = ScanProgressHUD.show(this, message);
        } else {
            mProgressHUD.show();
        }
    }

    @Override
    public void hideLoadingDialog() {
        if (loadingCount != -10) {
            loadingCount--;
            if (loadingCount < 0) {
                loadingCount = 0;
            }
            if (loadingCount == 0) {
                if (mProgressHUD != null && mProgressHUD.isShowing()) {
                    mProgressHUD.dismiss();
                }
            }
        } else {
            if (mProgressHUD != null && mProgressHUD.isShowing()) {
                mProgressHUD.dismiss();
            }
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
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        loadingCount = -10;
        hideLoadingDialog();
        AppManager.getAppManager().removeActivityFromStack(this);
        if (getCurrentPresent() != null) {
            getCurrentPresent().detachView(false);
        }
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    //隐藏输入法

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_net_error:
                clickNetErrorRetry();
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_scan:
                toScanActivity();
                break;
        }
    }

    protected void toScanActivity() {
        Bundle bundle = new Bundle();
        bundle.putInt(BundleParams.FLAG, flag);
        StartIntentUtils.startIntentUtils(this, ScanCode2Activity.class, bundle);
        finish();
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
//        InputDevice.getDeviceIds();
        // 按下键盘上返回按钮
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            return true;
//        }
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
    public void showResultError(ScanErrorBean errorBean, ScanErrorResultClickListner errorResultClickListner) {
        int errorCode = errorBean.getErrorCode();
        String dataMessage = errorBean.getDataMessage();
        Throwable throwable = errorBean.getThrowable();
        String errorMessage = errorBean.getErrorMessage();
        if (!TextUtils.isEmpty(errorMessage) && errorMessage.equals(ScanErrorCode.STRING_UNKNOWN_EXCEPTION)) {
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
            case ScanErrorCode.CODE_NET_EXCEPTION:
            case ScanErrorCode.CODE_NET_SOCKET_TIME_OUT:
            case ScanErrorCode.CODE_HTTP_EXCEPTION:
            case ScanErrorCode.CODE_UNKNOWN_EXCEPTION:
//            case ScanErrorCode.CODE_HTTP_NOTFOUND:
            case ScanErrorCode.CODE_RESULT_ERROR:
                showResultErrorDialog(errorCode, errorMessage, isShowTwoTips, errorResultClickListner);
                break;
            case ScanErrorCode.CODE_DATA_NULL:
                ToastUtil.showCenterShort(errorMessage, true);
                break;

            default:
                ToastUtil.showCenterShort(errorMessage, true);
                break;
        }
    }

    private void showResultErrorDialog(int code, String message, boolean isTwoTips, ScanErrorResultClickListner errorResultClickListner) {
//        List<WSResultBaseDialog> dialogs = ScanUserManager.getInstance().getDialogs();
//        for (int i = 0; i < dialogs.size(); i++) {
//            WSResultBaseDialog wsResultBaseDialog = dialogs.get(i);
//            if (wsResultBaseDialog != null && wsResultBaseDialog.getCode() == code) {
//                //code一致 message一致 不新增dialog
//                if (!TextUtils.isEmpty(message) && message.equals(wsResultBaseDialog.getTitle())) {
//                    return;
//                }
//            }
//        }
//        WSUserManager.getInstance().addDialog(ScanDialogUtils.showResultErrorDialog(this, code, message,
//                isTwoTips, errorResultClickListner));

        ScanDialogUtils.showResultErrorDialog(this, code, message,
                isTwoTips, errorResultClickListner);
    }

    @Override
    public void showResultError(int code, String message, ScanErrorResultClickListner errorResultClickListner) {
//        ToastUtil.showCenterShort(message, true);
        switch (code) {
            case ScanErrorCode.CODE_NET_EXCEPTION:
//                WSDialogUtils.showResultErrorDialog(this, code, message, errorResultClickListner);
                break;
            case ScanErrorCode.CODE_NET_SOCKET_TIME_OUT:
//                WSDialogUtils.showResultErrorDialog(this, code, message, errorResultClickListner);
                break;
            case ScanErrorCode.CODE_HTTP_EXCEPTION:
//                WSDialogUtils.showResultErrorDialog(this, code, message, errorResultClickListner);
                break;
            case ScanErrorCode.CODE_UNKNOWN_EXCEPTION:
//                WSDialogUtils.showResultErrorDialog(this, code, message, errorResultClickListner);
                break;
            case ScanErrorCode.CODE_DATA_NULL:
//                ToastUtil.showCenterShort(message, true);
                break;
            case ScanErrorCode.CODE_RESULT_ERROR:
//                WSDialogUtils.showResultErrorDialog(this, code, message, errorResultClickListner);
                break;
            default:
//                ToastUtil.showCenterShort(message, true);
                break;


        }
    }


    @Override
    public Resources getResources() {
        //避免设置字体大小
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }
}
