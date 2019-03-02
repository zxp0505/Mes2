package com.zjyk.quality.modle.request;

import com.zjyk.quality.modle.bean.QAPerson;
import com.zjyk.quality.modle.callback.QARxDataCallBack;
import com.zjyk.quality.modle.net.QAApiManager;
import com.zjyk.quality.modle.net.QAChildHttpResultObsever;
import com.zjyk.quality.ui.views.QABaseView;
import com.zjyk.quality.util.QAURLBuilder;

import java.util.Map;

/**
 * Created by zjgz on 2017/11/7.
 */

public class QALoginRequest {
    public void login(Map<String, String> params, final QABaseView baseView, final QARxDataCallBack<QAPerson> callBack) {
        QAApiManager.getInstance().get(QAURLBuilder.GET_PERSON_SCAN, params, baseView.<QAPerson>bindToLife(), new QAChildHttpResultObsever<QAPerson>(baseView,QAPerson.class) {
            @Override
            protected void _onErrorChild(int code, String error, Throwable e) {
                baseView.hideLoadingDialog();
                callBack.onFail();
            }

            @Override
            public void _showLoadingDialog(String message) {
                baseView.showLoadingDialog(message);
            }

            @Override
            public void _onSuccess(QAPerson QAPerson) {
                callBack.onSucess(QAPerson);
                baseView.hideLoadingDialog();
            }



        });
    }

    public void quit(Map<String, String> params, final QABaseView baseView, final QARxDataCallBack<QAPerson> callBack) {
        QAApiManager.getInstance().get(QAURLBuilder.LOGIN_OUT, params, baseView.<QAPerson>bindToLife(), new QAChildHttpResultObsever<QAPerson>(baseView, QAPerson.class) {
            @Override
            protected void _onErrorChild(int code, String error, Throwable e) {
                baseView.hideLoadingDialog();
                callBack.onFail();
            }

            @Override
            public void _onSuccess(QAPerson o) {
                callBack.onSucess(o);
                baseView.hideLoadingDialog();
            }

            @Override
            public void _showLoadingDialog(String message) {
                baseView.showLoadingDialog(message);
            }

        });
    }

    public void upLoadImage(final QABaseView baseView, Map<String, String> params, String filePath, String photoFileName, final QARxDataCallBack<QAPerson> callBack) {
//        File file = new File(filePath);
//
//        CommonApiManager.getInstance().upLoadImage(QAURLBuilder.UPLOAD_PHOTO, params, file, photoFileName, new CommonChildHttpResultSubscriber<CommonPerson>(baseView, CommonPerson.class) {
//            @Override
//            public void _onSuccess(CommonPerson s) {
//                callBack.onSucess(s);
//                baseView.hideLoadingDialog();
//            }
//
//            @Override
//            public void _showLoadingDialog(String message) {
//                baseView.showLoadingDialog(message);
//            }
//
//            @Override
//            protected void _onErrorChild(int code, String error, Throwable e) {
//                baseView.hideLoadingDialog();
//                callBack.onFail();
//            }
//        });

    }
}
