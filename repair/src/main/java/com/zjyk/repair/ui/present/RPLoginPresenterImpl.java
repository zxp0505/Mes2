package com.zjyk.repair.ui.present;

import android.graphics.Bitmap;
import android.text.format.DateFormat;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import cn.com.ethank.mylibrary.resourcelibrary.common_util.FileUtils;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import com.zjyk.repair.modle.bean.RPPerson;
import com.zjyk.repair.modle.callback.RPRxDataCallBack;
import com.zjyk.repair.modle.request.RPLoginRequest;
import com.zjyk.repair.ui.views.RPILoginView;
import com.zjyk.repair.ui.views.RPBaseView;
import com.zjyk.repair.util.RPConstants;

/**
 * Created by zjgz on 2017/11/7.
 */

public class RPLoginPresenterImpl extends RPRxPresent<RPILoginView> {

    private final RPLoginRequest loginRequest;
    private String photoFileName;
    private String photoFilePath;

    public RPLoginPresenterImpl(RPILoginView loginView) {
        attachView(loginView);
        loginRequest = new RPLoginRequest();
    }

    public void login(String code) {

        Map<String, String> map = new HashMap<>();
        map.put("code", code);
        loginRequest.login(map, getView(), new RPRxDataCallBack<RPPerson>() {
            @Override
            public void onSucess(RPPerson userBean) {
                ((RPILoginView) getView()).loginSuccess(userBean);
            }

            @Override
            public void onFail() {
                ((RPILoginView) getView()).loginError("");
            }
        });
    }

    public String savePhoto(final RPPerson person, final Bitmap b) {
        if (getView() != null) {
            getView().showLoadingDialog("");
        }
        photoFileName = DateFormat.format("yyyyMMdd_HHmmss", System.currentTimeMillis()) + ".jpg";
        photoFilePath = FileUtils.getAppPath(RPConstants.fileDir) + "/imgpai/" + photoFileName;
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
                            RPILoginView loginView = (RPILoginView) getView();
                            loginView.hideLoadingDialog();
//                            loginView.savePhotoError("照片保存失败");
                        }
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        if (aBoolean) {
                            uploadPhoto(person);
                        } else {
                            RPILoginView view = (RPILoginView) getView();
                            if (view != null) {
                                view.hideLoadingDialog();
//                                view.savePhotoError("照片保存失败");
                            }
                        }
                    }
                });
        return photoFilePath;
    }

    private void uploadPhoto(RPPerson person) {
//        if (userBean == null) {
//            return;
//        }
        Map<String, String> parms = new HashMap<>();
        parms.put("code", person.getCode());
        parms.put("personId", person.getPersonId());//usebean.getid()

        loginRequest.upLoadImage(getView(), parms, photoFilePath, photoFileName, new RPRxDataCallBack<RPPerson>() {
            @Override
            public void onSucess(RPPerson s) {
                if (getView() != null) {
                    ((RPILoginView) getView()).uploadResult(s);
                }
            }

            @Override
            public void onFail() {
                if (getView() != null) {
                    ((RPILoginView) getView()).uploadResult(null);
                }
            }
        });

    }

    /**
     * 登出
     *
     * @param code
     */
    public void quit(String code) {
        Map<String, String> map = new HashMap<>();
        map.put("code", code);
        loginRequest.quit(map, getView(), new RPRxDataCallBack<RPPerson>() {
            @Override
            public void onSucess(RPPerson userBean) {
                ((RPILoginView) getView()).quitResult(userBean);
            }

            @Override
            public void onFail() {
                ((RPILoginView) getView()).quitResult(null);
            }
        });
    }
}
