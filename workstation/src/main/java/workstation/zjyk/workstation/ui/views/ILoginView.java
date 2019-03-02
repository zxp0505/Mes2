package workstation.zjyk.workstation.ui.views;


import workstation.zjyk.workstation.modle.bean.WSPerson;

/**
 * Created by hanxue on 17/6/21.
 */

public interface ILoginView extends WSBaseView {

    void loginSuccess(WSPerson userBean);

    void loginError(String message);

    void quitResult(WSPerson person);

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

    void uploadResult(WSPerson person);
}
