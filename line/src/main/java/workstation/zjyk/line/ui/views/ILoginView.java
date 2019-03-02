package workstation.zjyk.line.ui.views;


import workstation.zjyk.line.modle.bean.Person;

/**
 * Created by hanxue on 17/6/21.
 */

public interface ILoginView extends BaseView {

    void loginSuccess(Person userBean);

    void loginError(String message);

    void quitResult(Person person);

    void savePhotoSuccess();

    void savePhotoError(String message);

    /**
     * 检测当前activity是否存在
     */
    boolean checkActivityIsFinishing();

    void showLoginProgress();

    void hideLoginProgress();

    void showUploadPhotoProgress();

    void hideUploadPhotoProgress();

    void uploadResult(Person person);
}
