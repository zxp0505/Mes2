package workstation.zjyk.workstation.ui.views;


import com.trello.rxlifecycle2.LifecycleTransformer;

import workstation.zjyk.workstation.modle.bean.WSErrorBean;
import workstation.zjyk.workstation.modle.net.WSErrorResultClickListner;

/**
 * Created by ping on 2017/2/21.
 */

public interface WSBaseView extends MvpView {

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

    void showResultError(int code, String message, WSErrorResultClickListner errorResultClickListner);

    void showResultError(WSErrorBean errorBean, WSErrorResultClickListner errorResultClickListner);

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
