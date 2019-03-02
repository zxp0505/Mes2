package com.zjyk.quality.ui.present;

import android.graphics.Bitmap;
import android.text.format.DateFormat;

import com.zjyk.quality.modle.bean.QAPerson;
import com.zjyk.quality.modle.callback.QARxDataCallBack;
import com.zjyk.quality.modle.request.QALoginRequest;
import com.zjyk.quality.ui.views.QABaseView;
import com.zjyk.quality.ui.views.QAILoginView;
import com.zjyk.quality.util.QAConstants;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import cn.com.ethank.mylibrary.resourcelibrary.common_util.FileUtils;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by zjgz on 2017/11/7.
 */

public class QALoginPresenterImpl extends QARxPresent<QAILoginView> {

    private final QALoginRequest loginRequest;
    private String photoFileName;
    private String photoFilePath;

    public QALoginPresenterImpl(QAILoginView loginView) {
        attachView(loginView);
        loginRequest = new QALoginRequest();
    }

    public void login(String code) {

        Map<String, String> map = new HashMap<>();
        map.put("code", code);
        loginRequest.login(map, getView(), new QARxDataCallBack<QAPerson>() {
            @Override
            public void onSucess(QAPerson userBean) {
                ((QAILoginView) getView()).loginSuccess(userBean);
            }

            @Override
            public void onFail() {
                ((QAILoginView) getView()).loginError("");
            }
        });
    }

    public String savePhoto(final QAPerson person, final Bitmap b) {
        if (getView() != null) {
            getView().showLoadingDialog("");
        }
        photoFileName = DateFormat.format("yyyyMMdd_HHmmss", System.currentTimeMillis()) + ".jpg";
        photoFilePath = FileUtils.getAppPath(QAConstants.fileDir) + "/imgpai/" + photoFileName;
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
                            QAILoginView loginView = (QAILoginView) getView();
                            loginView.hideLoadingDialog();
//                            loginView.savePhotoError("照片保存失败");
                        }
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        if (aBoolean) {
                            uploadPhoto(person);
                        } else {
                            QAILoginView view = (QAILoginView) getView();
                            if (view != null) {
                                view.hideLoadingDialog();
//                                view.savePhotoError("照片保存失败");
                            }
                        }
                    }
                });
        return photoFilePath;
    }

    private void uploadPhoto(QAPerson person) {
//        if (userBean == null) {
//            return;
//        }
        Map<String, String> parms = new HashMap<>();
        parms.put("code", person.getCode());
        parms.put("personId", person.getPersonId());//usebean.getid()

        loginRequest.upLoadImage(getView(), parms, photoFilePath, photoFileName, new QARxDataCallBack<QAPerson>() {
            @Override
            public void onSucess(QAPerson s) {
                if (getView() != null) {
                    ((QAILoginView) getView()).uploadResult(s);
                }
            }

            @Override
            public void onFail() {
                if (getView() != null) {
                    ((QAILoginView) getView()).uploadResult(null);
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
        loginRequest.quit(map, getView(), new QARxDataCallBack<QAPerson>() {
            @Override
            public void onSucess(QAPerson userBean) {
                ((QAILoginView) getView()).quitResult(userBean);
            }

            @Override
            public void onFail() {
                ((QAILoginView) getView()).quitResult(null);
            }
        });
    }
}
