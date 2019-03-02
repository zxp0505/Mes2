package workstation.zjyk.line.ui.present;

import android.graphics.Bitmap;
import android.text.format.DateFormat;

import workstation.zjyk.line.modle.bean.Person;
import workstation.zjyk.line.modle.callback.RxDataCallBack;
import workstation.zjyk.line.modle.request.LoginRequest;
import workstation.zjyk.line.ui.views.BaseView;
import workstation.zjyk.line.ui.views.ILoginView;
import workstation.zjyk.line.util.Constants;
import cn.com.ethank.mylibrary.resourcelibrary.common_util.FileUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by zjgz on 2017/11/7.
 */

public class LoginPresenterImpl extends RxPresent<ILoginView> {

    private final LoginRequest loginRequest;
    private String photoFileName;
    private String photoFilePath;

    public LoginPresenterImpl(ILoginView loginView) {
        attachView(loginView);
        loginRequest = new LoginRequest();
    }

    public void login(String code) {
        Map<String, String> map = new HashMap<>();
        map.put("code", code);
        Subscription login = loginRequest.login(map, getView(), new RxDataCallBack<Person>() {
            @Override
            public void onSucess(Person userBean) {
                ((ILoginView) getView()).loginSuccess(userBean);
            }

            @Override
            public void onFail() {
                ((ILoginView) getView()).loginError("");
            }
        });
        addSubscription(login);
    }

    public String savePhoto(final Person person, final Bitmap b) {
        if (getView() != null) {
            getView().showLoadingDialog("");
        }
        photoFileName = DateFormat.format("yyyyMMdd_HHmmss", System.currentTimeMillis()) + ".jpg";
        photoFilePath = FileUtils.getAppPath(Constants.fileDir) + "/imgpai/" + photoFileName;
        Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                if (!new File(photoFilePath).getParentFile().exists()) {
                    new File(photoFilePath).getParentFile().mkdirs();
                } else {
                    FileUtils.deleteAllFilesNoDir(new File(photoFilePath).getParentFile());
                }
                boolean result = FileUtils.saveImage(b, photoFilePath);
                subscriber.onNext(result);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        if (getView() != null) {
                            ILoginView loginView = (ILoginView) getView();
                            loginView.hideLoadingDialog();
                            loginView.savePhotoError("照片保存失败");
                        }
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        if (aBoolean) {
                            uploadPhoto(person);
                        } else {
                            ILoginView view = (ILoginView) getView();
                            if (view != null) {
                                view.hideLoadingDialog();
                                view.savePhotoError("照片保存失败");
                            }
                        }
                    }
                });
        return photoFilePath;
    }

    private void uploadPhoto(Person person) {
//        if (userBean == null) {
//            return;
//        }
        Map<String, String> parms = new HashMap<>();
        parms.put("code",person.getCode());
        parms.put("personId", person.getPersonId());//usebean.getid()

        loginRequest.upLoadImage(getView(), parms, photoFilePath, photoFileName, new RxDataCallBack<Person>() {
            @Override
            public void onSucess(Person s) {
            if(getView() != null){
                ((ILoginView)getView()).uploadResult(s);
            }
            }

            @Override
            public void onFail() {
                if(getView() != null){
                    ((ILoginView)getView()).uploadResult(null);
                }
            }
        });

    }

    /**
     * 登出
     * @param code
     */
    public void quit(String code){
        Map<String, String> map = new HashMap<>();
        map.put("code", code);
        Subscription login = loginRequest.quit(map, getView(), new RxDataCallBack<Person>() {
            @Override
            public void onSucess(Person userBean) {
                ((ILoginView) getView()).quitResult(userBean);
            }

            @Override
            public void onFail() {
                ((ILoginView) getView()).quitResult(null);
            }
        });
        addSubscription(login);
    }
}
