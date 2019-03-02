package com.zjyk.repair.ui.views;


import com.zjyk.repair.modle.bean.RPPerson;

/**
 * Created by hanxue on 17/6/21.
 */

public interface RPILoginView extends RPBaseView {

    void loginSuccess(RPPerson userBean);

    void loginError(String message);

    void quitResult(RPPerson person);

//    void savePhotoSuccess();

//    void savePhotoError(String message);

    /**
     * 检测当前activity是否存在
     */
//    boolean checkActivityIsFinishing();
//
//    void showLoginProgress();
//
//    void hideLoginProgress();
//
//    void showUploadPhotoProgress();
//
//    void hideUploadPhotoProgress();

    void uploadResult(RPPerson person);
}
