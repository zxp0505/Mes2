package workstation.zjyk.line.ui.activity;

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

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import cn.com.ethank.mylibrary.resourcelibrary.common_util.AppManager;
import cn.com.ethank.mylibrary.resourcelibrary.common_util.log.LoggerUtil;
import cn.com.ethank.mylibrary.resourcelibrary.common_util.scan.ScanGun;
import cn.com.ethank.mylibrary.resourcelibrary.common_util.scan.ScanGunKeyEventHelper;
import cn.com.ethank.mylibrary.resourcelibrary.keyboard.KeyboardUtils;
import cn.com.ethank.mylibrary.resourcelibrary.server.MessageEventBean;
import cn.com.ethank.mylibrary.resourcelibrary.server.MyServer;
import cn.com.ethank.mylibrary.resourcelibrary.toast.ToastUtil;
import cn.com.ethank.mylibrary.resourcelibrary.utils.PhoneUtils;
import cn.com.ethank.mylibrary.resourcelibrary.utils.notify.NotifyBean;
import cn.com.ethank.mylibrary.resourcelibrary.utils.notify.NotifyUtil;
import me.yokeyword.fragmentation.SupportActivity;
import workstation.zjyk.line.R;
import workstation.zjyk.line.modle.net.ErrorCode;
import workstation.zjyk.line.modle.net.ErrorResultClickListner;
import workstation.zjyk.line.ui.BaseApplication;
import workstation.zjyk.line.ui.customveiws.loading.VaryViewHelperController;
import workstation.zjyk.line.ui.fragments.FeedFragment;
import workstation.zjyk.line.ui.views.BaseView;
import workstation.zjyk.line.util.Constants;
import workstation.zjyk.line.util.DialogCallBackTwo;
import workstation.zjyk.line.util.dialog.DialogUtils;
import workstation.zjyk.line.util.dialog.ProgressHUD;

/**
 * Created by ping on 2017/2/17.
 */

public abstract class BaseActivity extends SupportActivity implements View.OnClickListener, BaseView {

    //    public ImageView mIv_title_back;
//    public TextView mTvTitle;
    public FrameLayout mFlContent;
//    public ImageView mIvTitleRight;
//    public RelativeLayout mRlTopTitle;
//    protected TextView mTvTitleRight;
//    protected LinearLayout llEmail;
//    protected Button btInbox;
//    protected Button btOutbox;
//    protected TextView mTvCancle;
//    protected View mFakeStatusBar;
//    private TextView mTvStack;

    private VaryViewHelperController mVaryViewHelperController;
    //记录每个界面的数据展示情况
    protected boolean dataNetError = false;
    protected boolean dataEmpty = false;
    private ScanGun mScanGun;
    private ProgressHUD mProgressHUD;
    private static final String TAG = "BaseActivity";
    private ScanGunKeyEventHelper mScanGunKeyEventHelper;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_base);
        AppManager.getAppManager().addActivity(this);
        EventBus.getDefault().register(this);
//        setTitle();
        initBaseView();
//        initScanGun();
        initScanGunXin();
        initStateControl();

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void initScanGunXin() {
        mScanGunKeyEventHelper = new ScanGunKeyEventHelper(new ScanGunKeyEventHelper.OnScanSuccessListener() {
            @Override
            public void onScanSuccess(String barcode) {
                Log.e(TAG, "barcode:" + barcode);
                if (!TextUtils.isEmpty(barcode)) {
                    if (BaseActivity.this instanceof ActivityLogin) {

                    } else {
                        //检查登陆信息
                        if (!Constants.isLogin) {
                            ToastUtil.showCenterShort(getString(R.string.text_un_login), true);
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
            mProgressHUD = ProgressHUD.show(this, message);
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
        EventBus.getDefault().unregister(this);
        AppManager.getAppManager().removeActivityFromStack(this);
        super.onDestroy();
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
//                        hideSofe();
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


    protected void onEvent(MessageEventBean messageEventBean) {

    }

    //收到推送 弹框 退出
    private void showInterceptToquitDialog(MessageEventBean messageEventBean) {
        DialogUtils.showInterceptQuitDialog(this, "", messageEventBean.getMessage(), new DialogCallBackTwo() {
            @Override
            public void OnPositiveClick(Object obj) {
                BaseApplication.getInstance().exit();
            }

            @Override
            public void OnNegativeClick() {

            }
        });
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
                    if (BaseActivity.this instanceof ActivityLogin) {

                    } else {
                        //检查登陆信息
                        if (!Constants.isLogin) {
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
        if (!PhoneUtils.isHuaweiPad() && keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }

        if (PhoneUtils.isHuaweiPad() && keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0 && (this instanceof MainActivity)) {
            exit();
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

    //退出时的时间
    private long mExitTime;

    public void exit() {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            ToastUtil.showInfoCenterShort("再按一次退出");
            mExitTime = System.currentTimeMillis();
        } else {
            BaseApplication.getInstance().exit();
        }
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
//        Log.e(TAG, "dispatchKeyEvent");
        if (PhoneUtils.isHuaweiPad() && KeyEvent.KEYCODE_BACK == event.getKeyCode()) {
            return super.dispatchKeyEvent(event);
        }
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
    public void showNetData(Object o) {

    }

    @Override
    public void showResultError(String message) {
        DialogUtils.showResultErrorDialog(this, -1, message, null);
    }

    @Override
    public void showResultError(int code, String message, ErrorResultClickListner errorResultClickListner) {
        switch (code) {
            case ErrorCode.CODE_NET_EXCEPTION:
                DialogUtils.showResultErrorDialog(this, code, message, errorResultClickListner);
                break;
            case ErrorCode.CODE_NET_SOCKET_TIME_OUT:
                DialogUtils.showResultErrorDialog(this, code, message, errorResultClickListner);
                break;
            case ErrorCode.CODE_HTTP_EXCEPTION:
                DialogUtils.showResultErrorDialog(this, code, message, errorResultClickListner);
                break;
            case ErrorCode.CODE_UNKNOWN_EXCEPTION:
                DialogUtils.showResultErrorDialog(this, code, message, errorResultClickListner);
                break;
            case ErrorCode.CODE_DATA_NULL:
                ToastUtil.showCenterShort(message, true);
                break;
            case ErrorCode.CODE_RESULT_ERROR:
                DialogUtils.showResultErrorDialog(this, code, message, errorResultClickListner);
                break;
            default:
                ToastUtil.showCenterShort(message, true);
                break;
        }
    }


}
