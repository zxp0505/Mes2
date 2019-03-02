package workstation.zjyk.workstation.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentationHack;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import cn.com.ethank.mylibrary.resourcelibrary.toast.ToastUtil;
import workstation.zjyk.workstation.R;
import workstation.zjyk.workstation.modle.bean.WSErrorBean;
import workstation.zjyk.workstation.modle.net.WSErrorResultClickListner;
import workstation.zjyk.workstation.ui.WSMainActivity;
import workstation.zjyk.workstation.ui.activity.WSBaseActivity;
import workstation.zjyk.workstation.ui.loading.VaryViewHelperController;
import workstation.zjyk.workstation.ui.manager.WSUserManager;
import workstation.zjyk.workstation.ui.present.WSRxPresent;
import workstation.zjyk.workstation.util.dialog.WSProgressHUD;


/**
 * Created by ping on 2017/3/8.
 */

public abstract class WSBaseFragment<P extends WSRxPresent> extends RxLifeFragment implements View.OnClickListener {
    private View contentView;
    private VaryViewHelperController mVaryViewHelperController;

    protected boolean dataNetError = false;
    protected boolean dataEmpty = false;
    Activity mActivity;
    P currentPresent;
    protected static String dataKey = "dataKey";

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mActivity = activity;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        if (contentView == null) {
        contentView = inflater.inflate(getLayoutId(), container, false);
        initView(contentView, savedInstanceState);
//        } else {
//            ViewGroup parent = (ViewGroup) contentView.getParent();
//            if (parent != null) {
//                parent.removeView(contentView);
//            }
//        }
        initPresent();
        setActivityLogoOrBack();
        initStateControl();
        return contentView;
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

    protected WSRxPresent getCurrentPresent() {
        return currentPresent;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }

    private void initStateControl() {
        if (null != getLoadingTargetView()) {
            mVaryViewHelperController = new VaryViewHelperController(getLoadingTargetView());
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            controlDrawLayout();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        controlDrawLayout();
    }

    private void controlDrawLayout() {
        if (this instanceof WSTaskFragment) {
            if (mActivity != null && mActivity instanceof WSMainActivity) {
                ((WSMainActivity) mActivity).setControlDrawLayout(false);
            }
        } else {
            if (mActivity != null && mActivity instanceof WSMainActivity) {
                ((WSMainActivity) mActivity).setControlDrawLayout(true);
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    public void setScanResult(String scanResult) {
    }

    public void setFragmentBack() {
        if (mActivity != null && mActivity instanceof WSMainActivity) {
            List<Fragment> activeFragments = FragmentationHack.getActiveFragments(((WSMainActivity) mActivity).getSupportFragmentManager());
            List<Fragment> stacksFragments = new ArrayList<>();//栈内有的fragment为空
            if (activeFragments != null) {
                for (int i = 0; i < activeFragments.size(); i++) {
                    Fragment activiFragment = activeFragments.get(i);
                    if (activiFragment != null) {
                        stacksFragments.add(activiFragment);
                    }
                }
            }
            if (stacksFragments != null && activeFragments.size() >= 2) {
                WSBaseFragment fragment = (WSBaseFragment) stacksFragments.get(stacksFragments.size() - 2);
                if (fragment != null) {
                    popTo(fragment.getClass(), false, new Runnable() {
                        @Override
                        public void run() {
                            fragment.setActivityLogoOrBack();
                        }
                    }, R.anim.v_fragment_exit);
                }
            }
        }
    }

    public abstract void setActivityLogoOrBack();

    protected abstract void initView(View view, Bundle savedInstanceState);

    /**
     * 切换到页面需要重新加载数据的实现此方法
     */
    public abstract void initData();

    //获取布局文件ID
    protected abstract int getLayoutId();

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
        toggleShowLoading(true, getString(R.string.text_loading));
    }

    @Override
    public void hideLoading() {
        toggleShowLoading(false, null);
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
        toggleShowEmpty(false, "暂无意见反馈", null);
        dataEmpty = false;
    }

    @Override
    public boolean getNetData() {
        return dataNetError;
    }

    @Override
    public boolean getEmptyData() {
        return dataEmpty;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_net_error:
                clickNetErrorRetry();
                break;
            case R.id.tv_hand_barcode:
            case R.id.tv_hand_barcode_two:
            case R.id.tv_hand_barcode_three:
            case R.id.tv_hand_barcode_two_two:
                showEntryBarcode();
                break;
            case R.id.iv_back_one:
            case R.id.iv_back_two:
            case R.id.iv_back_three:
            case R.id.iv_back_four:
                if (isSynStatus()) {
                    refreshQuit();
                } else {
                    clickBack();
                }
                break;
        }
    }

    private void refreshQuit() {
        Bundle bundle = new Bundle();
        bundle.putBoolean("isRefresh", true);
        setFragmentResult(0, bundle);
        clickBack();
    }

    protected void clickBack() {
        pop();
    }

    private void showEntryBarcode() {
        if (!checkLogin()) {
            return;
        }
        if (mActivity != null && mActivity instanceof WSBaseActivity) {
            ((WSBaseActivity) mActivity).showEntryBarcode();
        }
    }

    protected void clickNetErrorRetry() {

    }

    boolean synStatus = false;//同步状态设置

    public boolean isSynStatus() {
        return synStatus;
    }

    public void setSynStatus(boolean synStatus, int refreshType) {
        this.synStatus = synStatus;
        if (synStatus) {
            synCurrentFragment(refreshType);
        }
    }

    protected void synCurrentFragment(int refreshType) {

    }

    @Override
    public void onDestroy() {
        if (getCurrentPresent() != null) {
            getCurrentPresent().detachView(false);
        }
        loadingCount = -10;
        hideLoadingDialog();
        super.onDestroy();
    }

    WSProgressHUD mProgressHUD;
    int loadingCount = 0;

    @Override
    public void showLoadingDialog(String message) {
        if (mActivity != null && mActivity instanceof WSBaseActivity) {
            ((WSBaseActivity) mActivity).showLoadingDialog(message);
        }
//        loadingCount++;
//        if (mProgressHUD != null && mProgressHUD.isShowing()) {
//            return;
//        }
//
//        if (mProgressHUD == null) {
//            mProgressHUD = WSProgressHUD.show(mActivity, message);
//        } else {
//            mProgressHUD.show();
//        }
    }

    @Override
    public void hideLoadingDialog() {
        if (mActivity != null && mActivity instanceof WSBaseActivity) {
            ((WSBaseActivity) mActivity).hideLoadingDialog();
        }
//        if (loadingCount != -10) {
//            loadingCount--;
//            if (loadingCount < 0) {
//                loadingCount = 0;
//            }
//            if (loadingCount == 0) {
//                if (mProgressHUD != null && mProgressHUD.isShowing()) {
//                    mProgressHUD.dismiss();
//                }
//            }
//        } else {
//            if (mProgressHUD != null && mProgressHUD.isShowing()) {
//                mProgressHUD.dismiss();
//            }
//        }
    }

    @Override
    public void showResultError(String message) {
        if (mActivity != null && mActivity instanceof WSBaseActivity) {
            ((WSBaseActivity) mActivity).showResultError(message);
        }
    }

    @Override
    public void showResultError(int code, String message, WSErrorResultClickListner errorResultClickListner) {
        if (mActivity != null && mActivity instanceof WSBaseActivity) {
            ((WSBaseActivity) mActivity).showResultError(code, message, errorResultClickListner);
        }
    }

    @Override
    public void showResultError(WSErrorBean errorBean, WSErrorResultClickListner errorResultClickListner) {
        if (mActivity != null && mActivity instanceof WSBaseActivity) {
            ((WSBaseActivity) mActivity).showResultError(errorBean, errorResultClickListner);
        }
    }

    protected boolean checkLogin() {
        if (WSUserManager.getInstance().getCurrentPerson() != null) {
            return true;
        }
        ToastUtil.showCenterShort(getString(R.string.text_un_login), true);
        return false;
    }

}
