package workstation.zjyk.line.modle.request;

import workstation.zjyk.line.modle.bean.Person;
import workstation.zjyk.line.modle.callback.RxDataCallBack;
import workstation.zjyk.line.modle.net.ApiManager;
import workstation.zjyk.line.modle.net.ChildHttpResultSubscriber;
import workstation.zjyk.line.ui.views.BaseView;
import workstation.zjyk.line.util.URLBuilder;

import java.io.File;
import java.util.Map;

import rx.Subscription;

/**
 * Created by zjgz on 2017/11/7.
 */

public class LoginRequest {
    public Subscription login(Map<String, String> params, final BaseView baseView, final RxDataCallBack<Person> callBack) {
        return ApiManager.getInstance().get(URLBuilder.GET_PERSON_SCAN, params, new ChildHttpResultSubscriber<Person>(baseView, Person.class) {
            @Override
            protected void _onErrorChild(int code, String error, Throwable e) {
                baseView.hideLoadingDialog();
                callBack.onFail();
            }

            @Override
            public void _onSuccess(Person o) {
                callBack.onSucess(o);
                baseView.hideLoadingDialog();
            }

            @Override
            public void _showLoadingDialog(String message) {
                baseView.showLoadingDialog(message);
            }

        });
    }

    public Subscription quit(Map<String, String> params, final BaseView baseView, final RxDataCallBack<Person> callBack) {
        return ApiManager.getInstance().get(URLBuilder.LOGIN_OUT, params, new ChildHttpResultSubscriber<Person>(baseView, Person.class) {
            @Override
            protected void _onErrorChild(int code, String error, Throwable e) {
                baseView.hideLoadingDialog();
                callBack.onFail();
            }

            @Override
            public void _onSuccess(Person o) {
                callBack.onSucess(o);
                baseView.hideLoadingDialog();
            }

            @Override
            public void _showLoadingDialog(String message) {
                baseView.showLoadingDialog(message);
            }

        });
    }

    public Subscription upLoadImage(final BaseView baseView, Map<String, String> params, String filePath, String photoFileName, final RxDataCallBack<Person> callBack) {
        File file = new File(filePath);

        return ApiManager.getInstance().upLoadImage(URLBuilder.UPLOAD_PHOTO, params, file, photoFileName, new ChildHttpResultSubscriber<Person>(baseView, Person.class) {
            @Override
            public void _onSuccess(Person s) {
                callBack.onSucess(s);
                baseView.hideLoadingDialog();
            }

            @Override
            public void _showLoadingDialog(String message) {
                baseView.showLoadingDialog(message);
            }

            @Override
            protected void _onErrorChild(int code, String error, Throwable e) {
                baseView.hideLoadingDialog();
                callBack.onFail();
            }
        });

    }
}
