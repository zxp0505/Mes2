package com.zjyk.quality.ui.views;


import com.zjyk.quality.modle.bean.QAPerson;

/**
 * Created by hanxue on 17/6/21.
 */

public interface QAILoginView extends QABaseView {

    void loginSuccess(QAPerson userBean);

    void loginError(String message);

    void quitResult(QAPerson person);

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

    void uploadResult(QAPerson person);
}
