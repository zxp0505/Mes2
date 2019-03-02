package workstation.zjyk.com.scanapp.ui.views;


import com.trello.rxlifecycle2.LifecycleTransformer;

import workstation.zjyk.com.scanapp.modle.bean.ScanErrorBean;
import workstation.zjyk.com.scanapp.modle.net.ScanErrorResultClickListner;

/**
 * Created by ping on 2017/2/21.
 */

public interface ScanBaseView extends MvpView {

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


    void showResultError(int code, String message, ScanErrorResultClickListner errorResultClickListner);

    void showResultError(ScanErrorBean errorBean, ScanErrorResultClickListner errorResultClickListner);

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
