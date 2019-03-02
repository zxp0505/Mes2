package workstation.zjyk.line.ui.views;

import com.trello.rxlifecycle2.LifecycleTransformer;

import workstation.zjyk.line.modle.net.ErrorResultClickListner;

/**
 * Created by ping on 2017/2/21.
 */

public interface BaseView<T> extends MvpView {

    /**
     * @param msg
     */
    void showLoading(String msg);

    /**
     * hide loading
     */
    void hideLoading();

    void showLoadingDialog(String message);

    void hideLoadingDialog();

    @Deprecated
    void showResultError(String message);

    void showResultError(int code, String message, ErrorResultClickListner errorResultClickListner);

    /**
     * show error message
     */
    void showError(String msg);

    /**
     * show exception message
     */
    void showException(String msg);

    /**
     * show net error
     */
    void showNetError();

    void hideNetError();

    void showEmpty(String msg);

    void hideEmpty();

    boolean getNetData();

    boolean getEmptyData();

    void showNetData(T t);

    /**
     * 绑定生命周期
     */
//    <T> LifecycleTransformer<T> bindToLife();
}
