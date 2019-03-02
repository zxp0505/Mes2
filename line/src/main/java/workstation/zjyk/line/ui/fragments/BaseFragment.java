package workstation.zjyk.line.ui.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import workstation.zjyk.line.R;
import workstation.zjyk.line.modle.net.ErrorResultClickListner;
import workstation.zjyk.line.ui.activity.MainActivity;
import workstation.zjyk.line.ui.customveiws.loading.VaryViewHelperController;
import workstation.zjyk.line.ui.present.MvpBasePresenter;
import workstation.zjyk.line.ui.views.BaseView;

import me.yokeyword.fragmentation.SupportFragment;


/**
 * Created by ping on 2017/3/8.
 */

public abstract class BaseFragment extends SupportFragment implements BaseView, View.OnClickListener {
    private View contentView;
    private VaryViewHelperController mVaryViewHelperController;

    protected boolean dataNetError = false;
    protected boolean dataEmpty = false;
    Activity mActivity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mActivity = activity;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (contentView == null) {
            contentView = inflater.inflate(getLayoutId(), container, false);
            initView(contentView, savedInstanceState);
        } else {
            ViewGroup parent = (ViewGroup) contentView.getParent();
            if (parent != null) {
                parent.removeView(contentView);
            }
        }
        initStateControl();
        return contentView;
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
    public void onResume() {
        super.onResume();
        if (mActivity != null && mActivity instanceof MainActivity) {
            ((MainActivity) mActivity).setCurrentFragment(this.getClass());
        }
    }

    public void setScanResult(String scanResult) {
    }

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
        }
    }

    protected void clickNetErrorRetry() {

    }

    @Override
    public void onDestroy() {
        if (getPresenter() != null) {
            getPresenter().detachView(false);
        }
        super.onDestroy();
    }

    abstract MvpBasePresenter getPresenter();

    @Override
    public void showNetData(Object o) {
    }

    @Override
    public void showLoadingDialog(String message) {
        if (mActivity != null && mActivity instanceof MainActivity) {
            ((MainActivity) mActivity).showLoadingDialog(message);
        }
    }

    @Override
    public void hideLoadingDialog() {
        if (mActivity != null && mActivity instanceof MainActivity) {
            ((MainActivity) mActivity).hideLoadingDialog();
        }
    }

    @Override
    public void showResultError(String message) {
        if (mActivity != null && mActivity instanceof MainActivity) {
            ((MainActivity) mActivity).showResultError(message);
        }
    }

    @Override
    public void showResultError(int code, String message, ErrorResultClickListner errorResultClickListner) {
        if (mActivity != null && mActivity instanceof MainActivity) {
            ((MainActivity) mActivity).showResultError(code,message,errorResultClickListner);
        }
    }
}
