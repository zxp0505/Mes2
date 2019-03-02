package workstation.zjyk.workstation.ui.present;

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
import workstation.zjyk.workstation.modle.bean.WSPerson;
import workstation.zjyk.workstation.modle.callback.WSRxDataCallBack;
import workstation.zjyk.workstation.modle.request.WSLoginRequest;
import workstation.zjyk.workstation.ui.views.ILoginView;
import workstation.zjyk.workstation.ui.views.WSBaseView;
import workstation.zjyk.workstation.util.WSConstants;

/**
 * Created by zjgz on 2017/11/7.
 */

public class WSLoginPresenterImpl extends WSRxPresent<ILoginView> {

    private final WSLoginRequest loginRequest;
    private String photoFileName;
    private String photoFilePath;

    public WSLoginPresenterImpl(ILoginView loginView) {
//        attachView(loginView);
        loginRequest = new WSLoginRequest();
    }

    public void login(String code) {

        Map<String, String> map = new HashMap<>();
        map.put("code", code);
        loginRequest.login(map, getView(), new WSRxDataCallBack<WSPerson>() {
            @Override
            public void onSucess(WSPerson userBean) {
                getView().loginSuccess(userBean);
            }

            @Override
            public void onFail() {
                getView().loginError("");
            }
        });
    }

    public String savePhoto(final WSPerson person, final Bitmap b) {
        if (getView() != null) {
            getView().showLoadingDialog("");
        }
        photoFileName = DateFormat.format("yyyyMMdd_HHmmss", System.currentTimeMillis()) + ".jpg";
        photoFilePath = FileUtils.getAppPath(WSConstants.fileDir) + "/imgpai/" + photoFileName;
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
                            ILoginView loginView = getView();
                            loginView.hideLoadingDialog();
//                            loginView.savePhotoError("照片保存失败");
                        }
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        if (aBoolean) {
                            uploadPhoto(person);
                        } else {
                            ILoginView view = getView();
                            if (view != null) {
                                view.hideLoadingDialog();
//                                view.savePhotoError("照片保存失败");
                            }
                        }
                    }
                });
        return photoFilePath;
    }

    private void uploadPhoto(WSPerson person) {
//        if (userBean == null) {
//            return;
//        }
        Map<String, String> parms = new HashMap<>();
        parms.put("code", person.getCode());
        parms.put("personId", person.getPersonId());//usebean.getid()

        loginRequest.upLoadImage(getView(), parms, photoFilePath, photoFileName, new WSRxDataCallBack<WSPerson>() {
            @Override
            public void onSucess(WSPerson s) {
                if (getView() != null) {
                    getView().uploadResult(s);
                }
            }

            @Override
            public void onFail() {
                if (getView() != null) {
                    getView().uploadResult(null);
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
        loginRequest.quit(map, getView(), new WSRxDataCallBack<WSPerson>() {
            @Override
            public void onSucess(WSPerson userBean) {
                getView().quitResult(userBean);
            }

            @Override
            public void onFail() {
                getView().quitResult(null);
            }
        });
    }
}
