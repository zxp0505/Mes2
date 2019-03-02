package com.zjyk.quality.ui.views;


import com.trello.rxlifecycle2.LifecycleTransformer;
import com.zjyk.quality.modle.net.QAErrorResultClickListner;

/**
 * Created by ping on 2017/2/21.
 */

public interface QABaseView extends MvpView {

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

    void showResultError(int code, String message, QAErrorResultClickListner errorResultClickListner);

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

//    void showNetData(T t);
//    void createPresent(T t);


    /**
     * 绑定生命周期
     */
    <T> LifecycleTransformer<T> bindToLife();
}
